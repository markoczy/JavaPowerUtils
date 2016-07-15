/**
 * File: JCLI_Engine::IExecutable.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.jcli.definition;

/**
 * The Interface IExecutable. Represents an executable element. Each executable element
 * is defined by a set of input values and a return value of the generic type.
 *
 * @param <T> the generic type
 */
@FunctionalInterface
public interface IExecutable<T>
{
	/**
	 * Executes a Command and returns the result. ATTENTION: Be aware that the first 
	 * argument of the command array is the call string.
	 *
	 * @param aCmdArr the reference cmd array (first argument is the call string)
	 * @return the t
	 * @throws Exception the exception
	 */
	public T exec(String[] aCmdArr) throws Exception;
}
