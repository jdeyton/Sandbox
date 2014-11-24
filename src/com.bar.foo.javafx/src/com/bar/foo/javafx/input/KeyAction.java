/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.KeyEvent;

/**
 * @author Jordan Deyton
 *
 */
public interface KeyAction {
	public void keyPressed(float timePerFrame, KeyEvent event);

	public void keyReleased(float timePerFrame, KeyEvent event);
}
