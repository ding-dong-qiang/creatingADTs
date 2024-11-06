package utilities;

/**
 * StackADT interface defining the operations of a generic stack.
 *
 * @param <T> the type of elements in the stack
 */
public interface StackADT<T>
{
	/**
	 * Pushes an element onto the top of the stack.
	 *
	 * @param element the element to be added to the stack
	 * @pre element != null
	 * @post the element is added to the top of the stack
	 * @throws IllegalArgumentException if {@code element} is null
	 */
	public void push( T element ) throws IllegalArgumentException;

	/**
	 * Removes and returns the top element of the stack.
	 *
	 * @return the element removed from the top of the stack
	 * @pre the stack is not empty
	 * @post the top element is removed from the stack
	 * @throws java.util.NoSuchElementException if the stack is empty
	 */
	public T pop() throws java.util.NoSuchElementException;

	/**
	 * Returns the top element of the stack without removing it.
	 *
	 * @return the top element of the stack
	 * @pre the stack is not empty
	 * @post the stack remains unchanged
	 * @throws java.util.NoSuchElementException if the stack is empty
	 */
	public T peek() throws java.util.NoSuchElementException;

	/**
	 * Checks if the stack is empty.
	 *
	 * @return true if the stack is empty, false otherwise
	 * @pre none
	 * @post the stack remains unchanged
	 */
	public boolean isEmpty();

	/**
	 * Returns the number of elements in the stack.
	 *
	 * @return the size of the stack
	 * @pre none
	 * @post the stack remains unchanged
	 */
	public int size();

	/**
	 * Removes all elements from the stack.
	 *
	 * @pre none
	 * @post the stack is empty
	 */
	public void clear();
}
