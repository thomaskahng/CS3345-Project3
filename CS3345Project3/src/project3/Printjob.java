package project3;

public class Printjob implements Comparable<Printjob> {
	//Class variables
	private int priority;
	private int numPages;
	private String name;
	private String inOrOut;
	
	//Constructor
	public Printjob(int pr, int np, String n, String io) {
		priority = pr;
		numPages = np;
		name = n;
		inOrOut = io;
	}
	
	
	//Calculate priority
	public int getJobPriority() {
		return priority * numPages;
	}
	
	
	//Print result
	public String result() {
		String result = "Name: " + name + ", Priority: " + priority + ", Pages: " + numPages;
		return result;
	}

	//compareTo method of Printjob objects
	@Override
	public int compareTo(Printjob job) {
		//Priorities of objects
		int priority = this.getJobPriority();
		int priority2 = job.getJobPriority();
		
		//See if less than, greater than, or equal to
		if (priority < priority2)
			return -1;
		else if (priority == priority2)
			return 1;
		else
			return 0;
	}
}