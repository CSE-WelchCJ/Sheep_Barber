/*
 * Written by Chris Welch CSCE 146-007
 */

import java.io.File;
import java.util.Scanner;

public class SheepScheduler {

	// Decalre the MinHeap to hold and priortize the sheep whom have arrived
	protected static MinHeap<Sheep> sheepList;
	// Declare an array to hold the file contents and allow for sorting
	protected static Sheep[] arrival;
	// Declare a queue to hold the line of sheep waiting to join the MinHeap
	protected static ArrayQueue<Sheep> arrivalLine;
	// Declare a queue to hold the sheep who were removed from the MinHeap when shearing done
	protected static ArrayQueue<Sheep> exitLine;
	// Declare variable to hold the sheep object currently being sheared
	protected static Sheep currentSheep;
	// Declare variable to track current minute of time for simulation
	protected static int currentMinute;
	// Declare constants needed for constructor and delimiter
	public static final int DEF_INT = 0;
	public static final String DELIM = "\t";
	
	// Default constructor
	public SheepScheduler()
	{
		sheepList = new MinHeap<Sheep>();
		arrivalLine = new ArrayQueue<Sheep>();
		exitLine = new ArrayQueue<Sheep>();
		// Current sheep goes to null at construction to allow for first sheep to move straight to shearing
		// Setters not used since both only go to default values
		currentSheep = null;
		currentMinute = DEF_INT;
	}
	
	// Getters for instance variables 
	public Sheep getCurrentSheep()
	{
		return currentSheep;
	}
	
	public int getCurrentMinute()
	{
		return currentMinute;
	}
	
	// Method to add sheep either to the currentSheep spot or the MinHeap if currentSheep is currently occupied
	public void addSheep(Sheep aNewSheep)
	{
		if(currentSheep == null)
		{
			currentSheep = aNewSheep;
		}
		else
		{
			sheepList.add(aNewSheep);
		}
	}
	
	// Method to advance time 1 minute for the simulation
	public void advanceOneMinute()
	{
		// Advances timers by 1 minute
		currentMinute = currentMinute + 1;
		currentSheep.shearForOneMinute();
		// If current Sheep is done, puts current sheep into the exit queue and removes the next sheep from the heap
		if(currentSheep.isDone())
		{
			//currentSheep = sheepList.remove();
			exitLine.enqueue(currentSheep);
			currentSheep = sheepList.remove();
		}
	}
	
	// Method to check if currentSheep is empty.  This will only be true when all sheep are done
	public boolean isDone()
	{
		if(currentSheep == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Method to read the sheep file.  Its read twice, first to determine the needed size of all arrays and second to get the data
	public void readFile(String aFilename)
	{
		// sheepCount will be used to figure out how big the arrays need to be.  variable tracks number of valid lines in the file
		int sheepCount = 0;
		try
		{
			// Read file line by line
			Scanner fileScanner = new Scanner(new File("./"+aFilename));
			while(fileScanner.hasNextLine())
			{
				// variables to contain the different data which may be encountered
				String name = "none";
				int shearingTime = 0;
				int arrivalTime = 0;
				// Capture the file line as a string
				String fileLine = fileScanner.nextLine();
				// Split the file line into an array based on tab delimiter
				String[] splitLine = fileLine.split(DELIM);
				// The first part should be the shape name
				if(splitLine.length != 3)
				{
					continue;
				}
				try
				{
					name = splitLine[0];
					shearingTime = Integer.parseInt(splitLine[1]);
					arrivalTime = Integer.parseInt(splitLine[2]);
				}
				catch(Exception e)
				{
					continue;
				}
				// All file lines which were valid will increment the sheepCount.  Invalid lines will not
				sheepCount = sheepCount + 1;
			}
			fileScanner.close();
			// The sheepCount is used to make the initial array, arrivalLine array, and exit array of the correct size
			arrival = new Sheep[sheepCount];
			arrivalLine = new ArrayQueue(sheepCount);
			exitLine = new ArrayQueue(sheepCount);
			// File is read again this time to get the data from all valid lines
			fileScanner = new Scanner(new File("./"+aFilename));
			// index will be used to enter read data into the arrival array which is initially unsorted
			int index = 0;
			while(fileScanner.hasNextLine())
			{
				// variables to contain the different data which may be encountered
				String name = "none";
				int shearingTime = 0;
				int arrivalTime = 0;
				// Capture the file line as a string
				String fileLine = fileScanner.nextLine();
				// Split the file line into an array based on tab delimiter
				String[] splitLine = fileLine.split(DELIM);
				// The first part should be the shape name
				if(splitLine.length != 3)
				{
					continue;
				}
				try
				{
					name = splitLine[0];
					shearingTime = Integer.parseInt(splitLine[1]);
					arrivalTime = Integer.parseInt(splitLine[2]);
				}
				catch(Exception e)
				{
					continue;
				}
				// Data from file is used to make a new sheep object
				Sheep temp = new Sheep(name, shearingTime, arrivalTime);
				// Sheep data is entered into the arrival array
				arrival[index] = temp;
				// index is incremented so the next iteration adds data to the next array index
				index = index + 1;
			}
			fileScanner.close();
			// quickSort is used to sort the arrival array based on the arrival time protion of Sheep
			quickSort(arrival);
			/*
			 * For loop moves the now sorted data from arrival array into the arrivalLine Queue using the enqueue method.
			 * The sheep will wait in this line until simulation current time equals their arrival time.
			 */
			for(int i = 0; i < arrival.length; i++)
			{
				arrivalLine.enqueue(arrival[i]);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Prints the exitLine Queue showing how the sheep shearing was executed
	public void printSchedule()
	{
		exitLine.print();
	}
	
	// quickSort and partition methods to allow for quickSorting of arrival array
	public static void quickSort(Sheep[] a)
	{
		quickSort(a,0,a.length-1);
	}
	
	public static void quickSort(Sheep[] a, int start, int end)
	{
		if(start >= end)
		{
			return;
		}
		int pivot = partition(a, start, end);
		quickSort(a,start,pivot-1);
		quickSort(a,pivot+1, end);
	}
	
	public static int partition(Sheep[] a, int start, int end)
	{
		int pivot = a[end].getArrivalTime();
		int i = start;
		for(int j = start; j <= end; j++)
		{
			if(a[j].getArrivalTime() < pivot)
			{
				Sheep temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				i++;
			}
		}
		Sheep temp = a[i];
		a[i] = a[end];
		a[end] = temp;
		return i;
	}
	
	// Runs the clock simulation allowing sheep to be sheared, enter the heap, and leave the heap
	public void runSim()
	{
		// Declare variables to track which minute is current and how many sheep are in the mix
		int count = 0;
		int currSheep = 0;
		// While loop is true always.  a break will be needed to exit this loop.
		while(true)
		{
			// IF checks if all sheep waiting in the arrivalLine Queue have entered the heap.
			if(currSheep < arrival.length)
			{
				/*
				 * boolean and while loop will allow for more than one sheep to enter the heap during a minute
				 * in the case where more than one sheep have the same arrival time
				 */
				boolean keepGoing = true;
				while(keepGoing)
				{
					if(arrivalLine.peek().getArrivalTime() == count)
					{
						// Adds a sheep to the heap by dequeueing from arrivalLine
						this.addSheep(arrivalLine.dequeue());
						// tracks how many sheep have been added to the heap
						currSheep++;
					}
					// ELSE ends the while loop when all of the sheep arriving now have entered the heap
					else
					{
						keepGoing = false;
					}
				}
			}
			/*
			 * This IF will end the over arching loop if the number in sheep in play equals the original amount
			 * loaded into the queue and there are no sheep being currently sheared
			 */
			if(isDone() && currSheep == arrival.length)
			{
				break;
			}
			// Advances the sheep related data forward by one minute
			advanceOneMinute();
			// Advances the minute clock before repeating the loop
			count++;
		}
	}
} // Close class
