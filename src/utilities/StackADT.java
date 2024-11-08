/**
 * StackADT interface defines the basic methods for a stack.
 * 
 * @param <E> the type of elements in this stack
 */
public interface StackADT<E> {
    
    /**
     * Adds an element to the top of the stack.
     * 
     * @param element the element to add
     * @throws NullPointerException if the specified element is null
     */
    void push(E element);
    
    /**
     * Removes and returns the top element of the stack.
     * 
     * @return the top element of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    E pop();
    
    /**
     * Returns the top element of the stack without removing it.
     * 
     * @return the top element of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    E peek();
    
    /**
     * Checks if the stack is empty.
     * 
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Returns the number of elements in the stack.
     * 
     * @return the size of the stack
     */
    int size();
}
