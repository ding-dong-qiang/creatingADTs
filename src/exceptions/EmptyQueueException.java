package exceptions;

/**
 * Custom exception class to represent an empty queue condition. Thrown when
 * attempting to perform an operation on an empty queue.
 */
public class EmptyQueueException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new EmptyQueueException with no detail message.
	 */
	public EmptyQueueException()
	{
		super();
	}

	/**
	 * Constructs a new EmptyQueueException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public EmptyQueueException( String message )
	{
		super( message );
	}
}
