/**
 * File: MKZ_UtilsLib::ComplexNumberOperator.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.cnum.op;

import mkz.util.math.cnum.ComplexNumber;
import mkz.util.math.op.ArithmeticOperator;


/**
 * The Class ComplexNumberOperator.
 *
 * @param <T> the generic type
 */
public class ComplexNumberOperator<T>
{
	
	/** The member operator. */
	private ArithmeticOperator<T> mOp = null;
	
	/**
	 * Instantiates a new complex number operator.
	 *
	 * @param aOperationBase the reference operation base
	 */
	public ComplexNumberOperator(ArithmeticOperator<T> aOperationBase)
	{
		mOp = aOperationBase;
	}
	
	/**
	 * Addition.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the complex number
	 */
	public ComplexNumber<T> add(ComplexNumber<T> aA,ComplexNumber<T> aB)
	{
		return new ComplexNumber<T>(mOp.add(aA.getReal(),aB.getReal()), mOp.add(aA.getImaginary(),aB.getImaginary()));
	}
	
	/**
	 * Subtraction.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the complex number
	 */
	public ComplexNumber<T> sub(ComplexNumber<T> aA,ComplexNumber<T> aB)
	{
		return new ComplexNumber<T>(mOp.sub(aA.getReal(),aB.getReal()), mOp.sub(aA.getImaginary(),aB.getImaginary()));
	}

	/**
	 * Multiplication.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the complex number
	 */
	public ComplexNumber<T> mpl(ComplexNumber<T> aA,ComplexNumber<T> aB)
	{
		// (a + bi) (x + yi) = ax + ayi + bxi - by = (ax - by) + (ay + bx)i 
		return new ComplexNumber<T>(mOp.sub(mOp.mpl(aA.getReal(), aB.getReal()), mOp.mpl(aA.getImaginary(), aB.getImaginary())), // ax - by 
				mOp.add(mOp.mpl(aA.getReal(), aB.getImaginary()), mOp.mpl(aA.getImaginary(), aB.getReal()))); // ay + bx
	}
	
	/**
	 * Division.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the complex number
	 */
	public ComplexNumber<T> div(ComplexNumber<T> aA,ComplexNumber<T> aB)
	{
		// ((ax + by) / (x - y^2)) + ((ay + bx) / (x - y^2))
		T lDivisor = mOp.sub(aA.getReal(), mOp.mpl(aB.getImaginary(), aB.getImaginary()));
		return new ComplexNumber<T>(mOp.div(mOp.add(mOp.mpl(aA.getReal(), aB.getReal()), mOp.mpl(aA.getImaginary(), aB.getImaginary())),lDivisor), // (ax + by) / (x - y^2) 
				mOp.div(mOp.add(mOp.mpl(aA.getReal(), aB.getImaginary()), mOp.mpl(aA.getImaginary(), aB.getReal())),lDivisor)); // ay + bx / (x - y^2) 
	}
	
	/**
	 * Magnitude.
	 *
	 * @param aA the reference a
	 * @return the t
	 */
	public T magnitude(ComplexNumber<T> aA)
	{
		// a^2 + b^2
		return mOp.add(mOp.mpl(aA.getReal(),aA.getReal()),mOp.mpl(aA.getImaginary(),aA.getImaginary()));
	}
	
	/**
	 * Conjugate (gets complex conjucated -> a-bi).
	 *
	 * @param aA the reference a
	 * @return the complex number
	 */
	public ComplexNumber<T> conjugate(ComplexNumber<T> aA)
	{
		return new ComplexNumber<T>(aA.getReal(),mOp.sub(aA.getImaginary(),mOp.add(aA.getImaginary(), aA.getImaginary()))); // XXX hacked -b = (b-(b+b))
	}

}
