package implementations;

/**
 * Represents a single node in a doubly linked list.
 *
 * @param <E> The type of element stored in the node.
 */
public class MyDLLNode<E>
{
	E data;
	MyDLLNode<E> next;
	MyDLLNode<E> prev;

	/**
	 * Constructs a new node with the given data.
	 * 
	 * @param data The data to store in the node.
	 */
	public MyDLLNode( E data )
	{
		this.data = data;
		next = null;
		prev = null;
	}
}
