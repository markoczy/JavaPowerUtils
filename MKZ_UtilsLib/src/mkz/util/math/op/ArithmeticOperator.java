/**
 * File: MKZ_UtilsLib::ArithmeticOperator.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.op;

import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * The Class ArithmeticOperator. This class is the essential tool to perform arithmetic operations in a completely generic way. 
 * It is used as standalone tool {@link mkz.util.math.AOP} or as a part of more complex operators. 
 *
 * @param <T> the generic type
 */
public class ArithmeticOperator<T>
{
	/** The member add function. */
	protected BiFunction<T,T,T> mAdd = null; 
	
	/** The member sub function. */
	protected BiFunction<T,T,T> mSub = null; 
	
	/** The member mpl function. */
	protected BiFunction<T,T,T> mMpl = null; 
	
	/** The member div function. */
	protected BiFunction<T,T,T> mDiv = null; 
	
	/** The member modulus function. */
	protected BiFunction<T,T,T> mMod = null; 
	
	/** The member compare function. */
	protected Comparator<T> mComp = null; 
	
	/** The member zero. */
	protected T mZero = null; 
	
	/** The member one. */
	protected T mOne = null;
	
	/**
	 * Instantiates a new arithmetic operator. The constructor requires the basic arithmetic
	 * operations as well as a few constants to be able to do maths with a generic type.
	 *
	 * @param aZeroValue the reference zero value (e.g. 0)
	 * @param aOneValue the reference one value (e.g. 0)
	 * @param aAddFunction the reference add function (e.g. a+b)
	 * @param aSubtractFunction the reference subtract function (e.g. a-b)
	 * @param aMultiplyFunction the reference multiply function (e.g. a*b)
	 * @param aDivisionFunction the reference division function (e.g. a/b)
	 * @param aModulusFunction the reference modulus function (e.g. a%b)
	 * @param aNumberComparator the reference number comparator (e.g. Integer::compareTo)
	 */
	public ArithmeticOperator 
	///////////////////////////////////////// 
	(
		T 					aZeroValue, 
		T 					aOneValue, 
		BiFunction<T,T,T> 	aAddFunction, 
		BiFunction<T,T,T> 	aSubtractFunction, 
		BiFunction<T,T,T> 	aMultiplyFunction, 
		BiFunction<T,T,T> 	aDivisionFunction, 
		BiFunction<T,T,T> 	aModulusFunction, 
		Comparator<T> 		aNumberComparator 
	)
	/////////////////////////////////////////
	{
		mAdd=aAddFunction; 
		mSub=aSubtractFunction; 
		mMpl=aMultiplyFunction; 
		mDiv=aDivisionFunction; 
		mMod=aModulusFunction; 
		mOne=aOneValue; 
		mZero=aZeroValue; 
		mComp=aNumberComparator; 
	} 
	
	/**
	 * Instantiates a new arithmetic operator. Reference constructor.
	 *
	 * @param aOperator the reference operator
	 */
	public ArithmeticOperator(ArithmeticOperator<T> aOperator)
	{
		mAdd=aOperator.mAdd;
		mSub=aOperator.mSub; 
		mMpl=aOperator.mMpl; 
		mDiv=aOperator.mDiv; 
		mMod=aOperator.mMod; 
		mOne=aOperator.mOne; 
		mZero=aOperator.mZero; 
		mComp=aOperator.mComp; 
	}
	
	/////////////////////////////////////////////////
	// Basic Functions 
	/////////////////////////////////////////////////
	
	/**
	 * Adds the.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 */
	// 
	public T add(T aA, T aB){ return mAdd.apply(aA, aB); } 
	
	/**
	 * Sub.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 */
	public T sub(T aA, T aB){ return mSub.apply(aA, aB); } 
	
	/**
	 * Mpl.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 */
	public T mpl(T aA, T aB){ return mMpl.apply(aA, aB); } 
	
	/**
	 * Div.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 */
	public T div(T aA, T aB){ return mDiv.apply(aA, aB); } 
	
	/**
	 * Mod.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 */
	public T mod(T aA, T aB){ return mMod.apply(aA, aB); } 
	
	/**
	 * Compare.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the int
	 */
	public int compare(T aA, T aB){ return mComp.compare(aA, aB); } 
	
	/**
	 * Gets the zero.
	 *
	 * @return the zero
	 */
	public T getZero(){ return mZero; } 
	
	/**
	 * Gets the one.
	 *
	 * @return the one
	 */
	public T getOne(){ return mOne; } 

	/////////////////////////////////////////////////
	// Extended Functions 
	/////////////////////////////////////////////////
	
	/**
	 * Max.
	 *
	 * @param aArr the reference array
	 * @return the t
	 */
	public T max(T[] aArr) 
	{ 
		T lMax = getZero(); 
		for(T iArr:aArr) 
		{ 
			if(mComp.compare(lMax, iArr)<0) lMax=iArr; 
		} 
		return lMax; 
	} 
	 
	/**
	 * Min.
	 *
	 * @param aArr the reference array
	 * @return the t
	 */
	public T min(T[] aArr) 
	{ 
		T lMin = aArr[0]; 
		for(T iArr:aArr) 
		{ 
			if(mComp.compare(lMin, iArr)>0) lMin=iArr; 
		} 
		return lMin;
	} 
	 
	/**
	 * Gcd.
	 *
	 * @param aA the reference a
	 * @param aB the reference b
	 * @return the t
	 * @throws ArithmeticException the arithmetic exception
	 */
	// a.k.a. Euclid algo 
	public T gcd(T aA, T aB) throws ArithmeticException // @java.lang
	{ 
		if(isNegative(aA) || isNegative(aB)) throw new ArithmeticException("Value cannot be negative: a="+aA+"; b="+aB); 
		if(isFractal(aA) || isFractal(aB)) throw new ArithmeticException("Value cannot be fractal: a="+aA+"; b="+aB); 
		 
		T lGV = mComp.compare(aA, aB)>0 ? aA : aB; 
		T lLV = mComp.compare(aA, aB)>0 ? aB : aA; 
		 
		while(!getZero().equals(lLV)) 
		{ 
			T lBuf = lLV; 
			lLV = mod(lGV,lLV); 
			lGV = lBuf; 
		} 
	 
		return lGV; 
	}
	
	/**
	 * Gcd.
	 *
	 * @param aArr the reference array
	 * @return the t
	 */
	public T gcd(T[] aArr)
	{
		if(aArr.length==0) return getZero();
		
		T lGcd = aArr[0];
		for(int i=1;i<aArr.length;i++) lGcd = gcd(lGcd,aArr[i]);
		return lGcd;
	}
	
	/**
	 * Abs.
	 *
	 * @param aA the reference a
	 * @return the t
	 */
	public T abs(T aA)
	{
		//
		// XXX a bit hacky, but in return we don't have to ask the user for an abs() function
		//
		if(mComp.compare(aA,getZero())<0) return sub(aA, add(aA,aA));
		else return aA;
	}
	
	/**
	 * Checks if is negative.
	 *
	 * @param aA the reference a
	 * @return true, if is negative
	 */
	public boolean isNegative(T aA) 
	{ 
		return mComp.compare(aA, getZero())<0; 
	} 
	 
	/**
	 * Checks if is fractal.
	 *
	 * @param aA the reference a
	 * @return true, if is fractal
	 */
	public boolean isFractal(T aA) 
	{ 
		// How to define if a value is fractal? When mod(a,1) is not zero. 
		return mComp.compare(getZero(), mMod.apply(aA, getOne())) != 0; 
	}
	
	
}
