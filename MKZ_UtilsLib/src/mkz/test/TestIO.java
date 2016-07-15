package mkz.test;

import java.util.ArrayList;

import mkz.util.io.IO;

public class TestIO
{

	public static void main(String[] args)
	{
//		IO.Options.PRINT_FORMAT_OVERRIDE=(aLogLevel, aClass, aMethod, aLineNumber)-> {return "CL: "+aClass+" MT: "+aMethod;};
//		IO.Options.DEBUG_PRINT_OVERRIDE = System.out::print; // expected: all in one line
		IO.Options.word_wrap=true;
		IO.Options.line_wrap=30;
		
		//		IO.jIn("hello", new Integer());
		
		
//		IO.dbOut("Hello", 1);
		IO.jOut("Hello");
		
		
		try
		{
			doSomething(null);
		}
		catch (Exception lE)
		{
			IO.dbOutE(lE);
		}
		
		IO.tryCatch(2,(v)->{doSomething((Integer)v);}, (e) -> IO.dbOutE(e), null); // one fucking line to replace all the upper!
		
		
//		IO.dbOutD("Hello");
		printSt();
		IO.Options.line_wrap=10;
		
		String lTestStr = "hello world hello world hello world hello world thiswordistoolongtobewrapped";
		ArrayList<String> lArr=IO._getWrappedLines(lTestStr);
		
		for(String iStr:lArr)
		{
			IO.jOut(iStr);
		}
		
	}

	public static void doSomething(Integer aValue)
	{
		int bla = aValue+3;
		IO.jOut("bla = "+bla, true);
	}
	
	private static void printSt()
	{
		IO.dbOutD("Hello");
		IO.dbOutD("Hello");
		IO.dbOutD("Hello");
		
	}
}
