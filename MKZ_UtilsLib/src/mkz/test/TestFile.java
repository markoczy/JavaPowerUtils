package mkz.test;

import java.util.ArrayList;

import mkz.util.file.FileHandler;

public class TestFile
{

	public static void main(String[] args)
	{
		ArrayList<String> lStrings = FileHandler.loadFileText("C:\\Users\\amark\\Desktop\\cc_fibulib.txt", "#");
		
		for(String iStr:lStrings)
		{
			
			System.out.println("Str: "+iStr+";");
		}
		
	}

}
