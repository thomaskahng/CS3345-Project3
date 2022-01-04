package project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    //Construct the binary heap.
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }
    
    //Construct the binary heap given an array of items.
    public BinaryHeap(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for (AnyType item : items)
            array[i++] = item;
        buildHeap();
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        // Percolate up
        int hole = ++currentSize;
        for (array[0] = x; x.compareTo( array[hole / 2] ) < 0; hole /= 2)
            array[hole] = array[hole / 2];
        array[hole] = x;
    }

    private void enlargeArray(int newSize) {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];        
    }
    
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin() throws Exception {
        if (isEmpty())
            throw new Exception();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin() throws Exception {
        if (isEmpty())
            throw new Exception();

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    //Make the priority queue logically empty.
    public void makeEmpty() {
        currentSize = 0;
    }
    private static final int DEFAULT_CAPACITY = 10;

    //Number of elements in heap
    private int currentSize;   
    //The heap array
    private AnyType[] array;

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize &&
                    array[child + 1].compareTo(array[ child ]) < 0)
                child++;
            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }
}

public class Printer {
	public static void main(String[] args) throws Exception {
		BinaryHeap<Printjob> heap = new BinaryHeap<Printjob>();
		List<String> results = new ArrayList<String>();
		List<Integer> priorities = new ArrayList<Integer>();
		
		//See if file is found
		BufferedReader br = null; 
		try {
			br = new BufferedReader(new FileReader("input.txt"));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		//Each line of file stored
		List<String> lines = new ArrayList<String>();
		
		//Store names, priorities, number of pages, and indicator if inside our outside print
		List<String> names = new ArrayList<String>();
		List<Integer> userPriorities = new ArrayList<Integer>();
		List<Integer> numPages = new ArrayList<Integer>();
		List<String> inOrOut = new ArrayList<String>();
		
		//Read as long as no IOException
		try {
			//Iterate each line in file
			for (String line; (line = br.readLine()) != null;) {
				//New line, add to lines and split elements by tab
				lines.add(line);
				String[] elems = line.split("\t");
				
				//Convert to datatypes if possible and add to array lists
				names.add(elems[0]);
				userPriorities.add(Integer.parseInt(elems[1]));
				numPages.add(Integer.parseInt(elems[2]));
				inOrOut.add(elems[3]);
			} 
			for (int i=0; i<lines.size(); i++) {		
				//Elements of each line
				int priority = userPriorities.get(i);
				int pages = numPages.get(i);
				String name = names.get(i);
				String point = inOrOut.get(i);
				
				//Store possible job or outside job result and priority value
				Printjob job = new Printjob(priority, pages, name, point);
				OutsidePrintjob outJob = new OutsidePrintjob(priority, pages, name, point);
				
				//See to store outside job or normal job
				if (point.equals("I")) 
					heap.insert(job);				
				else if (point.equals("O"))
					heap.insert(outJob);			
			}
			while (!heap.isEmpty()) {
				//Get first job in heap
				Printjob job = (Printjob) heap.deleteMin();
				
				//Result if job is an outside job
				if (job instanceof OutsidePrintjob) {
					OutsidePrintjob outJob = (OutsidePrintjob) job;
					System.out.println(outJob.result());
				}
				else
					System.out.println(job.result());
			}
			
		}
		catch (IOException e) {
			System.out.println("Cannot read file");
		}
	}
}
