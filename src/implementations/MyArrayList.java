/**
 * 
 */
package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * @param <E>
 * 
 */
public class MyArrayList<E> implements ListADT<E> {

	/**
	 * 
	 */
	private E[] elements;
    private int size;
    private static final int DEFAULT_SIZE = 10;
    
    private class MyArrayListIterator implements Iterator<E> {
    	private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return currentIndex < size;
		}

		@Override
		public E next() throws NoSuchElementException {
			// TODO Auto-generated method stub
			if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[currentIndex++];
		}
    	
    }
    
	public MyArrayList() {
		// TODO Auto-generated constructor stub
		this.elements = (E[]) new Object[DEFAULT_SIZE];
        this.size = 0;
	}
	
	@SuppressWarnings("unchecked")
	private void expandCapacity() {
	    int newCapacity = elements.length * 2; 
	    E[] newElements = (E[]) new Object[newCapacity]; 
	    System.arraycopy(elements, 0, newElements, 0, size); 
	    elements = newElements; 
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++) {
	        elements[i] = null;
	    }
	    size = 0;
		
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (toAdd == null) {
	        throw new NullPointerException("Element to add cannot be null.");
	    }
	    if (index < 0 || index > size) {
	        throw new IndexOutOfBoundsException("Index out of bounds: " + index);
	    }
	    if (size == elements.length) { 
	        expandCapacity();
	    }
	    for (int i = size; i > index; i--) {
	        elements[i] = elements[i - 1];
	    }

	    elements[index] = toAdd;
	    size++;
	    return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException {
		// TODO Auto-generated method stub
		if (toAdd == null) {
	        throw new NullPointerException("Null elements are not allowed.");
	    }

	   
	    if (size == elements.length) {
	        expandCapacity();
	    }

	    
	    elements[size] = toAdd;
	    size++;  

	    return true; 
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		// TODO Auto-generated method stub
		if (toAdd == null) {
            throw new NullPointerException("The provided list cannot be null.");
        }

        Iterator<? extends E> iterator = toAdd.iterator();

        while (iterator.hasNext()) {
            E element = iterator.next();
            add(element);
        }

        return true;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return elements[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
		E removedElement = elements[index];
		for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;

        return removedElement;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		// TODO Auto-generated method stub
		if (toRemove == null) {
	        throw new NullPointerException("This list does not support null elements.");
	    }
		int indexToRemove = -1;
	    for (int i = 0; i < size; i++) {
	        if (elements[i].equals(toRemove)) {
	            indexToRemove = i;
	            break;
	        }
	    }
		if (indexToRemove == -1) {
			return null;
		}
		return remove(indexToRemove);
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    
	    if (toChange == null) {
	        throw new NullPointerException("This list does not support null elements.");
	    }

	    
	    E oldElement = elements[index];

	    
	    elements[index] = toChange;

	    
	    return oldElement;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		// TODO Auto-generated method stub
		if (toFind == null) {
	        throw new NullPointerException("Cannot search for null in this list.");
	    }

	    for (int i = 0; i < size; i++) {
	        if (elements[i].equals(toFind)) {
	            return true;
	        }
	    }

	    return false;
	}

	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
	    if (toHold == null) {
	        throw new NullPointerException("The provided array cannot be null.");
	    }

	    if (toHold.length < size) {
	        @SuppressWarnings("unchecked")
	        E[] newArray = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
	        System.arraycopy(elements, 0, newArray, 0, size);
	        return newArray;
	    }

	    System.arraycopy(elements, 0, toHold, 0, size);

	    if (toHold.length > size) {
	        toHold[size] = null;
	    }

	    return toHold;
	}


	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		Object[] result = new Object[size];
   
	    System.arraycopy(elements, 0, result, 0, size);

	    return result;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new MyArrayListIterator();
	}

}
