import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class GameStats {

	public ArrayList<Element> collection;

	public double averageCary;

	// normally 0.5
	public double range;

	public void parseFromExcel() {

	}

	public void parse(String fileName) {
		try {
			InputStream input = new BufferedInputStream(new FileInputStream(
					fileName));
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			
			for(int i=0;i<4;i++)
				rows.next();
			HSSFRow row=(HSSFRow) rows.next();
			Iterator cells =row.cellIterator();
			for(int i=0;i<7;i++)
				cells.next();
			HSSFCell cell=(HSSFCell) cells.next();
			averageCary=Double.parseDouble(cell.toString());
			
			rows.next();
			
			while (rows.hasNext()) {
				 row = (HSSFRow) rows.next();
				 cells = row.cellIterator();
				 Element element=new Element();
				 for(int i=0;i<7;i++)
					 cells.next();
				 element.returnPercentage=Double.parseDouble(((HSSFCell) cells.next()).toString());
				 element.winRate=Double.parseDouble(((HSSFCell) cells.next()).toString());
				 element.drawRate=Double.parseDouble(((HSSFCell) cells.next()).toString());
				 element.lossRate=Double.parseDouble(((HSSFCell) cells.next()).toString());
				 collection.add(element);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private double calculateDerivation(ArrayList<Element> c, Element aver) {
		int n = c.size();
		aver.average(n);
		Element squareSum = new Element();
		for (int i = 0; i < n; i++) {
			Element e = c.get(i);
			squareSum.winRate += Math.pow(e.winRate - aver.winRate, 2);
			squareSum.drawRate += Math.pow(e.drawRate - aver.drawRate, 2);
			squareSum.lossRate += Math.pow(e.lossRate - aver.lossRate, 2);

		}

		return 0;
	}

	public void CaryDerivation() {
		ArrayList<Element> rangedCollection = new ArrayList<Element>();

		Element aver = new Element();
		for (int i = 0; i < collection.size(); i++) {
			Element e = collection.get(i);
			if (e.returnPercentage <= averageCary + range
					&& e.returnPercentage >= averageCary - range) {
				rangedCollection.add(e);
				aver.returnPercentage += e.returnPercentage;
				aver.winRate += e.winRate;
				aver.drawRate += e.drawRate;
				aver.lossRate += e.lossRate;
			}
		}

	}

	public static void main(String[] args) {
		GameStats test = new GameStats();
		test.parse("1.xls");
	}
}