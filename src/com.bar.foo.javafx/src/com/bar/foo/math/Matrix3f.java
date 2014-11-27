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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = (this == object);
		if (!equals && object instanceof Matrix3f) {
			Matrix3f matrix = (Matrix3f) object;
			equals = (Float.compare(m00, matrix.m00) == 0
					&& Float.compare(m01, matrix.m01) == 0
					&& Float.compare(m02, matrix.m02) == 0
					&& Float.compare(m10, matrix.m10) == 0
					&& Float.compare(m11, matrix.m11) == 0
					&& Float.compare(m12, matrix.m12) == 0
					&& Float.compare(m20, matrix.m20) == 0
					&& Float.compare(m21, matrix.m21) == 0 && Float.compare(
					m22, matrix.m22) == 0);
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = Float.hashCode(m00);
		hash = hash * 31 + Float.hashCode(m01);
		hash = hash * 31 + Float.hashCode(m02);
		hash = hash * 31 + Float.hashCode(m10);
		hash = hash * 31 + Float.hashCode(m11);
		hash = hash * 31 + Float.hashCode(m12);
		hash = hash * 31 + Float.hashCode(m20);
		hash = hash * 31 + Float.hashCode(m21);
		return hash * 31 + Float.hashCode(m22);
	}

	/**
	 * Sets the values of the matrix to the specified elements. A value
	 * {@code mij} corresponds to the element in the <i>i</i>th row and
	 * <i>j</i>th column. Indexing starts at 0.
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
	 * @return A reference to this matrix.
	 */
	public Matrix3f set(float m00, float m01, float m02, float m10, float m11,
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
		return this;
	}

	/**
	 * Sets the values of the matrix to the same values in the given vectors.
	 * Each vector corresponds to a row, e.g., the first vector's x, y, and z
	 * values make up the first row in the matrix.
	 * 
	 * @param v0
	 *            The first row of float elements.
	 * @param v1
	 *            The second row of float elements.
	 * @param v2
	 *            The third row of float elements.
	 * @return A reference to this matrix.
	 */
	public Matrix3f set(Vector3f v0, Vector3f v1, Vector3f v2) {
		return set(v0, v1, v2, true);
	}

	/**
	 * Sets the values of the matrix to the same values in the given vectors.
	 * Each vector corresponds to either a row or a column in the matrix
	 * depending on the boolean argument.
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
	 *            {@link #set(Vector3f, Vector3f, Vector3f)}). If <b>false</b>,
	 *            then each vector corresponds to a <b>column</b> in the matrix.
	 * @return
	 */
	public Matrix3f set(Vector3f v0, Vector3f v1, Vector3f v2,
			boolean rowVectors) {
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
		return this;
	}

	/**
	 * Sets the values of the matrix to the same values in the specified matrix.
	 * 
	 * @param matrix
	 *            The matrix whose values will be copied.
	 * @return A reference to this matrix.
	 */
	public Matrix3f set(Matrix3f matrix) {
		m00 = matrix.m00;
		m01 = matrix.m01;
		m02 = matrix.m02;
		m10 = matrix.m10;
		m11 = matrix.m11;
		m12 = matrix.m12;
		m20 = matrix.m20;
		m21 = matrix.m21;
		m22 = matrix.m22;
		return this;
	}

	/**
	 * Sets the specified row of the matrix to the specified values.
	 * 
	 * @param row
	 *            The row, indexed from 0 (values other than 0, 1, and 2 will be
	 *            ignored).
	 * @param x
	 *            The first element in the row.
	 * @param y
	 *            The second element in the row.
	 * @param z
	 *            The third element in the row.
	 * @return A reference to this matrix.
	 */
	public Matrix3f setRow(int row, float x, float y, float z) {
		if (row == 0) {
			m00 = x;
			m01 = y;
			m02 = z;
		} else if (row == 1) {
			m10 = x;
			m11 = y;
			m12 = z;
		} else if (row == 2) {
			m20 = x;
			m21 = y;
			m22 = z;
		}
		return this;
	}

	/**
	 * Sets the specified row of the matrix to the values stored in the
	 * specified vector.
	 * 
	 * @param row
	 *            The row, indexed from 0 (values other than 0, 1, and 2 will be
	 *            ignored).
	 * @param vector
	 *            The x, y, and z of the vector will be the first, second, and
	 *            third elements in the row of the matrix.
	 * @return A reference to this matrix.
	 */
	public Matrix3f setRow(int row, Vector3f vector) {
		if (row == 0) {
			m00 = vector.x;
			m01 = vector.y;
			m02 = vector.z;
		} else if (row == 1) {
			m10 = vector.x;
			m11 = vector.y;
			m12 = vector.z;
		} else if (row == 2) {
			m20 = vector.x;
			m21 = vector.y;
			m22 = vector.z;
		}
		return this;
	}

	/**
	 * Sets the specified column of the matrix to the specified values.
	 * 
	 * @param column
	 *            The column, indexed from 0 (values other than 0, 1, and 2 will
	 *            be ignored).
	 * @param x
	 *            The first element in the column.
	 * @param y
	 *            The second element in the column.
	 * @param z
	 *            The third element in the column.
	 * @return A reference to this matrix.
	 */
	public Matrix3f setColumn(int column, float x, float y, float z) {
		if (column == 0) {
			m00 = x;
			m10 = y;
			m20 = z;
		} else if (column == 1) {
			m01 = x;
			m11 = y;
			m21 = z;
		} else if (column == 2) {
			m02 = x;
			m12 = y;
			m22 = z;
		}
		return this;
	}

	/**
	 * Sets the specified row of the matrix to the values stored in the
	 * specified vector.
	 * 
	 * @param column
	 *            The column, indexed from 0 (values other than 0, 1, and 2 will
	 *            be ignored).
	 * @param vector
	 *            The x, y, and z of the vector will be the first, second, and
	 *            third elements in the column of the matrix.
	 * @return A reference to this matrix.
	 */
	public Matrix3f setColumn(int column, Vector3f vector) {
		if (column == 0) {
			m00 = vector.x;
			m10 = vector.y;
			m20 = vector.z;
		} else if (column == 1) {
			m01 = vector.x;
			m11 = vector.y;
			m21 = vector.z;
		} else if (column == 2) {
			m02 = vector.x;
			m12 = vector.y;
			m22 = vector.z;
		}
		return this;
	}

	/**
	 * Sets the specified element to the specified value.
	 * <p>
	 * <b>Note:</b> The values can be accessed directly by setting one of
	 * {@link #m00}, {@link #m01}, ... {@link #m22}. This method must resort to
	 * nested switches, so it is not terribly effecient. It is provided for
	 * convenience.
	 * </p>
	 * 
	 * @param row
	 *            The row of the element, either 0, 1, or 2.
	 * @param column
	 *            The column of the element, either 0, 1, or 2.
	 * @param value
	 *            The new value of the specified element.
	 * @return A reference to this matrix.
	 */
	public Matrix3f set(int row, int column, float value) {
		switch (row) {
		case 0:
			switch (column) {
			case 0:
				m00 = value;
				break;
			case 1:
				m01 = value;
				break;
			case 2:
				m02 = value;
				break;
			}
			break;
		case 1:
			switch (column) {
			case 0:
				m10 = value;
				break;
			case 1:
				m11 = value;
				break;
			case 2:
				m12 = value;
				break;
			}
			break;
		case 2:
			switch (column) {
			case 0:
				m20 = value;
				break;
			case 1:
				m21 = value;
				break;
			case 2:
				m22 = value;
				break;
			}
			break;
		}
		return this;
	}

	/**
	 * Multiplies this matrix by the given matrix and stores the result in
	 * <i>this</i> matrix.
	 * 
	 * @param matrix
	 *            The matrix against which to multiply this one.
	 * @return A reference to this matrix.
	 */
	public Matrix3f multiply(Matrix3f matrix) {
		return multiply(matrix, this);
	}

	/**
	 * Multiplies this matrix by the given matrix and stores the result in the
	 * <i>cache</i> matrix.
	 * 
	 * @param matrix
	 *            The matrix against which to multiply this one.
	 * @param cache
	 *            The matrix in which to store the computed values.
	 * @return A reference to the cache.
	 */
	public Matrix3f multiply(Matrix3f matrix, Matrix3f cache) {
		float p00 = m00 * matrix.m00 + m01 * matrix.m10 + m02 * matrix.m20;
		float p10 = m10 * matrix.m00 + m11 * matrix.m10 + m12 * matrix.m20;
		float p20 = m20 * matrix.m00 + m21 * matrix.m10 + m22 * matrix.m20;

		float p01 = m00 * matrix.m01 + m01 * matrix.m11 + m02 * matrix.m21;
		float p11 = m10 * matrix.m01 + m11 * matrix.m11 + m12 * matrix.m21;
		float p21 = m20 * matrix.m01 + m21 * matrix.m11 + m22 * matrix.m21;

		float p02 = m00 * matrix.m02 + m01 * matrix.m12 + m02 * matrix.m22;
		float p12 = m10 * matrix.m02 + m11 * matrix.m12 + m12 * matrix.m22;
		float p22 = m20 * matrix.m02 + m21 * matrix.m12 + m22 * matrix.m22;

		if (cache != null) {
			cache.set(p00, p01, p02, p10, p11, p12, p20, p21, p22);
		} else {
			cache = new Matrix3f(p00, p01, p02, p10, p11, p12, p20, p21, p22);
		}
		return cache;
	}

	/**
	 * Multiplies this matrix by the given vector and stores the result in a new
	 * vector.
	 * 
	 * @param vector
	 *            The vector against which to multiply this matrix.
	 * @return A <i>new</i> vector containing the result.
	 */
	public Vector3f multiply(Vector3f vector) {
		return multiply(vector, null);
	}

	/**
	 * Multiplies this matrix by the given vector and stores the result in the
	 * <i>cache</i> vector.
	 * 
	 * @param vector
	 *            The vector against which to multiply this matrix.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return A reference to the cache.
	 */
	public Vector3f multiply(Vector3f vector, Vector3f cache) {
		float x = m00 * vector.x + m01 * vector.y + m02 * vector.z;
		float y = m10 * vector.x + m11 * vector.y + m12 * vector.z;
		float z = m20 * vector.x + m21 * vector.y + m22 * vector.z;

		if (cache != null) {
			cache.set(x, y, z);
		} else {
			cache = new Vector3f(x, y, z);
		}
		return cache;
	}

}
