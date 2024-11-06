/**
 * 
 */
package utilities;

/**
 * @param <T>
 * 
 */
public interface StackADT<T> {
	/**
	 * Constructor method to create a new stack.
	 *
	 * Precondition: None.
	 *
	 * Postcondition: A stack object is created and with a specified size.
	 *
	 * @param : the size of the new stack.
	 */
	public void create(int size);
	
	
	/**
     * Pushes an item onto the stack.
     *
     * Precondition: The stack is not full.
     * 
     * Postcondition: The item is added to the top of the stack.
     *
     * @param item The item to be pushed onto the stack.
     */
    public void push(T item);

    /**
     * Pops an item from the stack.
     *
     * Precondition: The stack is not empty.
     * 
     * Postcondition: The item is removed from the top of the stack.
     *
     * @return The item removed from the stack.
     */
    public T pop();

    /**
     * Peeks at the top item of the stack without removing it.
     *
     * Precondition: The stack is not empty.
     * 
     * Postcondition: The top item of the stack is returned without removal.
     *
     * @return The top item of the stack.
     */
    public T peek();

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of items currently in the stack.
     *
     * @return The size of the stack.
     */
    public int size();

}
