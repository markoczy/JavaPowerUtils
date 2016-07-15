package mkz.test;

import java.util.function.Function;

import mkz.util.math.AOP;
import mkz.util.math.cnum.ComplexNumber;
import mkz.util.math.cnum.op.ComplexNumberOperator;

public class TestComplexNumber
{
	private static ComplexNumberOperator<Double> op = AOP.C.DOUBLE;

	public static void main(String[] args)
	{
		ComplexNumber<Double> a = new ComplexNumber<Double>(2d,3d);
		ComplexNumber<Double> b = new ComplexNumber<Double>(6d,13d);
		System.out.println("a*b = "+op.mpl(a, b));
		
		// Default complex function: ax+b
		Function<ComplexNumber<Double>,ComplexNumber<Double>> f = (x) -> op.add(op.mpl(a, x),b);
		System.out.println("f: "+f.apply(new ComplexNumber<Double>(2d,2d)));
	}

}
