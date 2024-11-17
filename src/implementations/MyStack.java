package implementations;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

/**
 * Implementation of StackADT using MyArrayList as the underlying data
 * structure.
 *
 * @param <E> The type of elements this stack holds.
 */
public class MyStack<E> implements StackADT<E>, Serializable
{

	private static final long serialVersionUID = 1L;

	private MyArrayList<E> list;

	/**
	 * Constructs an empty stack using MyArrayList.
	 */
	public MyStack()
	{
		list = new MyArrayList<>();
	}

	@Override
	public void push( E toAdd ) throws NullPointerException
	{
		if( toAdd == null )
		{
			throw new NullPointerException( "Cannot push null element." );
		}
		list.add( toAdd ); // Always add to the end (top of stack)
	}

	@Override
	public E pop() throws EmptyStackException
	{
		if( isEmpty() )
		{
			throw new EmptyStackException();
		}
		return list.remove( list.size() - 1 ); // Remove and return the last element
	}

	@Override
	public E peek() throws EmptyStackException
	{
		if( isEmpty() )
		{
			throw new EmptyStackException();
		}
		return list.get( list.size() - 1 ); // Return the last element without removing it
	}

	@Override
	public void clear()
	{
		list.clear(); // Clear all elements in the list
	}

	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Override
	public Object[] toArray()
	{
		Object[] result = new Object[size()];
		for( int i = 0; i < size(); i++ )
		{
			result[i] = list.get( size() - i - 1 ); // Copy elements in reverse order
		}
		return result;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public E[] toArray( E[] holder ) throws NullPointerException
	{
		if( holder == null )
		{
			throw new NullPointerException( "Target array cannot be null." );
		}

		if( holder.length < size() )
		{
			holder = (E[]) java.lang.reflect.Array.newInstance( holder.getClass().getComponentType(), size() );
		}

		for( int i = 0; i < size(); i++ )
		{
			holder[i] = list.get( size() - 1 - i ); // Copy elements in reverse order
		}

		if( holder.length > size() )
		{
			holder[size()] = null; // Set the element after the last one to null
		}

		return holder;
	}

	@Override
	public boolean contains( E toFind ) throws NullPointerException
	{
		return list.contains( toFind ); // Use MyArrayList's contains implementation
	}

	@Override
	public int search( E toFind )
	{
		if( toFind == null )
		{
			return -1;
		}
		for( int i = list.size() - 1; i >= 0; i-- )
		{
			if( toFind.equals( list.get( i ) ) )
			{
				return list.size() - i; // 1-based position from the top
			}
		}
		return -1; // Not found
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int currentIndex = size() - 1;

			@Override
			public boolean hasNext()
			{
				return currentIndex >= 0;
			}

			@Override
			public E next()
			{
				if( !hasNext() )
				{
					throw new NoSuchElementException( "No more elements to iterate." );
				}
				return list.get( currentIndex-- );
			}
		};
	}

	@Override
	public boolean equals( StackADT<E> that )
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
	public int size()
	{
		return list.size(); // Use MyArrayList's size implementation
	}

	@Override
	public boolean stackOverflow()
	{
		return false; // Dynamic array does not have a fixed size
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "[" );
		for( int i = list.size() - 1; i >= 0; i-- ) // 从栈顶开始
		{
			sb.append( list.get( i ) );
			if( i != 0 )
			{
				sb.append( ", " );
			}
		}
		sb.append( "]" );
		return sb.toString();
	}
}