package mkz.test;

import mkz.util.math.AOP;

public class TestArithmetic
{
	public static void main(String[] args)
	{
		Integer[] i = new Integer[]{45,55,25,65};
		System.out.println(AOP.INT.gcd(i));
		
//		// following is invalid -> fractals not allowed
//		Double[] d = new Double[]{45.4,55d,25d,65d}; 
//		System.out.println(AOP.DOUBLE.gcd(d));
		
		Double d2 = -32.43;
		System.out.println(AOP.DOUBLE.abs(d2));
		
		
	}
}
