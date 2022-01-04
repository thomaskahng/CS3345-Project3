package project3;

public class OutsidePrintjob extends Printjob {
	//Class variables
	private static double pricePerPrint = 0.1;
	private int numPages;
	private double price;
	
	//Constructor with inheritance
	public OutsidePrintjob(int pr, int np, String n, String io) {
		super(pr, np, n, io);
		numPages = np;		
		price = numPages * pricePerPrint;
	}
	
	//Inherited method
	public int getJobPriority() {
		return super.getJobPriority();
	}

	//Print part of superclass result plus price
	public String result() {
		String otherInfo = super.result();
		return otherInfo + ", Price: $" + price;
	}
}