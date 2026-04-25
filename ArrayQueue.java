/*/
 * Written by Chris Welch CSCE 146-007
 */


public class ArrayQueue <T> {

	// Default size 
	public static final int DEF_SIZE = 200;
	// Declares the array to hold the queue (generic type) and the required tail and head index needed for a queue
	protected T[] queue;
	protected int headIndex;
	protected int tailIndex;
	
	// default constructor calls the initializer using default array size
	public ArrayQueue()
	{
		init(DEF_SIZE);
	}
	
	// Parameterized constructor which uses an size argument.  Later this will be defined by the file that is read in
	public ArrayQueue(int aSize)
	{
		init(aSize);
	}
	
	// Initializer method for arrays to help save retyping code in constructors
	public void init(int aSize)
	{
		// An empty list defines head and tail at the same array index
		headIndex = tailIndex = 0;
		// Checks for valid array sizes
		if(aSize <= 0)
		{
			/*
			 * constructs the queue of the specified type. type cast is required here since object type is not known in advance
			 * since using a generic array queue.  If aSize is not valid uses the default size.
			 */
			queue = (T[])(new Object[DEF_SIZE]);
		}
		else
		{
			// constructs the queue of the specified type with type casting.  Size is based on input argument
			queue = (T[])(new Object[aSize]);
		}
	}
	
	
	// method to add data to the queue.  All data enters at the end of the line
	public void enqueue(T aData)
	{
		/*
		 * Checks if the line is full.  If the headIndex is not null and the tailIndex (which is always one index past the last
		 * data in the line) equals headIndex, then the array is full and new data cannot be added. 
		 */
		if(queue[headIndex] != null && headIndex == tailIndex)
		{
			return;
		}
		// adds the data to the tailIndex
		queue[tailIndex] = aData;
		/*
		 * increments the tailIndex to the next index.  The modulus operator causes this to wrap back around to zero if the tailIndex
		 * was at the arrays last index. 
		 */
		tailIndex = (tailIndex+1)%queue.length;
	}
	
	// removes the data at the head of the line and then updates where the head is
	public T dequeue()
	{
		// makes a copy of the data at the head index 
		T ret = queue[headIndex];
		// updates the head index to move to the next in line.  Modulus allows the head to wrap back to the 0 index if at the end
		headIndex = (headIndex+1)%queue.length;
		// returns the data to calling method
		return ret;
	}
	
	// returns the data at the head of the line without changing the order of the line
	public T peek()
	{
		return queue[headIndex];
	}
	
	// prints out the queue
	public void print()
	{
		/*
		 * Prints the data at the head.  This needs to be done before the FOR loop because of how the modulus operator
		 */
		System.out.println(queue[headIndex]);
		/*
		 * The FOR loop starts at head+1 (modulus cause it wrap back to 0 if head was at last index).  It will run until
		 * the iterator reaches the tailIndex (end of line).  The increment must also use the modulus operator so that the 
		 * iterator can loop back to 0 if the end of the list is reached before tailIndex.
		 */
		for(int i = (headIndex + 1)%queue.length; i != tailIndex; i = (i+1)%queue.length)
		{
			System.out.println(queue[i]);
		}
	}
	
} // close class
