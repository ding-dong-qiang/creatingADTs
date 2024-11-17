package implementations;

import java.io.Serializable;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * Implementation of QueueADT using MyDLL as the underlying data structure.
 *
 * @param <E> The type of elements this queue holds.
 */
public class MyQueue<E> implements QueueADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;

	private MyDLL<E> list;

	/**
	 * Constructs an empty queue using MyDLL.
	 */
	public MyQueue()
	{
		list = new MyDLL<>();
	}

	@Override
	public void enqueue( E toAdd ) throws NullPointerException
	{
		if( toAdd == null )
		{
			throw new NullPointerException( "Cannot enqueue a null element." );
		}
		list.add( toAdd ); // Add to the tail of the list
	}

	@Override
	public E dequeue() throws EmptyQueueException
	{
		if( isEmpty() )
		{
			throw new EmptyQueueException( "Cannot dequeue from an empty queue." );
		}
		return list.remove( 0 ); // Remove from the head of the list
	}

	@Override
	public E peek() throws EmptyQueueException
	{
		if( isEmpty() )
		{
			throw new EmptyQueueException( "Cannot peek into an empty queue." );
		}
		return list.get( 0 ); // Get the head of the list without removing it
	}

	@Override
	public void dequeueAll()
	{
		list.clear(); // Clear the entire list
	}

	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		return list.contains( toFind ); // Use MyDLL's contains method
	}

	@Override
	public int search( E toFind )
	{
		if( toFind == null )
		{
			return -1;
		}
		Iterator<E> it = iterator();
		int index = 1;
		while( it.hasNext() )
		{
			if( toFind.equals( it.next() ) )
			{
				return index;
			}
			index++;
		}
		return -1; // Not found
	}

	@Override
	public Iterator<E> iterator()
	{
		return list.iterator(); // Use MyDLL's iterator
	}

	@Override
	public boolean equals( QueueADT<E> that )
	{
		if( that == null || this.size() != that.size() )
		{
			return false;
		}
		Iterator<E> thisIterator = this.iterator();
		Iterator<E> thatIterator = that.iterator();
		while( thisIterator.hasNext() )
		{
			if( !thisIterator.next().equals( thatIterator.next() ) )
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] toArray()
	{
		return list.toArray(); // Use MyDLL's toArray method
	}

	@Override
	public E[] toArray( E[] holder ) throws NullPointerException
	{
		return list.toArray( holder ); // Use MyDLL's toArray(E[] holder) method
	}

	@Override
	public boolean isFull()
	{
		return false; // MyDLL has dynamic capacity
	}

	@Override
	public int size()
	{
		return list.size(); // Use MyDLL's size method
	}
}
