package implementations;

import utilities.StackADT;
import utilities.Iterator;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> stackList;  // Use MyArrayList for underlying storage

    // Constructor to initialize the stack
    public MyStack() {
        stackList = new MyArrayList<>();
    }

    // Push an item onto the stack
    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the stack.");
        }
        stackList.add(toAdd);  // Add the element at the end of the list
    }

    // Pop the top item from the stack
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();  // Throw exception if stack is empty
        }
        return stackList.remove(stackList.size() - 1);  // Remove and return the last element
    }

    // Peek the top item of the stack without removing it
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();  // Throw exception if stack is empty
        }
        return stackList.get(stackList.size() - 1);  // Return the last element
    }

    // Clear the stack
    @Override
    public void clear() {
        stackList.clear();  // Clear all elements from the underlying list
    }

    // Check if the stack is empty
    @Override
    public boolean isEmpty() {
        return stackList.size() == 0;  // Return true if the list is empty
    }

    // Convert the stack to an array (non-parameterized)
    @Override
    public Object[] toArray() {
    	Object[] result = new Object[stackList.size];  // 创建一个大小为栈的大小的数组

        // 按照 LIFO 顺序填充数组
        for (int i = 0; i < stackList.size; i++) {
            result[i] = stackList.get(stackList.size() - 1 - i);  // 从栈顶开始填充数组
        }

        return result;  // 返回数组
    }

    // Convert the stack to an array (parameterized)
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
    	if (toHold == null) {
            throw new NullPointerException("The provided array cannot be null.");
        }

        // 如果提供的数组长度不足，创建一个新的数组
        if (toHold.length < stackList.size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) java.lang.reflect.Array.newInstance(
                toHold.getClass().getComponentType(),
                stackList.size
            );
            toHold = newArray;
        }

        // 按 LIFO 顺序填充数组
        for (int i = 0; i < stackList.size; i++) {
        	toHold[i] = stackList.get(stackList.size() - 1 - i);
        }

        // 如果数组长度超过需要，多余的位置置为 null
        if (toHold.length > stackList.size) {
            toHold[stackList.size] = null;
        }

        return toHold;
    }

    // Check if the stack contains the specified element
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("The element to find cannot be null.");
        }
        return stackList.contains(toFind);  // Use MyArrayList's contains method
    }

    // Search for the element's position in the stack
    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in the stack.");
        }

        // Iterate from the top of the stack (i.e., from the last element in the list)
        for (int i = stackList.size - 1; i >= 0; i--) {
            try {
                // Get the element at index i in the MyArrayList (representing the stack)
                E element = stackList.get(i);
                
                // Compare the element with the one we are looking for
                if (element.equals(toFind)) {
                    // Return the 1-based index from the top
                    return stackList.size - i;
                }
            } catch (IndexOutOfBoundsException e) {
                // This shouldn't happen, as we're within bounds, but just in case
                throw new IllegalStateException("Index out of bounds while searching", e);
            }
        }

        // If the element is not found, return -1
        return -1;
    }


    // Return an iterator over the elements in this stack
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = stackList.size() - 1;  // Start from the top of the stack

            @Override
            public boolean hasNext() {
                return index >= 0;  // Return true if there are more elements in the stack
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the stack.");
                }
                return stackList.get(index--);  // Return the element and move towards the bottom of the stack
            }
        };
    }

    // Check if this stack is equal to another stack
    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null) return false;
        if (this == that) return true;

        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }
        return !thisIterator.hasNext() && !thatIterator.hasNext();  // Ensure both iterators have no more elements
    }

    // Return the size of the stack
    @Override
    public int size() {
        return stackList.size();  // Return the size of the underlying list
    }

    // Check if the stack has reached its maximum capacity (if it's a fixed-size stack)
    @Override
    public boolean stackOverflow() {
        // This method would be relevant only for a fixed-size stack.
        // We assume no fixed size is implemented in MyArrayList.
        return false;  // MyArrayList can grow dynamically, so no overflow.
    }
}
