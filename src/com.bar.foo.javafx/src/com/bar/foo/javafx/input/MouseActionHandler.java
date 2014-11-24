/**
 * 
 */
package com.bar.foo.javafx.input;

import javafx.scene.input.MouseEvent;

/**
 * @author Jordan Deyton
 *
 */
public class MouseActionHandler extends ActionHandler<MouseCode, MouseEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.input.ActionHandler#isPressed(javafx.event.Event)
	 */
	@Override
	protected boolean isOn(MouseEvent event) {
		return (MouseEvent.MOUSE_PRESSED == event.getEventType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.javafx.input.ActionHandler#getTrigger(javafx.event.Event)
	 */
	@Override
	protected MouseCode getTrigger(MouseEvent event) {
		return MouseCode.fromMouseEvent(event);
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
