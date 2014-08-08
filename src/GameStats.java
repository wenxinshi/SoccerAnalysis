import java.util.ArrayList;

public class GameStats {

	public ArrayList<Element> collection;

	public double averageCary;

	// normally 0.5
	public double range;

	public void parseFromExcel() {

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
}