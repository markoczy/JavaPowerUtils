package mkz.util.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

import mkz.util.io.definition.sysout.IIOPrintFormatOverride;
import mkz.util.io.definition.sysout.IIOPrintOverride;
import mkz.util.io.definition.trycatch.IIOTryConsumer;
import mkz.util.io.definition.trycatch.IIOTryProcessor;

public class IO
{
	private static Scanner scanner=null;

	/** The Constant NEWLINE. Platform specific newline char */
	private static final String NEWLINE = System.lineSeparator();
//	private static final String EMPTY = "";
	private static final String SPACE = " ";

	public static class Options
	{
		// Constants
		//
		public static final int LOG_FATAL=0;
		public static final int LOG_ERROR=1;
		public static final int LOG_WARN=2;
		public static final int LOG_DEBUG=3;
		public static final int LOG_VERBOSE=4;
		
		// Options
		//
		public static int log_level = LOG_DEBUG;
		public static IIOPrintOverride debug_print_override = null;
		public static IIOPrintFormatOverride print_format_override = null;
		public static IIOPrintOverride normal_print_override = null;
		public static int line_wrap = 0;
		public static boolean word_wrap = false;
		public static int tab_size = 8;
		
//		public static int tab_output_width = 8;
		
	}

	public static String jIn()
	{
		return jIn(null);
	}
	
	public static String jIn(String aText)
	{
		return jIn(aText,true);
	}
	
	public static String jIn(String aText, boolean newLine)
	{
		if(aText!=null) jOut(aText, newLine);
		return _getScanner().nextLine();
	}
	
	public static Object jIn(String aText, Type aType)
	{
		return jIn(aText,aType,true);
	}
	
	public static Object jIn(String aText, Type aType, boolean newLine)
	{
		if(aText!=null) jOut(aText,newLine);
		switch(aType)
		{
			case STRING: _getScanner().nextLine();
			case INTEGER: _getScanner().nextInt();
			case DOUBLE: _getScanner().nextDouble();
			case BOOLEAN: _getScanner().nextBoolean();
			case BYTE:  _getScanner().nextByte();
			case FLOAT: _getScanner().nextFloat();
			case LONG: _getScanner().nextLong();
			case SHORT: _getScanner().nextShort();
			case BIGDECIMAL: _getScanner().nextBigDecimal();
			case BIGINTEGER: _getScanner().nextBigInteger();
			default: return _getScanner().nextLine();
		}
	}
	
	public static void jOut(String aText)
	{
		jOut(aText, true);
	}
	
	public static void jOut(String aText, boolean newLine)
	{
		jOut(aText, newLine, false);
	}
	
	public static void jOut(String aText, boolean newLine, boolean showTrace)
	{
		StringBuilder sb = new StringBuilder();
		if(showTrace) sb.append(_getTraceStr(4));
		sb.append(aText);
		
		if(Options.normal_print_override!=null) Options.normal_print_override.print(sb.toString());
		else 
		{
			ArrayList<String> lLines = _getWrappedLines(sb.toString());
			for(String iLine:lLines) System.out.println(iLine);
			if(newLine) System.out.println();
		}
	}
	
//	public static void dbOut(){}
//	
//	public static void dbOutV(){}
//	public static void dbOutD(){}
//	public static void dbOutW(){}
//	public static void dbOutE(){}
//	public static void dbOutF(){}
	
	
	
	public static enum Type
	{
		STRING,INTEGER,DOUBLE,BOOLEAN,BYTE,FLOAT,LONG,SHORT,BIGDECIMAL,BIGINTEGER
	}
	
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Verbose
	 *
	 * @param message the message
	 */
	public static void dbOutV(String aMessage)
	{
		_dbOut(aMessage, Options.LOG_VERBOSE);
	}
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Debug
	 *
	 * @param message the message
	 */
	public static void dbOutD(String aMessage)
	{
		_dbOut(aMessage, Options.LOG_DEBUG);
	}
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Warning
	 *
	 * @param message the message
	 */
	public static void dbOutW(String aMessage)
	{
		_dbOut(aMessage, Options.LOG_WARN);
	}
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Error
	 *
	 * @param message the message
	 */
	public static void dbOutE(String aMessage)
	{
		_dbOut(aMessage, Options.LOG_ERROR);
	}
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Error
	 *
	 * @param t the message
	 */
	public static void dbOutE(Throwable t)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Exception thrown: "+t+", Message: "+t.getMessage()+NEWLINE);
		sb.append(NEWLINE+"Exception details: ");
		sb.append(_getStackTrace(t));
		
		_dbOut(sb.toString(), Options.LOG_ERROR);
	}
	
	
	/**
	 * Output current method tree and a message text, 
	 * log-level: Fatal Error (will always be shown)
	 *
	 * @param t the message
	 */
	public static void dbOutF(Throwable t)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Exception thrown: "+t+", Message: "+t.getMessage()+NEWLINE);
		sb.append(NEWLINE+"Exception details: ");
		sb.append(_getStackTrace(t));
		
		_dbOut(sb.toString(), Options.LOG_FATAL);
	}

	/**
	 * Output current method tree and a message text, 
	 * log-level: Fatal Error (will always be shown)
	 *
	 * @param message the message
	 */
	public static void dbOutF(String aMessage)
	{
		_dbOut(aMessage, Options.LOG_FATAL);
	}
	
//	public interface ITryConsumer
//	{
//		public <T> void process(T aValue) throws Exception;
//	}
//
//	public interface ITryProcessor
//	{
//		public <T> void process(T aValue) throws Exception;
//	}
	
	public static boolean tryCatch(IIOTryProcessor aTry, Consumer<Exception> aCatch,Runnable aFinally)
	{
		boolean rVal = false;
		try
		{
			if(aTry != null) aTry.process();
			rVal=true;
		}
		catch (Exception e)
		{
			if(aCatch != null) aCatch.accept(e);
		}
		finally
		{
			if(aFinally != null) aFinally.run();
		}
		return rVal;
	}
	
	
	public static <T> boolean tryCatch(T aValue,IIOTryConsumer<T> aTry,Consumer<Exception> aCatch,Consumer<T> aFinally)
	{
		boolean rVal = false;
		try
		{
			if(aTry != null) aTry.process(aValue);
		}
		catch (Exception e)
		{
			if(aCatch != null) aCatch.accept(e);
		}
		finally
		{
			if(aFinally != null) aFinally.accept(aValue);
		}
		return rVal;
	}
	
	/**
	 * System out - Shows current method tree
	 * and a debug message (if provided that the Application
	 * error level is greater or equal to the message error level)
	 *
	 * @param message the message
	 * @param errorLevel the error level
	 */
	private static void _dbOut(String aMessage, int errorLevel)
	{
		if(Options.log_level<errorLevel)
		{
			return;
		}
		
		StringBuilder msg = new StringBuilder();
		
		if(Options.print_format_override != null) 
		{
			StackTraceElement lStTr=_getTrace(4);
			if(lStTr!=null)	msg.append(Options.print_format_override.format(errorLevel, lStTr.getClassName(), lStTr.getMethodName(), lStTr.getLineNumber()));
			else msg.append(Options.print_format_override.format(errorLevel, null, null, null));
		}
		else
		{
			msg.append(getErrorLvTxt(errorLevel));
			msg.append(".");
			msg.append(_getTraceStr(5));
			msg.append(aMessage);
		}
		
		
		if(Options.debug_print_override!=null) Options.debug_print_override.print(msg.toString());
		else
		{
			ArrayList<String> lLines = _getWrappedLines(msg.toString());
			for(String iLine:lLines) System.out.println(iLine);
		}
	}
	
	private static StackTraceElement _getTrace(int aOffset)
	{
		StackTraceElement[] el=Thread.currentThread().getStackTrace();

		if(el.length>aOffset)
		{
			return el[aOffset];
		}
		else return null;
	}
	
	private static String _getTraceStr(int aOffset)
	{
		StringBuilder msg = new StringBuilder();

		StackTraceElement lStTr=_getTrace(aOffset);
		if(lStTr!=null)
		{
			msg.append(lStTr.getClassName());
			msg.append("::"+lStTr.getMethodName());
			msg.append("["+lStTr.getLineNumber()+"]");
		}
		msg.append(": ");
		
		return msg.toString();
	}
	
	/**
	 * Gets the error level text.
	 *
	 * @param level the level
	 * @return the error level text
	 */
	private static String getErrorLvTxt(int aLevel)
	{
		switch(aLevel)
		{
		case(Options.LOG_VERBOSE):
			return "Verbose"; 
		case(Options.LOG_DEBUG):
			return "Debug"; 
		case(Options.LOG_WARN):
			return "WARNING"; 
		case(Options.LOG_ERROR):
			return "ERROR"; 
		case(Options.LOG_FATAL):
			return "!!FATAL!!"; 
		default:
			return "<UNKNOWN>"; 
		}
	}
	
	private static String _getStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	
	private static Scanner _getScanner()
	{
		if(scanner == null) scanner = new Scanner(System.in);
		return scanner;
	}
	
	public static ArrayList<String> _getWrappedLines(String aStr)
	{
		ArrayList<String> rVal = new ArrayList<String>();
		
		for(String iStr:aStr.split(NEWLINE))
		{
			if(Options.line_wrap<=0)
			{
				rVal.add(iStr);
			}
			else
			{
				String lStr=_replaceTabulators(iStr);
				
				if(Options.word_wrap)
				{
					
					while(lStr.length() > Options.line_wrap+1)
					{
						int lIdx=lStr.indexOf(SPACE);
						int lLastIdx=0;
						
						while(lIdx!=-1  && lIdx < Options.line_wrap+1 && lIdx < lStr.length()-1)
						{
							lLastIdx=lIdx;
							lIdx=lStr.indexOf(SPACE,lIdx+1);
						}
						
						if(lLastIdx==0 /*&& (lIdx>=Options.line_wrap+1 || lIdx==-1)*/)
						{
							rVal.add(lStr.substring(0, Options.line_wrap));
							lStr=lStr.substring(Options.line_wrap);						
						}
						else
						{
							rVal.add(lStr.substring(0,lLastIdx));
							lStr=lStr.substring(lLastIdx+1);
						}
						
					}
					
					if(lStr.length()>0) rVal.add(lStr);
				}
				else
				{
					while(lStr.length()>Options.line_wrap)
					{
						rVal.add(lStr.substring(0, Options.line_wrap));
						lStr=lStr.substring(Options.line_wrap);
//						System.out.println("New String is: "+lStr);
					}
					
					rVal.add(lStr);
				}
			}
		}

		return rVal;
	}
	
	private static String _replaceTabulators(String aStr)
	{
		return aStr.replaceAll("\t", _getTabWS());
		
	}
	
	// Dynamic update...
	private static String tabReplacement = null;
	private static String _getTabWS()
	{
		if(tabReplacement==null || tabReplacement.length()<Options.tab_size)
		{
			tabReplacement = "";
			for(int i = 0;i<Options.tab_size;i++) tabReplacement+=" ";
		}
		return tabReplacement;
	}

}
