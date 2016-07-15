/**
 * File: MKZ_UtilsLib::AOP.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math;

import java.math.BigDecimal;

import mkz.util.math.cnum.op.ComplexNumberOperator;
import mkz.util.math.matrix.op.MatrixOperator;
import mkz.util.math.op.ArithmeticOperator;

/**
 * ---------------------------------------------------------------------------------<br/>
 * AOP - The Arithmetic OPeration database for standard java types.<br/>
 * ---------------------------------------------------------------------------------<br/>
 * 
 * Supported Operators:<p>
 * 
 * {@link mkz.util.math.AOP#BYTE AOP::BYTE} : Byte number operator<br/>
 * {@link mkz.util.math.AOP#INT AOP::INT} : Integer number operator<br/>
 * {@link mkz.util.math.AOP#DOUBLE AOP::DOUBLE} : Double number operator<br/>
 * {@link mkz.util.math.AOP#FLOAT AOP::FLOAT} : Float number operator<br/>
 * {@link mkz.util.math.AOP#BIG_DECIMAL AOP::BIG_DECIMAL} : BigDecimal number operator<p>
 * 
 * More advanced Operators:<p>
 * 
 * {@link mkz.util.math.AOP.MTX AOP::MTX} : Matrix Operators<br/>
 * {@link mkz.util.math.AOP.C AOP::C} : Complex number Operators<br/>
 */
public class AOP
{
	/** Predefined Operator for Operations of the java type Byte. */
	public static final ArithmeticOperator<Byte> BYTE = new ArithmeticOperator<Byte>((byte)0,(byte)1,(a,b)->(byte)(a+b),(a,b)->(byte)(a-b),(a,b)->(byte)(a*b),(a,b)->(byte)(a/b),(a,b)->(byte)(a%b), Byte::compare); 
	
	/** Predefined Operator for Operations of the java type Integer. */
	public static final ArithmeticOperator<Integer> INT = new ArithmeticOperator<Integer>(0,1,(a,b)->a+b,(a,b)->a-b,(a,b)->a*b,(a,b)->a/b,(a,b)->a%b, Integer::compare); 
	
	/** Predefined Operator for Operations of the java type Double. */
	public static final ArithmeticOperator<Double> DOUBLE = new ArithmeticOperator<Double>(0d,1d,(a,b)->a+b,(a,b)->a-b,(a,b)->a*b,(a,b)->a/b,(a,b)->a%b, Double::compare); 
	
	/** Predefined Operator for Operations of the java type Float. */
	public static final ArithmeticOperator<Float> FLOAT = new ArithmeticOperator<Float>(0f,1f,(a,b)->a+b,(a,b)->a-b,(a,b)->a*b,(a,b)->a/b,(a,b)->a%b, Float::compare); 
	
	/** Predefined Operator for Operations of the java type BigDecimal. */
	public static final ArithmeticOperator<BigDecimal> BIG_DECIMAL = new ArithmeticOperator<BigDecimal>(BigDecimal.ZERO,BigDecimal.ONE,BigDecimal::add,BigDecimal::subtract,BigDecimal::multiply,BigDecimal::divide,BigDecimal::remainder, BigDecimal::compareTo); 

	/**
	 * MTX - Matrix Operators.<p>
	 * 
	 * {@link mkz.util.math.AOP.MTX#BYTE AOP::MTX::BYTE} : Byte matrix operator<br/>
	 * {@link mkz.util.math.AOP.MTX#INT AOP::MTX::INT} : Integer matrix operator<br/>
	 * {@link mkz.util.math.AOP.MTX#DOUBLE AOP::MTX::DOUBLE} : Double matrix operator<br/>
	 * {@link mkz.util.math.AOP.MTX#FLOAT AOP::MTX::FLOAT} : Float matrix operator<br/>
	 * {@link mkz.util.math.AOP.MTX#BIG_DECIMAL AOP::MTX::BIG_DECIMAL} : BigDecimal matrix operator<p>
	 */
	public static final class MTX
	{
		/** Predefined Operator for Operations with Matrices of the java type Byte. */
		public static final MatrixOperator<Byte> BYTE = new MatrixOperator<Byte>(AOP.BYTE); 
		
		/** Predefined Operator for Operations with Matrices of the java type Integer. */
		public static final MatrixOperator<Integer> INT = new MatrixOperator<Integer>(AOP.INT); 
		
		/** Predefined Operator for Operations with Matrices of the java type Double. */
		public static final MatrixOperator<Double> DOUBLE = new MatrixOperator<Double>(AOP.DOUBLE); 
		
		/** Predefined Operator for Operations with Matrices of the java type Float. */
		public static final MatrixOperator<Float> FLOAT = new MatrixOperator<Float>(AOP.FLOAT); 
		
		/** Predefined Operator for Operations with Matrices of the java type BigDecimal. */
		public static final MatrixOperator<BigDecimal> BIG_DECIMAL = new MatrixOperator<BigDecimal>(AOP.BIG_DECIMAL); 
	}
	
	/**
	 * C - Complex Number Operators.<p>
	 * 
	 * {@link mkz.util.math.AOP.C#BYTE AOP::C::BYTE} : Byte complex number operator<br/>
	 * {@link mkz.util.math.AOP.C#INT AOP::C::INT} : Integer complex number operator<br/>
	 * {@link mkz.util.math.AOP.C#DOUBLE AOP::C::DOUBLE} : Double complex number operator<br/>
	 * {@link mkz.util.math.AOP.C#FLOAT AOP::C::FLOAT} : Float complex number operator<br/>
	 * {@link mkz.util.math.AOP.C#BIG_DECIMAL AOP::C::BIG_DECIMAL} : BigDecimal complex number operator<p>
	 */
	public static final class C
	{
		/** Predefined Operator for Operations with Complex Numbers of the java type Byte. */
		public static final ComplexNumberOperator<Byte> BYTE = new ComplexNumberOperator<Byte>(AOP.BYTE); 
		
		/** Predefined Operator for Operations with Complex Numbers of the java type Integer. */
		public static final ComplexNumberOperator<Integer> INT = new ComplexNumberOperator<Integer>(AOP.INT); 
		
		/** Predefined Operator for Operations with Complex Numbers of the java type Double. */
		public static final ComplexNumberOperator<Double> DOUBLE = new ComplexNumberOperator<Double>(AOP.DOUBLE); 
		
		/** Predefined Operator for Operations with Complex Numbers of the java type Float. */
		public static final ComplexNumberOperator<Float> FLOAT = new ComplexNumberOperator<Float>(AOP.FLOAT); 
		
		/** Predefined Operator for Operations with Complex Numbers of the java type BigDecimal. */
		public static final ComplexNumberOperator<BigDecimal> BIG_DECIMAL = new ComplexNumberOperator<BigDecimal>(AOP.BIG_DECIMAL); 
	}
}
