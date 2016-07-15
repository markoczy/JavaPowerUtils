/**
 * File: MKZ_UtilsLib::ComplexNumber.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.cnum;

/**
 * The Class ComplexNumber. Form a+bi.
 *
 * @param <T> the generic type
 */
public class ComplexNumber<T>
{
	
	/** The member real part. */
	T mReal = null;
	
	/** The member imaginary part. */
	T mImaginary = null;
	
	/**
	 * Instantiates a new complex number.
	 *
	 * @param aReal the reference real
	 * @param aImaginary the reference imaginary
	 */
	public ComplexNumber(T aReal, T aImaginary)
	{
		mReal=aReal;
		mImaginary=aImaginary;
	}

	/**
	 * Gets the real part (a element R).
	 *
	 * @return the real
	 */
	public T getReal(){ return mReal; }
	
	/**
	 * Gets the imaginary part (b element R).
	 *
	 * @return the imaginary
	 */
	public T getImaginary(){ return mImaginary; }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "{RE:"+mReal.toString()+";IM:"+mImaginary.toString()+"}";
	}
}
