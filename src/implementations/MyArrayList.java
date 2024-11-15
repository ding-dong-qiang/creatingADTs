package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * Implementation of ListADT using an array as the underlying data structure.
 * 
 * @param <E> The type of elements this list holds.
 */
public class MyArrayList<E> implements ListADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;

	private E[] data;
	private int size;
	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Constructs an empty list with an initial capacity of 10.
	 */
	@SuppressWarnings( "unchecked" )
	public MyArrayList()
	{
		data = (E[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Ensures the array has enough capacity to store more elements.
	 *
	 * @param requiredCapacity The required capacity.
	 */
	private void ensureCapacity( int requiredCapacity )
	{
		if( requiredCapacity > data.length )
		{
			int newCapacity = Math.max( data.length * 2, requiredCapacity );
			data = java.util.Arrays.copyOf( data, newCapacity );
		}
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		for( int i = 0; i < size; i++ )
		{
			data[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean add( int index, E toAdd ) throws NullPointerException, IndexOutOfBoundsException
	{
		if( toAdd == null )
			throw new NullPointerException( "Cannot add null elements to this list." );
		if( index < 0 || index > size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		ensureCapacity( size + 1 );
		System.arraycopy( data, index, data, index + 1, size - index );
		data[index] = toAdd;
		size++;
		return true;
	}

	@Override
	public boolean add( E toAdd ) throws NullPointerException
	{
		if( toAdd == null )
			throw new NullPointerException( "Cannot add null element to the list." );

		ensureCapacity( size + 1 );
		data[size++] = toAdd;
		return true;
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
		if( index < 0 || index >= size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		return data[index];
	}

	@Override
	public E remove( int index ) throws IndexOutOfBoundsException
	{
		if( index < 0 || index >= size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		E removedElement = data[index];
		System.arraycopy( data, index + 1, data, index, size - index - 1 );
		data[--size] = null;
		return removedElement;
	}

	@Override
	public E remove( E toRemove ) throws NullPointerException
	{
		if( toRemove == null )
			throw new NullPointerException( "Cannot remove null element from the list." );

		for( int i = 0; i < size; i++ )
		{
			if( data[i].equals( toRemove ) )
			{
				return remove( i );
			}
		}
		return null;
	}

	@Override
	public E set( int index, E toChange ) throws NullPointerException, IndexOutOfBoundsException
	{
		if( toChange == null )
			throw new NullPointerException( "Cannot set null element in the list." );
		if( index < 0 || index >= size )
			throw new IndexOutOfBoundsException( "Index out of bounds." );

		E oldElement = data[index];
		data[index] = toChange;
		return oldElement;
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

		for( int i = 0; i < size; i++ )
		{
			if( data[i].equals( toFind ) )
			{
				return true;
			}
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
			return java.util.Arrays.copyOf( data, size, (Class<? extends E[]>) toHold.getClass() );
		}
		System.arraycopy( data, 0, toHold, 0, size );
		if( toHold.length > size )
		{
			toHold[size] = null;
		}
		return toHold;
	}

	@Override
	public Object[] toArray()
	{
		return java.util.Arrays.copyOf( data, size );
	}

	@Override
	public Iterator<E> iterator()
	{
		return new MyArrayListIterator();
	}

	/**
	 * Iterator implementation for MyArrayList.
	 */
	private class MyArrayListIterator implements Iterator<E>
	{
		private int currentIndex = 0;

		@Override
		public boolean hasNext()
		{
			return currentIndex < size;
		}

		@Override
		public E next() throws NoSuchElementException
		{
			if( !hasNext() )
				throw new NoSuchElementException( "No more elements to iterate." );
			return data[currentIndex++];
		}
	}
}
