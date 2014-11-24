/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Jordan Deyton
 *
 */
public class KeyControlManager implements EventHandler<KeyEvent> {

	private final Map<KeyCode, List<KeyAction>> keyControls = new HashMap<KeyCode, List<KeyAction>>();

	public boolean addControl(KeyCode code, KeyAction action) {
		boolean added = false;
		if (code != null && action != null) {
			List<KeyAction> controls = keyControls.get(code);
			// If necessary, add a new list of control actions to the map.
			if (controls == null) {
				controls = new ArrayList<KeyAction>();
				keyControls.put(code, controls);
			}
			// We can now add the control.
			added = controls.add(action);
		}
		return added;
	}

	public boolean removeControl(KeyCode code, KeyAction action) {
		boolean removed = false;
		if (code != null && action != null) {
			List<KeyAction> controls = keyControls.get(code);
			// If possible, try to remove the control from the list.
			if (controls != null) {
				removed = controls.remove(action);
			}
		}
		return removed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(KeyEvent event) {
		List<KeyAction> controls = keyControls.get(event.getCode());
		if (controls != null) {
			if (KeyEvent.KEY_PRESSED == event.getEventType()) {
				for (KeyAction control : controls) {
					control.keyPressed(-1.0f, event);
				}
			} else {
				for (KeyAction control : controls) {
					control.keyReleased(-1.0f, event);
				}
			}
		}
		// Consume the event so it is not processed again.
		event.consume();
	}

}
