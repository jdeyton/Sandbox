/**
 * 
 */
package com.bar.foo.javafx.test;

import javafx.application.Platform;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.bar.foo.javafx.Node;

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

	private static final double radius = 100;

	public EmbeddedView(Node parent, Color color) {

		// Set up the root node.
		root = new Node();

		// Create a box with the specifications and add it to the root node.
		Box box = new Box(radius, radius, radius);
		PhongMaterial material = new PhongMaterial(color);
		material.setSpecularColor(Color.WHITE);
		box.setMaterial(material);
		box.getTransforms().add(
				new Translate(radius * MultiViewLauncher.count, -100, 0.0));
		root.getChildren().add(box);

		// Set up the view camera.
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-800.0);
		camera.setTranslateX(radius * 2.0);
		root.getChildren().add(camera);

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
	}

	public Node getRoot() {
		return root;
	}

	public Composite createComposite(Composite parent) {
		Composite composite = null;
		if (canvas == null) {
			canvas = new FXCanvas(parent, SWT.BORDER);
			scene = new Scene(root, Color.BLACK);
			scene.setCamera(camera);
			canvas.setScene(scene);
			composite = canvas;
		}
		return composite;
	}

	public void dispose() {
		if (canvas != null) {
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
