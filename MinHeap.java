/*
 * Written by Chris Welch CSCE 146-007
 */

// Defines a MinHeap data structure.  Written as generic type T that extends Comparable
public class MinHeap <T extends Comparable<T>>{

	// Declares type T array as the heap
	protected T[] heap;
	// Sets default size of MinHeap tree.  2^7 used so the tree is balanced
	public static final int DEF_SIZE = 128;
	// lastIndex tracks the first null element in the tree based on breadth traversal
	protected int lastIndex;
	
	// Default constructor
	public MinHeap()
	{
		init(DEF_SIZE);
	}
	
	// parameterized constructor
	public MinHeap(int size)
	{
		init(size);
	}
	
	// initializer method for the constructors
	private void init(int size)
	{
		if(size > 0)
		{
			heap = (T[])(new Comparable[size]);
			lastIndex = 0;
		}
		else
		{
			init(DEF_SIZE);
		}
	}
	
	/*
	 * Method for adding data to the array tree.  All values are added to the end of the array/first null index.
	 * After the data is added, the data will be compared to its parents and moved up the tree as needed.  Since
	 * this is a minimum heap, the smallest data goes up to the root
	 */
	public void add(T aData)
	{
		// If the array is full, return because there is no where to insert the new data
		if(lastIndex >= heap.length) {
			return;
		}
		// Add the data to the first null index
		heap[lastIndex] = aData;
		// Call the method to move the new data upwards in the tree if needed
		bubbleUp();
		// Increment last index so it points to the next array location which contains null
		lastIndex++;
	}
	
	// Method to move newly added data up the tree if the newly added value is less than its parents
	private void bubbleUp()
	{
		// copy lastIndex for modification as needed to move data upwards
		int index = lastIndex;
		/*
		 * The loop will run until either 1) the index is at zero meaning the top of the tree is reached or 2) the child data is larger
		 * than all parents up to the root.  Since its a MinHeap, the root holds the smallest value.
		 */
		while(index > 0 && heap[(index-1)/2].compareTo(heap[index]) > 0)
		{
			/*
			 * (index-1)/2 takes the current index and calculates the parent.  Since its integer math, only whole numbers result.
			 * If the parent is greater than the child, the child and parent swap locations
			 */
			T temp = heap[(index-1)/2];
			heap[(index-1)/2] = heap[index];
			heap[index] = temp;
			// After a swap is done, index is updated to the parents value and the loop continues
			index = (index-1)/2;
		}
	}
	 
	/*
	 * The remove method for a MinHeap always removes the root and moves the last value on the tree into the root's position.
	 * Once moved, the bubbleDown method will look for children of smaller values and perform swaps until the smallest value
	 * is at the root again, maintaining the MinHeap
	 */
	public T remove()
	{
		// If lastIndex is zero, there is nothing in the heap and therefore nothing to remove
		if(lastIndex == 0)
		{
			return null;
		}
		// Save a copy of the root data to be returned later to the calling method
		T ret = heap[0];
		// Take the last value of the tree and put it in the root position, regardless of value
		heap[0] = heap[lastIndex-1];
		// Make the position which just got moved to root to null
		heap[lastIndex-1] = null;
		// Adjust lastIndex to account for the earlier swap
		lastIndex--;
		// bubbleDown will start at the root and move the data down if its not the smallest data in the tree
		bubbleDown();
		// return the original root to the calling method
		return ret;
	}
	
	// Method to move tree values down after a remove operation if required
	private void bubbleDown()
	{
		// Operation starts at the root
		int index = 0;
		/*
		 * While loop runs while the calculated child index is less than lastIndex meaning its still 
		 * a valid value for the array.  This prevents out of bounds errors
		 */
		while(index*2+1 < lastIndex)
		{
			// Calculates the index for the left child of the current node assuming its the smaller value
			int smallIndex = index*2+1;
			/*
			 * Checks to see if the index of the right child is a valid value for the array length (with data)
			 * and checks to see if the left child or right child is bigger.  If left child minus right child
			 * is positive, then the left child is larger tan right and therefore smallestIndex is updated
			 */
			if(index*2+2 < lastIndex && heap[index*2+1].compareTo(heap[index*2+2]) > 0)
			{
				smallIndex = index*2+2;
			}
			/*
			 * Compares the current index to the smaller child index.  If current - child = +, then the child
			 * is smaller and the swap occurs.  After, the index is updated to the child value and the process repeated
			 * If not, the else breaks the while loop and the swapping is done.
			 */
			if(heap[index].compareTo(heap[smallIndex]) > 0)
			{
				T temp = heap[index];
				heap[index] = heap[smallIndex];
				heap[smallIndex] = temp;
			}
			else
			{
				break;
			}
			index = smallIndex;
		}
	}
	
	// Prints the MinHeap array until a null value is hit
	public void print()
	{
		for(T h : heap)
		{
			if(h == null)
			{
				break;
			}
			System.out.println(h);
		}
	}
	
	/*
	 * Unused for this particular problem, provides a means to sort using MinHeap's add and remove.
	 * Bound by the compareTo method in terms of how it sorts
	 */
	public void heapSort(T[] array)
	{
		if(array == null)
		{
			return;
		}
		MinHeap<T> mHeap = new MinHeap<T>(array.length);
		for(int i = 0; i < array.length; i++)
		{
			mHeap.add(array[i]);
		}
		for(int i = 0; i < array.length; i++)
		{
			array[i] = mHeap.remove();
		}
	}
	
	
} // close class
