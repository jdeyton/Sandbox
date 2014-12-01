package com.bar.foo.javafx;

import javafx.scene.Group;

/**
 * This class provides a {@link Group} for 3D-based objects. It maintains added
 * 3D transformations so they are not re-calculated by the JavaFX rendering
 * engine.
 * 
 * @author Jordan Deyton
 *
 */
public class Node extends Group {

	// TODO Change this to private and expose it via setters for scaling,
	// rotating, and translating.
	public final GeneralTransform transform = new GeneralTransform();
	
	public Node() {
		super();
		getTransforms().add(transform);
	}
}
