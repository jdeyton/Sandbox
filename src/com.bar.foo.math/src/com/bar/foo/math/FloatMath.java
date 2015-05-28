/**
 * 
 */
package com.bar.foo.math;

/**
 * This class provides convenient math operations on floats. This prevents any
 * unnecessary type casting.
 * 
 * @author Jordan Deyton
 *
 */
public class FloatMath {

	// TODO Test this class.
	
	/**
	 * Computes and returns the square root of a float value.
	 * 
	 * @param value
	 *            The value to take the square root of.
	 * @return The square root of the value.
	 * @see Math#sqrt(double)
	 */
	public static float sqrt(float value) {
		return (float) Math.sqrt(value);
	}

	/**
	 * Computes and returns the cosine of a float angle, in radians.
	 * 
	 * @param value
	 *            An angle, in radians.
	 * @see Math#cos(double)
	 */
	public static float cos(float value) {
		return (float) Math.cos(value);
	}

	/**
	 * Computes and returns the sine of a float angle, in radians.
	 * 
	 * @param value
	 *            An angle, in radians.
	 * @see Math#sin(double)
	 */
	public static float sin(float value) {
		return (float) Math.sin(value);
	}

	/**
	 * Gets a value restricted (or "clamped") to the provided range.
	 * 
	 * @param value
	 *            The value to clamp.
	 * @param min
	 *            The minimum value (inclusive).
	 * @param max
	 *            The maximum value (inclusive).
	 * @return The min if the value is less than min, the max if the value is
	 *         grater than max, or the value if it lies between min and max.
	 */
	public static float clamp(float value, float min, float max) {
		return (value < min ? min : value > max ? max : value);
	}

}
