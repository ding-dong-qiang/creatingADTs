package src.utilities;

import java.io.Serializable;

/**
 * StackADT.java
 * 
 * @author Haonan Ye
 * @version 1.0
 * 
 * Class Definition: This interface represents the public contract for the
 * implementation of Stack for the Assignment 2. This data type will store 
 * data in a Last-In-First-Out (LIFO) order.
 * 
 * @param <E> The type of element this stack holds.
 */

public interface StackADT<E> extends Serializable {
    
    /**
     * The size method will return the current element count contained in the
     * stack.
     * 
     * @return The current element count.
     */
    public int size();
    
    /**
     * Removes all of the elements from this stack. This stack will be empty
     * after this call returns.
     */
    public void clear();
    
    /**
     * Pushes an element onto the top of the stack.
     * 
     * @param toPush The element to be pushed onto this stack.
     * @return <code>true</code> if the element is pushed successfully.
     * @throws NullPointerException If the specified element is
     *                              <code>null</code>
     *                              and the stack implementation does not
     *                              support having <code>null</code>
     *                              elements.
     */
    public boolean push(E toPush) throws NullPointerException;
    
    /**
     * Pushes all elements from another stack onto the top of this stack.
     * The elements are pushed in the order they are returned from the
     * specified stack's iterator.
     * 
     * @param toPush The stack to push onto this stack.
     * @return <code>true</code> if the elements are pushed successfully.
     * @throws NullPointerException If the specified stack is
     *                              <code>null</code>
     *                              and the stack implementation does not
     *                              support having <code>null</code>
     *                              elements.
     */
    public boolean pushAll( StackADT<? extends E> toPush ) throws NullPointerException;
    
    /**
     * Pushes all elements from another stack onto the top of this stack.
     * The elements are pushed in the reverse order they are returned from the
     * specified stack's iterator.
     * 
     * @param toPush The stack to push onto this stack.
     * @return <code>true</code> if the elements are pushed successfully.
     * @throws NullPointerException If the specified stack is
     *                              <code>null</code>
     *                              and the stack implementation does not
     *                              support having <code>null</code>
     *                              elements.
     */
    public boolean pushAllReversed( StackADT<? extends E> toPush ) throws NullPointerException;
    
    /**
     * Removes the element at the top of the stack.
     * 
     * @return The element at the top of the stack.
     * @throws NullPointerException If the stack is empty.
     */
    public E pop() throws NullPointerException;
    
    /**
     * Retrieves, but does not remove, the element at the top of the stack.
     * 
     * @return The element at the top of the stack.
     * @throws NullPointerException If the stack is empty.
     */
    public E peek() throws NullPointerException;
    
    /**
     * Returns <code>true</code> if this stack contains no elements.
     * 
     * @return <code>true</code> if this stack contains no elements.
     */
    public boolean isEmpty();
    
    /** 
     * Returns an array containing all of the elements in this stack in proper
     * sequence (from top to bottom).
     * 
     * @return An array containing all of the elements in this stack in proper
     *         sequence.
     */
    public Object[] toArray();
    
    /**
     * Returns an iterator over the elements in this stack, in proper sequence.
     * 
     * @return An iterator over the elements in this stack, in proper sequence.
     */
    public Iterator<E> iterator();
}
