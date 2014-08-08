public class Element {
	public double returnPercentage;
	public double winRate;
	public double drawRate;
	public double lossRate;

	public Element(double r, double w, double d, double l) {
		returnPercentage = r;
		winRate = w;
		drawRate = d;
		lossRate = l;
	}
	

	public Element() {
		returnPercentage = 0;
		winRate = 0;
		drawRate = 0;
		lossRate = 0;
	}

	public void average(int n) {
		returnPercentage /= n;
		winRate /= n;
		drawRate /= n;
		lossRate /= n;
	}
	
	public String toString(){
		return returnPercentage+" "+winRate+" "+drawRate+" "+lossRate;
	}
}