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

	public static final Quaternion ZERO = new Quaternion(0f, 0f, 0f, 0f);
	public static final Quaternion IDENTITY = new Quaternion(1f, 0f, 0f, 0f);

	public Quaternion() {
		w = 1f;
		x = y = z = 0f;
	}

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

	public Quaternion set(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Quaternion set(float w, Vector3f vector) {
		this.w = w;
		x = vector.x;
		y = vector.y;
		z = vector.z;
		return this;
	}

	public Quaternion set(Quaternion q) {
		w = q.w;
		x = q.x;
		y = q.y;
		z = q.z;
		return this;
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

	// TODO Change this to a static Matrix3f method, e.g.,
	// Matrix3f.fromQuaternion(Quaternion q, Matrix3f cache)
	public Matrix3f fillRotationMatrix(Matrix3f matrix) {
		if (matrix == null) {
			matrix = new Matrix3f();
		}

		float normSquared = normSquared();
		float norm = FloatMath.sqrt(normSquared);

		// This factor is 2 for normalize quaternions and 2/norm^2 for
		// non-normalized quaternions.
		float f = 2f;
		if (norm != 1f) {
			if (norm > 0f) {
				f = 2f / normSquared();
			} else {
				w = x = y = z = 0f;
				f = 0f;
			}
		}

		// This calculation comes from
		// http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/
		// and takes advantage of the fact that, for unit (normalized)
		// quaternion q = (w, x, y, z), 1 = w*w + x*x + y*y + z*z.

		float fx = f * x;
		float fy = f * y;
		float fz = f * z;
		float fwx = fx * w;
		float fwy = fy * w;
		float fwz = fz * w;
		float fxx = fx * x;
		float fyy = fy * y;
		float fzz = fz * z;
		float fxy = fx * y;
		float fxz = fx * z;
		float fyz = fy * z;

		matrix.m00 = 1 - (fyy + fzz);
		matrix.m01 = fxy - fwz;
		matrix.m02 = fxz + fwy;
		matrix.m10 = fxy + fwz;
		matrix.m11 = 1 - (fxx + fzz);
		matrix.m12 = fyz - fwx;
		matrix.m20 = fxz - fwy;
		matrix.m21 = fyz + fwx;
		matrix.m22 = 1 - (fxx + fyy);

		return matrix;
	}

	// ---- Static Helpers for Creating Quaternions ---- //
	// These are placed here and do not have constructors for multiple reasons:
	// (1) The code is more complex. A method with a cache is preferred, as the
	// code would otherwise be duplicated in constructors and setters.
	// (2) The code is computationally slower than what might be expected from a
	// constructor.

	/**
	 * Computes a quaternion representing a rotation by an angle around a fixed
	 * (Euler) axis. The result is stored in a new Quaternion.
	 * 
	 * @param axis
	 *            The vector representing an Euler axis. If this is the same as
	 *            the zero vector, the returned quaternion is the
	 *            {@link #IDENTITY}.
	 * @param angle
	 *            The rotation angle.
	 * @return A new quaternion representing the angle-axis rotation.
	 */
	public static Quaternion fromAxisAngle(Vector3f axis, float angle) {
		return fromAxisAngle(axis, angle, null);
	}

	/**
	 * Computes a quaternion representing a rotation by an angle around a fixed
	 * (Euler) axis. The result is stored in a the cache.
	 * 
	 * @param axis
	 *            The vector representing an Euler axis. If this is the same as
	 *            the zero vector, the returned quaternion is the
	 *            {@link #IDENTITY}.
	 * @param angle
	 *            The rotation angle.
	 * @param cache
	 *            The quaternion in which to store the angle-axis rotation.
	 * @return The cache quaternion.
	 */
	public static Quaternion fromAxisAngle(Vector3f axis, float angle,
			Quaternion cache) {
		return fromUnitAxisAngle(axis.normalize(null), angle, cache);
	}

	/**
	 * Computes a quaternion representing a rotation by an angle around a fixed
	 * (Euler) axis. The result is stored in a new Quaternion.
	 * 
	 * @param axis
	 *            The vector representing an Euler axis. If this is the same as
	 *            the zero vector, the returned quaternion is the
	 *            {@link #IDENTITY}. <b><i>This is expected to be a unit vector.
	 *            Unexpected results may occur if it is not!</i></b>
	 * @param angle
	 *            The rotation angle.
	 * @return A new quaternion representing the angle-axis rotation.
	 */
	public static Quaternion fromUnitAxisAngle(Vector3f axis, float angle) {
		return fromUnitAxisAngle(axis, angle, null);
	}

	/**
	 * Computes a quaternion representing a rotation by an angle around a fixed
	 * (Euler) axis. The result is stored in a the cache.
	 * 
	 * @param axis
	 *            The vector representing an Euler axis. If this is the same as
	 *            the zero vector, the returned quaternion is the
	 *            {@link #IDENTITY}. <b><i>This is expected to be a unit vector.
	 *            Unexpected results may occur if it is not!</i></b>
	 * @param angle
	 *            The rotation angle.
	 * @param cache
	 *            The quaternion in which to store the angle-axis rotation.
	 * @return The cache quaternion.
	 */
	public static Quaternion fromUnitAxisAngle(Vector3f axis, float angle,
			Quaternion cache) {
		if (cache == null) {
			cache = new Quaternion();
		}
		// Make sure this isn't a rotation around the origin. If not, normalize
		// the vector, then compute the rotation quaternion.
		if (axis.x != 0f || axis.y != 0f || axis.x != 0f) {
			float halfAngle = 0.5f * angle;
			float sinHalfAngle = FloatMath.sin(halfAngle);
			cache.w = FloatMath.cos(halfAngle);
			cache.x = axis.x * sinHalfAngle;
			cache.y = axis.y * sinHalfAngle;
			cache.z = axis.z * sinHalfAngle;
		}
		// If this is rotation about the origin, load the identity quaternion.
		else {
			cache.w = IDENTITY.w;
			cache.x = IDENTITY.x;
			cache.y = IDENTITY.y;
			cache.z = IDENTITY.z;
		}
		return cache;
	}

	/**
	 * Computes a quaternion representing a rotation from a vector <i>u</i> to a
	 * vector <i>v</i>.
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public static Quaternion fromTwoVectors(Vector3f u, Vector3f v) {
		return fromTwoVectors(u, v, null);
	}

	public static Quaternion fromTwoVectors(Vector3f u, Vector3f v,
			Quaternion cache) {
		if (cache == null) {
			cache = new Quaternion();
		}

		// http://lolengine.net/blog/2013/09/18/beautiful-maths-quaternion-from-vectors
		Vector3f cross = u.cross(v);
		cache.w = u.dot(v);
		cache.x = cross.x;
		cache.y = cross.y;
		cache.z = cross.z;
		cache.w += cache.norm();
		cache.normalize();

		// TODO Clean this.
		
		return cache;
	}

	public static Quaternion fromTwoUnitVectors(Vector3f u, Vector3f v) {
		return fromTwoUnitVectors(u, v, null);
	}

	public static Quaternion fromTwoUnitVectors(Vector3f u, Vector3f v,
			Quaternion cache) {
		// TODO
		return cache;
	}

	public static Quaternion fromRotationMatrix(Matrix3f rotation) {
		return fromRotationMatrix(rotation, null);
	}

	public static Quaternion fromRotationMatrix(Matrix3f rotation,
			Quaternion cache) {
		if (cache == null) {
			cache = new Quaternion();
		}

		// This is based off the algebra performed in
		// http://www.ee.ucr.edu/~farrell/AidedNavigation/D_App_Quaternions/Rot2Quat.pdf

		// To minimize the chance of dividing by zero, we compute the list of
		// possible divisors and pick the largest non-zero value.

		// We have four possible divisors for determining the quaternion from
		// the rotation matrix. We should use the greatest divisor furthest from
		// 0! Note that all values are positive.
		float wD = Math.abs(1f + rotation.m00 + rotation.m11 + rotation.m22);
		float xD = Math.abs(1f + rotation.m00 - rotation.m11 - rotation.m22);
		float yD = Math.abs(1f - rotation.m00 + rotation.m11 - rotation.m22);
		float zD = Math.abs(1f - rotation.m00 - rotation.m11 + rotation.m22);

		// See if the w divisor is the greatest.
		if (wD > xD && wD > yD && wD > zD) {
			wD = FloatMath.sqrt(wD);
			cache.w = 0.5f * wD;
			wD = 0.5f / wD;
			cache.x = (rotation.m21 - rotation.m12) * wD;
			cache.y = (rotation.m02 - rotation.m20) * wD;
			cache.z = (rotation.m10 - rotation.m01) * wD;
		}
		// See if the x divisor is the greatest. We have ruled out w.
		else if (xD > yD && xD > zD) {
			xD = FloatMath.sqrt(xD);
			cache.x = 0.5f * xD;
			xD = 0.5f / xD;
			cache.w = (rotation.m21 - rotation.m12) * xD;
			cache.y = (rotation.m01 + rotation.m10) * xD;
			cache.z = (rotation.m02 + rotation.m20) * xD;
		}
		// See if the y divisor is the greatest. We have ruled out w and x.
		else if (yD > zD) {
			yD = FloatMath.sqrt(yD);
			cache.y = 0.5f * yD;
			yD = 0.5f / yD;
			cache.w = (rotation.m02 - rotation.m20) * yD;
			cache.x = (rotation.m01 + rotation.m10) * yD;
			cache.z = (rotation.m12 + rotation.m21) * yD;
		}
		// The z divisor is greatest. We have ruled out w, x, and y.
		else {
			zD = FloatMath.sqrt(zD);
			cache.z = 0.5f * zD;
			zD = 0.5f / zD;
			cache.w = (rotation.m10 - rotation.m01) * zD;
			cache.x = (rotation.m02 + rotation.m20) * zD;
			cache.y = (rotation.m12 + rotation.m21) * zD;
		}

		// TODO Test this method!
		// TODO This might be simplified...

		return cache;
	}
}
