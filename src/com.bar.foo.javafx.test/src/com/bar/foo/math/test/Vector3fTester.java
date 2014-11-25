package com.bar.foo.math.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bar.foo.math.Vector3f;

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
		Vector3f vector;
		Vector3f copy;

		// The default constructor should initialize the values to 0.
		vector = new Vector3f();
		assertEquals(0f, vector.x, delta);
		assertEquals(0f, vector.y, delta);
		assertEquals(0f, vector.z, delta);

		// Test the constructor where coordinates are specified.
		vector = new Vector3f(1f, 2f, 3f);
		assertEquals(1f, vector.x, delta);
		assertEquals(2f, vector.y, delta);
		assertEquals(3f, vector.z, delta);

		// Test the copy constructor with valid arguments. Coordinates should be
		// copied.
		copy = new Vector3f(vector);
		assertEquals(1f, copy.x, delta);
		assertEquals(2f, copy.y, delta);
		assertEquals(3f, copy.z, delta);

		// Test the copy constructor with a null argument. Coordinates should be
		// set to 0. No exception should occur.
		vector = null;
		copy = new Vector3f(vector);
		assertEquals(0f, copy.x, delta);
		assertEquals(0f, copy.y, delta);
		assertEquals(0f, copy.z, delta);

		return;
	}

	public void checkSet() {
		
	}
	
	public void checkNegate() {
		
	}
	
	public void checkNormalize() {
		
	}
	
	public void checkLength() {
		
	}
	
	public void checkAddition() {
		
	}
	
	public void checkSubtraction() {
		
	}
	
	public void checkMultiplication() {
		
	}
	
	public void checkDistance() {
		
	}
	
	public void checkDotProduct() {
		
	}
	
	public void checkCrossProduct() {
		
	}
	
	
}
