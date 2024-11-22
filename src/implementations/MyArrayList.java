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
		private final E[] copyElements;
		private int currentIndex = 0;

		@SuppressWarnings("unchecked")
		public MyArrayListIterator() {
			copyElements = toArray((E[]) new Object[size]);
		}

		@Override
		public boolean hasNext() {
			return currentIndex < copyElements.length;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return copyElements[currentIndex++];
		}

	}

	@SuppressWarnings("unchecked")
	public MyArrayList() {
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
		return size;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;

	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
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
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return elements[index];
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
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
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
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

			return Arrays.copyOf(toHold, size);
		}

		System.arraycopy(elements, 0, toHold, 0, size);

		return toHold;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];

		System.arraycopy(elements, 0, result, 0, size);

		return result;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyArrayListIterator();
	}

}
