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
import com.bar.foo.math.FloatMath;
import com.bar.foo.math.Matrix3f;
import com.bar.foo.math.Quaternion;
import com.bar.foo.math.Vector3f;

/**
 * This class provides a "flying" camera that includes setters used to imitate,
 * roughly, flying in an aircraft. Additional functionality includes the ability
 * to "strafe" (move horizontally left or right), raise, or lower the camera.
 * When clicking and dragging with the mouse, the view rotates as if you are
 * moving your head in the cockpit.
 * <p>
 * This class extends {@link Node} and wraps a JavaFX {@link PerspectiveCamera}.
 * </p>
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

	/**
	 * The rate at which the camera moves forward, backward, and sideways.
	 */
	private float moveRate = 10f;
	/**
	 * The rate at which the camera rolls and rotates up, down, and sideways.
	 */
	private float rotateRate = 10f;
	/**
	 * The rate at which the camera zooms.
	 */
	private float zoomRate = 10f;

	private final Vector3f defaultDir = new Vector3f(0f, 0f, -1f);
	private final Vector3f defaultUp = new Vector3f(0f, 1f, 0f);
	private final Vector3f defaultRight = new Vector3f(1f, 0f, 0f);
	
	/**
	 * The current position or location of the camera.
	 */
	private final Vector3f position = new Vector3f();

	/**
	 * The current direction in which the camera points.
	 */
	private final Vector3f dir = new Vector3f(defaultDir);
	/**
	 * The current up axis for the camera. This must be orthogonal to
	 * {@link #dir}.
	 */
	private final Vector3f up = new Vector3f(defaultUp);
	/**
	 * The current right axis for the camera. This must be normal to
	 * {@link #dir} and {@link #up}. To compute it from the direction and up
	 * vectors, use direction cross up (assuming we have a right-handed
	 * coordinate system).
	 */
	private final Vector3f right = new Vector3f(defaultRight);

	/**
	 * The current up axis for the camera. This is used only when dragging the
	 * mouse so that changes in pitch combined with changes in yaw do not
	 * effectively roll the camera.
	 */
	private final Vector3f dragUp = new Vector3f(up);
	/**
	 * Whether or not the camera's rotation is controlled by dragging the mouse.
	 */
	private boolean dragging = false;
	
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

		// Views the scene from the positive x = z axis.
//		setPosition(new Vector3f(1000f, 0f, 1000f));
//		setOrientation(new Vector3f(-1f, 0f, -1f), new Vector3f(0f, 1f, 0f));

		// Views the scene from the negative z axis. The direction is 180
		// degrees from the default direction.
//		setPosition(new Vector3f(0f, 0f, -1000f));
//		setOrientation(new Vector3f(0f, 0f, 1f), new Vector3f(0f, 1f, 0f));

		// This flips the camera on its side, with x up and y left. It still
		// looks in the negative z direction.
		setPosition(new Vector3f(0f, 0f, 1000f));
		setOrientation(new Vector3f(0f, 0f, -1f), new Vector3f(1f, 0f, 0f));

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
			// W is registered as an analog listener. The others are registered
			// as
			// action listeners.
			controls.keys.addAnalog(KeyCode.W, new KeyAnalogAction() {
				@Override
				public void run(float value, float timePerFrame, KeyEvent event) {
					System.out.println("Moving forward. Time per frame: " + timePerFrame);
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
					Quaternion q = Quaternion.fromUnitAxisAngle(Vector3f.UNIT_Y,
							(float) (Math.PI / 20.0), null);
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
					Quaternion q = Quaternion.fromAxisAngle(Vector3f.UNIT_Y,
							(float) (Math.PI / -20.0), null);
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

	// ---- Configuration Getters/Setters ---- //
	/**
	 * Sets the rate at which the camera moves. The default value is
	 * <code>3.0</code>.
	 * 
	 * @param moveRate
	 *            The new move rate. This should not be negative.
	 */
	public void setMoveRate(float moveRate) {
		if (moveRate > 0f) {
			this.moveRate = moveRate;
		}
	}

	/**
	 * Sets the rate at which the camera rotates. The default value is
	 * <code>1.0</code>.
	 * 
	 * @param rotateRate
	 *            The new rotation rate. This should not be negative.
	 */
	public void setRotationRate(float rotateRate) {
		if (rotateRate > 0f) {
			this.rotateRate = rotateRate;
		}
	}

	/**
	 * Sets the rate at which the camera zooms. The default value is
	 * <code>1.0</code>.
	 * 
	 * @param zoomRate
	 *            The new zoom rate. This should not be negative.
	 */
	public void setZoomRate(float zoomRate) {
		if (zoomRate > 0f) {
			this.zoomRate = zoomRate;
		}
	}
	// --------------------------------------- //

	// ---- Non-incremental Setters ---- //
	/**
	 * Sets the position of the camera at once, as opposed to incremental
	 * positioning.
	 * 
	 * @param position
	 *            The new position. If null, an exception is thrown.
	 * 
	 * @see #thrustCamera(float)
	 * @see #strafeCamera(float)
	 * @see #raiseCamera(float)
	 */
	public void setPosition(Vector3f position) {
		// Check for nulls first.
		if (position == null) {
			throw new IllegalArgumentException("FlyCamera error: "
					+ "Null arguments not accepted for positioning the camera.");
		}

		// Update the local position and the camera.
		transform.translation.set(this.position.set(position));

		return;
	}

	/**
	 * Sets the orientation of the camera at once, as opposed to incremental
	 * orientation.
	 * 
	 * @param direction
	 *            The new direction in which the camera will point. If null, an
	 *            exception is thrown.
	 * @param up
	 *            The new up direction. If null or if it is not orthogonal to
	 *            the camera direction, an exception is thrown.
	 * 
	 * @see #rollCamera(float)
	 * @see #pitchCamera(float)
	 * @see #yawCamera(float)
	 */
	public void setOrientation(Vector3f direction, Vector3f up) {
		// Check for nulls first.
		if (direction == null || up == null) {
			throw new IllegalArgumentException("FlightCamera error: "
					+ "Null arguments not accepted for orienting the camera.");
		}
		// Make sure the direction and up vectors are orthogonal.
		else if (Math.abs(direction.dot(up)) > 1e-5f || direction.equals(up)
				|| Vector3f.ZERO.equals(direction) || Vector3f.ZERO.equals(up)) {
			throw new IllegalArgumentException("FlightCamera error: "
					+ "Direction and up vector are not orthogonal.");
		}

		// We can treat this like the rotation between two planes:
		// The source plane is the plane formed by defaultDir and defaultUp.
		// The destination plane is the plane formed by direction and up.
		// The resulting rotation can be computed from the *normal* vectors for
		// those planes, i.e. defaultRight and right computed from direction and
		// up.

		// This is a right-handed system. Compute right by crossing direction
		// with up.
		Vector3f right = direction.cross(up).normalize();

		Quaternion q = Quaternion.fromTwoUnitVectors(defaultRight, right);
		transform.rotation.set(q);
		transform.refresh(true);

		return;
	}

	// --------------------------------- //
	
	// ---- Incremental Setters ---- //
	/**
	 * Zooms the camera in or out.
	 * 
	 * @param value
	 *            The amount by which to zoom in or out. Negative zooms in,
	 *            positive zooms out.
	 */
	public void zoomCamera(float value) {
		System.out.println("zoomCamera(" + value + ")");
		// // This method comes straight from FlyByCamera.
		//
		// // derive fovY value
		// float h = camera.getFrustumTop();
		// float w = camera.getFrustumRight();
		// float aspect = w / h;
		//
		// float near = camera.getFrustumNear();
		//
		// float fovY = FastMath.atan(h / near) / (FastMath.DEG_TO_RAD * .5f);
		// float newFovY = fovY + value * 0.1f * zoomSpeed;
		// if (newFovY > 0f) {
		// // Don't let the FOV go zero or negative.
		// fovY = newFovY;
		// }
		//
		// h = FastMath.tan(fovY * FastMath.DEG_TO_RAD * .5f) * near;
		// w = h * aspect;
		//
		// camera.setFrustumTop(h);
		// camera.setFrustumBottom(-h);
		// camera.setFrustumLeft(-w);
		// camera.setFrustumRight(w);
		//
		// return;
	}

	/**
	 * Moves the camera forward or backward.
	 * 
	 * @param distance
	 *            If positive, the camera moves forward. If negative, the camera
	 *            moves backward.
	 */
	public void thrustCamera(float distance) {
		System.out.println("thrustCamera(" + distance + ")");
		// Vector3f velocity = dir.mult(distance);
		// camera.setLocation(position.addLocal(velocity));
	}

	/**
	 * Moves the camera right or left.
	 * 
	 * @param distance
	 *            If positive, the camera moves right. If negative, the camera
	 *            moves left.
	 */
	public void strafeCamera(float distance) {
		System.out.println("strafeCamera(" + distance + ")");
		// Vector3f velocity = left.mult(distance);
		// // We have to subtract because we're using the left direction! Right
		// // should be positive.
		// camera.setLocation(position.subtractLocal(velocity));
	}

	/**
	 * Moves the camera up or down.
	 * 
	 * @param distance
	 *            If positive, the camera moves up. If negative, the camera
	 *            moves down.
	 */
	public void raiseCamera(float distance) {
		System.out.println("raiseCamera(" + distance + ")");
		// Vector3f velocity = up.mult(distance);
		// camera.setLocation(position.addLocal(velocity));
	}

	/**
	 * Rotates (rolls) the camera right or left.
	 * 
	 * @param radians
	 *            If positive, the camera rolls right. If negative, the camera
	 *            rolls left.
	 */
	public void rollCamera(float radians) {
		System.out.println("rollCamera(" + radians + ")");
		// rotateCamera(radians, dir);
		// // When not dragging, we should update the up vector that is used
		// // when dragging with the mouse. Note: The camera cannot be rolled
		// when
		// // dragging!
		// dragUp.set(up);
	}

	/**
	 * Changes the pitch of the camera (rotates up and down).
	 * 
	 * @param radians
	 *            If positive, the camera pitches up. If negative, the camera
	 *            pitches down.
	 */
	public void pitchCamera(float radians) {
		System.out.println("pitchCamera(" + radians + ")");
		// rotateCamera(-radians, left);
		// if (!dragging) {
		// // When not dragging, we should update the up vector that is used
		// // when dragging with the mouse.
		// dragUp.set(up);
		// }
	}

	/**
	 * Changes the yaw of the camera right or left.
	 * 
	 * @param radians
	 *            If positive, the camera rotates right. If negative, the camera
	 *            rotates left.
	 */
	public void yawCamera(float radians) {
		System.out.println("yawCamera(" + radians + ")");
		// // If the mouse is controlling yaw, use the up vector for dragging.
		// if (dragging) {
		// rotateCamera(-radians, dragUp);
		// } else {
		// rotateCamera(-radians, up);
		// // When not dragging, we should update the up vector that is used
		// // when dragging with the mouse.
		// dragUp.set(up);
		// }
	}

	/**
	 * Rotates the camera based on the provided angle and axis. {@link #up},
	 * {@link #left}, and {@link #dir} are all updated in this method.
	 * 
	 * @param radians
	 *            The angle to rotate.
	 * @param axis
	 *            The axis to rotate around.
	 */
	private void rotateCamera(float radians, Vector3f axis) {
		System.out.println("rotateCamera(" + radians + ", " + axis + ")");
		// Matrix3f matrix = new Matrix3f();
		// matrix.fromAngleNormalAxis(radians, axis);
		//
		// // TODO We can probably shorten the number of calculations required
		// by
		// // tailoring the rotations for pitch, yaw, and roll. (The matrix
		// should
		// // always be roughly the same for each case.)
		//
		// // Apply the overall rotation to the direction vectors. Note that we
		// do
		// // not update the dragUp vector, since it should not be updated when
		// // using the mouse drag to rotate.
		// matrix.multLocal(up).normalizeLocal();
		// matrix.multLocal(left).normalizeLocal();
		// matrix.multLocal(dir).normalizeLocal();
		//
		// camera.setAxes(left, up, dir);
	}
	// ----------------------------- //
}
