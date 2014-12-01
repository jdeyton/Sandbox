/**
 * 
 */
package com.bar.foo.javafx.camera;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;

import com.bar.foo.javafx.Node;

/**
 * TODO Document this class.
 * 
 * @author Jordan Deyton
 *
 */
public class FlyCamera extends Node {

	// TODO Abstract this class so its super class can be extended to provide
	// custom cameras.

	/**
	 * The JavaFX {@link Camera} for the {@link #scene}.
	 */
	private final PerspectiveCamera camera;

	/**
	 * The current scene associated with the {@link #camera}. If no scene is
	 * set, this value will be null.
	 */
	private Scene scene = null;

	// TODO set up the camera controls

	/**
	 * The default constructor.
	 */
	public FlyCamera() {
		super();

		// Initialize the camera with fixedEyeAtZero to prevent rendering issues
		// when moving or rotating the camera.
		camera = new PerspectiveCamera(true);
		// Set the limits for rendering objects.
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		// The camera starts off with positive y going top to bottom and
		// positive z going into the screen. This rotates the camera 180 degrees
		// around the x axis so y increases up and z increases to the user. Note
		// that this is a right-handed coordinate system.
		camera.getTransforms().add(new Rotate(180.0, Rotate.X_AXIS));
		// Attach the PerspectiveCamera to this Node (Group).
		getChildren().add(camera);

		return;
	}

	/**
	 * Sets the scene associated with this camera.
	 * <p>
	 * <b>Note:</b> A camera can only be used with one scene at a time, so if
	 * this is called with a new scene, the camera will be removed from the
	 * previous scene.
	 * </p>
	 * 
	 * @param scene
	 *            The new scene. If null, the camera will be removed from its
	 *            current scene but not attached to another scene.
	 */
	public void setScene(Scene scene) {
		// Only proceed if the scene is new.
		if (scene != this.scene) {
			// Unset the JavaFX Camera from the previous scene.
			if (this.scene != null) {
				this.scene.setCamera(null);
			}
			// Set the reference to the new scene.
			this.scene = scene;
			// Set the JavaFX Camera for the new scene if possible.
			if (scene != null) {
				scene.setCamera(camera);
			}
		}
		return;
	}
}
