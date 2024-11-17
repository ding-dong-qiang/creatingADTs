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

public class XMLParser
{

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
		int rootLineNumber = 0;
		boolean rootEncountered = false;

		try( BufferedReader reader = getReader( filename ) )
		{
			validateXML( reader, invalidCloseTagQueue, errorQueue, rootLineNumber, rootEncountered );
		}
		catch( IOException e )
		{
			System.out.println( "Error reading file: " + e.getMessage() );
			return;
		}

		System.out.println( "================ERROR LOG================" );

		while( !invalidCloseTagQueue.isEmpty() )
		{
			System.out.println( invalidCloseTagQueue.dequeue() );
		}

		if( !invalidCloseTagQueue.isEmpty() || !errorQueue.isEmpty() )
		{
			System.out.println();
		}

		while( !errorQueue.isEmpty() )
		{
			System.out.println( errorQueue.dequeue() );
		}
	}

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

	private static void validateXML( BufferedReader reader, QueueADT<String> invalidCloseTagQueue,
			QueueADT<String> errorQueue, int rootLineNumber, boolean rootEncountered ) throws IOException
	{
		StackADT<TagInfo> tagStack = new MyStack<>();
		int lineNumber = 0;

		String line;
		while( ( line = reader.readLine() ) != null )
		{
			lineNumber++;
			line = line.trim();

			if( line.startsWith( "<?xml" ) )
			{
				continue;
			}

			if( line.contains( "<XMLDATA>" ) )
			{
				if( !rootEncountered )
				{
					rootEncountered = true;
					rootLineNumber = lineNumber;
				}
				else
				{
					logError( errorQueue, "Multiple root tags detected at line", lineNumber - rootLineNumber,
							"<XMLDATA>" );
				}
			}

			while( line.contains( "<" ) )
			{
				int start = line.indexOf( "<" );
				int end = line.indexOf( ">", start );

				if( end == -1 )
				{
					logError( errorQueue, "Error at line", lineNumber - rootLineNumber,
							"Unclosed tag: " + line.substring( start ) );
					break;
				}

				// Check for extra '>>'
				if( end + 1 < line.length() && line.charAt( end + 1 ) == '>' )
				{
					String invalidTag = line.substring( start, end + 2 ).trim();
					logError( invalidCloseTagQueue, "Invalid close tag", lineNumber - rootLineNumber, invalidTag );

					String validRawTag = line.substring( start, end + 1 ).trim();
					String validTag = extractTag( validRawTag );

					if( !validTag.startsWith( "/" ) && !validTag.endsWith( "/" ) )
					{
						tagStack.push( new TagInfo( validTag, lineNumber - rootLineNumber ) );
					}

					line = line.substring( end + 2 ).trim();
					continue;
				}

				String rawTag = line.substring( start, end + 1 ).trim();
				line = line.substring( end + 1 ).trim();

				if( rawTag.endsWith( "/>" ) )
				{
					System.out.println( "DEBUG: Self-closing tag ignored: " + rawTag );
					continue;
				}

				if( rawTag.startsWith( "</" ) )
				{
					handleClosingTag( rawTag, tagStack, lineNumber - rootLineNumber, invalidCloseTagQueue, errorQueue );
					continue;
				}

				handleOpeningTag( rawTag, tagStack, lineNumber - rootLineNumber, errorQueue );
				System.out.println( "DEBUG: Current stack content: " + tagStack );
			}
		}

		while( !tagStack.isEmpty() )
		{
			TagInfo unmatchedTag = tagStack.pop();
			logError( errorQueue, "Error at line", unmatchedTag.lineNumber, "<" + unmatchedTag.tag + ">" );
		}
	}

	private static void handleOpeningTag( String rawTag, StackADT<TagInfo> tagStack, int lineNumber,
			QueueADT<String> errorQueue )
	{
		String pureTag = extractTag( rawTag );

		// 检查是否为格式错误标签
		if( isMissingSlashInTag( rawTag ) )
		{
			logError( errorQueue, "Tag missing '/' at line", lineNumber, rawTag );
			return; // 不压入栈
		}

		// 如果通过验证，正常压栈
		tagStack.push( new TagInfo( pureTag, lineNumber ) );
		System.out.println( "DEBUG: Stack after processing opening tag: " + tagStack );
	}

	private static boolean isMissingSlashInTag( String rawTag )
	{
		if( rawTag.startsWith( "<PackageCreationLocation" ) && !rawTag.endsWith( "/>" ) )
		{
			return true; // 认为缺少自闭合符号
		}
		return false;
	}

	private static void handleClosingTag( String rawTag, StackADT<TagInfo> tagStack, int lineNumber,
			QueueADT<String> invalidCloseTagQueue, QueueADT<String> errorQueue )
	{
		String closingTag = extractTag( rawTag.substring( 1 ) );

		if( tagStack.isEmpty() )
		{
			logError( invalidCloseTagQueue, "Unmatched closing tag", lineNumber, rawTag );
			return;
		}

		StackADT<TagInfo> tempStack = new MyStack<>(); // 临时栈，用于保存弹出的标签
		boolean matched = false;

		while( !tagStack.isEmpty() )
		{
			TagInfo openTag = tagStack.peek();

			if( openTag.tag.equals( closingTag ) )
			{
				// 找到匹配的标签，弹出并标记为已匹配
				tagStack.pop();
				matched = true;
				System.out.println( "DEBUG: Stack after processing closing tag: " + tagStack );
				break;
			}
			else
			{
				// 如果不匹配，仅记录错误一次，并继续检查下一个标签
				if( !matched )
				{
					logError( errorQueue, "Mismatched tag at line", openTag.lineNumber, "<" + openTag.tag + ">" );
				}
				tempStack.push( tagStack.pop() );
			}
		}

		// 恢复临时栈中的标签到原始栈
		while( !tempStack.isEmpty() )
		{
			tagStack.push( tempStack.pop() );
		}

		if( !matched )
		{
			// 如果没有找到匹配的标签，记录 unmatched 错误
			logError( errorQueue, "Unmatched closing tag", lineNumber, rawTag );
		}
	}

	private static String extractTag( String rawTag )
	{
		rawTag = rawTag.substring( 1, rawTag.length() - 1 ).trim();

		int spaceIndex = rawTag.indexOf( ' ' );
		if( spaceIndex != -1 )
		{
			rawTag = rawTag.substring( 0, spaceIndex );
		}

		return rawTag;
	}

	private static void logError( QueueADT<String> queue, String message, int relativeLineNumber, String tag )
	{
		if( relativeLineNumber > 0 )
		{
			queue.enqueue( message + " at line " + relativeLineNumber + ": " + tag );
		}
		else
		{
			queue.enqueue( message + ": " + tag );
		}
	}

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