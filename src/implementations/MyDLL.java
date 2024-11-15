package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * Implementation of ListADT using a doubly linked list as the underlying data
 * structure.
 *
 * @param <E> The type of elements this list holds.
 */
public class MyDLL<E> implements ListADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;

	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;

	/**
	 * Constructs an empty doubly linked list.
	 */
	public MyDLL()
	{
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}

	@Override
	public boolean add( int index, E toAdd ) throws NullPointerException, IndexOutOfBoundsException
	{
		if( toAdd == null )
			throw new NullPointerException( "Cannot add null element." );
		if( index < 0 || index > size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		MyDLLNode<E> newNode = new MyDLLNode<>( toAdd );

		if( index == 0 )
		{
			// Insert at the beginning
			if( head == null )
			{
				head = tail = newNode;
			}
			else
			{
				newNode.next = head;
				head.prev = newNode;
				head = newNode;
			}
		}
		else if( index == size )
		{
			// Insert at the end
			if( tail == null )
			{
				head = tail = newNode;
			}
			else
			{
				tail.next = newNode;
				newNode.prev = tail;
				tail = newNode;
			}
		}
		else
		{
			// Insert in the middle
			MyDLLNode<E> current = getNodeAt( index );
			MyDLLNode<E> prevNode = current.prev;

			newNode.next = current;
			newNode.prev = prevNode;

			prevNode.next = newNode;
			current.prev = newNode;
		}

		size++;
		return true;
	}

	@Override
	public boolean add( E toAdd ) throws NullPointerException
	{
		return add( size, toAdd );
	}

	@Override
	public boolean addAll( ListADT<? extends E> toAdd ) throws NullPointerException
	{
		if( toAdd == null )
			throw new NullPointerException( "The list to add cannot be null." );

		Iterator<? extends E> iterator = toAdd.iterator();
		while( iterator.hasNext() )
		{
			add( iterator.next() );
		}
		return true;
	}

	@Override
	public E get( int index ) throws IndexOutOfBoundsException
	{
		MyDLLNode<E> node = getNodeAt( index );
		return node.data;
	}

	@Override
	public E remove( int index ) throws IndexOutOfBoundsException
	{
		if( index < 0 || index >= size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		MyDLLNode<E> toRemove = getNodeAt( index );

		if( toRemove == head )
		{
			head = toRemove.next;
			if( head != null )
			{
				head.prev = null;
			}
		}
		else if( toRemove == tail )
		{
			tail = toRemove.prev;
			if( tail != null )
			{
				tail.next = null;
			}
		}
		else
		{
			MyDLLNode<E> prevNode = toRemove.prev;
			MyDLLNode<E> nextNode = toRemove.next;

			prevNode.next = nextNode;
			nextNode.prev = prevNode;
		}

		if( size == 1 )
		{
			head = tail = null;
		}

		size--;
		return toRemove.data;
	}

	@Override
	public E remove( E toRemove ) throws NullPointerException
	{
		if( toRemove == null )
			throw new NullPointerException( "Cannot remove null element." );

		MyDLLNode<E> current = head;
		while( current != null )
		{
			if( current.data.equals( toRemove ) )
			{
				return remove( getIndexOf( current ) );
			}
			current = current.next;
		}
		return null;
	}

	@Override
	public E set( int index, E toChange ) throws NullPointerException, IndexOutOfBoundsException
	{
		if( toChange == null )
			throw new NullPointerException( "Cannot set null element." );
		MyDLLNode<E> node = getNodeAt( index );
		E oldData = node.data;
		node.data = toChange;
		return oldData;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		if( toFind == null )
			throw new NullPointerException( "Cannot search for null element." );
		MyDLLNode<E> current = head;
		while( current != null )
		{
			if( current.data.equals( toFind ) )
			{
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public E[] toArray( E[] toHold ) throws NullPointerException
	{
		if( toHold == null )
			throw new NullPointerException( "Target array cannot be null." );

		if( toHold.length < size )
		{
			toHold = (E[]) java.util.Arrays.copyOf( toHold, size );
		}

		MyDLLNode<E> current = head;
		for( int i = 0; i < size; i++ )
		{
			toHold[i] = current.data;
			current = current.next;
		}

		return toHold;
	}

	@Override
	public Object[] toArray()
	{
		Object[] array = new Object[size];
		MyDLLNode<E> current = head;
		for( int i = 0; i < size; i++ )
		{
			array[i] = current.data;
			current = current.next;
		}
		return array;
	}

	@Override
	public Iterator<E> iterator()
	{
		return new MyDLLIterator();
	}

	private MyDLLNode<E> getNodeAt( int index )
	{
		if( index < 0 || index >= size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		MyDLLNode<E> current;
		if( index < size / 2 )
		{
			current = head;
			for( int i = 0; i < index; i++ )
			{
				current = current.next;
			}
		}
		else
		{
			current = tail;
			for( int i = size - 1; i > index; i-- )
			{
				current = current.prev;
			}
		}
		return current;
	}

	private int getIndexOf( MyDLLNode<E> node )
	{
		MyDLLNode<E> current = head;
		int index = 0;
		while( current != null )
		{
			if( current == node )
			{
				return index;
			}
			current = current.next;
			index++;
		}
		throw new NoSuchElementException( "Node not found in the list." );
	}

	private class MyDLLIterator implements Iterator<E>
	{
		private MyDLLNode<E> current = head;

		@Override
		public boolean hasNext()
		{
			return current != null;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if( !hasNext() )
				throw new NoSuchElementException( "No more elements to iterate." );
			E data = current.data;
			current = current.next;
			return data;
		}
	}
}
