package com.bar.foo.math;

public class Vector3f implements IVector3f<Vector3f> {

	/**
	 * The x-coordinate for the vector.
	 */
	public float x;
	/**
	 * The y-coordinate for the vector.
	 */
	public float y;
	/**
	 * The z-coordinate for the vector.
	 */
	public float z;

	/**
	 * The zero vector (all coordinates 0), for convenience. <b>Do not modify
	 * this vector!</b>
	 */
	public static final Vector3f ZERO = new Vector3f(0f, 0f, 0f);
	/**
	 * The unit-x (x coordinate is 1) vector, for convenience. <b>Do not modify
	 * this vector!</b>
	 */
	public static final Vector3f UNIT_X = new Vector3f(1f, 0f, 0f);
	/**
	 * The unit-y vector (y coordinate is 1), for convenience. <b>Do not modify
	 * this vector!</b>
	 */
	public static final Vector3f UNIT_Y = new Vector3f(0f, 1f, 0f);
	/**
	 * The unit-z vector (z coordinate is 1), for convenience. <b>Do not modify
	 * this vector!</b>
	 */
	public static final Vector3f UNIT_Z = new Vector3f(0f, 0f, 1f);
	/**
	 * The identity vector (all coordinates 1), for convenience. <b>Do not
	 * modify this vector!</b>
	 */
	public static final Vector3f IDENTITY = new Vector3f(1f, 1f, 1f);

	/**
	 * The default constructor. Creates a new zero vector.
	 */
	public Vector3f() {
		x = 0f;
		y = 0f;
		z = 0f;
	}

	/**
	 * The full constructor. All coordinates must be specified.
	 * 
	 * @param x
	 *            The x-coordinate for the vector.
	 * @param y
	 *            The y-coordinate for the vector.
	 * @param z
	 *            The z-coordinate for the vector.
	 */
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * A copy constructor. This may take any vector implementing
	 * {@link IVector3f}.
	 * 
	 * @param v
	 *            The vector to copy. If {@code null}, the coordinates are set
	 *            to 0 and a {@code NullPointerException} is thrown.
	 */
	public Vector3f(IVector3f<?> v) {
		if (v == null) {
			x = 0f;
			y = 0f;
			z = 0f;
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		x = v.getX();
		y = v.getY();
		z = v.getZ();
		return;
	}

	// ---- Equals and HashCode ---- //
	/**
	 * Determines whether the object is equivalent to this vector. If the
	 * references are the same, it returns immediately, otherwise it attempts to
	 * convert the object to an {@link IVector2f} and compares the coordinates.
	 * 
	 * @param object
	 *            The object to test for equality.
	 * @return True if they are the same object or if they are both IVector3fs
	 *         and their coordinates match, false otherwise.
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = this == object;
		if (!equals && object != null && object instanceof IVector3f) {
			IVector3f<?> v = (IVector3f<?>) object;
			equals = (Float.compare(x, v.getX()) == 0)
					&& (Float.compare(y, v.getY()) == 0)
					&& (Float.compare(z, v.getZ()) == 0);
		}
		return equals;
	}

	/*
	 * Overrides the hash to include the coordinates.
	 */
	@Override
	public int hashCode() {
		int hash = Float.hashCode(x);
		hash = 31 * hash + Float.hashCode(y);
		return 31 * hash + Float.hashCode(z);
	}

	// ----------------------------- //

	// ---- Getters and Setters ---- //
	/*
	 * Implements IVector3f.
	 */
	@Override
	public int n() {
		return 3;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public int size() {
		return 3;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float getX() {
		return x;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float getY() {
		return y;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float getZ() {
		return z;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/*
	 * Implements IVector.
	 */
	@Override
	public Vector3f set(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return set(v.x, v.y, v.z);
	}

	// ----------------------------- //

	// ---- Vector Arithmetic ---- //}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(float x, float y, float z, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x + x;
		cache.y = this.y + y;
		cache.z = this.z + z;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(float x, float y, float z, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x - x;
		cache.y = this.y - y;
		cache.z = this.z - z;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(float x, float y, float z, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x * x;
		cache.y = this.y * y;
		cache.z = this.z * z;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return add(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(Vector3f v, Vector3f cache) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return add(v.x, v.y, v.z, cache);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return subtract(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(Vector3f v, Vector3f cache) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return subtract(v.x, v.y, v.z, cache);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return multiply(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(Vector3f v, Vector3f cache) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return multiply(v.x, v.y, v.z, cache);
	}

	// --------------------------------- //

	// ---- Scalar Arithmetic ---- //

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(float scalar) {
		x += scalar;
		y += scalar;
		z += scalar;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f add(float scalar, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x + scalar;
		cache.y = y + scalar;
		cache.z = z + scalar;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		z -= scalar;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f subtract(float scalar, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x - scalar;
		cache.y = y - scalar;
		cache.z = z - scalar;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f multiply(float scalar, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x * scalar;
		cache.y = y * scalar;
		cache.z = z * scalar;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f divide(float scalar) {
		if (Float.compare(scalar, 0f) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		return multiply(1f / scalar);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f divide(float scalar, Vector3f cache) {
		if (Float.compare(scalar, 0f) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		return multiply(1f / scalar, cache);
	}

	// --------------------------- //

	// ---- Other Vector Operations ---- //

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float lengthSquared() {
		double x2 = x * x;
		double y2 = y * y;
		double z2 = z * z;
		return (float) (x2 + y2 + z2);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float length() {
		return FloatMath.sqrt(lengthSquared());
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f normalize() {
		float length = length();
		if (Float.compare(length, 0f) != 0) {
			multiply(1 / length);
		} else {
			System.err.println("Vector3f warning: "
					+ "Cannot normalize 0-length vector.");
		}
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f normalize(Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		float length = length();
		if (Float.compare(length, 0f) != 0) {
			multiply(1 / length, cache);
		} else {
			System.err.println("Vector3f warning: "
					+ "Cannot normalize 0-length vector.");
		}
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f negate(Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = -x;
		cache.y = -y;
		cache.z = -z;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float distanceSquared(float x, float y, float z) {
		double dx = this.x - x;
		double dy = this.y - y;
		double dz = this.z - z;
		return (float) (dx * dx + dy * dy + dz * dz);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float distance(float x, float y, float z) {
		return FloatMath.sqrt(distanceSquared(x, y, z));
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float dot(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f cross(float x, float y, float z) {
		float cx = this.y * z - this.z * y;
		float cy = this.z * x - this.x * z;
		this.z = this.x * y - this.y * x;
		this.x = cx;
		this.y = cy;
		return this;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f cross(float x, float y, float z, Vector3f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.y * z - this.z * y;
		cache.y = this.z * x - this.x * z;
		cache.z = this.x * y - this.y * x;
		return cache;
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float distanceSquared(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return distanceSquared(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float distance(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return distance(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Float dot(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return dot(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f cross(Vector3f v) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return cross(v.x, v.y, v.z);
	}

	/*
	 * Implements IVector3f.
	 */
	@Override
	public Vector3f cross(Vector3f v, Vector3f cache) {
		if (v == null) {
			throw new NullPointerException("Vector3f error: "
					+ "Null arguments not allowed.");
		}
		return cross(v.x, v.y, v.z, cache);
	}
	// --------------------------------- //
}
