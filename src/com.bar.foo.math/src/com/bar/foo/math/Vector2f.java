package com.bar.foo.math;

public class Vector2f implements IVector2f<Vector2f> {

	public float x;
	public float y;

	public static final Vector2f ZERO = new Vector2f(0f, 0f);
	public static final Vector2f UNIT_X = new Vector2f(1f, 0f);
	public static final Vector2f UNIT_Y = new Vector2f(0f, 1f);
	public static final Vector2f IDENTITY = new Vector2f(1f, 1f);

	public Vector2f() {
		x = 0f;
		y = 0f;
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(IVector2f<?> v) {
		if (v == null) {
			x = 0f;
			y = 0f;
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		x = v.getX();
		y = v.getY();
		return;
	}

	// ---- Equals and HashCode ---- //
	@Override
	public boolean equals(Object object) {
		boolean equals = this == object;
		if (!equals && object != null && object instanceof IVector2f<?>) {
			IVector2f<?> v = (IVector2f<?>) object;
			equals = (Float.compare(x, v.getX()) == 0)
					&& (Float.compare(y, v.getY()) == 0);
		}
		return equals;
	}

	@Override
	public int hashCode() {
		int hash = Float.hashCode(x);
		return 31 * hash + Float.hashCode(y);
	}

	// ----------------------------- //

	// ---- Getters and Setters ---- //
	@Override
	public int n() {
		return 2;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public Float getX() {
		return x;
	}

	@Override
	public Float getY() {
		return y;
	}

	@Override
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public Vector2f set(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return set(v.x, v.y);
	}

	// ----------------------------- //

	// ---- Vector Arithmetic ---- //}

	@Override
	public Vector2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	@Override
	public Vector2f add(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x + x;
		cache.y = this.y + y;
		return cache;
	}

	@Override
	public Vector2f subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	@Override
	public Vector2f subtract(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x - x;
		cache.y = this.y - y;
		return cache;
	}

	@Override
	public Vector2f multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	@Override
	public Vector2f multiply(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = this.x * x;
		cache.y = this.y * y;
		return cache;
	}

	@Override
	public Vector2f add(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return add(v.x, v.y);
	}

	@Override
	public Vector2f add(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return add(v.x, v.y, cache);
	}

	@Override
	public Vector2f subtract(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return subtract(v.x, v.y);
	}

	@Override
	public Vector2f subtract(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return subtract(v.x, v.y, cache);
	}

	@Override
	public Vector2f multiply(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return multiply(v.x, v.y);
	}

	@Override
	public Vector2f multiply(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return multiply(v.x, v.y, cache);
	}

	// --------------------------------- //

	// ---- Scalar Arithmetic ---- //
	@Override
	public Vector2f add(float scalar) {
		x += scalar;
		y += scalar;
		return this;
	}

	@Override
	public Vector2f add(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x + scalar;
		cache.y = y + scalar;
		return cache;
	}

	@Override
	public Vector2f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		return this;
	}

	@Override
	public Vector2f subtract(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x - scalar;
		cache.y = y - scalar;
		return cache;
	}

	@Override
	public Vector2f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	@Override
	public Vector2f multiply(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = x * scalar;
		cache.y = y * scalar;
		return cache;
	}

	@Override
	public Vector2f divide(float scalar) {
		if (Float.compare(scalar, 0f) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		return multiply(1f / scalar);
	}

	@Override
	public Vector2f divide(float scalar, Vector2f cache) {
		if (Float.compare(scalar, 0f) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		return multiply(1f / scalar, cache);
	}

	// --------------------------- //

	// ---- Other Vector Operations ---- //

	@Override
	public Float lengthSquared() {
		double x2 = x * x;
		double y2 = y * y;
		return (float) (x2 + y2);
	}

	@Override
	public Float length() {
		return FloatMath.sqrt(lengthSquared());
	}

	@Override
	public Vector2f normalize() {
		float length = length();
		if (Float.compare(length, 0f) != 0) {
			multiply(1 / length);
		} else {
			System.err.println("Vector2f warning: "
					+ "Cannot normalize 0-length vector.");
		}
		return this;
	}

	@Override
	public Vector2f normalize(Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		float length = length();
		if (Float.compare(length, 0f) != 0) {
			multiply(1 / length, cache);
		} else {
			System.err.println("Vector2f warning: "
					+ "Cannot normalize 0-length vector.");
		}
		return cache;
	}

	@Override
	public Vector2f negate() {
		x = -x;
		y = -y;
		return this;
	}

	@Override
	public Vector2f negate(Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		cache.x = -x;
		cache.y = -y;
		return cache;
	}

	@Override
	public Float distanceSquared(float x, float y) {
		double dx = this.x - x;
		double dy = this.y - y;
		return (float) (dx * dx + dy * dy);
	}

	@Override
	public Float distance(float x, float y) {
		return FloatMath.sqrt(distanceSquared(x, y));
	}

	@Override
	public Float dot(float x, float y) {
		return this.x * x + this.y * y;
	}

	@Override
	public Float cross(float x, float y) {
		return this.x * y - this.y * x;
	}

	@Override
	public Float distanceSquared(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return distanceSquared(v.x, v.y);
	}

	@Override
	public Float distance(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return distance(v.x, v.y);
	}

	@Override
	public Float dot(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return dot(v.x, v.y);
	}

	@Override
	public Float cross(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not allowed.");
		}
		return cross(v.x, v.y);
	}

	// --------------------------------- //
}
