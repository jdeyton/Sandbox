/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.event.Event;

/**
 * @author Jordan Deyton
 *
 */
public interface ToggleAction<T extends Event> {
	public void pressed(float timePerFrame, T event);
	public void released(float timePerFrame, T event);
}
