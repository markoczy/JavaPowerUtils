package mkz.test;

import mkz.util.io.IO;

public class TestTryCatch
{

	public static void main(String[] args)
	{
//		IO.tryCatch(()->doSomething(null), (t)->IO.dbOutE(t), null);
		
		IO.dbOutE("Hello");
		
		try
		{
			 doSomething(null);
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
		}
	}
	
	public static void doSomething(Integer aNum)
	{
		aNum+=3;
	}
}
