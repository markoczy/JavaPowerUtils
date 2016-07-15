package mkz.test;

import mkz.util.math.AOP;
import mkz.util.math.matrix.Matrix;

public class TestMatrix
{

	public static void main(String[] args)
	{
		try
		{
			Matrix<Double> m1 = Matrix.createVector(new Double[]{0d,0d,3d,0d});
			m1 = Matrix.createAssembly(m1, Matrix.createVector(new Double[]{1d,0d,6d,5d}), false);
			m1 = Matrix.createAssembly(m1, Matrix.createVector(new Double[]{6d,3d,9d,7d}), false);
			m1 = Matrix.createAssembly(m1, Matrix.createVector(new Double[]{2d,6d,9d,6d}), false);
			
//			m1 = Matrix.createAssembly(m1, Matrix.createIdentity(4, 0d, 1d), false);
			
			System.out.println("Printing M1:");
			System.out.println(m1);
			System.out.println();
			
			System.out.println("Printing ltm(M1) -> Gauss:");
			System.out.println(AOP.MTX.DOUBLE.ltm(m1));//AOP.MTX.DOUBLE.ltmSort(m1));
			System.out.println();
			
			Matrix<Double> m1Inv = AOP.MTX.DOUBLE.invert(m1);
			System.out.println("Printing M1^-1 -> Gauss Jordan:");
			System.out.println(m1Inv);//AOP.MTX.DOUBLE.ltmSort(m1));
			System.out.println();
			
			System.out.println("Printing  M1^-1 harmonized:");
			System.out.println(Matrix.oneByAllOperation(m1Inv, (a)->round(a,10))); // applies round(a) to all a element of A
			System.out.println();
			
			System.out.println("Printing M1^-1*M1:");
			Matrix<Double> m1InvTimesM1 = AOP.MTX.DOUBLE.mpl(m1, m1Inv);
			System.out.println(m1InvTimesM1);
			System.out.println();
			
			System.out.println("Printing M1^-1*M1 harmonized:");
			System.out.println(Matrix.oneByAllOperation(m1InvTimesM1, (a)->round(a,10))); // applies round(a) to all a element of A
			System.out.println();
			
			Matrix<Double> m2 = new Matrix<Double>(20,3,Math::random);
			
			System.out.println("Printing M2:");
			System.out.println(m2);
			System.out.println();
			
//			System.out.println("Printing M1*M2:");
//			Matrix<Double> m3 = AOP.MTX.DOUBLE.mmult(m1, m2);
//			System.out.println(m3);
//			System.out.println();
			
//			System.out.println("Det(M1*M2) = "+AOP.MTX.DOUBLE.det(m3));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	// http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
