/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.Event;

/**
 * @author Jordan Deyton
 *
 */
public abstract class ToggleAction<T extends Event> {
	
	private final AtomicBoolean pressed = new AtomicBoolean(false);
	
	protected void on(float timePerFrame, T event) {
		if (pressed.compareAndSet(false, true)) {
			pressed(timePerFrame, event);
		}
	}

	protected void off(float timePerFrame, T event) {
		if (pressed.compareAndSet(true, false)) {
			released(timePerFrame, event);
		}
	}
	
	public abstract void pressed(float timePerFrame, T event);
	public abstract void released(float timePerFrame, T event);
}
