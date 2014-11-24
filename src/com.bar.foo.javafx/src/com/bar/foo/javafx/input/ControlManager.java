/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class manages key and mouse controls for a collection of JavaFX scenes.
 * 
 * @author Jordan Deyton
 *
 */
public class ControlManager {

	/**
	 * The collection of managed {@code Scene}s.
	 */
	private final List<Scene> managedScenes = new ArrayList<Scene>();

	public final KeyActionHandler keys = new KeyActionHandler();
	public final MouseActionHandler mouse = new MouseActionHandler();

	/**
	 * Adds a {@code Scene} whose controls will be managed by this
	 * {@code ControlManager}.
	 * 
	 * @param scene
	 *            The scene that will use this {@code ControlManager}. Nothing
	 *            will be added if the scene is {@code null} or if it has
	 *            already been added to this manager.
	 * @return True if the scene was added, false otherwise.
	 */
	public boolean addScene(Scene scene) {
		boolean added = false;
		if (scene != null && !managedScenes.contains(scene)) {
			added = managedScenes.add(scene);

			// Register key event handlers.
			scene.addEventHandler(KeyEvent.KEY_PRESSED, keys);
			scene.addEventHandler(KeyEvent.KEY_RELEASED, keys);
			
			// Register mouse event handlers.
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouse);
			scene.addEventFilter(MouseEvent.MOUSE_RELEASED, mouse);
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouse);
		}
		return added;
	}

	/**
	 * Removes a {@code Scene} that is already attached to this
	 * {@code ControlManager}. The manager will no longer process associated
	 * controls when the scene is updated.
	 * 
	 * @param scene
	 *            The scene that will no longer send input {@code Event}s to the
	 *            manager.
	 * @return True if the scene was removed, false otherwise.
	 */
	public boolean removeScene(Scene scene) {
		boolean removed = false;
		if (scene != null && managedScenes.remove(scene)) {

			// Unregister key event handlers.
			scene.removeEventHandler(KeyEvent.KEY_PRESSED, keys);
			scene.removeEventHandler(KeyEvent.KEY_RELEASED, keys);
			
			// TODO remove things added via addScene...
		}
		return removed;
	}

}
