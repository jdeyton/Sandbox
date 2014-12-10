package com.bar.foo.javafx;

/**
 * This is an interface for an object that provides the frame rate of the JavaFX
 * scenes. Since all JavaFX scenes operate under the same thread, the
 * implementation of this interface should be shared amongst all open views.
 * 
 * @author Jordan Deyton
 *
 */
public interface IFrameRateManager {

	/**
	 * Gets the current frames per second for JavaFX-based 3D views.
	 * <p>
	 * <b>Note:</b> Depending on the implementation, this method may be slower
	 * than {@link #getTPF()} by a mathematical operation.
	 * </p>
	 * 
	 * @return The current FPS of all JavaFX-based 3D views.
	 */
	public float getFPS();

	/**
	 * Gets the current time per frame (individual) for JavaFX-based 3D views.
	 * 
	 * @return The current time per frame.
	 */
	public float getTPF();
}
