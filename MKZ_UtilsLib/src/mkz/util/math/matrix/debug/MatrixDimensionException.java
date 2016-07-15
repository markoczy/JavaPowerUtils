/**
 * File: MKZ_UtilsLib::MatrixDimensionException.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.matrix.debug;

/**
 * The Class MatrixDimensionException. Is thrown when there's a dimension mismatch or an access
 * to an invalid field.
 */
public class MatrixDimensionException extends MatrixException
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new matrix dimension exception.
	 */
	public MatrixDimensionException() { super();}
	
	/**
	 * Instantiates a new matrix dimension exception.
	 *
	 * @param aMsg the reference msg
	 */
	public MatrixDimensionException(String aMsg) { super(aMsg);}
}
