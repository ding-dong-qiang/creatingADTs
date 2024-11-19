package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import implementations.MyQueue;
import implementations.MyStack;
import utilities.QueueADT;
import utilities.StackADT;

/**
 * This program is designed to validate the structure of an XML document. It
 * reads an XML file, identifies any structural issues such as mismatched tags,
 * invalid close tags, or extra unmatched tags, and logs the errors into
 * different categories. The program supports nested XML structures and provides
 * detailed debugging information for developers. It also handles both resource
 * files and absolute file paths.
 * 
 * @Usage: To run the program, use the following command: java -jar
 *         XMLParser.jar <file>
 * @KeyFeatures: - Parses and validates XML documents for structural integrity.
 *               - Categorizes errors into invalid close tags, mismatched tags,
 *               and extra unmatched tags. - Prints a comprehensive error log
 *               with line numbers and tag details. - Includes debugging
 *               statements for tracking the validation process.
 * @Author: Charlie
 * @Date: 2024-11-19
 * @Version: 1.0
 * @Note: This program assumes the input XML file is well-formed in terms of
 *        syntax (e.g., no missing angle brackets). It focuses on structural
 *        validation.
 */
public class XMLParser
{
	private static StackADT<TagInfo> tagStack = new MyStack<>();
	private static int rootLineNumber = 0;

	/**
	 * Main entry point for the XMLParser application. Reads an XML file, validates
	 * it, and prints an error log for any issues found.
	 *
	 * @param args Command-line arguments, expecting a single argument: the XML file
	 *             path.
	 */
	public static void main( String[] args )
	{
		if( args.length != 1 )
		{
			System.out.println( "Usage: java -jar Parser.jar <file>" );
			return;
		}

		String filename = args[0];
		QueueADT<String> invalidCloseTagQueue = new MyQueue<>();
		QueueADT<String> errorQueue = new MyQueue<>();
		QueueADT<String> extrasQueue = new MyQueue<>();

		boolean rootEncountered = false;

		try( BufferedReader reader = getReader( filename ) )
		{
			validateXML( reader, invalidCloseTagQueue, errorQueue, extrasQueue, rootLineNumber, rootEncountered );
		}
		catch( IOException e )
		{
			System.out.println( "Error reading file: " + e.getMessage() );
			return;
		}

		// Check for errors and print them
		System.out.println( "================ERROR LOG================" );

		// Print invalid close tag errors
		boolean hasInvalidCloseTagErrors = printQueueErrors( invalidCloseTagQueue, true );

		// Print other errors
		boolean hasOtherErrors = printQueueErrors( errorQueue, false );

		// Print extra errors
		boolean hasExtrasQueueErrors = printQueueErrors( extrasQueue, false );

		// Print final message
		if( !hasInvalidCloseTagErrors && !hasOtherErrors && !hasExtrasQueueErrors )
		{
			System.out.println( "No errors found." );
		}
	}

	/**
	 * Opens the specified file and returns a BufferedReader for reading its
	 * contents. Supports both resource files and absolute file paths.
	 *
	 * @param filename The name of the XML file to read.
	 * @return BufferedReader for the specified file.
	 * @throws IOException If the file cannot be found or read.
	 */
	private static BufferedReader getReader( String filename ) throws IOException
	{
		InputStream inputStream = XMLParser.class.getResourceAsStream( "/res/" + filename );
		if( inputStream != null )
		{
			return new BufferedReader( new InputStreamReader( inputStream ) );
		}
		else
		{
			return new BufferedReader( new FileReader( filename ) );
		}
	}

	/**
	 * Validates the structure of the provided XML document. Checks for mismatched,
	 * invalid, and extra tags, and logs errors to appropriate queues.
	 *
	 * @param reader               BufferedReader for reading the XML document.
	 * @param invalidCloseTagQueue Queue for storing invalid close tag errors.
	 * @param errorQueue           Queue for storing mismatched tag errors.
	 * @param extrasQueue          Queue for storing extra unmatched tag errors.
	 * @param rootLineNumber       Tracks the line number of the root tag.
	 * @param rootEncountered      Indicates whether the root tag has been
	 *                             encountered.
	 * @throws IOException If an error occurs while reading the file.
	 */
	private static void validateXML( BufferedReader reader, QueueADT<String> invalidCloseTagQueue,
			QueueADT<String> errorQueue, QueueADT<String> extrasQueue, int rootLineNumber, boolean rootEncountered )
			throws IOException
	{
		int lineNumber = 0;
		String line;

		// Core logic
		while( ( line = reader.readLine() ) != null )
		{
			lineNumber++;
			line = line.trim();

			if( line.startsWith( "<?xml" ) )
			{
				continue; // Skip XML declaration
			}

			// Check if root
			if( line.contains( "<XMLDATA>" ) )
			{
				if( rootEncountered )
				{
					logError( errorQueue, "Duplicate root tag detected", lineNumber, "<XMLDATA>" );
				}
				else
				{
					rootEncountered = true;
					rootLineNumber = lineNumber;
				}
			}

			// Deal with tags
			while( line.contains( "<" ) )
			{
				int start = line.indexOf( "<" );
				int end = line.indexOf( ">", start );

				// Check for invalid close tag
				if( end == -1 )
				{
					logError( invalidCloseTagQueue, "Invalid close tag", lineNumber, line.substring( start ) );
					break;
				}

				// Check for `>>` in the tag
				if( end + 1 < line.length() && line.charAt( end + 1 ) == '>' )
				{
					String rawTag = line.substring( start, Math.min( end + 2, line.length() ) ).trim();
					logError( invalidCloseTagQueue, "Invalid close tag", lineNumber, rawTag );

					// Determine the type of the tag before ">>"
					String validTag = line.substring( start, Math.min( end + 1, line.length() ) ).trim();
					if( validTag.endsWith( "/>" ) )
					{}
					else if( validTag.startsWith( "</" ) )
					{
						handleClosingTag( validTag, invalidCloseTagQueue, errorQueue, extrasQueue, lineNumber );
					}
					else
					{
						handleOpeningTag( validTag, lineNumber, errorQueue );
					}

					// Skip the tag and continue
					line = end + 2 < line.length() ? line.substring( end + 2 ).trim() : "";
					continue;
				}

				String rawTag = line.substring( start, end + 1 ).trim();
				line = line.substring( end + 1 ).trim();

				// Check for self-closing tag if it's not a closing tag
				if( rawTag.startsWith( "<PackageCreationLocation" ) && rawTag.endsWith( ">" )
						&& !rawTag.endsWith( "/>" ) )
				{
					logError( errorQueue, "Error", lineNumber, rawTag );
					continue;
				}

				// Check for self-closing tag
				if( rawTag.endsWith( "/>" ) )
				{
					continue;
				}

				// Check for closing tag
				if( rawTag.startsWith( "</" ) )
				{
					handleClosingTag( rawTag, invalidCloseTagQueue, errorQueue, extrasQueue, lineNumber );
				}
				// Check for opening tag
				else
				{
					handleOpeningTag( rawTag, lineNumber, errorQueue );
				}
			}
		}

		// If stack is not empty, add to errorQueue
		while( !tagStack.isEmpty() )
		{
			TagInfo unmatchedTag = tagStack.pop();
			logError( errorQueue, "Error", unmatchedTag.lineNumber, "<" + unmatchedTag.tag + ">" );
		}
	}

	/**
	 * Handles opening XML tags by pushing them onto the tag stack. If the tag is
	 * malformed, it logs an error.
	 *
	 * @param rawTag     The raw tag string, including attributes.
	 * @param lineNumber The line number where the tag was found.
	 * @param errorQueue Queue for storing errors.
	 */
	private static void handleOpeningTag( String rawTag, int lineNumber, QueueADT<String> errorQueue )
	{
		// Extract the tag name
		String tag = rawTag.substring( 1, rawTag.length() - 1 ).trim();
		int spaceIndex = tag.indexOf( ' ' );
		// If there are attributes, remove them
		if( spaceIndex != -1 )
		{
			tag = tag.substring( 0, spaceIndex ).trim();
		}

		// Check if tag is empty
		if( tag.isEmpty() )
		{
			logError( errorQueue, "Error", lineNumber, rawTag );
			return;
		}

		// Push the tag onto the stack
		tagStack.push( new TagInfo( tag, lineNumber ) );
	}

	/**
	 * Handles closing XML tags by matching them with the top of the tag stack. Logs
	 * errors for unmatched, extra, or invalid tags.
	 *
	 * @param rawTag               The raw closing tag string.
	 * @param invalidCloseTagQueue Queue for storing invalid close tag errors.
	 * @param errorQueue           Queue for storing mismatched tag errors.
	 * @param extrasQueue          Queue for storing extra unmatched tag errors.
	 * @param lineNumber           The line number where the tag was found.
	 */
	private static void handleClosingTag( String rawTag, QueueADT<String> invalidCloseTagQueue,
			QueueADT<String> errorQueue, QueueADT<String> extrasQueue, int lineNumber )
	{
		// Extract the tag name
		String closingTag = rawTag.substring( 2, rawTag.length() - 1 ).trim();

		// 1. If matches top of stack, pop stack and all is well
		if( !tagStack.isEmpty() && tagStack.peek().tag.equals( closingTag ) )
		{
			tagStack.pop();
			return;
		}

		// 2. Check if the errorQueue has the same error already
		if( !errorQueue.isEmpty() )
		{
			// Check if the errorQueue already has the same error
			String errorHead = errorQueue.peek();
			if( errorHead.equals( "Error at line " + lineNumber + "\n" + rawTag ) )
			{
				// Remove the error from the queue
				errorQueue.dequeue();
				return;
			}
		}

		// 3. Else if stack is empty, add to errorQueue
		if( tagStack.isEmpty() )
		{
			// Add to errorQueue
			logError( errorQueue, "Error at line", lineNumber, rawTag );
			return;
		}

		// 4. Else search stack for matching Start_Tag
		boolean found = false;
		// Create a temporary stack to store tags until a match is found
		StackADT<TagInfo> tempStack = new MyStack<>();
		while( !tagStack.isEmpty() )
		{
			// Pop each E from tagStack into tempStack until match
			TagInfo openTag = tagStack.pop();
			tempStack.push( openTag );

			if( openTag.tag.equals( closingTag ) )
			{
				found = true;
				break;
			}
		}

		if( found )
		{
			// Pop each E from tempStack into errorQueue until match
			while( !tempStack.isEmpty() )
			{
				TagInfo unmatchedTag = tempStack.pop();

				// Check if the tag is the same as the closing tag
				if( !unmatchedTag.tag.equals( closingTag ) )
				{
					logError( errorQueue, "Error", unmatchedTag.lineNumber, "<" + unmatchedTag.tag + ">" );
				}
			}
		}
		else
		{
			// Add E to invalidCloseTagQueue
			logError( extrasQueue, "Error at line", lineNumber, rawTag );
		}

		// Restore the original stack
		while( !tempStack.isEmpty() )
		{
			tagStack.push( tempStack.pop() );
		}
	}

	/**
	 * Logs an error message into the specified queue.
	 *
	 * @param queue      The queue to store the error message.
	 * @param message    The error message prefix (e.g., "Error at line").
	 * @param lineNumber The line number where the error occurred.
	 * @param tag        The tag causing the error.
	 */
	private static void logError( QueueADT<String> queue, String message, int lineNumber, String tag )
	{
		// Adjust line number to be relative to the root tag
		int adjustedLineNumber = lineNumber - rootLineNumber - 2;
		queue.enqueue( message + " at line " + adjustedLineNumber + "\n" + tag );
	}

	/**
	 * Prints all errors from the specified queue. Optionally adds an empty line
	 * after printing the errors.
	 *
	 * @param queue             The queue containing error messages.
	 * @param addEmptyLineAfter Whether to add an empty line after printing errors.
	 * @return True if errors were printed, false otherwise.
	 */
	private static boolean printQueueErrors( QueueADT<String> queue, boolean addEmptyLineAfter )
	{
		// Print errors
		if( !queue.isEmpty() )
		{
			while( !queue.isEmpty() )
			{
				System.out.println( queue.dequeue() );
			}
			// Add an empty line after printing errors
			if( addEmptyLineAfter )
			{
				System.out.println();
			}
			return true;
		}
		return false;
	}

	/**
	 * TagInfo is a helper class that represents an XML tag and its associated line
	 * number. Used to track tags during validation.
	 */
	private static class TagInfo
	{
		String tag;
		int lineNumber;

		TagInfo( String tag, int lineNumber )
		{
			this.tag = tag;
			this.lineNumber = lineNumber;
		}

		@Override
		public String toString()
		{
			return "[Tag: <" + tag + ">, Line: " + lineNumber + "]";
		}
	}
}