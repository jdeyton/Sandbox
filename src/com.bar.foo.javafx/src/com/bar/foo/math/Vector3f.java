/**
 * 
 */
package com.bar.foo.math;

/**
 * This class provides a vector composed of 3 floats as an alternative to the
 * JavaFX library. This class includes methods to normalize the vector or
 * compute vector length, distance between two vectors, dot products, and cross
 * products.
 * <p>
 * Methods that return a {@code Vector3f} will return either a reference to
 * {@code this} or a reference to the specified <i>cache</i> {@code Vector3f}
 * (or a new {@code Vector3f} if the cache was specified as {@code null}). The
 * exception to this rule is {@link #cross(Vector3f)}, which <i>always returns a
 * new {@code Vector3f} containing the cross product by default</i>. If you wish
 * to compute the cross product and overwrite the contents of {@code this}, you
 * may use {@link #crossLocal(Vector3f)} instead.
 * </p>
 * <p>
 * This class additionally provides static vectors representing the zero,
 * identity, and unit vectors as a convenience. Note that if you plan to
 * manipulate these values, <b>DO NOT MANIPULATE THESE VALUES!</b> Instead, use
 * <b><i>copies</i></b> of them by calling the copy constructor, e.g.,
 * {@code new Vector3f(Vector3f.ZERO)} or by using them with the set operation,
 * e.g., {@code myVector.set(Vector3f.IDENTITY)}.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public class Vector3f {

	/**
	 * The x coordinate of the vector.
	 */
	public float x;
	/**
	 * The y coordinate of the vector.
	 */
	public float y;
	/**
	 * The z coordinate of the vector.
	 */
	public float z;

	/**
	 * The zero-vector.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS VECTOR!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Vector3f(Vector3f.ZERO)}.
	 * </p>
	 */
	public static final Vector3f ZERO = new Vector3f();
	/**
	 * The unit-x vector.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS VECTOR!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Vector3f(Vector3f.UNIT_X)} .
	 * </p>
	 */
	public static final Vector3f UNIT_X = new Vector3f(1f, 0f, 0f);
	/**
	 * The unit-y vector.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS VECTOR!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Vector3f(Vector3f.UNIT_Y)} .
	 * </p>
	 */
	public static final Vector3f UNIT_Y = new Vector3f(0f, 1f, 0f);
	/**
	 * The unit-z vector.
	 * <p>
	 * <b>DO NOT MANIPULATE THIS VECTOR!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Vector3f(Vector3f.UNIT_Z)} .
	 * </p>
	 */
	public static final Vector3f UNIT_Z = new Vector3f(0f, 0f, 1f);
	/**
	 * The identity vector. It does NOT have unit length!
	 * <p>
	 * <b>DO NOT MANIPULATE THIS VECTOR!</b> Instead, use a <b><i>copy</i></b>
	 * of it by calling the copy constructor, e.g.,
	 * {@code new Vector3f(Vector3f.IDENTITY)}.
	 * </p>
	 */
	public static final Vector3f IDENTITY = new Vector3f(1f, 1f, 1f);

	/**
	 * The default constructor. Creates a zero vector.
	 */
	public Vector3f() {
		x = y = z = 0f;
	}

	/**
	 * Creates a vector with the specified coordinates.
	 * 
	 * @param x
	 *            The x coordinate of the vector.
	 * @param y
	 *            The y coordinate of the vector.
	 * @param z
	 *            The z coordinate of the vector.
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * A copy constructor. Creates a vector with the same values as the
	 * specified vector or zeroes if the vector is null.
	 * 
	 * @param vector
	 *            The vector to copy.
	 */
	public Vector3f(Vector3f vector) {
		if (vector != null) {
			x = vector.x;
			y = vector.y;
			z = vector.z;
		} else {
			x = y = z = 0f;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (!equals && object instanceof Vector3f) {
			Vector3f vector = (Vector3f) object;
			equals = (Float.compare(x, vector.x) == 0
					&& Float.compare(y, vector.y) == 0 && Float.compare(z,
					vector.z) == 0);
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
		int hash = Float.hashCode(x);
		hash = hash * 31 + Float.hashCode(y);
		return hash * 31 + Float.hashCode(z);
	}

	/**
	 * Computes the length of the vector.
	 * 
	 * @return The length of the vector.
	 */
	public float length() {
		return FloatMath.sqrt(lengthSquared());
	}

	/**
	 * Computes the length (squared) of the vector.
	 * 
	 * @return The length (squared) of the vector.
	 */
	public float lengthSquared() {
		double x2 = x * x;
		double y2 = y * y;
		double z2 = z * z;
		return (float) (x2 + y2 + z2);
	}

	/**
	 * Sets the vector's coordinates to the supplied values.
	 * 
	 * @param x
	 *            The new x value.
	 * @param y
	 *            The new y value.
	 * @param z
	 *            The new z value.
	 * @return A reference to this vector.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the vector's coordinates to the supplied vector's coordinates.
	 * 
	 * @param vector
	 *            The vector containing the new coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f set(Vector3f vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		return this;
	}

	/**
	 * Negates the x, y, and z values of this vector.
	 * 
	 * @return A reference to this vector.
	 */
	public Vector3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * Negates the x, y, and z values of this vector, but stores the resulting
	 * values in the specified <i>cache</i> vector.
	 * 
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f negate(Vector3f cache) {
		if (cache != null) {
			cache.x = -x;
			cache.y = -y;
			cache.z = -z;
		} else {
			cache = new Vector3f(-x, -y, -z);
		}
		return cache;
	}

	/**
	 * Normalizes the vector's values. After this operation, the vector will be
	 * a <i>unit</i> vector (i.e., its length is 1).
	 * 
	 * @return A reference to this vector.
	 */
	public Vector3f normalize() {
		float lengthSquared = lengthSquared();
		if (lengthSquared != 1f && lengthSquared > 0f) {
			float inverseLength = 1f / FloatMath.sqrt(lengthSquared);
			x *= inverseLength;
			y *= inverseLength;
			z *= inverseLength;
		}
		return this;
	}

	/**
	 * Normalizes the vector's values, but stores the resulting values in the
	 * specified <i>cache</i> vector. After this operation, the <i>cache</i>
	 * vector will be a <i>unit</i> vector (i.e., its length is 1).
	 * 
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f normalize(Vector3f cache) {
		float lengthSquared = lengthSquared();
		if (lengthSquared != 1f && lengthSquared > 0f) {
			float inverseLength = 1f / FloatMath.sqrt(lengthSquared);
			if (cache != null) {
				cache.x = x * inverseLength;
				cache.y = y * inverseLength;
				cache.z = z * inverseLength;
			} else {
				cache = new Vector3f(x * inverseLength, y * inverseLength, z
						* inverseLength);
			}
		}
		// TODO Test this logic!!!
		else if (cache != null) {
			cache.x = x;
			cache.y = y;
			cache.z = z;
		} else {
			cache = new Vector3f(x, y, z);
		}
		return cache;
	}

	/**
	 * Adds the specified x, y, and z values to this vector's coordinates.
	 * 
	 * @param x
	 *            The value added to the vector's x coordinate.
	 * @param y
	 *            The value added to the vector's y coordinate.
	 * @param z
	 *            The value added to the vector's z coordinate.
	 * @return A reference to this vector.
	 */
	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Adds the specified vector's coordinates to this vector's coordinates.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be added to this
	 *            vector's coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f add(Vector3f vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		return this;
	}

	/**
	 * Adds the specified x, y, and z values to this vector's coordinates, but
	 * stores the resulting values in the specified <i>cache</i> vector.
	 * 
	 * @param x
	 *            The value added to the vector's x coordinate.
	 * @param y
	 *            The value added to the vector's y coordinate.
	 * @param z
	 *            The value added to the vector's z coordinate.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f add(float x, float y, float z, Vector3f cache) {
		if (cache != null) {
			cache.x = this.x + x;
			cache.y = this.y + y;
			cache.z = this.z + z;
		} else {
			cache = new Vector3f(this.x + x, this.y + y, this.z + z);
		}
		return cache;
	}

	/**
	 * Adds the specified vector's coordinates to this vector's coordinates, but
	 * stores the resulting values in the specified <i>cache</i> vector.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be added to this
	 *            vector's coordinates.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f add(Vector3f vector, Vector3f cache) {
		if (cache != null) {
			cache.x = x + vector.x;
			cache.y = y + vector.y;
			cache.z = z + vector.z;
		} else {
			cache = new Vector3f(x + vector.x, y + vector.y, z + vector.z);
		}
		return cache;
	}

	/**
	 * Subtracts the specified x, y, and z values from this vector's
	 * coordinates.
	 * 
	 * @param x
	 *            The value subtracted from the vector's x coordinate.
	 * @param y
	 *            The value subtracted from the vector's y coordinate.
	 * @param z
	 *            The value subtracted from the vector's z coordinate.
	 * @return A reference to this vector.
	 */
	public Vector3f subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * Subtracts the specified vector's coordinates from this vector's
	 * coordinates.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be subtracted from
	 *            this vector's coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f subtract(Vector3f vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		return this;
	}

	/**
	 * Subtracts the specified x, y, and z values from this vector's
	 * coordinates, but stores the resulting values in the specified
	 * <i>cache</i> vector.
	 * 
	 * @param x
	 *            The value subtracted from the vector's x coordinate.
	 * @param y
	 *            The value subtracted from the vector's y coordinate.
	 * @param z
	 *            The value subtracted from the vector's z coordinate.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f subtract(float x, float y, float z, Vector3f cache) {
		if (cache != null) {
			cache.x = this.x - x;
			cache.y = this.y - y;
			cache.z = this.z - z;
		} else {
			cache = new Vector3f(this.x - x, this.y - y, this.z - z);
		}
		return cache;
	}

	/**
	 * 
	 * Subtracts the specified vector's coordinates from this vector's
	 * coordinates, but stores the resulting values in the specified
	 * <i>cache</i> vector.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be subtracted from
	 *            this vector's coordinates.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f subtract(Vector3f vector, Vector3f cache) {
		if (cache != null) {
			cache.x = x - vector.x;
			cache.y = y - vector.y;
			cache.z = z - vector.z;
		} else {
			cache = new Vector3f(x - vector.x, y - vector.y, z - vector.z);
		}
		return cache;
	}

	/**
	 * Multiplies or scales this vector by the scalar argument.
	 * 
	 * @param scalar
	 *            The scalar value by which this vector's coordinates are
	 *            multiplied.
	 * @return A reference to this vector.
	 */
	public Vector3f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/**
	 * Multiplies or scales this vector by the scalar arguments. Each axis can
	 * be scaled uniquely.
	 * 
	 * @param xScale
	 *            The scalar value by which this vector's x coordinate is
	 *            multiplied.
	 * @param yScale
	 *            The scalar value by which this vector's y coordinate is
	 *            multiplied.
	 * @param zScale
	 *            The scalar value by which this vector's z coordinate is
	 *            multiplied.
	 * @return A reference to this vector.
	 */
	public Vector3f multiply(float xScale, float yScale, float zScale) {
		x *= xScale;
		y *= yScale;
		z *= zScale;
		return this;
	}

	/**
	 * Multiplies or scales this vector by the scalar argument, but stores the
	 * resulting values in the specified <i>cache</i> vector.
	 * 
	 * @param scalar
	 *            The scalar value by which this vector's coordinates are
	 *            multiplied.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f multiply(float scalar, Vector3f cache) {
		if (cache != null) {
			cache.x = x * scalar;
			cache.y = y * scalar;
			cache.z = z * scalar;
		} else {
			cache = new Vector3f(x * scalar, y * scalar, z * scalar);
		}
		return cache;
	}

	/**
	 * Multiplies or scales this vector by the scalar arguments, but stores the
	 * resulting values in the specified <i>cache</i> vector. Each axis can be
	 * scaled uniquely.
	 * 
	 * @param xScale
	 *            The scalar value by which this vector's x coordinate is
	 *            multiplied.
	 * @param yScale
	 *            The scalar value by which this vector's y coordinate is
	 *            multiplied.
	 * @param zScale
	 *            The scalar value by which this vector's z coordinate is
	 *            multiplied.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The <i>cache</i> vector.
	 */
	public Vector3f multiply(float xScale, float yScale, float zScale,
			Vector3f cache) {
		if (cache != null) {
			cache.x = x * xScale;
			cache.y = y * yScale;
			cache.z = z * zScale;
		} else {
			cache = new Vector3f(x * xScale, y * yScale, z * zScale);
		}
		return cache;
	}

	/**
	 * Computes the dot product between this vector and the supplied vector.
	 * 
	 * @param vector
	 *            The opposite vector in the dot product.
	 * @return The scalar dot product value.
	 */
	public float dot(Vector3f vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/**
	 * Computes the cross product between this vector and the supplied vector.
	 * <b>The returned value is a <i>new</i> {@code Vector3f}!!!</b>
	 * <p>
	 * The returned value is the normal (perpendicular vector) for the plane
	 * formed by two vectors. Note that the normal returned is dependent on the
	 * order of the vectors and that our system is based on a right-handed
	 * xyz-coordinate system.
	 * </p>
	 * 
	 * @param vector
	 *            The opposite vector in the cross product.
	 * @return The cross product between this vector and the supplied vector.
	 *         <b>This is a <i>new</i> {@code Vector3f}!!!</b>
	 * @see #crossLocal(Vector3f)
	 */
	public Vector3f cross(Vector3f vector) {
		return cross(vector, null);
	}

	/**
	 * Computes the cross product between this vector and the supplied vector.
	 * <b>The returned value is the <i>cache</i> {@code Vector3f}!!!</b>
	 * <p>
	 * The returned value is the normal (perpendicular vector) for the plane
	 * formed by two vectors. Note that the normal returned is dependent on the
	 * order of the vectors and that our system is based on a right-handed
	 * xyz-coordinate system.
	 * </p>
	 * 
	 * @param vector
	 *            The opposite vector in the cross product.
	 * @param cache
	 *            The vector in which to store the computed values.
	 * @return The cross product between this vector and the supplied vector.
	 *         <b>This is the <i>cache</i> {@code Vector3f}!!!</b>
	 */
	public Vector3f cross(Vector3f vector, Vector3f cache) {
		float cx = y * vector.z - z * vector.y;
		float cy = z * vector.x - x * vector.z;
		float cz = x * vector.y - y * vector.x;
		if (cache != null) {
			cache.x = cx;
			cache.y = cy;
			cache.z = cz;
		} else {
			cache = new Vector3f(cx, cy, cz);
		}
		return cache;
	}

	/**
	 * Computes the cross product between this vector and the supplied vector.
	 * <b>The cross product is stored in <i>this</i> {@code Vector3f}!!!</b>.
	 * 
	 * @param vector
	 *            The opposite vector in the cross product.
	 * @return <b>A reference to <i>this</i> vector.</b>, which is now the cross
	 *         product between this vector's previous values and the supplied
	 *         vector.
	 * 
	 * @see #cross(Vector3f)
	 */
	public Vector3f crossLocal(Vector3f vector) {
		// This has been shortened to as few operations as possible.
		float x = y * vector.z - z * vector.y;
		float y = z * vector.x - this.x * vector.z;
		z = this.x * vector.y - this.y * vector.x;
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Computes the distance between this vector and the supplied vector.
	 * 
	 * @param vector
	 *            The other vector whose distance from this one is being
	 *            calculated.
	 * @return The distance between this vector and the supplied vector.
	 */
	public float distance(Vector3f vector) {
		return FloatMath.sqrt(distanceSquared(vector));
	}

	/**
	 * Computes the distance (squared) between this vector and the supplied
	 * vector.
	 * 
	 * @param vector
	 *            The other vector whose distance from this one is being
	 *            calculated.
	 * @return The distance (squared) between this vector and the supplied
	 *         vector.
	 */
	public float distanceSquared(Vector3f vector) {
		double distX = x - vector.x;
		double distY = y - vector.y;
		double distZ = z - vector.z;
		return (float) (distX * distX + distY * distY + distZ * distZ);
	}

}
