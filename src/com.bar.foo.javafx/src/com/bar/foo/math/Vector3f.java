/**
 * 
 */
package com.bar.foo.math;

/**
 * @author Jordan Deyton
 *
 */
public class Vector3f implements IVector3f {

	// TODO Test this class.

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

	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (!equals && object instanceof Vector3f) {
			Vector3f vector = (Vector3f) object;
			equals = (x == vector.x && y == vector.y && z == vector.z);
		}
		return equals;
	}

	@Override
	public int hashCode() {
		int hash = Float.hashCode(x);
		hash = hash * 31 + Float.hashCode(y);
		hash = hash * 31 + Float.hashCode(z);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#length()
	 */
	@Override
	public float length() {
		return FloatMath.sqrt(lengthSquared());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#lengthSquared()
	 */
	@Override
	public float lengthSquared() {
		double x2 = x * x;
		double y2 = y * y;
		double z2 = z * z;
		return (float) (x2 + y2 + z2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#set(float, float, float)
	 */
	@Override
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#set(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f set(Vector3f vector) {
		x = vector.x;
		y = vector.y;
		z = vector.z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#negate()
	 */
	@Override
	public Vector3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#negate(com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#normalize()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#normalize(com.bar.foo.math.Vector3f)
	 */
	@Override
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
		return cache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(float, float, float)
	 */
	@Override
	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f add(Vector3f vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(float, float, float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(float, float, float)
	 */
	@Override
	public Vector3f subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f subtract(Vector3f vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(float, float, float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float)
	 */
	@Override
	public Vector3f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float, float, float)
	 */
	@Override
	public Vector3f multiply(float xScale, float yScale, float zScale) {
		x *= xScale;
		y *= yScale;
		z *= zScale;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float, float, float
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#dot(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float dot(Vector3f vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#cross(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f cross(Vector3f vector) {
		return new Vector3f(y * vector.z - z * vector.y, z * vector.x - x
				* vector.z, x * vector.y - y * vector.x);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#cross(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f cross(Vector3f vector, Vector3f cache) {
		if (cache != null) {
			cache.x = y * vector.z - z * vector.y;
			cache.y = z * vector.x - x * vector.z;
			cache.z = x * vector.y - y * vector.x;
		} else {
			cache = cross(vector);
		}
		return cache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#crossLocal(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f crossLocal(Vector3f vector) {
		float x = y * vector.z - z * vector.y;
		float y = z * vector.x - x * vector.z;
		z = x * vector.y - y * vector.x;
		this.x = x;
		this.y = y;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#distance(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float distance(Vector3f vector) {
		return FloatMath.sqrt(distanceSquared(vector));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.math.IVector3f#distanceSquared(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float distanceSquared(Vector3f vector) {
		double distX = x - vector.x;
		double distY = y - vector.y;
		double distZ = z - vector.z;
		return (float) (distX * distX + distY * distY + distZ * distZ);
	}

}
