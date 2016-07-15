/**
 * File: JCLI_Engine::Command.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.jcli.definition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Command. Represents an executable command.
 *
 * @param <T> the generic type
 */
public class Command<T> implements Comparable<Command<T>>, IExecutable<T>
{
	/** The member pattern. */
	private Pattern mPattern = null;
	
	/** The member priority. */
	private Integer mPriority = 0;
	
	/** The member action. */
	IExecutable<T> mAction = null;
	
	/** The member case sensitive. */
	private boolean mCaseSensitive=false;

	/**
	 * Instantiates a new command.
	 *
	 * @param aRegEx the reference reg ex
	 * @param aAction the reference action
	 * @param aPriority the reference priority
	 * @param matchCase the match case
	 */
	public Command(String aRegEx,IExecutable<T> aAction, Integer aPriority, boolean matchCase)
	{
		mPattern =  matchCase ? Pattern.compile(aRegEx) : Pattern.compile(aRegEx, Pattern.CASE_INSENSITIVE);
		mPriority = aPriority;
		mAction = aAction;
		mCaseSensitive=matchCase;
	}
	
	/**
	 * Instantiates a new command.
	 *
	 * @param aRegEx the reference reg ex
	 * @param aAction the reference action
	 */
	public Command(String aRegEx,IExecutable<T> aAction)
	{
		this(aRegEx,aAction,100,false);
	}

	/**
	 * Checks if the member RegEx matches the call.
	 *
	 * @param aCall the reference call
	 * @return true, if is match
	 */
	public boolean isMatching(String aCall)
	{
		Matcher lMatch = mPattern.matcher(aCall);
		return lMatch.matches();
	}

	/* (non-Javadoc)
	 * @see mkz.util.jcli.definition.IExecutable#exec(java.lang.String[])
	 */
	@Override
	public T exec(String[] aCmdArr) throws Exception
	{
		return mAction.exec(aCmdArr);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Command<T> aCmd)
	{
		return mPriority.compareTo(aCmd.getPriority());
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Integer getPriority()
	{
		return mPriority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param aPriority the new priority
	 */
	public void setPriority(Integer aPriority)
	{
		mPriority = aPriority;
	}

	/**
	 * Checks if is case sensitive.
	 *
	 * @return true, if is case sensitive
	 */
	public boolean isCaseSensitive()
	{
		return mCaseSensitive;
	}

}
