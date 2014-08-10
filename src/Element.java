
public class Element {
	public double returnPercentage;
	public double winRate;
	public double drawRate;
	public double lossRate;
	
	public Element(double r, double w, double d, double l){
		returnPercentage = r;
		winRate = w;
		drawRate = d;
		lossRate = l;
	}
	
	public Element(){
		returnPercentage = 0;
		winRate = 0;
		drawRate = 0;
		lossRate = 0;
	}
	
	public void average(double n){
		returnPercentage /= n;
		winRate /= n;
		drawRate /= n;
		lossRate /= n;
	}
	
	public void standard(){
		winRate = Math.sqrt(winRate);
		drawRate = Math.sqrt(drawRate);
		lossRate = Math.sqrt(lossRate);

	}
	
	
}
