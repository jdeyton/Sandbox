/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author Jordan Deyton
 *
 */
public enum MouseCode {

	MOVE, BUTTON_PRIMARY, BUTTON_SECONDARY, BUTTON_MIDDLE;

	private static Map<Object, MouseCode> dictionary = new HashMap<Object, MouseCode>(); 
	
	static {
		dictionary.put(MouseEvent.MOUSE_MOVED, MOVE);
		dictionary.put(MouseButton.PRIMARY, BUTTON_PRIMARY);
		dictionary.put(MouseButton.SECONDARY, BUTTON_SECONDARY);
		dictionary.put(MouseButton.MIDDLE, BUTTON_MIDDLE);
	}
	
	public static MouseCode fromMouseEvent(MouseEvent event) {
		MouseCode code = dictionary.get(event.getEventType());
		if (code == null) {
			code = dictionary.get(event.getButton());
		}
		return code;
	}
}
