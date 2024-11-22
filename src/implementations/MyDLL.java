/**
 * 
 */
package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * @param <E>
 * 
 */
public class MyDLL<E> implements ListADT<E> {

	/**
	 * 
	 */
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	public MyDLL() {
	    head = null;
	    tail = null;
	    size = 0;
	}
	
	private class MyDLLIterator implements Iterator<E> {
	    private MyDLLNode<E> current;

		public MyDLLIterator() {
            current = head;}

	    @Override
	    public boolean hasNext() {
	        return current != null;
	    }

	    @Override
	    public E next() {
	        if (!hasNext()) {
	            throw new NoSuchElementException("No more elements in the list.");
	        }
	        
	        E data = current.getData();
	        current = current.getNext();
	        return data;
	    }
    }

	
	@Override
	public int size() {
	    return size;
	}

	@Override
	public boolean isEmpty() {
	    return size == 0;
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		head = null;
	    tail = null;
	    
	    // Set size to 0, indicating the list is empty
	    size = 0;
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
	    if (toAdd == null) {
	        throw new NullPointerException("Cannot add null element.");
	    }
	    if (index < 0 || index > size) { // index > size allows adding at the end
	        throw new IndexOutOfBoundsException("Index out of range.");
	    }

	    MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

	    if (index == 0) {  // Insert at the head
	        if (isEmpty()) {
	            head = tail = newNode;
	        } else {
	            newNode.setNext(head);
	            head.setPrev(newNode);
	            head = newNode;
	        }
	    } else if (index == size) {  // Insert at the tail (if index == size)
	        add(toAdd);
	        size--;// Call the method that appends the element at the end
	    } else {  // Insert in the middle
	        MyDLLNode<E> current = getNode(index);
	        MyDLLNode<E> prevNode = current.getPrev();

	        newNode.setNext(current);
	        newNode.setPrev(prevNode);
	        prevNode.setNext(newNode);
	        current.setPrev(newNode);
	    }

	    size++;  // Update the size
	    return true;
	}


	@Override
	public boolean add(E toAdd) throws NullPointerException {
	    if (toAdd == null) {
	        throw new NullPointerException("Cannot add null element.");
	    }

	    MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

	    if (isEmpty()) {
	        head = tail = newNode;
	    } else {
	        tail.setNext(newNode);
	        newNode.setPrev(tail);
	        tail = newNode;
	    }
	    size++;
	    return true;
	}

	

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
	    if (toAdd == null) {
	        throw new NullPointerException("The list to add cannot be null.");
	    }

	    Iterator<? extends E> iterator = toAdd.iterator();
	    while (iterator.hasNext()) {
	        this.add(iterator.next()); // Reuse the add(E toAdd) method.
	    }

	    return true;
	}


	@Override
	public E get(int index) throws IndexOutOfBoundsException {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index out of range.");
	    }

	    return getNode(index).getData();
	}

	private MyDLLNode<E> getNode(int index) {
	    MyDLLNode<E> current;

	    if (index <= size / 2) {
	        current = head;
	        for (int i = 0; i < index; i++) {
	            current = current.getNext();
	        }
	    } else {
	        current = tail;
	        for (int i = size - 1; i > index; i--) {
	            current = current.getPrev();
	        }
	    }

	    return current;
	}


	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index out of range.");
	    }

	    MyDLLNode<E> nodeToRemove = getNode(index);
	    E data = nodeToRemove.getData();

	    if (nodeToRemove == head) {
	        head = head.getNext();
	        if (head != null) {
	            head.setPrev(null);
	        } else {
	            tail = null; // List is now empty.
	        }
	    } else if (nodeToRemove == tail) {
	        tail = tail.getPrev();
	        if (tail != null) {
	            tail.setNext(null);
	        }
	    } else {
	        MyDLLNode<E> prevNode = nodeToRemove.getPrev();
	        MyDLLNode<E> nextNode = nodeToRemove.getNext();

	        prevNode.setNext(nextNode);
	        nextNode.setPrev(prevNode);
	    }

	    size--;
	    return data;
	}


	@Override
	public E remove(E toRemove) throws NullPointerException {
	    if (toRemove == null) {
	        throw new NullPointerException("The element to remove cannot be null.");
	    }

	    // Start from the head node
	    MyDLLNode<E> current = head;

	    while (current != null) {
	        if (toRemove.equals(current.data)) {
	            // Found the node to remove

	            // Update links
	            if (current.prev != null) {
	                current.prev.next = current.next;
	            } else {
	                head = current.next; // If it's the head node
	            }

	            if (current.next != null) {
	                current.next.prev = current.prev;
	            } else {
	                tail = current.prev; // If it's the tail node
	            }

	            size--; // Decrement the size
	            return current.data; // Return the removed element
	        }

	        current = current.next;
	    }

	    return null; // Element not found
	}


	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
	    if (toChange == null) {
	        throw new NullPointerException("The element to change cannot be null.");
	    }

	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index out of range: " + index);
	    }

	    // Traverse to the node at the specified index
	    MyDLLNode<E> current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    // Replace the data and return the old value
	    E oldData = current.data;
	    current.data = toChange;
	    return oldData;
	}


	

	@Override
	public boolean contains(E toFind) throws NullPointerException {
	    if (toFind == null) {
	        throw new NullPointerException("The element to find cannot be null.");
	    }

	    MyDLLNode<E> current = head;

	    // Traverse the list to find the element
	    while (current != null) {
	        if (toFind.equals(current.data)) {
	            return true;
	        }
	        current = current.next;
	    }

	    return false; // Element not found
	}


	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
	    if (toHold == null) {
	        throw new NullPointerException("Provided array is null.");
	    }

	    if (toHold.length < size) {
	        toHold = (E[]) java.lang.reflect.Array.newInstance(
	            toHold.getClass().getComponentType(), size);
	    }

	    MyDLLNode<E> current = head;
	    for (int i = 0; i < size; i++) {
	        toHold[i] = current.getData();
	        current = current.getNext();
	    }

	    if (toHold.length > size) {
	        toHold[size] = null; // Fill extra space with null.
	    }

	    return toHold;
	}


	@Override
	public Object[] toArray() {
	    Object[] array = new Object[size];
	    MyDLLNode<E> current = head;

	    for (int i = 0; i < size; i++) {
	        array[i] = current.getData();
	        current = current.getNext();
	    }

	    return array;
	}


	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new MyDLLIterator();
	}

}
