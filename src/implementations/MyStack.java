package implementations;

import utilities.StackADT;
import utilities.Iterator;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

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
        list.add(toAdd);
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
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
        Object[] array = list.toArray();

        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("The provided array cannot be null.");
        }

        E[] array = list.toArray(toHold);

        for (int i = 0; i < array.length / 2; i++) {
            E temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
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
        return new Iterator<E>() {
            private int currentIndex = list.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return list.get(currentIndex--);
            }
        };
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
