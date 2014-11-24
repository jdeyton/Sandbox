/**
 * 
 */
package com.bar.foo.javafx.test;

import javafx.application.Platform;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.bar.foo.javafx.Node;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.input.common.KeyToggleAction;
import com.bar.foo.javafx.input.common.MouseAnalogAction;

/**
 * @author Jordan Deyton
 *
 */
public class EmbeddedView {

	private final Node root;
	private final Camera camera;

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
		Node cameraNode = new Node();
		// cameraNode.setRotateX(180.0); // An alternative to rotating the
		// camera.
		root.getChildren().add(cameraNode);
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		cameraNode.setTranslateZ(1000.0);
		camera.setTranslateX(radius * 2.0); // Move the camera to the right.
		camera.getTransforms().add(new Rotate(180.0, Rotate.X_AXIS));
		cameraNode.getChildren().add(camera);

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
		
		controls.mouse.addAnalog(MouseEvent.MOUSE_MOVED, new MouseAnalogAction() {
			@Override
			public void run(float value, float timePerFrame, MouseEvent event) {
				System.out.println("x,y: " + event.getSceneX() + "," + event.getSceneY());
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
			scene.setCamera(camera);
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
