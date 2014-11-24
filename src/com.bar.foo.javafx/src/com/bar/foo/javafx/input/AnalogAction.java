/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.event.Event;

/**
 * @author Jordan Deyton
 *
 */
public interface AnalogAction<T extends Event> {
	public void run(float value, float timePerFrame, T event);
}
