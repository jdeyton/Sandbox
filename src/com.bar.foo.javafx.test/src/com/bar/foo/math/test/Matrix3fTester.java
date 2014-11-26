package com.bar.foo.math.test;

import static org.junit.Assert.assertEquals;
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
		final Matrix3f nullMatrix = null;
		final Vector3f nullVector = null;
		Vector3f v0, v1, v2;

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

		// ---- Check the first default Vector3f-based constructor. ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		matrix = new Matrix3f(v0, v1, v2);
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(m22, matrix.m22, delta);
		// ------------------------------------------------------------- //

		// ---- Check the full Vector3f-based constructor. ---- //
		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a row in the matrix.
		v0 = new Vector3f(m00, m01, m02);
		v1 = new Vector3f(m10, m11, m12);
		v2 = new Vector3f(m20, m21, m22);

		matrix = new Matrix3f(v0, v1, v2, true);
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(m22, matrix.m22, delta);

		// Set up the vectors so that the resulting matrix should be the same as
		// above. Note that each vector corresponds to a column in the matrix.
		v0 = new Vector3f(m00, m10, m20);
		v1 = new Vector3f(m01, m11, m21);
		v2 = new Vector3f(m02, m12, m22);

		matrix = new Matrix3f(v0, v1, v2, false);
		assertEquals(m00, matrix.m00, delta);
		assertEquals(m01, matrix.m01, delta);
		assertEquals(m02, matrix.m02, delta);
		assertEquals(m10, matrix.m10, delta);
		assertEquals(m11, matrix.m11, delta);
		assertEquals(m12, matrix.m12, delta);
		assertEquals(m20, matrix.m20, delta);
		assertEquals(m21, matrix.m21, delta);
		assertEquals(m22, matrix.m22, delta);
		// ---------------------------------------------------- //

		// ---- Check the copy constructor. ---- //
		// Try it with a valid matrix.
		copy = new Matrix3f(matrix);
		assertEquals(m00, copy.m00, delta);
		assertEquals(m01, copy.m01, delta);
		assertEquals(m02, copy.m02, delta);
		assertEquals(m10, copy.m10, delta);
		assertEquals(m11, copy.m11, delta);
		assertEquals(m12, copy.m12, delta);
		assertEquals(m20, copy.m20, delta);
		assertEquals(m21, copy.m21, delta);
		assertEquals(m22, copy.m22, delta);
		
		// Try it with a null matrix. All values should revert to 0.
		copy = new Matrix3f(nullMatrix);
		assertEquals(0f, copy.m00, delta);
		assertEquals(0f, copy.m01, delta);
		assertEquals(0f, copy.m02, delta);
		assertEquals(0f, copy.m10, delta);
		assertEquals(0f, copy.m11, delta);
		assertEquals(0f, copy.m12, delta);
		assertEquals(0f, copy.m20, delta);
		assertEquals(0f, copy.m21, delta);
		assertEquals(0f, copy.m22, delta);		
		// ------------------------------------- //
		
		// ---- Make sure null pointers throw exceptions. ---- //
		// Check all possible nulls in the default vector-based constructor.
		try {
			matrix = new Matrix3f(nullVector, v1, v2);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, nullVector, v2);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, v1, nullVector);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// Check all possible nulls in the other vector-based constructor.
		try {
			matrix = new Matrix3f(nullVector, v1, v2, false);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, nullVector, v2, false);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			matrix = new Matrix3f(v0, v1, nullVector, false);
			fail(failurePrefix + "Operation supports null matrix argument!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// --------------------------------------------------- //

		return;
	}

}
