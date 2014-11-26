/**
 * 
 */
package com.bar.foo.math;

/**
 * This class provides a square 3x3 matrix composed of floats as an alternative
 * to the JavaFX library. This class includes mathematical operations for
 * matrices and other constructs.
 * <p>
 * Methods may return a reference to {@code this} or a reference to the
 * specified <i>cache</i> {@code Matrix3f}. It is highly recommended to pay
 * attention to the documentation of methods in this class.
 * </p>
 * <p>
 * This class additionally provides static matrices representing the zero and
 * identity matrices as a convenience. Note that if you plan to manipulate these
 * values, <b>DO NOT MANIPULATE THESE VALUES!</b> Instead, use
 * <b><i>copies</i></b> of them by calling the copy constructor, e.g.,
 * {@code new Matrix3f(Matrix3f.ZERO)} or by using them with the set operation,
 * e.g., {@code myMatrix.set(Matrix3f.IDENTITY)}.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public class Matrix3f {

	/**
	 * The zero-matrix.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS MATRIX!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Matrix3f(Matrix3f.ZERO)}.
	 * </p>
	 */
	public static final Matrix3f ZERO = new Matrix3f();
	/**
	 * The identity-matrix.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS MATRIX!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Matrix3f(Matrix3f.IDENTITY)}.
	 * </p>
	 */
	public static final Matrix3f IDENTITY = new Matrix3f(1f, 0f, 0f, 0f, 1f,
			0f, 0f, 0f, 1f);
	/**
	 * First row, first element.
	 */
	public float m00;
	/**
	 * First row, second element.
	 */
	public float m01;
	/**
	 * First row, third element.
	 */
	public float m02;
	/**
	 * Second row, first element.
	 */
	public float m10;
	/**
	 * Second row, second element.
	 */
	public float m11;
	/**
	 * Second row, third element.
	 */
	public float m12;
	/**
	 * Third row, first element.
	 */
	public float m20;
	/**
	 * Third row, second element.
	 */
	public float m21;
	/**
	 * Third row, third element.
	 */
	public float m22;

	/**
	 * The default constructor. Creates a zero matrix.
	 */
	public Matrix3f() {
		// Zero out the values.
		m00 = m01 = m02 = 0f;
		m10 = m11 = m12 = 0f;
		m20 = m21 = m22 = 0f;
		return;
	}

	/**
	 * Creates a matrix with the specified elements. A value {@code mij}
	 * corresponds to the element in the <i>i</i>th row and <i>j</i>th column.
	 * Indexing starts at 0.
	 * 
	 * @param m00
	 *            First row, first element.
	 * @param m01
	 *            First row, second element.
	 * @param m02
	 *            First row, third element.
	 * @param m10
	 *            Second row, first element.
	 * @param m11
	 *            Second row, second element.
	 * @param m12
	 *            Second row, third element.
	 * @param m20
	 *            Third row, first element.
	 * @param m21
	 *            Third row, second element.
	 * @param m22
	 *            Third row, third element.
	 */
	public Matrix3f(float m00, float m01, float m02, float m10, float m11,
			float m12, float m20, float m21, float m22) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		return;
	}

	/**
	 * Creates a matrix using the specified vectors to populate the matrix. Each
	 * vector corresponds to a row, e.g., the first vector's x, y, and z values
	 * make up the first row in the matrix.
	 * 
	 * @param v0
	 *            The first row of float elements.
	 * @param v1
	 *            The second row of float elements.
	 * @param v2
	 *            The third row of float elements.
	 */
	public Matrix3f(Vector3f v0, Vector3f v1, Vector3f v2) {
		this(v0, v1, v2, true);
	}

	/**
	 * Creates a matrix using the specified vectors to populate the matrix. Each
	 * vector corresponds to either a row or a column in the matrix depending on
	 * the boolean argument.
	 * 
	 * @param v0
	 *            The first row/column of float elements.
	 * @param v1
	 *            The second row/column of float elements.
	 * @param v2
	 *            The third row/column of float elements.
	 * @param rowVectors
	 *            If <b>true</b>, then each vector corresponds to a <b>row</b>
	 *            in the matrix (this is the same as calling
	 *            {@link #Matrix3f(Vector3f, Vector3f, Vector3f)}). If
	 *            <b>false</b>, then each vector corresponds to a <b>column</b>
	 *            in the matrix.
	 */
	public Matrix3f(Vector3f v0, Vector3f v1, Vector3f v2, boolean rowVectors) {
		if (rowVectors) {
			m00 = v0.x;
			m01 = v0.y;
			m02 = v0.z;
			m10 = v1.x;
			m11 = v1.y;
			m12 = v1.z;
			m20 = v2.x;
			m21 = v2.y;
			m22 = v2.z;
		} else {
			m00 = v0.x;
			m01 = v1.x;
			m02 = v2.x;
			m10 = v0.y;
			m11 = v1.y;
			m12 = v2.y;
			m20 = v0.z;
			m21 = v1.z;
			m22 = v2.z;
		}
		return;
	}

	/**
	 * A copy constructor. Creates a matrix with the same values as the
	 * specified matrix or zeroes if the matrix is null.
	 * 
	 * @param matrix
	 *            The matrix to copy.
	 */
	public Matrix3f(Matrix3f matrix) {
		if (matrix != null) {
			m00 = matrix.m00;
			m01 = matrix.m01;
			m02 = matrix.m02;
			m10 = matrix.m10;
			m11 = matrix.m11;
			m12 = matrix.m12;
			m20 = matrix.m20;
			m21 = matrix.m21;
			m22 = matrix.m22;
		} else {
			// Zero out the values.
			m00 = m01 = m02 = 0f;
			m10 = m11 = m12 = 0f;
			m20 = m21 = m22 = 0f;
		}
		return;
	}
}
