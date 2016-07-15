/**
 * File: MKZ_UtilsLib::Matrix.java
 *
 * @author Aleistar Markóczy
 * 
 */
package mkz.util.math.matrix;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import mkz.util.math.matrix.debug.MatrixDimensionException;
import mkz.util.math.matrix.debug.MatrixException;

/**
 * The Class Matrix. A matrix is a two dimensional list of elements.  The class provides a few static operations to
 * interact with Matrices, for anything related to arithmetic matrix operations use the more dedicated operator classes
 * {@link mkz.util.math.matrix.op.MatrixOperator MatrixOperator} (for new types) or {@link mkz.util.math.AOP.MTX AOP::MTX} (for standard types)
 *
 * @param <T> the generic type
 */
public class Matrix<T>
{
	/** The member data. */
	private Object[][] mData = null;

	/**
	 * Instantiates a new matrix.
	 *
	 * @param aValue the reference value
	 */
	public Matrix(T aValue)
	{
		// Default initialization gives [0]
		mData = new Object[1][1];
		mData[0][0] = aValue;
	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param aSizeX the reference size x
	 * @param aSizeY the reference size y
	 * @param aDefault the reference default
	 */
	public Matrix(int aSizeX, int aSizeY, T aDefault)
	{
		mData = new Object[aSizeY][aSizeX];

		for (int iX = 0; iX < aSizeX; iX++)
		{
			for (int iY = 0; iY < aSizeY; iY++)
			{
				mData[iY][iX] = aDefault;
			}
		}
	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param aSizeX the reference size x
	 * @param aSizeY the reference size y
	 * @param aGenerator the reference generator
	 */
	public Matrix(int aSizeX, int aSizeY, Supplier<T> aGenerator)
	{
		mData = new Object[aSizeY][aSizeX];

		for (int iY = 0; iY < aSizeY; iY++)
		{
			for (int iX = 0; iX < aSizeX; iX++)
			{
				mData[iY][iX] = aGenerator.get();
			}
		}

	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param aValues the reference values
	 */
	public Matrix(Object[][] aValues)
	{
		mData = aValues;
	}

	/**
	 * Instantiates a new matrix.
	 *
	 * @param aValues the reference values
	 */
	public Matrix(List<List<Double>> aValues)
	{
		for (List<Double> iList : aValues)
		{
			double[] lVals = new double[iList.size()];

			for (int i = 0; i < iList.size(); i++)
			{
				lVals[i] = iList.get(i);
			}
		}
	}

	/**
	 * Gets the.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the t
	 * @throws MatrixDimensionException the matrix dimension exception
	 */
	@SuppressWarnings("unchecked")
	public T get(int x, int y) throws MatrixDimensionException
	{
		if (mData.length > y && mData[y].length > x) return (T) mData[y][x];
		else throw new MatrixDimensionException("Invalid field x=" + x + "; y=" + y + "; sizeX=" + getSizeX() + "; sizeY=" + getSizeY());
	}

	/**
	 * Sets the.
	 *
	 * @param x the x
	 * @param y the y
	 * @param aValue the reference value
	 * @throws MatrixDimensionException the matrix dimension exception
	 */
	public void set(int x, int y, double aValue) throws MatrixDimensionException
	{
		if (mData.length > y && mData[y].length > x) mData[y][x] = aValue;
		else throw new MatrixDimensionException("Invalid field x=" + x + "; y=" + y + "; sizeX=" + getSizeX() + "; sizeY=" + getSizeY());
	}

	/**
	 * Gets the size y.
	 *
	 * @return the size y
	 */
	public int getSizeY()
	{
		return mData != null ? mData.length : 0;
	}

	/**
	 * Gets the size x.
	 *
	 * @return the size x
	 */
	public int getSizeX()
	{
		return mData[0] != null ? mData[0].length : 0;
	}

	/**
	 * Swap lines.
	 *
	 * @param aLine1 the reference line1
	 * @param aLine2 the reference line2
	 * @param swapColumns the swap columns
	 */
	public void swapLines(int aLine1, int aLine2, boolean swapColumns)
	{
		if (!swapColumns)
		{
			Object[] lLine = mData[aLine1];
			mData[aLine1] = mData[aLine2];
			mData[aLine2] = lLine;
		}
		else
		{
			for (int iRow = 0; iRow < mData.length; iRow++)
			{
				Object lVal = mData[iRow][aLine1];
				mData[iRow][aLine1] = mData[iRow][aLine2];
				mData[iRow][aLine2] = lVal;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		// sb.append('[');

		for (int iY = 0; iY < mData.length; iY++)
		{
			for (int iX = 0; iX < mData[iY].length; iX++)
			{
				sb.append(mData[iY][iX]);
				if (iX < mData[iY].length - 1)
					sb.append(',');
			}

			if (iY < mData.length - 1)
				sb.append('\n');
		}

		// sb.append(']');
		return sb.toString();
	}

	/**
	 * Copy.
	 *
	 * @return the matrix
	 */
	public Matrix<T> copy()
	{
		Object[][] lContent = new Object[mData.length][mData[0].length];

		for (int iY = 0; iY < mData.length; iY++)
		{
			for (int iX = 0; iX < mData[0].length; iX++)
			{
				lContent[iY][iX] = mData[iY][iX];
			}
		}

		return new Matrix<T>(lContent);
	}

	/**
	 * Sets the data.
	 *
	 * @param aData the new data
	 */
	public void setData(Object[][] aData)
	{
		mData = aData;
	}

	/**
	 * Sets the row.
	 *
	 * @param idx the idx
	 * @param aRow the reference row
	 */
	// XXX no error check
	public void setRow(int idx, Object[] aRow)
	{
		mData[idx] = aRow;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	@SuppressWarnings("unchecked")
	public T[][] getData()
	{
		return (T[][]) mData;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// OPERATION TOOLS
	//////////////////////////////////////////////////////////////////////////////////////

	/** 
	 * The SARRUS Predicate. Returns if the value must be multiplied by -1.
	 * 
	 * +-+-
	 * -+-+
	 * +-+-
	 * 
	 * Value1 is X, Value2 is Y.
	 **/
	public static final BiPredicate<Integer, Integer> SARRUS = (x, y) -> x % 2 == 0 ? y % 2 != 0 : y % 2 == 0;
	
	/**
	 * Creates an operation where values of two matrices are operated one by one. Essentially used
	 * for operations: addition, subtraction
	 *
	 * @param <T> the generic type
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @param aOperation the reference operation
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> oneByOneOperation(Matrix<T> aM1, Matrix<T> aM2, BiFunction<T, T, T> aOperation) throws MatrixException
	{
		int lSizeX = aM1.getSizeX();
		int lSizeY = aM1.getSizeY();

		if (lSizeX != aM2.getSizeX()) throw new MatrixDimensionException("M1.sizeX must match M2.sizeX.");
		if (lSizeY != aM2.getSizeY()) throw new MatrixDimensionException("M1.sizeY must match M2.sizeY.");

		Object[][] lContent = new Object[lSizeY][lSizeX];

		for (int iX = 0; iX < lSizeX; iX++)
		{
			for (int iY = 0; iY < lSizeX; iY++)
			{
				lContent[iY][iX] = aOperation.apply(aM1.get(iX, iY), aM2.get(iX, iY));
			}
		}

		return new Matrix<T>(lContent);
	}

	/**
	 * Creates an operation where values of one matrix is operator by one value. Essentially used
	 * for operations: scale
	 *
	 * @param <T> the generic type
	 * @param aM the reference m
	 * @param aOperation the reference operation
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> oneByAllOperation(Matrix<T> aM, Function<T, T> aOperation) throws MatrixException
	{
		int lSizeX = aM.getSizeX();
		int lSizeY = aM.getSizeY();

		Object[][] lContent = new Object[lSizeY][lSizeX];

		for (int iX = 0; iX < lSizeX; iX++)
		{
			for (int iY = 0; iY < lSizeX; iY++)
			{
				lContent[iY][iX] = aOperation.apply(aM.get(iX, iY));
			}
		}

		return new Matrix<T>(lContent);
	}
	
	/**
	 * Single line operation.
	 *
	 * @param <T> the generic type
	 * @param aM1 the reference m1
	 * @param aLine1 the reference line1
	 * @param aM2 the reference m2
	 * @param aLine2 the reference line2
	 * @param aOperation the reference operation
	 * @param isVertical the is vertical
	 * @return the object[]
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Object[] singleLineOperation(Matrix<T> aM1, int aLine1, Matrix<T> aM2, int aLine2, BiFunction<T, T, T> aOperation, boolean isVertical) throws MatrixException
	{
		int lSize = isVertical ? aM1.getSizeY():aM1.getSizeX();
		int lSize2 = isVertical ? aM2.getSizeY():aM2.getSizeX();
		if(lSize!=lSize2) throw new MatrixDimensionException("Size mismatch m1:"+lSize+", m2:"+lSize2);
		Object[] lLine = new Object[lSize];
		
		if(isVertical) for(int i=0;i<lSize;i++) lLine[i]=aOperation.apply(aM1.get(aLine1,i), aM1.get(aLine2,i));
		else for(int i=0;i<lSize;i++) lLine[i]=aOperation.apply(aM1.get(i, aLine1), aM1.get(i, aLine2));
		
		return lLine;
	}
	
	/**
	 * Single line operation.
	 *
	 * @param <T> the generic type
	 * @param aM the reference m
	 * @param aLine the reference line
	 * @param aOperation the reference operation
	 * @param isVertical the is vertical
	 * @return the object[]
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Object[] singleLineOperation(Matrix<T> aM, int aLine, Function<T, T> aOperation, boolean isVertical) throws MatrixException
	{
		int lSize = isVertical ? aM.getSizeY():aM.getSizeX();
		Object[] lLine = new Object[lSize];
		
		if(isVertical) for(int i=0;i<lSize;i++) lLine[i]=aOperation.apply(aM.get(aLine,i));
		else for(int i=0;i<lSize;i++) lLine[i]=aOperation.apply(aM.get(i, aLine));
		
		return lLine;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// FACTORY TOOLS
	//////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a vector.
	 *
	 * @param <T> the generic type
	 * @param aValues the reference values
	 * @return the matrix
	 */
	public static <T> Matrix<T> createVector(T[] aValues)
	{
		Object[][] lContent = new Object[aValues.length][1];
		for (int i = 0; i < aValues.length; i++)
		{
			lContent[i][0] = aValues[i];
		}

		return new Matrix<T>(lContent);
	}

	/**
	 * Creates a identity.
	 *
	 * @param <T> the generic type
	 * @param aSize the reference size
	 * @param aZeroValue the reference zero value
	 * @param aOneValue the reference one value
	 * @return the matrix
	 */
	public static <T> Matrix<T> createIdentity(int aSize, T aZeroValue, T aOneValue)
	{
		Object[][] lContent = new Object[aSize][aSize];

		for (int iY = 0; iY < aSize; iY++)
		{
			for (int iX = 0; iX < aSize; iX++)
			{
				if (iY == iX)
					lContent[iY][iX] = aOneValue;
				else
					lContent[iY][iX] = aZeroValue;
			}
		}

		return new Matrix<T>(lContent);
	}

	/**
	 * Creates the transposed.
	 *
	 * @param <T> the generic type
	 * @param aMatrix the reference matrix
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> createTransposed(Matrix<T> aMatrix) throws MatrixException
	{
		Object[][] lContent = new Object[aMatrix.getSizeX()][aMatrix.getSizeY()];
		for (int iX = 0; iX < aMatrix.getSizeY(); iX++)
		{
			for (int iY = 0; iY < aMatrix.getSizeX(); iY++)
			{
				lContent[iY][iX] = aMatrix.get(iY, iX);
			}
		}

		return new Matrix<T>(lContent);
	}

	/**
	 * Creates the sub grid.
	 *
	 * @param <T> the generic type
	 * @param aMatrix the reference matrix
	 * @param aX the reference x
	 * @param aY the reference y
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> createSubGrid(Matrix<T> aMatrix, int aX, int aY) throws MatrixException
	{
		//
		// 1 4 7
		// 2 5 8 , 1 , 1 -> 1 7
		// 3 6 9            3 9
		//
		if (aX >= aMatrix.getSizeX() || aY >= aMatrix.getSizeY())throw new MatrixDimensionException("Dimension too small sizeX=" 
					+ aMatrix.getSizeX() + "; sizeY=" + aMatrix.getSizeY() + ", x=" + aX + "; y=" + aY);

		Object[][] lContent = new Object[aMatrix.getSizeY() - 1][aMatrix.getSizeX() - 1];

		int lCursorX = 0;
		int lCursorY = 0;

		for (int iY = 0; iY < aMatrix.getSizeY(); iY++)
		{
			if (iY != aY)
			{
				for (int iX = 0; iX < aMatrix.getSizeX(); iX++)
				{
					if (iX != aX)
					{
						// System.out.println("CursorX: " +lCursorX);
						// System.out.println("CursorY: " +lCursorY);
						lContent[lCursorY][lCursorX] = aMatrix.get(iX, iY);
						lCursorX++;
					}
				}
				lCursorY++;
				lCursorX = 0;
			}
		}

		return new Matrix<T>(lContent);
	}
	
	/**
	 * Creates the horizontal splitted.
	 *
	 * @param <T> the generic type
	 * @param aMatrix the reference matrix
	 * @param aPos the reference pos
	 * @param isLeft the is left
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> createHorizontalSplitted(Matrix<T> aMatrix, int aPos, boolean isLeft) throws MatrixException
	{
		if(aPos>=aMatrix.getSizeX()) throw new MatrixDimensionException("Position must be smaller than sizeX-1 for split: pos="+aPos+", sizeX="+aMatrix.getSizeX());
		
		Object[][] lContent1 = new Object[aMatrix.getSizeY()][aPos]; 
		Object[][] lContent2 = new Object[aMatrix.getSizeY()][aMatrix.getSizeX()-aPos]; 
		
		for(int iY=0;iY<aMatrix.getSizeY();iY++)
		{
			for(int iX=0;iX<aMatrix.getSizeX();iX++)
			{
				if(iX<aPos) lContent1[iY][iX]=aMatrix.get(iX, iY);
				else lContent2[iY][(aMatrix.getSizeX()-aPos)-(aMatrix.getSizeX()-iX)]=aMatrix.get(iX, iY);
			}
		}
		
		return new Matrix<T>(isLeft?lContent1:lContent2);
	}

	/**
	 * Creates an assembly of two matrices.
	 *
	 * @param <T> the generic type
	 * @param aM1 the reference m1
	 * @param aM2 the reference m2
	 * @param vertical the vertical
	 * @return the matrix
	 * @throws MatrixException the matrix exception
	 */
	public static <T> Matrix<T> createAssembly(Matrix<T> aM1, Matrix<T> aM2, boolean vertical) throws MatrixException
	{
		if (vertical)
		{
			if (aM1.getSizeX() != aM2.getSizeX()) throw new MatrixDimensionException("SizeX mismatch m1:" + aM1.getSizeX() + "m2:" + aM2.getSizeX());

			Object[][] lData1 = aM1.getData();
			Object[][] lData2 = aM2.getData();

			Object[][] lContent = new Object[aM1.getSizeY() + aM2.getSizeY()][];
			int iContent = 0;

			for (int i = 0; i < lData1.length; i++) lContent[iContent++] = Arrays.copyOf(lData1[i], lData1[i].length);
			for (int i = 0; i < lData2.length; i++) lContent[iContent++] = Arrays.copyOf(lData2[i], lData2[i].length);

			return new Matrix<T>(lContent);
		}
		else
		{
			if (aM1.getSizeY() != aM2.getSizeY()) throw new MatrixDimensionException("SizeY mismatch m1:" + aM1.getSizeY() + "m2:" + aM2.getSizeY());
			Object[][] lContent = new Object[aM1.getSizeY()][aM1.getSizeX() + aM2.getSizeX()];

			for (int iY = 0; iY < aM1.getSizeY(); iY++)
			{
				int lCurX = 0;
				for (int iX = 0; iX < aM1.getSizeX(); iX++) lContent[iY][lCurX++] = aM1.get(iX, iY);
				for (int iX = 0; iX < aM2.getSizeX(); iX++) lContent[iY][lCurX++] = aM2.get(iX, iY);
			}

			return new Matrix<T>(lContent);
		}
	}
}
