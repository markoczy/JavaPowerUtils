/**
 * File: MKZ_UtilsLib::MatrixOperator.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.matrix.op;

import java.util.Arrays;
import java.util.Comparator;
import mkz.util.math.matrix.Matrix;
import mkz.util.math.matrix.debug.MatrixDimensionException;
import mkz.util.math.matrix.debug.MatrixException;
import mkz.util.math.op.ArithmeticOperator;


/**
 * Holds all implementations used to perform matrix operations in an entirely generic way. 
 * Uses an {@link mkz.util.math.op.ArithmeticOperator ArithmeticOperator} to perform math operations.
 *
 * @param <T> the generic type
 */
public class MatrixOperator<T>
{
	
	/** The member op. */
	private ArithmeticOperator<T> mOp = null;
	
	/**
	 * Instantiates a new matrix operator.
	 *
	 * @param aOperationBase the reference operation base
	 */
	public MatrixOperator(ArithmeticOperator<T> aOperationBase)
	{
		mOp = aOperationBase;
	}
	
	/**
	 * Adds the.
	 *
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public Matrix<T> add(Matrix<T> aM1, Matrix<T> aM2) throws MatrixException
	{
		return Matrix.oneByOneOperation(aM1, aM2, mOp::add);
	}

	/**
	 * Sub.
	 *
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public Matrix<T> sub(Matrix<T> aM1, Matrix<T> aM2) throws MatrixException
	{
		return Matrix.oneByOneOperation(aM1, aM2, mOp::sub);
	}

	/**
	 * Scale.
	 *
	 * @param aM the reference m
	 * @param aScale the reference scale
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public Matrix<T> scale(Matrix<T> aM, T aScale) throws MatrixException
	{
		return Matrix.oneByAllOperation(aM, (a)->mOp.mpl(a,aScale));
	}
	
	/**
	 * Mpl.
	 *
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public Matrix<T> mpl(Matrix<T> aM1, Matrix<T> aM2) throws MatrixException
	{
		if (aM1.getSizeX() != aM2.getSizeY()) throw new MatrixDimensionException("M1.sizeX must match M2.sizeY.");

		Object[][] lContent = new Object[aM1.getSizeY()][aM2.getSizeX()];

		for (int iSizeY = 0; iSizeY < lContent.length; iSizeY++)
		{
			for (int iSizeX = 0; iSizeX < lContent[0].length; iSizeX++)
			{
				T lVal = mOp.getZero();

				for (int iMult = 0; iMult < aM1.getSizeX(); iMult++)
				{
					lVal = mOp.add(lVal,mOp.mpl(aM1.get(iMult, iSizeY), aM2.get(iSizeX, iMult)));
				}

				lContent[iSizeY][iSizeX] = lVal;
			}
		}

		return new Matrix<T>(lContent);
	}
	
	/**
	 * Div.
	 *
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public Matrix<T> div(Matrix<T> aM1, Matrix<T> aM2) throws MatrixException
	{
		return mpl(aM1,invert(aM2));
	}
	
	/**
	 * Ltm.
	 *
	 * @param aMatrix the reference matrix
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	// Left Triangle Matrix (a.k.a. Gauss reduced mtx)
	public Matrix<T> ltm(Matrix<T> aMatrix) throws MatrixException
	{
		Matrix<T> rVal = ltmSort(aMatrix);
		for(int iYP=0;iYP<rVal.getSizeY()-1;iYP++)
		{
			T lPivot = rVal.get(iYP, iYP);
			if(!mOp.getZero().equals(lPivot))
			{
				for(int iYC=iYP+1;iYC<rVal.getSizeY();iYC++)
				{
					T lCur = rVal.get(iYP, iYC);
					
					if(!mOp.getZero().equals(lCur))
					{
						// XXX could be optimized..
						//
						// PFactor*Current[i]-Pivot[i]*CFactor
						Object[] lRow = Matrix.singleLineOperation(rVal, iYP, rVal, iYC, (a,b) -> mOp.sub(mOp.mpl(b, lPivot), mOp.mpl(a, lCur)), false);
						rVal.setRow(iYC,lRow);
					}
				}
			}
		}
		return rVal;
	}
	
//	public Matrix<T> rtm(Matrix<T> aLtmMatrix) throws MatrixException
//	{
//		if(aLtmMatrix.getSizeX()<aLtmMatrix.getSizeY()) throw new MatrixDimensionException("Size X must at least be Size Y (sizeX="+aLtmMatrix.getSizeX()+", sizeY="+aLtmMatrix.getSizeY()+")");
//		
//		Matrix<T> rVal = aLtmMatrix.copy();
//		for(int iYP=rVal.getSizeY()-1;iYP>0;iYP--)
//		{
//			T lPivot = rVal.get(iYP, iYP);
//			if(!mOp.getZero().equals(lPivot))
//			{
//				for(int iYC=iYP-1;iYC>=0;iYC--)
//				{
//					T lCur = rVal.get(iYP, iYC);
//					
//					if(!mOp.getZero().equals(lCur))
//					{
//						// XXX could be optimized..
//						//
//						// PFactor*Current[i]-Pivot[i]*CFactor
//						Object[] lRow = Matrix.singleLineOperation(rVal, iYP, rVal, iYC, (a,b) -> mOp.sub(mOp.mpl(b, lPivot), mOp.mpl(a, lCur)), false);
//						rVal.setRow(iYC,lRow);
//					}
//				}
//			}
//		}
//		return rVal;
//		
//	}

	/**
 * Invert.
 *
 * @param aMatrix the reference matrix
 * @return the matrix
 * @throws MatrixException the matrix exception
 */
public Matrix<T> invert(Matrix<T> aMatrix) throws MatrixException
	{
		if(aMatrix.getSizeX()!=aMatrix.getSizeY()) throw new MatrixDimensionException("Matrix must be square (sizeX="+aMatrix.getSizeX()+", sizeY="+aMatrix.getSizeY()+")");
		
		// append identity matrix and ltm sort
		Matrix<T> rVal = ltm(Matrix.createAssembly(aMatrix, newIdentity(aMatrix.getSizeX()),false));
		
//		System.out.println("Pre jordan:");
//		System.out.println(rVal);
//		System.out.println();
		
		
		for(int iYP=rVal.getSizeY()-1;iYP>0;iYP--)
		{
			T lPivot = rVal.get(iYP, iYP);
			if(!mOp.getZero().equals(lPivot))
			{
				for(int iYC=iYP-1;iYC>=0;iYC--)
				{
					T lCur = rVal.get(iYP, iYC);
					
					if(!mOp.getZero().equals(lCur))
					{
						// XXX should be optimized..
						//
						// PFactor*Current[i]-Pivot[i]*CFactor
						Object[] lRow = Matrix.singleLineOperation(rVal, iYP, rVal, iYC, (a,b) -> mOp.sub(mOp.mpl(b, lPivot), mOp.mpl(a, lCur)), false);
						rVal.setRow(iYC,lRow);
					}
				}
			}
		}
		
//		System.out.println("Pre division:");
//		System.out.println(rVal);
//		System.out.println();
		
		for(int iYP=0;iYP<rVal.getSizeY();iYP++)
		{
			T lDivisor = rVal.get(iYP, iYP);
//			System.out.println("Divisor is: " +lDivisor);
			Object[] lRow = Matrix.singleLineOperation(rVal, iYP, (a)->mOp.div(a, lDivisor), false);
			rVal.setRow(iYP,lRow);
		}
		
//		System.out.println("Post division:");
//		System.out.println(rVal);
//		System.out.println();
		
		return Matrix.createHorizontalSplitted(rVal, aMatrix.getSizeX(), false);
		
	}
	
	/**
	 * Det.
	 *
	 * @param aM the reference m
	 * @return the t
	 * @throws MatrixException the matrix exception
	 */
	public T det(Matrix<T> aM) throws MatrixException
	{
		int lSize = aM.getSizeX();
		if(aM.getSizeY()!=lSize) throw new MatrixDimensionException("Matrix must be square.");
		
		// a
		if(lSize==1) return aM.get(0, 0);
		// ad-bc
		else if(lSize==2) return mOp.sub(mOp.mpl(aM.get(0, 0), aM.get(1, 1)),mOp.mpl(aM.get(1, 0), aM.get(0, 1)));
		else
		{
			T rVal = mOp.getZero();
			for(int i=0; i<lSize; i++)
			{
				if(Matrix.SARRUS.test(i, 0)) rVal=mOp.sub(rVal, mOp.mpl(aM.get(i, 0), det(Matrix.createSubGrid(aM, i, 0))));
				else rVal=mOp.add(rVal, mOp.mpl(aM.get(i, 0), det(Matrix.createSubGrid(aM, i, 0))));
			}
			
			return rVal;
		}
	}
	
	/**
	 * New identity.
	 *
	 * @param aSize the reference size
	 * @return the matrix
	 */
	public Matrix<T> newIdentity(int aSize)
	{
		return Matrix.createIdentity(aSize, mOp.getZero(), mOp.getOne());
	}
	
	/**
	 * Ltm sort.
	 *
	 * @param aMatrix the reference matrix
	 * @return the matrix
	 */
	public Matrix<T> ltmSort(Matrix<T> aMatrix)
	{
		// TODO gcd reduction
		Matrix<T> r = aMatrix.copy(); 
		T[][] lData = r.getData();
		Arrays.sort(lData, mGaussComparator);
		r.setData(lData);
		return r;
	}
	
	/** The member gauss comparator. */
	private final Comparator<T[]> mGaussComparator = (t1,t2)->
	{
		int iVal = 0;
		int lLastZero=0;
		while(iVal<t1.length)
		{
			if(t1[iVal].equals(mOp.getZero()))
			{
				if(!t2[iVal].equals(mOp.getZero())) return 1;
				// indicates last pos where both where 0
				else lLastZero=iVal;
			}
			else
			{
				if(t2[iVal].equals(mOp.getZero())) return -1;
			}
			iVal++;
		}
		
		if(lLastZero<t1.length-1) return mOp.compare(t1[lLastZero+1],t2[lLastZero+1]);
		else return 0;
	};
	
}
