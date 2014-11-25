package com.bar.foo.math.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import com.bar.foo.math.FloatMath;
import com.bar.foo.math.Vector3f;

/**
 * This class checks all methods and attributes provided by {@link Vector3f}.
 * 
 * @author Jordan
 *
 */
public class Vector3fTester {

	/**
	 * The delta value to be used in all float/double comparisons.
	 */
	private static final double delta = 1e-7;

	/**
	 * A prefix used when failing with a custom message.
	 */
	private static final String failurePrefix = "Vector3fTester failure: ";

	/**
	 * A random instance used to generate random numbers.
	 */
	private static final Random random = new Random(System.nanoTime());

	/**
	 * This tests the static vectors provided by {@code Vector3f}.
	 */
	@Test
	public void checkStaticVectors() {
		// zero vector
		assertEquals(0f, Vector3f.ZERO.x, delta);
		assertEquals(0f, Vector3f.ZERO.y, delta);
		assertEquals(0f, Vector3f.ZERO.z, delta);

		// unit-x vector
		assertEquals(1f, Vector3f.UNIT_X.x, delta);
		assertEquals(0f, Vector3f.UNIT_X.y, delta);
		assertEquals(0f, Vector3f.UNIT_X.z, delta);

		// unit-y vector
		assertEquals(0f, Vector3f.UNIT_Y.x, delta);
		assertEquals(1f, Vector3f.UNIT_Y.y, delta);
		assertEquals(0f, Vector3f.UNIT_Y.z, delta);

		// unit-z vector
		assertEquals(0f, Vector3f.UNIT_Z.x, delta);
		assertEquals(0f, Vector3f.UNIT_Z.y, delta);
		assertEquals(1f, Vector3f.UNIT_Z.z, delta);

		// identity vector
		assertEquals(1f, Vector3f.IDENTITY.x, delta);
		assertEquals(1f, Vector3f.IDENTITY.y, delta);
		assertEquals(1f, Vector3f.IDENTITY.z, delta);

		return;
	}

	/**
	 * This tests the constructors provided by {@code Vector3f}.
	 */
	@Test
	public void checkConstructors() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector;
		Vector3f copy;

		// The default constructor should initialize the values to 0.
		vector = new Vector3f();
		assertEquals(0f, vector.x, delta);
		assertEquals(0f, vector.y, delta);
		assertEquals(0f, vector.z, delta);

		// Test the constructor where coordinates are specified.
		vector = new Vector3f(x, y, z);
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);

		// Test the copy constructor with valid arguments. Coordinates should be
		// copied.
		copy = new Vector3f(vector);
		assertEquals(x, copy.x, delta);
		assertEquals(y, copy.y, delta);
		assertEquals(z, copy.z, delta);

		// Test the copy constructor with a null argument. Coordinates should be
		// set to 0. No exception should occur.
		vector = null;
		copy = new Vector3f(vector);
		assertEquals(0f, copy.x, delta);
		assertEquals(0f, copy.y, delta);
		assertEquals(0f, copy.z, delta);

		return;
	}

	/**
	 * Checks that equality and hash code can be determined properly for
	 * {@code Vector3f}.
	 */
	@Test
	public void checkEquality() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f object = new Vector3f(x, y, z);
		Vector3f equalObject = new Vector3f(x, y, z);
		Vector3f unequalObject = null;

		// ---- Check bad arguments to equals. ---- //
		assertFalse(object.equals(unequalObject));
		assertFalse(object.equals(1));
		assertFalse(object.equals("pew pew pew pew"));
		// ---------------------------------------- //

		// ---- Check valid arguments to equals. ---- //
		// Set up the unequal object.
		unequalObject = new Vector3f(x, y, z + 0.00001f);

		// Reflexive.
		assertTrue(object.equals(object));
		assertTrue(equalObject.equals(equalObject));
		// Symmetric.
		assertTrue(object.equals(equalObject));
		assertTrue(equalObject.equals(object));
		// Check the hash codes.
		assertEquals(object.hashCode(), equalObject.hashCode());

		// Check inequality for both object and equalObject in both directions.
		assertFalse(object.equals(unequalObject));
		assertFalse(equalObject.equals(unequalObject));
		assertFalse(unequalObject.equals(object));
		assertFalse(unequalObject.equals(equalObject));
		// Check that the hash codes are different.
		assertFalse(object.hashCode() == unequalObject.hashCode());
		assertFalse(equalObject.hashCode() == unequalObject.hashCode());
		// ------------------------------------------ //

		return;
	}

	/**
	 * Checks the bulk set operations provided by {@code Vector3f}.
	 */
	@Test
	public void checkSet() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f otherVector;

		// Try the basic bulk set method. Also check the return value.
		assertSame(vector, vector.set(x, y, z));
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);

		// Try the vector set method. Also check the return value.
		x = random.nextFloat();
		y = random.nextFloat();
		z = random.nextFloat();
		otherVector = new Vector3f(x, y, z);
		assertSame(vector, vector.set(otherVector));
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);

		// Trying to set with a null vector should throw an exception.
		try {
			otherVector = null;
			vector.set(otherVector);
			fail(failurePrefix
					+ "Operation supports null vector argument for vector that is not used as a cache!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}

		return;
	}

	/**
	 * Checks the negate operations provided by {@code Vector3f}.
	 */
	@Test
	public void checkNegate() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		// Get the expected values.
		final float negatedX = -x;
		final float negatedY = -y;
		final float negatedZ = -z;

		// Calling negate initially shouldn't make a difference. Check the
		// return value.
		assertSame(vector, vector.negate());
		assertEquals(0f, vector.x, delta);
		assertEquals(0f, vector.y, delta);
		assertEquals(0f, vector.z, delta);
		// Same check with the cache value.
		assertSame(cache, vector.negate(cache));
		assertEquals(0f, cache.x, delta);
		assertEquals(0f, cache.y, delta);
		assertEquals(0f, cache.z, delta);

		// Calling negate should change the local values.
		vector.set(x, y, z); // Reset the vector.
		assertSame(vector, vector.negate());
		assertEquals(negatedX, vector.x, delta);
		assertEquals(negatedY, vector.y, delta);
		assertEquals(negatedZ, vector.z, delta);
		// Same check with the cache value, but the original vector is same.
		vector.set(x, y, z); // Reset the vector.
		assertSame(cache, vector.negate(cache));
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		assertEquals(negatedX, cache.x, delta);
		assertEquals(negatedY, cache.y, delta);
		assertEquals(negatedZ, cache.z, delta);
		// Same check with a null cache. A null value should cause a new vector
		// to be created.
		vector.set(x, y, z); // Reset the vector.
		newCache = vector.negate(newCache);
		assertNotSame(vector, newCache);
		assertNotSame(cache, newCache);
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		assertEquals(negatedX, newCache.x, delta);
		assertEquals(negatedY, newCache.y, delta);
		assertEquals(negatedZ, newCache.z, delta);

		return;
	}

	/**
	 * Checks the normalize operations provided by {@code Vector3f}.
	 */
	@Test
	public void checkNormalize() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		// Get the expected values.
		float length = FloatMath.sqrt(x * x + y * y + z * z);
		final float normalizedX = x / length;
		final float normalizedY = y / length;
		final float normalizedZ = z / length;

		// Calling negate initially shouldn't make a difference. Check the
		// return value.
		assertSame(vector, vector.negate());
		assertEquals(0f, vector.x, delta);
		assertEquals(0f, vector.y, delta);
		assertEquals(0f, vector.z, delta);
		// Same check with the cache value.
		assertSame(cache, vector.negate(cache));
		assertEquals(0f, cache.x, delta);
		assertEquals(0f, cache.y, delta);
		assertEquals(0f, cache.z, delta);

		// Calling negate should change the local values.
		vector.set(x, y, z); // Reset the vector.
		assertSame(vector, vector.normalize());
		assertEquals(normalizedX, vector.x, delta);
		assertEquals(normalizedY, vector.y, delta);
		assertEquals(normalizedZ, vector.z, delta);
		// Same check with the cache value, but the original vector is same.
		vector.set(x, y, z); // Reset the vector.
		assertSame(cache, vector.normalize(cache));
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		assertEquals(normalizedX, cache.x, delta);
		assertEquals(normalizedY, cache.y, delta);
		assertEquals(normalizedZ, cache.z, delta);
		// Same check with a null cache. A null value should cause a new vector
		// to be created.
		vector.set(x, y, z); // Reset the vector.
		newCache = vector.normalize(newCache);
		assertNotSame(vector, newCache);
		assertNotSame(cache, newCache);
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		assertEquals(normalizedX, newCache.x, delta);
		assertEquals(normalizedY, newCache.y, delta);
		assertEquals(normalizedZ, newCache.z, delta);

		return;
	}

	/**
	 * Checks the computations of the vector length.
	 */
	@Test
	public void checkLength() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();

		// Get the expected values.
		final float lengthSquared = x * x + y * y + z * z;
		final float length = FloatMath.sqrt(lengthSquared);

		// Check the default value.
		assertSame(vector, vector.negate());
		assertEquals(0f, vector.lengthSquared(), delta);
		assertEquals(0f, vector.length(), delta);

		// Check the computed value with the specified coordinates.
		vector.set(x, y, z);
		assertEquals(lengthSquared, vector.lengthSquared(), delta);
		assertEquals(length, vector.length(), delta);

		// Normalize it and try taking the length again.
		vector.normalize();
		assertEquals(1f, vector.lengthSquared(), delta);
		assertEquals(1f, vector.length(), delta);

		return;
	}

	/**
	 * Checks that the addition operations provided by {@code Vector3f} are
	 * calculated correctly.
	 * 
	 * @see Vector3f#add(float, float, float)
	 * @see Vector3f#add(Vector3f)
	 * @see Vector3f#add(float, float, float, Vector3f)
	 * @see Vector3f#add(Vector3f, Vector3f)
	 */
	@Test
	public void checkAddition() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		// Generate values to add.
		float x2 = random.nextFloat();
		float y2 = random.nextFloat();
		float z2 = random.nextFloat();
		Vector3f vector2 = new Vector3f(x2, y2, z2);

		// Get the expected values.
		final float addedX = x + x2;
		final float addedY = y + y2;
		final float addedZ = z + z2;

		// ---- Check the two non-cache (local) operations. ---- //
		// Add x, y, z to the zero vector.
		assertSame(vector, vector.add(x, y, z)); // Returned: vector
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);

		// Add x2, y2, z2 to the vector using the vector form of the operation.
		assertSame(vector, vector.add(vector2)); // Returned: vector
		assertEquals(addedX, vector.x, delta);
		assertEquals(addedY, vector.y, delta);
		assertEquals(addedZ, vector.z, delta);
		// ----------------------------------------------------- //

		// ---- Check the two cache-based operations. ---- //
		// Reset the vector to the x, y, and z values.
		vector.set(x, y, z);

		// Add x2, y, z2 to the vector using the float form.
		assertSame(cache, vector.add(x2, y2, z2, cache)); // Returned: cache
		// The values of the original vector should not change.
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		// The values in the cache should be the expected values.
		assertEquals(addedX, cache.x, delta);
		assertEquals(addedY, cache.y, delta);
		assertEquals(addedZ, cache.z, delta);

		// Add x2, y, z2 to the vector using the vector form.
		assertSame(cache, vector.add(vector2, cache)); // Returned: cache
		// The values of the original vector should not change.
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		// The values in the cache should be the expected values.
		assertEquals(addedX, cache.x, delta);
		assertEquals(addedY, cache.y, delta);
		assertEquals(addedZ, cache.z, delta);
		// ----------------------------------------------- //

		// ---- Check that the two cache-based ops handle null cache. ---- //
		// Reset the vector to the x, y, and z values.
		vector.set(x, y, z);

		// Add x2, y, z2 to the vector using the float form.
		newCache = null;
		// Make sure the return value is a new vector.
		vector.add(x2, y2, z2, newCache);
		assertNotSame(newCache, vector);
		assertNotSame(newCache, cache);
		// The values of the original vector should not change.
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		// The values in the cache should be the expected values.
		assertEquals(addedX, cache.x, delta);
		assertEquals(addedY, cache.y, delta);
		assertEquals(addedZ, cache.z, delta);

		// Add x2, y, z2 to the vector using the vector form.
		newCache = null;
		// Make sure the return value is a new vector.
		vector.add(x2, y2, z2, newCache);
		assertNotSame(newCache, vector);
		assertNotSame(newCache, cache);
		// The values of the original vector should not change.
		assertEquals(x, vector.x, delta);
		assertEquals(y, vector.y, delta);
		assertEquals(z, vector.z, delta);
		// The values in the cache should be the expected values.
		assertEquals(addedX, cache.x, delta);
		assertEquals(addedY, cache.y, delta);
		assertEquals(addedZ, cache.z, delta);
		// --------------------------------------------------------------- //

		// ---- Check for null pointer exceptions. ---- //
		// These should only happen when a non-cache vector is passed.
		try {
			vector2 = null;
			vector.add(vector2);
			fail(failurePrefix
					+ "Operation supports null vector argument for vector that is not used as a cache!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		try {
			vector2 = null;
			vector.add(vector2, newCache);
			fail(failurePrefix
					+ "Operation supports null vector argument for vector that is not used as a cache!");
		} catch (NullPointerException e) {
			// Nothing to do.
		}
		// -------------------------------------------- //

		return;
	}

	/**
	 * TODO
	 */
	@Test
	public void checkSubtraction() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		fail("Not implemented.");

		return;
	}

	/**
	 * TODO
	 */
	@Test
	public void checkMultiplication() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		fail("Not implemented.");

		return;
	}

	/**
	 * TODO
	 */
	@Test
	public void checkDistance() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();

		fail("Not implemented.");

		return;
	}

	/**
	 * TODO
	 */
	@Test
	public void checkDotProduct() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();

		fail("Not implemented.");

		return;
	}

	/**
	 * TODO
	 */
	@Test
	public void checkCrossProduct() {
		// Generate some random coordinates. Since float is a primitive type, we
		// don't have to worry about pass-by-reference affecting test results.
		float x = random.nextFloat();
		float y = random.nextFloat();
		float z = random.nextFloat();

		Vector3f vector = new Vector3f();
		Vector3f cache = new Vector3f();
		Vector3f newCache = null;

		fail("Not implemented.");

		return;
	}
}
