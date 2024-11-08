/**
 * QueueADT interface defines the basic methods for a queue.
 * 
 * @param <E> the type of elements in this queue
 */
public interface QueueADT<E> {
    
    /**
     * Adds an element to the end of the queue.
     * 
     * @param element the element to add
     * @throws NullPointerException if the specified element is null
     */
    void enqueue(E element);
    
    /**
     * Removes and returns the front element of the queue.
     * 
     * @return the front element of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    E dequeue();
    
    /**
     * Returns the front element of the queue without removing it.
     * 
     * @return the front element of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    E peek();
    
    /**
     * Checks if the queue is empty.
     * 
     * @return true if the queue is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Returns the number of elements in the queue.
     * 
     * @return the size of the queue
     */
    int size();
}
