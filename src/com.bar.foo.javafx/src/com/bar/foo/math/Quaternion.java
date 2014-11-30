/**
 * 
 */
package com.bar.foo.math;

/**
 * @author Jordan Deyton
 *
 */
public class Quaternion {

	/**
	 * The scalar part of the quaternion.
	 */
	public float w;
	/**
	 * The x coordinate of the vector part of the quaternion.
	 */
	public float x;
	/**
	 * The y coordinate of the vector part of the quaternion.
	 */
	public float y;
	/**
	 * The z coordinate of the vector part of the quaternion.
	 */
	public float z;

	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Quaternion(float w, Vector3f vector) {
		this.w = w;
		x = vector.x;
		y = vector.y;
		z = vector.z;
	}

	public Quaternion(Quaternion quaternion) {
		w = quaternion.w;
		x = quaternion.x;
		y = quaternion.y;
		z = quaternion.z;
	}

	/**
	 * Constructs a quaternion representing the rotation by angle around a fixed
	 * (Euler) axis.
	 * 
	 * @param axis
	 *            The <b><i>unit</i></b> vector representing an Euler axis.
	 * @param angle
	 *            The rotation angle.
	 */
	public Quaternion(Vector3f axis, float angle) {
		float halfAngle = 0.5f * angle;
		float sinHalfAngle = FloatMath.sin(halfAngle);
		w = FloatMath.cos(halfAngle);
		x = axis.x * sinHalfAngle;
		y = axis.y * sinHalfAngle;
		z = axis.z * sinHalfAngle;
	}

	public Quaternion add(float w, float x, float y, float z) {
		return add(w, x, y, z, this);
	}

	public Quaternion add(float w, Vector3f vector) {
		return add(w, vector, this);
	}

	public Quaternion add(float w, float x, float y, float z, Quaternion cache) {
		if (cache != null) {
			cache.w = this.w + w;
			cache.x = this.x + x;
			cache.y = this.y + y;
			cache.z = this.z + z;
		} else {
			cache = new Quaternion(this.w + w, this.x + x, this.y + y, this.z
					+ z);
		}
		return cache;
	}

	public Quaternion add(float w, Vector3f vector, Quaternion cache) {
		if (cache != null) {
			cache.w = this.w + w;
			cache.x = x + vector.x;
			cache.y = y + vector.y;
			cache.z = z + vector.z;
		} else {
			cache = new Quaternion(this.w + w, x + vector.x, vector.y
					+ vector.y, z + vector.z);
		}
		return cache;
	}

	public Quaternion multiply(float scalar) {
		return multiply(scalar, this);
	}

	public Quaternion multiply(float scalar, Quaternion cache) {
		if (cache != null) {
			cache.w = w * scalar;
			cache.x = x * scalar;
			cache.y = y * scalar;
			cache.z = z * scalar;
		} else {
			cache = new Quaternion(w * scalar, x * scalar, y * scalar, z
					* scalar);
		}
		return cache;
	}

	public Quaternion multiply(float w, float x, float y, float z) {
		return multiply(w, x, y, z, this);
	}

	public Quaternion multiply(float w, Vector3f v) {
		return multiply(w, v, this);
	}

	public Quaternion multiply(Quaternion q) {
		return multiply(q, this);
	}

	public Quaternion multiply(float w, float x, float y, float z,
			Quaternion cache) {
		float pqW = this.w * w - this.x * x - this.y * y - this.z * z;
		float pqX = this.w * x + this.x * w + this.y * z - this.z * y;
		float pqY = this.w * y - this.x * z + this.y * w + this.z * x;
		float pqZ = this.w * z + this.x * y - this.y * x + this.z * w;

		if (cache != null) {
			cache.w = pqW;
			cache.x = pqX;
			cache.y = pqY;
			cache.z = pqZ;
		} else {
			cache = new Quaternion(pqW, pqX, pqY, pqZ);
		}
		return cache;
	}

	public Quaternion multiply(float w, Vector3f v, Quaternion cache) {
		float pqW = this.w * w - x * v.x - y * v.y - z * v.z;
		float pqX = this.w * v.x + x * w + y * v.z - z * v.y;
		float pqY = this.w * v.y - x * v.z + y * w + z * v.x;
		float pqZ = this.w * v.z + x * v.y - y * v.x + z * w;

		if (cache != null) {
			cache.w = pqW;
			cache.x = pqX;
			cache.y = pqY;
			cache.z = pqZ;
		} else {
			cache = new Quaternion(pqW, pqX, pqY, pqZ);
		}
		return cache;
	}

	public Quaternion multiply(Quaternion q, Quaternion cache) {
		float pqW = w * q.w - x * q.x - y * q.y - z * q.z;
		float pqX = w * q.x + x * q.w + y * q.z - z * q.y;
		float pqY = w * q.y - x * q.z + y * q.w + z * q.x;
		float pqZ = w * q.z + x * q.y - y * q.x + z * q.w;

		if (cache != null) {
			cache.w = pqW;
			cache.x = pqX;
			cache.y = pqY;
			cache.z = pqZ;
		} else {
			cache = new Quaternion(pqW, pqX, pqY, pqZ);
		}
		return cache;
	}

	public float norm() {
		return FloatMath.sqrt(normSquared());
	}

	public float normSquared() {
		return w * w + x * x + y * y + z * z;
	}

	public Quaternion normalize() {
		return normalize(this);
	}

	public Quaternion normalize(Quaternion cache) {
		float normSquared = normSquared();
		if (normSquared != 1f && normSquared > 0f) {
			float inverseLength = 1f / FloatMath.sqrt(normSquared);
			if (cache != null) {
				cache.w = w * inverseLength;
				cache.x = x * inverseLength;
				cache.y = y * inverseLength;
				cache.z = z * inverseLength;
			} else {
				cache = new Quaternion(w * inverseLength, x * inverseLength, y
						* inverseLength, z * inverseLength);
			}
		}
		return cache;
	}

	public Quaternion conjugate() {
		return conjugate(this);
	}

	public Quaternion conjugate(Quaternion cache) {
		if (cache != null) {
			cache.w = w;
			cache.x = -x;
			cache.y = -y;
			cache.z = -z;
		} else {
			cache = new Quaternion(w, -x, -y, -z);
		}
		return cache;
	}

	public Quaternion reciprocate() {
		return reciprocate(this);
	}

	public Quaternion reciprocate(Quaternion cache) {
		float normSquared = normSquared();
		if (normSquared > 0f) {
			float inverseNormSquared = 1f / normSquared();
			Quaternion conjugate = conjugate(null);
			if (cache != null) {
				cache.w = conjugate.w * inverseNormSquared;
				cache.x = conjugate.x * inverseNormSquared;
				cache.y = conjugate.y * inverseNormSquared;
				cache.z = conjugate.z * inverseNormSquared;
			} else {
				cache = new Quaternion(conjugate.w * inverseNormSquared,
						conjugate.x * inverseNormSquared, conjugate.y
								* inverseNormSquared, conjugate.z
								* inverseNormSquared);
			}
		} else if (cache != null) {
			cache.w = w;
			cache.x = x;
			cache.y = y;
			cache.z = z;
		} else {
			cache = new Quaternion(w, x, y, z);
		}
		return cache;
	}

	public Vector3f rotate(Vector3f vector) {
		return rotate(vector, vector);
	}

	public Vector3f rotate(Vector3f vector, Vector3f cache) {
		// To rotate a vector v, convert it to quaternion p = (0, x, y, z),
		// compute p' = qp(q^-1), and extract the x, y, and z from p'.
		Quaternion p = new Quaternion(0f, vector);
		Quaternion qReciprocal = reciprocate(null);
		multiply(p, p).multiply(qReciprocal);

		if (cache != null) {
			cache.set(p.x, p.y, p.z);
		} else {
			cache = new Vector3f(p.x, p.y, p.z);
		}
		return cache;
	}

}
