import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;

public class GameStats {

	public ArrayList<Element> collection = new ArrayList<Element>();

	// default value
	public double averageReturn;

	// normally 0.004
	public double range = 0.004;

	public String name;

	public Element derivation;

	private int number;


	public void parse(String fileName) {
		try {
			InputStream input = new BufferedInputStream(new FileInputStream(fileName));
			NPOIFSFileSystem fs = new NPOIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs.getRoot(),true);
			HSSFSheet sheet = wb.getSheetAt(1);
			name = sheet.getSheetName();

			Iterator rows = sheet.rowIterator();

			for (int i = 0; i < 4; i++)
				rows.next();
			HSSFRow row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			for (int i = 0; i < 7; i++)
				cells.next();
			HSSFCell cell = (HSSFCell) cells.next();
			averageReturn = Double.parseDouble(cell.toString());

			rows.next();

			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				cells = row.cellIterator();
				Element element = new Element();
				for (int i = 0; i < 7; i++)
					cells.next();
				element.returnPercentage = Double.parseDouble(((HSSFCell) cells
						.next()).toString());
				element.winRate = 100 * Double.parseDouble(((HSSFCell) cells
						.next()).toString());
				element.drawRate = 100 * Double.parseDouble(((HSSFCell) cells
						.next()).toString());
				element.lossRate = 100 * Double.parseDouble(((HSSFCell) cells
						.next()).toString());
				collection.add(element);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// calculate the derivation
	private Element calculateDerivation(ArrayList<Element> c, Element aver) {
		aver.average(number);
		Element derivation = new Element();
		for (int i = 0; i < number; i++) {
			Element e = c.get(i);
			derivation.winRate += Math.pow(e.winRate - aver.winRate, 2);
			derivation.drawRate += Math.pow(e.drawRate - aver.drawRate, 2);
			derivation.lossRate += Math.pow(e.lossRate - aver.lossRate, 2);
		}
		derivation.average(number);
		derivation.standard();

		return derivation;
	}

	public void CaryDerivation() {
		ArrayList<Element> rangedCollection = new ArrayList<Element>();

		Element aver = new Element();
		// System.out.println(collection.size());
		for (int i = 0; i < collection.size(); i++) {
			Element e = collection.get(i);
			if (e.returnPercentage <= averageReturn + range
					&& e.returnPercentage >= averageReturn - range) {
				rangedCollection.add(e);
				aver.returnPercentage += e.returnPercentage;
				aver.winRate += e.winRate;
				aver.drawRate += e.drawRate;
				aver.lossRate += e.lossRate;
			}
		}

		number = rangedCollection.size();
		derivation = calculateDerivation(rangedCollection, aver);

		printCary();

	}

	public void printCary() {
		System.out.println("Analysis results:\n");
		System.out.println(name);
		System.out.println("average return percentage = " + averageReturn);
		System.out.println("\nrange = " + range + "\tnumber = " + number);
		System.out.println("win rate = " + derivation.winRate);
		System.out.println("draw rate = " + derivation.drawRate);
		System.out.println("loss rate = " + derivation.lossRate);
	}
}
