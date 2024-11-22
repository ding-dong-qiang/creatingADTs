package implementations;

import utilities.StackADT;
import utilities.Iterator;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @param <E>
 */
public class MyStack<E> implements StackADT<E> {
    /**
     * 
     */
    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<>();
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Null elements can't be added into the stack.");
        }
        list.add(0, toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(0);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(0);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("The provided arraylist cannot be null.");
        }

        if (toHold.length < size()) {
            toHold = Arrays.copyOf(toHold, size());
        }

        for (int i = 0; i < size(); i++) {
            toHold[i] = list.get(i);
        }

        if (toHold.length > size()) {
            toHold[size()] = null;
        }

        return toHold;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in this list.");
        }

        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in this list.");
        }
        for (int i = 0; i < list.size(); i++) {
            if (toFind.equals(list.get(i))) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (that == null) {
            return false;
        }
        if (this.size() != that.size()) {
            return false;
        }
        Object[] thisArray = this.toArray();
        Object[] thatArray = that.toArray();

        for (int i = 0; i < this.size(); i++) {
            if (!thisArray[i].equals(thatArray[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean stackOverflow() {
        // This implementation uses a dynamic-size ArrayList, so stack overflow is not
        // possible
        return false;
    }

}
