/**
 * 
 */
package com.bar.foo.math;

/**
 * @author Jordan Deyton
 *
 */
public interface IVector3f {

	// nullary constructor
	// set constructor
	// copy constructor
	/**
	 * Computes the length of the vector.
	 * 
	 * @return The length of the vector.
	 */
	public float length();

	/**
	 * Computes the length (squared) of the vector.
	 * 
	 * @return The length (squared) of the vector.
	 */
	public float lengthSquared();

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
	public Vector3f set(float x, float y, float z);

	/**
	 * Sets the vector's coordinates to the supplied vector's coordinates.
	 * 
	 * @param vector
	 *            The vector containing the new coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f set(Vector3f vector);

	/**
	 * Negates the x, y, and z values of this vector.
	 * 
	 * @return A reference to this vector.
	 */
	public Vector3f negate();

	/**
	 * Negates the x, y, and z values of this vector, but stores the resulting
	 * values in the specified <i>store</i> vector.
	 * 
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f negate(Vector3f store);

	/**
	 * Normalizes the vector's values. After this operation, the vector will be
	 * a <i>unit</i> vector (i.e., its length is 1).
	 * 
	 * @return A reference to this vector.
	 */
	public Vector3f normalize();

	/**
	 * 
	 * Normalizes the vector's values, but stores the resulting values in the
	 * specified <i>store</i> vector. After this operation, the <i>store</i>
	 * vector will be a <i>unit</i> vector (i.e., its length is 1).
	 * 
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f normalize(Vector3f store);

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
	public Vector3f add(float x, float y, float z);

	/**
	 * Adds the specified vector's coordinates to this vector's coordinates.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be added to this
	 *            vector's coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f add(Vector3f vector);

	/**
	 * Adds the specified x, y, and z values to this vector's coordinates, but
	 * stores the resulting values in the specified <i>store</i> vector.
	 * 
	 * @param x
	 *            The value added to the vector's x coordinate.
	 * @param y
	 *            The value added to the vector's y coordinate.
	 * @param z
	 *            The value added to the vector's z coordinate.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f add(float x, float y, float z, Vector3f store);

	/**
	 * Adds the specified vector's coordinates to this vector's coordinates, but
	 * stores the resulting values in the specified <i>store</i> vector.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be added to this
	 *            vector's coordinates.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f add(Vector3f vector, Vector3f store);

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
	public Vector3f subtract(float x, float y, float z);

	/**
	 * Subtracts the specified vector's coordinates from this vector's
	 * coordinates.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be subtracted from
	 *            this vector's coordinates.
	 * @return A reference to this vector.
	 */
	public Vector3f subtract(Vector3f vector);

	/**
	 * Subtracts the specified x, y, and z values from this vector's
	 * coordinates, but stores the resulting values in the specified
	 * <i>store</i> vector.
	 * 
	 * @param x
	 *            The value subtracted from the vector's x coordinate.
	 * @param y
	 *            The value subtracted from the vector's y coordinate.
	 * @param z
	 *            The value subtracted from the vector's z coordinate.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f subtract(float x, float y, float z, Vector3f store);

	/**
	 * 
	 * Subtracts the specified vector's coordinates from this vector's
	 * coordinates, but stores the resulting values in the specified
	 * <i>store</i> vector.
	 * 
	 * @param vector
	 *            The vector whose x, y, and z values will be subtracted from
	 *            this vector's coordinates.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f subtract(Vector3f vector, Vector3f store);

	/**
	 * Multiplies or scales this vector by the scalar argument.
	 * 
	 * @param scalar
	 *            The scalar value by which this vector's coordinates are
	 *            multiplied.
	 * @return A reference to this vector.
	 */
	public Vector3f multiply(float scalar);

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
	public Vector3f multiply(float xScale, float yScale, float zScale);

	/**
	 * Multiplies or scales this vector by the scalar argument, but stores the
	 * resulting values in the specified <i>store</i> vector.
	 * 
	 * @param scalar
	 *            The scalar value by which this vector's coordinates are
	 *            multiplied.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f multiply(float scalar, Vector3f store);

	/**
	 * Multiplies or scales this vector by the scalar arguments, but stores the
	 * resulting values in the specified <i>store</i> vector. Each axis can be
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
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The <i>store</i> vector.
	 */
	public Vector3f multiply(float xScale, float yScale, float zScale,
			Vector3f store);

	/**
	 * Computes the dot product between this vector and the supplied vector.
	 * 
	 * @param vector
	 *            The opposite vector in the dot product.
	 * @return The scalar dot product value.
	 */
	public float dot(Vector3f vector);

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
	public Vector3f cross(Vector3f vector);

	/**
	 * Computes the cross product between this vector and the supplied vector.
	 * <b>The returned value is the <i>store</i> {@code Vector3f}!!!</b>
	 * <p>
	 * The returned value is the normal (perpendicular vector) for the plane
	 * formed by two vectors. Note that the normal returned is dependent on the
	 * order of the vectors and that our system is based on a right-handed
	 * xyz-coordinate system.
	 * </p>
	 * 
	 * @param vector
	 *            The opposite vector in the cross product.
	 * @param store
	 *            The vector in which to store the computed values.
	 * @return The cross product between this vector and the supplied vector.
	 *         <b>This is the <i>store</i> {@code Vector3f}!!!</b>
	 */
	public Vector3f cross(Vector3f vector, Vector3f store);

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
	public Vector3f crossLocal(Vector3f vector);

	/**
	 * Computes the distance between this vector and the supplied vector.
	 * 
	 * @param vector
	 *            The other vector whose distance from this one is being
	 *            calculated.
	 * @return The distance between this vector and the supplied vector.
	 */
	public float distance(Vector3f vector);

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
	public float distanceSquared(Vector3f vector);
}
