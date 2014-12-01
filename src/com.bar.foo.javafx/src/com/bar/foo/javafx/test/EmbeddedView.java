/**
 * 
 */
package com.bar.foo.javafx.test;

import javafx.application.Platform;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.bar.foo.javafx.Node;
import com.bar.foo.javafx.camera.FlyCamera;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.input.KeyAnalogAction;
import com.bar.foo.javafx.input.KeyToggleAction;
import com.bar.foo.javafx.input.MouseAnalogAction;
import com.bar.foo.javafx.input.MouseCode;
import com.bar.foo.javafx.input.MouseToggleAction;
import com.bar.foo.math.Quaternion;
import com.bar.foo.math.Vector3f;

/**
 * @author Jordan Deyton
 *
 */
public class EmbeddedView {

	private final Node root;
	private final FlyCamera camera;

	private FXCanvas canvas;
	// Scenes must be constructed or modified on the Application thread.
	private Scene scene;

	private final ControlManager controls = new ControlManager();

	private static final double radius = 100;

	public EmbeddedView(Node parent, Color color) {
		// Set up the root node.
		root = new Node();

		// Create a box with the specifications and add it to the root node.
		Box box = new Box(radius, radius, radius);
		PhongMaterial material = new PhongMaterial(color);
		material.setSpecularColor(Color.WHITE);
		box.setMaterial(material);
		int count = MultiViewLauncher.count;
		box.getTransforms().add(
				new Translate(radius * count, radius * count, radius * count));
		root.getChildren().add(box);

		// Set up the view camera.
		camera = new FlyCamera();
		// Add the camera to the root node so it can move.
		root.getChildren().add(camera);
		// Move the camera out towards the user and to the right.
		camera.transform.translation.set((float) (radius * 2.0), 0f, 1000f);

		if (parent != null) {
			// Note: This is a lazy way to pass execution to the render thread.
			// Normally, you should check Platform.isFxApplicationThread() first
			// and then queue the task if necessary.
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					parent.getChildren().add(root);
				}
			});
		}

		final float moveRate = 10f;

		// Add listeners to move the camera a little bit.
		// W is registered as an analog listener. The others are registered as
		// action listeners.
		controls.keys.addAnalog(KeyCode.W, new KeyAnalogAction() {
			@Override
			public void run(float value, float timePerFrame, KeyEvent event) {
				camera.transform.translation.subtract(0f, 0f, moveRate);
				camera.transform.refresh(false);
			}
		});
		controls.keys.addToggle(KeyCode.S, new KeyToggleAction() {
			@Override
			public void pressed(float timePerFrame, KeyEvent event) {
				camera.transform.translation.add(0f, 0f, moveRate);
				camera.transform.refresh(false);
			}

			@Override
			public void released(float timePerFrame, KeyEvent event) {

			}
		});
		controls.keys.addToggle(KeyCode.A, new KeyToggleAction() {
			@Override
			public void pressed(float timePerFrame, KeyEvent event) {
				camera.transform.translation.subtract(moveRate, 0f, 0f);
				camera.transform.refresh(false);
			}

			@Override
			public void released(float timePerFrame, KeyEvent event) {

			}
		});
		controls.keys.addToggle(KeyCode.D, new KeyToggleAction() {
			@Override
			public void pressed(float timePerFrame, KeyEvent event) {
				camera.transform.translation.add(moveRate, 0f, 0f);
				camera.transform.refresh(false);
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
				q.multiply(camera.transform.rotation);
				camera.transform.rotation.set(q);
				camera.transform.refresh(true);
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
				q.multiply(camera.transform.rotation);
				camera.transform.rotation.set(q);
				camera.transform.refresh(true);
			}

			@Override
			public void released(float timePerFrame, KeyEvent event) {

			}
		});

		// Add a listener for the space key.
		controls.keys.addToggle(KeyCode.SPACE, new KeyToggleAction() {
			@Override
			public void pressed(float timePerFrame, KeyEvent event) {
				System.out.println("SPAAAAAAAAAAACE GHOOOOOOOOOOOOOST");
				System.out.println(color);
			}

			@Override
			public void released(float timePerFrame, KeyEvent event) {
				System.out.println("Released...");
			}
		});

		// Add a listener for mouse movement.
		controls.mouse.addAnalog(MouseCode.MOVE, new MouseAnalogAction() {
			@Override
			public void run(float value, float timePerFrame, MouseEvent event) {
				System.out.println("x,y: " + event.getSceneX() + ","
						+ event.getSceneY());
			}
		});

		// Add a listener for the first mouse button.
		controls.mouse.addToggle(MouseCode.BUTTON_PRIMARY,
				new MouseToggleAction() {
					@Override
					public void pressed(float timePerFrame, MouseEvent event) {
						System.out.println("mouse down...");
					}

					@Override
					public void released(float timePerFrame, MouseEvent event) {
						System.out.println("mouse up...");
					}
				});
	}

	public Node getRoot() {
		return root;
	}

	public Composite createComposite(Composite parent) {
		Composite composite = null;
		if (canvas == null) {
			canvas = new FXCanvas(parent, SWT.BORDER);
			scene = new Scene(root, 0.0, 0.0, true, SceneAntialiasing.BALANCED);
			camera.setScene(scene);
			canvas.setScene(scene);
			composite = canvas;

			controls.addScene(scene);
		}
		return composite;
	}

	public void dispose() {
		if (canvas != null) {
			controls.removeScene(scene);

			if (!canvas.isDisposed()) {
				canvas.dispose();
			}
			canvas = null;
			scene = null;

			Node parent = (Node) root.getParent();
			if (parent != null) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						parent.getChildren().remove(root);
					}
				});
			}
		}
	}

}
