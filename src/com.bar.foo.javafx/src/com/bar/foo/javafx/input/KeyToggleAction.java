/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.KeyEvent;

/**
 * This is a convenience class for key-based "toggle" actions. For instances of
 * this class, key-press and key-release are treated as two distinct, single
 * actions and are <i>not</i> triggered repeatedly.
 * <p>
 * This class is particularly useful for handling mouse clicks. For instance,
 * you might use the following code: *
 * 
 * <pre>
 * <code>
 * controlManager.keys.addToggle(KeyCode.SPACE, new KeyToggleAction() {
 * 	{@literal @}Override
 * 	public void pressed(float timePerFrame, KeyEvent event) {
 * 		// Initiate a jump action...
 * 	}
 * 	{@literal @}Override
 * 	public void released(float timePerFrame, KeyEvent event) {
 * 		// Stop the jump action.
 * 	}
 * });
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public abstract class KeyToggleAction extends ToggleAction<KeyEvent> {

}
