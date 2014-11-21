/**
 * 
 */
package com.bar.foo.javafx.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * @author Jordan Deyton
 *
 */
public class EmbeddedView {

	private FXCanvas canvas;
	private final Group root;
	private final Color color;

	// Scenes must be constructed or modified on the Application thread.
	private Scene scene;

	public EmbeddedView(Color color) {
		root = new Group();
		this.color = color;
	}

	public Composite createComposite(Composite parent) {
		Composite composite = null;
		if (canvas == null) {
			canvas = new FXCanvas(parent, SWT.BORDER);
			scene = new Scene(root, color);
			canvas.setScene(scene);
			composite = canvas;
		}
		return composite;
	}

	public void dispose() {
		if (canvas != null) {
			canvas.dispose();
			canvas = null;
			scene = null;
		}
	}

}
