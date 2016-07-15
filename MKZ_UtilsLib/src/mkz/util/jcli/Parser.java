/**
 * File: JCLI_Engine::Parser.java
 *
 * @author Aleistar Markóczy
 * 
 */

package mkz.util.jcli;

import java.util.ArrayList;
import java.util.Collections;

import mkz.util.jcli.debug.JCLI_CommandNotFoundEx;
import mkz.util.jcli.debug.JCLI_Exception;
import mkz.util.jcli.definition.Command;
import mkz.util.jcli.definition.IExecutable;

/**
 * The Class Parser. The parser can be used to define a set of commands (using method "addCommand").
 * The commands can then be called by the "exec" function (with first argument matching the commands RegEx).
 *
 * @param <T> the generic type
 */
public class Parser<T> implements IExecutable<T>
{
	/** The member commands. */
	private ArrayList<Command<T>> mCommands= new ArrayList<Command<T>>();

	/**
	 * Instantiates a new parser.
	 */
	public Parser(){}

	/**
	 * Adds the command. Minimal setup (no priority, case insensitive).
	 *
	 * @param aRegEx the reference reg ex
	 * @param aAction the reference action
	 * @return true, if successful
	 */
	public boolean addCommand(String aRegEx,IExecutable<T> aAction)
	{
		return addCommand(aRegEx,aAction,0,false);
	}

	/**
	 * Adds the command. Normal setup.
	 *
	 * @param aRegEx the reference regular expression
	 * @param aAction the reference action
	 * @param aPriority the reference priority
	 * @param matchCase the match case
	 * @return true, if successful
	 */
	public boolean addCommand(String aRegEx,IExecutable<T> aAction, Integer aPriority, boolean matchCase)
	{
		Command<T> lCmd = new Command<T>(aRegEx, aAction, aPriority, matchCase);
		mCommands.add(lCmd);
		Collections.sort(mCommands);
		return true;
	}
	
	/**
	 * Adds the command. Takes a command element.
	 *
	 * @param aCommand the reference command
	 * @return true, if successful
	 */
	public boolean addCommand(Command<T> aCommand)
	{
		mCommands.add(aCommand);
		Collections.sort(mCommands);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mkz.util.jcli.IExecutable#exec(java.lang.String[])
	 */
	@Override
	public T exec(String[] aCmdArr) throws Exception
	{
		if (aCmdArr == null) throw new JCLI_Exception("Command array is null");
		if (aCmdArr.length == 0) throw new JCLI_Exception("Command array has no Elements");

		Command<T> lCmd = _getCommand(aCmdArr[0]);
		if(lCmd==null) throw new JCLI_CommandNotFoundEx("No command matching to key '" + aCmdArr[0] + "' found");
		return lCmd.exec(aCmdArr);
	}
	
	/**
	 * [restricted] get matching command.
	 *
	 * @param aInput the reference input
	 * @return the command
	 */
	private Command<T> _getCommand(String aInput)
	{
		for(Command<T> iCmd:mCommands)
		{
			if(iCmd.isMatching(aInput)) return iCmd;
		}
		
		return null;
	}

}
