package utilities;

/**
 * QueueADT interface defining the operations of a generic queue.
 *
 * @param <T> the type of elements in the queue
 */
public interface QueueADT<T>
{
	/**
	 * Constructor method to create a new queue. Precondition: None. Postcondition:
	 * A stack object is created and with a specified size.
	 *
	 * @param : the size of the new queue.
	 */
	public void create( int size );

	/**
	 * Adds an element to the end of the queue.
	 *
	 * @param element the element to be added to the queue
	 * @pre element != null
	 * @post the element is added to the end of the queue
	 * @throws IllegalArgumentException if {@code element} is null
	 */
	public void enqueue( T element ) throws IllegalArgumentException;

	/**
	 * Removes and returns the front element of the queue.
	 *
	 * @return the element removed from the front of the queue
	 * @pre the queue is not empty
	 * @post the front element is removed from the queue
	 * @throws java.util.NoSuchElementException if the queue is empty
	 */
	public T dequeue() throws java.util.NoSuchElementException;

	/**
	 * Returns the front element of the queue without removing it.
	 *
	 * @return the front element of the queue
	 * @pre the queue is not empty
	 * @post the queue remains unchanged
	 * @throws java.util.NoSuchElementException if the queue is empty
	 */
	public T front() throws java.util.NoSuchElementException;

	/**
	 * Checks if the queue is empty.
	 *
	 * @return true if the queue is empty, false otherwise
	 * @pre none
	 * @post the queue remains unchanged
	 */
	public boolean isEmpty();

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return the size of the queue
	 * @pre none
	 * @post the queue remains unchanged
	 */
	public int size();

	/**
	 * Removes all elements from the queue.
	 *
	 * @pre none
	 * @post the queue is empty
	 */
	public void clear();
}
