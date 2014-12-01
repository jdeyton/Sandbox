/**
 * 
 */
package com.bar.foo.math.test;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

import com.bar.foo.math.Quaternion;

/**
 * This class tests all constructors and methods provided by {@link Quaternion}.
 * 
 * @author Jordan Deyton
 *
 */
public class QuaternionTester {
	/**
	 * The delta value to be used in all float/double comparisons.
	 */
	private static final double delta = 1e-5;

	/**
	 * A prefix used when failing with a custom message.
	 */
	private static final String failurePrefix = "QuaternionTester failure: ";

	/**
	 * A random instance used to generate random numbers.
	 */
	private static final Random random = new Random(System.nanoTime());

	/**
	 * This tests the static matrices provided by {@code Quaternion}.
	 */
	@Test
	public void checkStaticMatrices() {
		// TODO
		fail("Not implemented...");
	}

	/**
	 * This tests the constructors provided by {@code Quaternion}.
	 */
	@Test
	public void checkConstructors() {
		// TODO
		fail("Not implemented...");
	}
	
	// TODO Other tests
}
