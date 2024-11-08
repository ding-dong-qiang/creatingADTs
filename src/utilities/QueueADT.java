/**
 * 
 */
package utilities;

import java.util.NoSuchElementException;

/**
 * 
 * @param <T> The type of elements in the queue
 */
public interface QueueADT<T> {
	
	/**
	 * Constructor method to create a new queue.
	 *
	 * Precondition: None.
	 *
	 * Postcondition: A stack object is created and with a specified size.
	 *
	 * @param : the size of the new queue.
	 */
	public void create(int size);
	/**
     * Adds an item to the back of the queue.
     *
     * @param item The item to be added to the queue.
     * @throws IllegalArgumentException if the item is null.
     * @throws IllegalStateException if the queue is full.
     * Preconditions: The item is not full.
     * Postcondition: The item is added to the end of the queue.
     */
    public void enqueue(T item);

    /**
     * Removes and returns the item at the front of the queue.
     *
     * @return The item at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     * precondition: The queue is not empty
     * Postcondition: The front item is removed from the queue.
     */
    public T dequeue() throws NoSuchElementException;

    /**
     * Returns the item at the front of the queue without removing it.
     *
     * @return The item at the front of the queue.
     * @throws EmptyQueueException if the queue is empty.
     * precondition: The queue is not empty
     * Post-condition: The queue remains unchanged.
     */
    public T front() throws NoSuchElementException;

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return The size of the queue.
     */
    int size();

}
