package com.bar.foo.math2;

import com.bar.foo.math.FloatMath;

public class Vector2f {

	public float x = 0f;
	public float y = 0f;

	public static final Vector2f ZERO = new Vector2f(0f, 0f);
	public static final Vector2f UNIT = new Vector2f(1f, 1f);
	public static final Vector2f UNIT_X = new Vector2f(1f, 0f);
	public static final Vector2f UNIT_Y = new Vector2f(0f, 1f);

	public Vector2f() {
		// Nothing to do.
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		x = v.x;
		y = v.y;
		return;
	}

	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f set(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return set(v.x, v.y);
	}

	public Vector2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2f add(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = this.x + x;
		cache.y = this.y + y;
		return cache;
	}

	public Vector2f add(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return add(v.x, v.y);
	}

	public Vector2f add(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return add(v.x, v.y, cache);
	}

	public Vector2f subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2f subtract(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = this.x - x;
		cache.y = this.y - y;
		return cache;
	}

	public Vector2f subtract(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return subtract(v.x, v.y);
	}

	public Vector2f subtract(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return subtract(v.x, v.y, cache);
	}

	public Vector2f multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	public Vector2f multiply(float x, float y, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = this.x * x;
		cache.y = this.y * y;
		return cache;
	}

	public Vector2f multiply(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return multiply(v.x, v.y);
	}

	public Vector2f multiply(Vector2f v, Vector2f cache) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return multiply(v.x, v.y, cache);
	}

	public float dot(float x, float y) {
		return this.x * x + this.y * y;
	}

	public float dot(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return dot(v.x, v.y);
	}

	public float cross(float x, float y) {
		return this.x * y - this.y * x;
	}

	public float cross(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return cross(v.x, v.y);
	}

	// ---- Scalar Operations ---- //
	public Vector2f add(float scalar) {
		x += scalar;
		y += scalar;
		return this;
	}

	public Vector2f add(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = x + scalar;
		cache.y = y + scalar;
		return this;
	}

	public Vector2f subtract(float scalar) {
		x -= scalar;
		y -= scalar;
		return this;
	}

	public Vector2f subtract(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = x + scalar;
		cache.y = y + scalar;
		return cache;
	}

	public Vector2f multiply(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public Vector2f multiply(float scalar, Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = x * scalar;
		cache.y = y * scalar;
		return this;
	}

	public Vector2f divide(float scalar) {
		if (scalar == 0f) {
			throw new IllegalArgumentException("Vector2f error: "
					+ "Cannot divide by zero.");
		}
		return multiply(1f / scalar);
	}

	public Vector2f divide(float scalar, Vector2f cache) {
		if (scalar == 0f) {
			throw new IllegalArgumentException("Vector2f error: "
					+ "Cannot divide by zero.");
		}
		return multiply(1f / scalar, cache);
	}

	// --------------------------- //

	// ---- Properties ---- //
	public float lengthSquared() {
		double x2 = x * x;
		double y2 = y * y;
		return (float) (x2 + y2);
	}

	public float length() {
		return FloatMath.sqrt(lengthSquared());
	}

	public Vector2f normalize() {
		float lengthSquared = lengthSquared();
		if (lengthSquared != 1f && lengthSquared > 0f) {
			float inverseLength = 1f / FloatMath.sqrt(lengthSquared);
			x *= inverseLength;
			y *= inverseLength;
		}
		return this;
	}

	public Vector2f normalize(Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		float lengthSquared = lengthSquared();
		if (lengthSquared != 1f && lengthSquared > 0f) {
			float inverseLength = 1f / FloatMath.sqrt(lengthSquared);
			cache.x = x * inverseLength;
			cache.y = y * inverseLength;
		}
		return cache;
	}

	public Vector2f negate() {
		x = -x;
		y = -y;
		return this;
	}

	public Vector2f negate(Vector2f cache) {
		if (cache == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		cache.x = -x;
		cache.y = -y;
		return cache;
	}

	public float distanceSquared(float x, float y) {
		double dx = this.x - x;
		double dy = this.y - y;
		return (float) (dx * dx + dy * dy);
	}

	public float distanceSquared(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return distanceSquared(v.x, v.y);
	}

	public float distance(float x, float y) {
		return FloatMath.sqrt(distanceSquared(x, y));
	}

	public float distance(Vector2f v) {
		if (v == null) {
			throw new NullPointerException("Vector2f error: "
					+ "Null arguments not accepted");
		}
		return distance(v.x, v.y);
	}

	// -------------------- //

	// ---- Equals/Hash ---- //
	public boolean equals(Object object) {
		boolean equals = this == object;
		if (!equals && object != null && object instanceof Vector2f) {
			Vector2f v = (Vector2f) object;
			equals = Float.compare(x, v.x) == 0 && Float.compare(y, v.y) == 0;
		}
		return equals;
	}

	public int hashCode() {
		int hash = 37;
		hash = hash * 31 + Float.hashCode(x);
		return hash * 31 + Float.hashCode(y);
	}
	// --------------------- //
}
