/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Jordan Deyton
 *
 */
public class KeyActionHandler extends
		ActionHandler<KeyCode, KeyEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.ControlActionManager#isPressed(javafx.event.
	 * Event)
	 */
	@Override
	protected boolean isPressed(KeyEvent event) {
		return (KeyEvent.KEY_PRESSED == event.getEventType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.ControlActionManager#getTrigger(javafx.event
	 * .Event)
	 */
	@Override
	protected KeyCode getTrigger(KeyEvent event) {
		return event.getCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.ControlActionManager#getValue(javafx.event.Event
	 * )
	 */
	@Override
	protected float getValue(KeyEvent event) {
		return (isPressed(event) ? 1f : 0f);
	}

}
