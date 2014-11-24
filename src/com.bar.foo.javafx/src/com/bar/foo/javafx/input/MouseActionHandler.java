/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * @author Jordan Deyton
 *
 */
public class MouseActionHandler extends ActionHandler<EventType<MouseEvent>, MouseEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.input.ActionHandler#isPressed(javafx.event.Event)
	 */
	@Override
	protected boolean isPressed(MouseEvent event) {
		return (MouseEvent.MOUSE_PRESSED == event.getEventType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.ActionHandler#getTrigger(javafx.event.Event)
	 */
	@Override
	protected EventType<MouseEvent> getTrigger(MouseEvent event) {
		return (EventType<MouseEvent>) event.getEventType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.input.ActionHandler#getValue(javafx.event.Event)
	 */
	@Override
	protected float getValue(MouseEvent event) {
		// TODO
		return 0;
	}

}
