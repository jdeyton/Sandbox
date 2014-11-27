package com.bar.foo.math.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import com.bar.foo.math.Matrix3f;
import com.bar.foo.math.Vector3f;

/**
 * This class checks all methods and attributes provided by {@link Matrix3f}.
 * 
 * @author Jordan
 *
 */
public class Matrix3fTester {
	/**
	 * The delta value to be used in all float/double comparisons.
	 */
	private static final double delta = 1e-5;

	/**
	 * A prefix used when failing with a custom message.
	 */
	private static final String failurePrefix = "Matrix3fTester failure: ";

	/**
	 * A random instance used to generate random numbers.
	 */
	private static final Random random = new Random(System.nanoTime());

	/**
	 * This tests the static matrices provided by {@code Matrix3f}.
	 */
	@Test
	public void checkStaticMatrices() {
		// zero matrix
		assertEquals(0f, Matrix3f.ZERO.m00, delta);
		assertEquals(0f, Matrix3f.ZERO.m01, delta);
		assertEquals(0f, Matrix3f.ZERO.m02, delta);
		assertEquals(0f, Matrix3f.ZERO.m10, delta);
		assertEquals(0f, Matrix3f.ZERO.m11, delta);
		assertEquals(0f, Matrix3f.ZERO.m12, delta);
		assertEquals(0f, Matrix3f.ZERO.m20, delta);
		assertEquals(0f, Matrix3f.ZERO.m21, delta);
		assertEquals(0f, Matrix3f.ZERO.m22, delta);

		// identity matrix with ones down the diagonal top-left to bottom-right
		assertEquals(1f, Matrix3f.IDENTITY.m00, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m01, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m02, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m10, delta);
		assertEquals(1f, Matrix3f.IDENTITY.m11, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m12, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m20, delta);
		assertEquals(0f, Matrix3f.IDENTITY.m21, delta);
		assertEquals(1f, Matrix3f.IDENTITY.m22, delta);

		return;
	}

	/**
	 * This tests the constructors provided by {@code Matrix3f}.
	 */
	@Test
	public void checkConstructors() {

		Matrix3f matrix;
		Matrix3f copy;
		Vector3f v0, v1, v2;
		final Matrix3f nullMatrix = null;
		final Vector3f nullVector = null;
		final Matrix3f expectedMatrix;

		// Generate random numbers for some of the following tests.
		final float m00 = random.nextFloat();
		final float m01 = random.nextFloat();
		final float m02 = random.nextFloat();
		final float m10 = random.nextFloat();
		final float m11 = random.nextFloat();
		final float m12 = random.nextFloat();
		final float m20 = random.nextFloat();
		final float m21 = random.nextFloat();
		final float m22 = random.nextFloat();

		// ---- Check the default nullary constructor. ---- //
		matrix = new Matrix3f();
		assertEquals(0f, matrix.m00, delta);
		assertEquals(0f, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);
		// ------------------------------------------------ //

		// ---- Check the full constructor. ---- //
		matrix = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(m22, matrix.m22, delta);
		// ------------------------------------- //

		// We've now tested the full constructor satisfactorily. Now construct
		// the expected matrix so we can reduce the test code by a factor of 9.
		expectedMatrix = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21,
				m22);

		// ---- Check the first default Vector3f-based constructor. ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		matrix = new Matrix3f(v0, v1, v2);
		assertEquals(expectedMatrix, matrix);
		// ------------------------------------------------------------- //

		// ---- Check the full Vector3f-based constructor. ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		matrix = new Matrix3f(v0, v1, v2, true);
		assertEquals(expectedMatrix, matrix);

		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a column in the matrix.
		v0 = new Vector3f(m00, m10, m20);
		v1 = new Vector3f(m01, m11, m21);
		v2 = new Vector3f(m02, m12, m22);

		matrix = new Matrix3f(v0, v1, v2, false);
		assertEquals(expectedMatrix, matrix);
		// ---------------------------------------------------- //

		// ---- Check the copy constructor. ---- //
		// Try it with a valid matrix.
		copy = new Matrix3f(matrix);
		assertEquals(expectedMatrix, matrix);

		// Try it with a null matrix. All values should revert to 0.
		copy = new Matrix3f(nullMatrix);
		assertEquals(Matrix3f.ZERO, copy);
		// ------------------------------------- //

		// ---- Make sure null pointers throw exceptions. ---- //
		// Check all possible nulls in the default vector-based constructor.
		try {
			matrix = new Matrix3f(nullVector, v1, v2);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, nullVector, v2);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, v1, nullVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// Check all possible nulls in the other vector-based constructor.
		try {
			matrix = new Matrix3f(nullVector, v1, v2, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, nullVector, v2, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, v1, nullVector, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// --------------------------------------------------- //

		return;
	}

	/**
	 * This tests all of the set methods provided by {@code Matrix3f}.
	 */
	@Test
	public void checkSet() {
		Matrix3f matrix;
		Vector3f v0, v1, v2;
		final Matrix3f nullMatrix = null;
		final Vector3f nullVector = null;
		final Matrix3f expectedMatrix;

		// Generate random numbers for some of the following tests.
		final float m00 = random.nextFloat();
		final float m01 = random.nextFloat();
		final float m02 = random.nextFloat();
		final float m10 = random.nextFloat();
		final float m11 = random.nextFloat();
		final float m12 = random.nextFloat();
		final float m20 = random.nextFloat();
		final float m21 = random.nextFloat();
		final float m22 = random.nextFloat();

		matrix = new Matrix3f();

		// Set up the expected matrix values so we don't have to compare each
		// matrix value manually.
		expectedMatrix = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21,
				m22);

		// ---- Check set(float, float, ... float) ---- //
		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix,
				matrix.set(m00, m01, m02, m10, m11, m12, m20, m21, m22));
		assertEquals(expectedMatrix, matrix);
		// -------------------------------------------- //

		// ---- Check set(Vector3f, Vector3f, Vector3f) ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.set(v0, v1, v2));
		assertEquals(expectedMatrix, matrix);

		// Passing in a null vector shouldn't succeed regardless of which
		// argument is null.
		try {
			matrix.set(nullVector, v1, v2);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix.set(v0, nullVector, v2);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix.set(v0, v1, nullVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// ------------------------------------------------- //

		// ---- Check set(Vector3f, Vector3f, Vector3f, boolean) ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.set(v0, v1, v2, true));
		assertEquals(expectedMatrix, matrix);

		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a column in the matrix.
		v0 = new Vector3f(m00, m10, m20);
		v1 = new Vector3f(m01, m11, m21);
		v2 = new Vector3f(m02, m12, m22);

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.set(v0, v1, v2, false));
		assertEquals(expectedMatrix, matrix);

		// Passing in a null vector shouldn't succeed regardless of which
		// argument is null.
		try {
			matrix.set(nullVector, v1, v2, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix.set(v0, nullVector, v2, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix.set(v0, v1, nullVector, false);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// ---------------------------------------------------------- //

		// ---- Check set(Matrix3f) ---- //
		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.set(expectedMatrix));
		assertEquals(expectedMatrix, matrix);

		// Try it with a null matrix. An exception should be thrown.
		try {
			matrix.set(nullMatrix);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// ----------------------------- //

		// ---- Check setRow(int, float, float, float) ---- //
		// Below, we set row 0, then row 1, and lastly row 3, testing after each
		// call. After setting row 3, the matrix should be the same as the
		// expected matrix. We can then test some invalid arguments.

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.setRow(0, m00, m01, m02));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the second row.
		assertSame(matrix, matrix.setRow(1, m10, m11, m12));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the third row.
		assertSame(matrix, matrix.setRow(2, m20, m21, m22));
		assertEquals(expectedMatrix, matrix);

		// Passing in an invalid row shouldn't change anything.
		assertSame(matrix, matrix.setRow(-1, 0f, 0f, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.setRow(3, 0f, 0f, 0f));
		assertEquals(expectedMatrix, matrix);
		// ------------------------------------------------ //

		// ---- Check setRow(int, Vector3f) ---- //
		// Below, we set row 0, then row 1, and lastly row 3, testing after each
		// call. After setting row 3, the matrix should be the same as the
		// expected matrix. We can then test some invalid arguments.

		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.setRow(0, v0));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the second row.
		assertSame(matrix, matrix.setRow(1, v1));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the third row.
		assertSame(matrix, matrix.setRow(2, v2));
		assertEquals(expectedMatrix, matrix);

		// Passing in a null vector should throw an exception.
		try {
			matrix.setRow(0, nullVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}

		// Passing in an invalid row shouldn't change anything.
		assertSame(matrix, matrix.setRow(-1, Vector3f.ZERO));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.setRow(3, Vector3f.ZERO));
		assertEquals(expectedMatrix, matrix);
		// ------------------------------------- //

		// ---- Check setColumn(int, float, float, float) ---- //
		// Below, we set column 0, then column 1, and lastly column 3, testing
		// after each call. After setting column 3, the matrix should be the
		// same as the expected matrix. We can then test some invalid arguments.

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.setColumn(0, m00, m10, m20));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(0f, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the second column.
		assertSame(matrix, matrix.setColumn(1, m01, m11, m21));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the third column.
		assertSame(matrix, matrix.setColumn(2, m02, m12, m22));
		assertEquals(expectedMatrix, matrix);

		// Passing in an invalid row shouldn't change anything.
		assertSame(matrix, matrix.setColumn(-1, 0f, 0f, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.setColumn(3, 0f, 0f, 0f));
		assertEquals(expectedMatrix, matrix);
		// --------------------------------------------------- //

		// ---- Check setColumn(int, Vector3f) ---- //
		// Below, we set column 0, then column 1, and lastly column 3, testing
		// after each call. After setting column 3, the matrix should be the
		// same as the expected matrix. We can then test some invalid arguments.

		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a column in the matrix.
		v0 = new Vector3f(m00, m10, m20);
		v1 = new Vector3f(m01, m11, m21);
		v2 = new Vector3f(m02, m12, m22);

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		// Check that set works and check its return value.
		assertSame(matrix, matrix.setColumn(0, v0));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(0f, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the second column.
		assertSame(matrix, matrix.setColumn(1, v1));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		// Check the third column.
		assertSame(matrix, matrix.setColumn(2, v2));
		assertEquals(expectedMatrix, matrix);

		// Passing in a null vector should throw an exception.
		try {
			matrix.setColumn(0, nullVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}

		// Passing in an invalid column shouldn't change anything.
		assertSame(matrix, matrix.setColumn(-1, Vector3f.ZERO));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.setColumn(3, Vector3f.ZERO));
		assertEquals(expectedMatrix, matrix);
		// ---------------------------------------- //

		// ---- Check set(int, int, float) ---- //
		// Below, we test setting each individual position, testing after each
		// call. By the end, we can just compare the matrix against the expected
		// matrix.

		// Zero out the matrix.
		matrix.set(Matrix3f.ZERO);
		assertFalse(expectedMatrix.equals(matrix));

		assertSame(matrix, matrix.set(0, 0, m00));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(0f, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(0, 1, m01));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(0f, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(0, 2, m02));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(0f, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(1, 0, m10));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(0f, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(1, 1, m11));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(0f, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(1, 2, m12));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(0f, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(2, 0, m20));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(0f, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(2, 1, m21));
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(0f, matrix.m22, delta);

		assertSame(matrix, matrix.set(2, 2, m22));
		assertEquals(expectedMatrix, matrix);

		// Now try some invalid locations. Below, I try every (invalid)
		// combination of
		// invalid/valid row/column.
		assertSame(matrix, matrix.set(-1, -1, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(-1, 0, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(-1, 3, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(0, -1, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(0, 3, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(3, -1, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(3, 0, 0f));
		assertEquals(expectedMatrix, matrix);
		assertSame(matrix, matrix.set(3, 3, 0f));
		assertEquals(expectedMatrix, matrix);
		// ------------------------------------ //

		return;
	}

	/**
	 * This tests {@link Matrix3f#equals(Object)} and
	 * {@link Matrix3f#hashCode()}.
	 */
	@Test
	public void checkEquality() {
		Matrix3f object;
		Matrix3f equalObject;
		Matrix3f unequalObject;
		final Matrix3f nullMatrix = null;

		// Generate random numbers for some of the following tests.
		final float m00 = random.nextFloat();
		final float m01 = random.nextFloat();
		final float m02 = random.nextFloat();
		final float m10 = random.nextFloat();
		final float m11 = random.nextFloat();
		final float m12 = random.nextFloat();
		final float m20 = random.nextFloat();
		final float m21 = random.nextFloat();
		final float m22 = Float.MIN_NORMAL;

		object = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
		equalObject = new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
		unequalObject = new Matrix3f(object);
		unequalObject.m22 = m22 * 1.1f;

		// ---- Check bad arguments to equals. ---- //
		assertFalse(object.equals(nullMatrix));
		assertFalse(object.equals(42));
		assertFalse(object.equals("the one"));
		// ---------------------------------------- //

		// ---- Check valid arguments to equals. ---- //
		// Reflexive
		assertTrue(object.equals(object));
		assertTrue(equalObject.equals(equalObject));
		assertTrue(unequalObject.equals(unequalObject));
		// Symmetric
		assertTrue(object.equals(equalObject));
		assertTrue(equalObject.equals(object));
		assertFalse(object.equals(unequalObject));
		assertFalse(unequalObject.equals(object));
		assertFalse(equalObject.equals(unequalObject));
		assertFalse(unequalObject.equals(equalObject));
		// Check the hash codes.
		assertTrue(object.hashCode() == equalObject.hashCode());
		assertFalse(object.hashCode() == unequalObject.hashCode());
		assertFalse(equalObject.hashCode() == unequalObject.hashCode());
		// ------------------------------------------ //

		return;
	}

	/**
	 * This tests all multiplication methods provided by {@code Matrix3f}.
	 */
	@Test
	public void checkMultiply() {

		Matrix3f A;
		Matrix3f B;
		final Matrix3f AB;
		final Matrix3f Acopy;
		Vector3f u;
		final Vector3f v;
		Matrix3f cacheMatrix;
		final Matrix3f nullMatrix = null;
		Vector3f cacheVector;
		final Vector3f nullVector = null;

		// Generate random numbers for some of the following tests.
		// These are for matrix A.
		final float a00 = random.nextFloat();
		final float a01 = random.nextFloat();
		final float a02 = random.nextFloat();
		final float a10 = random.nextFloat();
		final float a11 = random.nextFloat();
		final float a12 = random.nextFloat();
		final float a20 = random.nextFloat();
		final float a21 = random.nextFloat();
		final float a22 = random.nextFloat();
		A = new Matrix3f(a00, a01, a02, a10, a11, a12, a20, a21, a22);
		Acopy = new Matrix3f(A);
		// These are for the vector u.
		final float u0 = random.nextFloat();
		final float u1 = random.nextFloat();
		final float u2 = random.nextFloat();
		u = new Vector3f(u0, u1, u2);
		// These are for the vector v = Au.
		final float v0 = a00 * u0 + a01 * u1 + a02 * u2;
		final float v1 = a10 * u0 + a11 * u1 + a12 * u2;
		final float v2 = a20 * u0 + a21 * u1 + a22 * u2;
		v = new Vector3f(v0, v1, v2);
		// These are for matrix B.
		final float b00 = random.nextFloat();
		final float b01 = random.nextFloat();
		final float b02 = random.nextFloat();
		final float b10 = random.nextFloat();
		final float b11 = random.nextFloat();
		final float b12 = random.nextFloat();
		final float b20 = random.nextFloat();
		final float b21 = random.nextFloat();
		final float b22 = random.nextFloat();
		B = new Matrix3f(b00, b01, b02, b10, b11, b12, b20, b21, b22);
		// These are for the resulting matrix AB.
		final float ab00 = a00 * b00 + a01 * b10 + a02 * b20;
		final float ab01 = a00 * b01 + a01 * b11 + a02 * b21;
		final float ab02 = a00 * b02 + a01 * b12 + a02 * b22;
		final float ab10 = a10 * b00 + a11 * b10 + a12 * b20;
		final float ab11 = a10 * b01 + a11 * b11 + a12 * b21;
		final float ab12 = a10 * b02 + a11 * b12 + a12 * b22;
		final float ab20 = a20 * b00 + a21 * b10 + a22 * b20;
		final float ab21 = a20 * b01 + a21 * b11 + a22 * b21;
		final float ab22 = a20 * b02 + a21 * b12 + a22 * b22;
		AB = new Matrix3f(ab00, ab01, ab02, ab10, ab11, ab12, ab20, ab21, ab22);

		// ---- Check multiply(Vector3f) and its cache version. ---- //
		// First, try multiplying the identity matrix with a vector. The end
		// result should be the same vector.

		// Try the no-cache version. It returns a new vector.
		cacheVector = Matrix3f.IDENTITY.multiply(u);
		assertNotSame(u, cacheVector);
		assertEquals(u, cacheVector);
		// Try the cache version.
		assertSame(cacheVector, Matrix3f.IDENTITY.multiply(v, cacheVector));
		assertEquals(v, cacheVector);
		// Try the cache version with a null cache. It should create a new one.
		assertNotSame(cacheVector, Matrix3f.IDENTITY.multiply(v, nullVector));
		assertEquals(v, Matrix3f.IDENTITY.multiply(v, nullVector));

		// Try multiplying another matrix with a vector. The end result should
		// be the vector v.
		// Try the no-cache version.
		cacheVector = A.multiply(u);
		assertEquals(v, cacheVector);
		// Try the cache version.
		assertSame(cacheVector, A.multiply(u, cacheVector));
		assertEquals(v, cacheVector);
		// Try the cache version with a null cache. It should create a new one.
		assertNotSame(cacheVector, A.multiply(u, nullVector));
		assertEquals(v, A.multiply(u, nullVector));
		
		// Trying either version with a null vector should throw an exception.
		try {
			A.multiply(nullVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			A.multiply(nullVector, cacheVector);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// --------------------------------------------------------- //

		// ---- Check multiply(Matrix3f) and its cache version. ---- //
		// First, try multiplying matrix A with the identity matrix. The end
		// result should be the same matrix A.

		// Try the no-cache version. It returns the updated matrix A.
		assertSame(A, A.multiply(Matrix3f.IDENTITY));
		assertEquals(Acopy, A);
		// Try the cache version with a null cache. It should create a new one.
		cacheMatrix = A.multiply(Matrix3f.IDENTITY, nullMatrix);
		assertNotSame(A, cacheMatrix);
		assertEquals(A, cacheMatrix);
		// Try the cache version with a non-null cache. It should update it.
		cacheMatrix.set(Matrix3f.ZERO); // Zero out the matrix.
		assertNotEquals(A, cacheMatrix);
		assertSame(cacheMatrix, A.multiply(Matrix3f.IDENTITY, cacheMatrix));
		assertEquals(A, cacheMatrix);
		
		// Try multiplying matrix A with matrix B. The result should be AB.
		
		// Try the cache-version first with a null cache.
		assertNotSame(cacheMatrix, A.multiply(B, nullMatrix));
		assertEquals(AB, A.multiply(B, nullMatrix));
		// Try the cache-version with a non-null cache. The cache should == AB.
		assertSame(cacheMatrix, A.multiply(B, cacheMatrix));
		assertEquals(AB, cacheMatrix);
		// Lastly, try the non-cache version. A now equals AB.
		assertSame(A, A.multiply(B));
		assertEquals(AB, A);
		
		// Trying either version with a null vector should throw an exception.
		try {
			A.multiply(nullMatrix);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			A.multiply(nullMatrix, cacheMatrix);
			fail(failurePrefix + "Operation supports null vector argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// --------------------------------------------------------- //

		return;
	}
}
