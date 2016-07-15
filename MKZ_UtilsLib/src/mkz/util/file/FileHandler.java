/**
 * File: MKZ_UtilsLib::FileHandler.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import mkz.util.io.IO;

public class FileHandler
{
	/**
	 * File exists.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public static boolean fileExists(String path)
	{
		try
		{
			File f = new File(path);
			return f.exists();
		}
		catch(Exception e)
		{
			IO.dbOutW("unable to verify for path: "+path);
			return false;
		}
		
	}
	
	/**
	 * Returns a file's binary content as a byte array.
	 *
	 * @param path the path
	 * @return the file stream
	 */
	public static byte[] getFileStream(String path)
	{
		try
		{
			byte[] rVal= Files.readAllBytes(getPath(path));
			if(rVal==null)
			{
				IO.dbOutE("No data could be fetched");
			}
			else
			{
				IO.dbOutV("Found data");
			}
			return rVal;
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
			return null;
		}
	}
	
	/**
	 * Gets the file size.
	 *
	 * @param path the path
	 * @return the file size
	 */
	public static long getFileSize(String path)
	{
		try
		{
			return new File(path).length();
		}
		catch(Exception e)
		{
			IO.dbOutE(e);
			return 0;
		}
	}
	
	/**
	 * Delete file.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public static boolean deleteFile(String path)
	{
		try
		{
			Files.delete(getPath(path));
			return true;
		}
		catch(Exception e)
		{
			IO.dbOutE(e);
			return false;
		}
	}
	
	/**
	 * Copy file.
	 *
	 * @param source the source
	 * @param target the target
	 * @param overwrite the overwrite
	 * @return true, if successful
	 */
	public static boolean copyFile(String source, String target, boolean overwrite)
	{
		try
		{
			if(overwrite)
			{
				Files.copy(getPath(source), getPath(target), StandardCopyOption.REPLACE_EXISTING);
			}
			else
			{
				Files.copy(getPath(source), getPath(target));
			}
			
			return true;
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
			IO.dbOutD("Source was: "+source);
			IO.dbOutD("Target was: "+target);
			return false;
		}
	}
	
	
	/**
	 * Creates the directory.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public static boolean createDirectory(String path)
	{
		try
		{
			if(Files.exists(getPath(path))) return true;
			Files.createDirectory(getPath(path));
			return true;
		}
		catch(Exception e)
		{
			IO.dbOutE(e);
			return false;
		}
	}
	
	
	/**
	 * Wrapped Paths.get() to provide future change (e.g for URI paths)
	 *
	 * @param path the path
	 * @return the path
	 */
	public static Path getPath(String path)
	{
		try
		{
			return Paths.get(path);
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
			return null;
		}
	}
	
	/**
	 * Save data to file.
	 *
	 * @param data the data
	 * @param path the path
	 * @return true, if successful
	 */
	public static boolean saveDataToFile(byte[] data,String path)
	{
		return saveDataToFile(data,path, false);
	}
	
	/**
	 * Save data to file.
	 *
	 * @param data the data
	 * @param path the path
	 * @param overwrite the overwrite
	 * @return true, if successful
	 */
	public static boolean saveDataToFile(byte[] data, String path, boolean overwrite)
	{
		boolean rVal = true;
		
		// If not allowed to overwrite, check if file exists
		if(!overwrite) if(new File(path).exists()) return false;
		
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(path);
			fos.write(data);
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
			rVal=false;
		}
		finally
		{
			IO.tryCatch(fos,(f)->f.close(), null, null);
		}
		
		return rVal;
	}
	
	/**
	 * Loads file into text.
	 *
	 * @param aPath the reference path
	 * @param aIgnorePrefix the reference ignore prefix
	 * @return the array list
	 */
	public static ArrayList<String> loadFileText(String aPath, String aIgnorePrefix)
	{
		return loadFileText(aPath, aIgnorePrefix, Charset.defaultCharset(), true);
	}
	
	/**
	 * Loads file into text.
	 *
	 * @param aPath the reference path
	 * @param aIgnorePrefix the reference ignore prefix
	 * @param aEncoding the reference encoding
	 * @param auto trim whitespace at end + begin
	 * @return the array list
	 */
	public static ArrayList<String> loadFileText(String aPath, String aIgnorePrefix, Charset aEncoding, boolean autoTrim)
	{
		try
		{
			List<String> lLines = Files.readAllLines(getPath(aPath), aEncoding);
			
			ArrayList<String> rLines = new ArrayList<String>();
			for(String iLine:lLines)
			{
				String lTrimmed = iLine.trim();
				if(lTrimmed.length()>0 && !lTrimmed.startsWith(aIgnorePrefix)) rLines.add(autoTrim ? lTrimmed : iLine);
			}
			
			return rLines;
		}
		catch (IOException e)
		{
			IO.dbOutE(e);
			return null;
		}
	}
	
}
