/**
 * 
 */
package com.bar.foo.javafx.camera;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import com.bar.foo.javafx.Node;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.input.IControlContributor;
import com.bar.foo.javafx.input.KeyAnalogAction;
import com.bar.foo.javafx.input.KeyToggleAction;
import com.bar.foo.math.Quaternion;
import com.bar.foo.math.Vector3f;

/**
 * TODO Document this class.
 * 
 * @author Jordan Deyton
 *
 */
public class FlyCamera extends Node implements IControlContributor {

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

	private ControlManager controls = null;

	private static final float moveRate = 10f;
	
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

	// ---- Implements IControlContributor ---- //

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.IControlContributor#registerControls(com.bar
	 * .foo.javafx.input.ControlManager)
	 */
	@Override
	public boolean registerControls(ControlManager controlManager) {
		boolean registered = false;
		if (controls == null && controlManager != null) {
			// Update the reference to the registered ControlManager.
			controls = controlManager;
			// TODO Add all key, mouse, and/or joystick controls.
			
			// Add listeners to move the camera a little bit.
			// W is registered as an analog listener. The others are registered as
			// action listeners.
			controls.keys.addAnalog(KeyCode.W, new KeyAnalogAction() {
				@Override
				public void run(float value, float timePerFrame, KeyEvent event) {
					transform.translation.subtract(0f, 0f, moveRate);
					transform.refresh(false);
				}
			});
			controls.keys.addToggle(KeyCode.S, new KeyToggleAction() {
				@Override
				public void pressed(float timePerFrame, KeyEvent event) {
					transform.translation.add(0f, 0f, moveRate);
					transform.refresh(false);
				}

				@Override
				public void released(float timePerFrame, KeyEvent event) {

				}
			});
			controls.keys.addToggle(KeyCode.A, new KeyToggleAction() {
				@Override
				public void pressed(float timePerFrame, KeyEvent event) {
					transform.translation.subtract(moveRate, 0f, 0f);
					transform.refresh(false);
				}

				@Override
				public void released(float timePerFrame, KeyEvent event) {

				}
			});
			controls.keys.addToggle(KeyCode.D, new KeyToggleAction() {
				@Override
				public void pressed(float timePerFrame, KeyEvent event) {
					transform.translation.add(moveRate, 0f, 0f);
					transform.refresh(false);
				}

				@Override
				public void released(float timePerFrame, KeyEvent event) {

				}
			});
			controls.keys.addToggle(KeyCode.Q, new KeyToggleAction() {
				@Override
				public void pressed(float timePerFrame, KeyEvent event) {
					Quaternion q = new Quaternion(Vector3f.UNIT_Y,
							(float) (Math.PI / 20.0));
					q.multiply(transform.rotation);
					transform.rotation.set(q);
					transform.refresh(true);
				}

				@Override
				public void released(float timePerFrame, KeyEvent event) {

				}
			});
			controls.keys.addToggle(KeyCode.E, new KeyToggleAction() {
				@Override
				public void pressed(float timePerFrame, KeyEvent event) {
					Quaternion q = new Quaternion(Vector3f.UNIT_Y,
							(float) (Math.PI / -20.0));
					q.multiply(transform.rotation);
					transform.rotation.set(q);
					transform.refresh(true);
				}

				@Override
				public void released(float timePerFrame, KeyEvent event) {

				}
			});

		}
		return registered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.input.IControlContributor#unregisterControls()
	 */
	@Override
	public boolean unregisterControls() {
		boolean unregistered = false;
		if (controls != null) {
			// TODO Remove all key, mouse, and/or joystick controls.

			// Update the reference to the registered ControlManager.
			controls = null;
		}
		return unregistered;
	}
	// ---------------------------------------- //

}
