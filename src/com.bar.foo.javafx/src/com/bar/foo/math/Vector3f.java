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
	 * @see com.bar.foo.math.IVector3f#length()
	 */
	@Override
	public float length() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#lengthSquared()
	 */
	@Override
	public float lengthSquared() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#set(float, float, float)
	 */
	@Override
	public Vector3f set(float x, float y, float z) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#set(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f set(Vector3f vector) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#negate()
	 */
	@Override
	public Vector3f negate() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#negate(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f negate(Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#normalize()
	 */
	@Override
	public Vector3f normalize() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#normalize(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f normalize(Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(float, float, float)
	 */
	@Override
	public Vector3f add(float x, float y, float z) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f add(Vector3f vector) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(float, float, float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f add(float x, float y, float z, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#add(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f add(Vector3f vector, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(float, float, float)
	 */
	@Override
	public Vector3f subtract(float x, float y, float z) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f subtract(Vector3f vector) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(float, float, float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f subtract(float x, float y, float z, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#subtract(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f subtract(Vector3f vector, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float)
	 */
	@Override
	public Vector3f multiply(float scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#multiply(float,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f multiply(float scalar, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#dot(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float dot(Vector3f vector) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#cross(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f cross(Vector3f vector) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#cross(com.bar.foo.math.Vector3f,
	 * com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f cross(Vector3f vector, Vector3f store) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#crossLocal(com.bar.foo.math.Vector3f)
	 */
	@Override
	public Vector3f crossLocal(Vector3f vector) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.math.IVector3f#distance(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float distance(Vector3f vector) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.math.IVector3f#distanceSquared(com.bar.foo.math.Vector3f)
	 */
	@Override
	public float distanceSquared(Vector3f vector) {
		// TODO Auto-generated method stub
		return 0;
	}

	// TODO Add unit vectors.

}
