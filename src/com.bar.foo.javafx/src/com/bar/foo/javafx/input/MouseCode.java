/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * This is an enumeration used to wrap supported {@link MouseEvent}s and
 * {@link MouseButton}s under a single namespace. This is required for the "key"
 * type for {@link MouseActionHandler} and is necessitated by the fact that the
 * mouse button used during mouse press and release events are obtained via
 * {@link MouseEvent#getButton()}.
 * 
 * @author Jordan Deyton
 *
 */
public enum MouseCode {

	/**
	 * The code representing mouse movement.
	 */
	MOVE,
	/**
	 * The primary button, usually the left mouse button.
	 */
	BUTTON_PRIMARY,
	/**
	 * The secondary button, usually the right mouse button.
	 */
	BUTTON_SECONDARY,
	/**
	 * The middle mouse button, usually either the wheel itself or a button near
	 * it.
	 */
	BUTTON_MIDDLE;

	/**
	 * This is a dictionary used to look up the appropriate {@code MouseCode}
	 * based on the triggering {@code MouseEvent}.
	 */
	private static Map<Object, MouseCode> dictionary = new HashMap<Object, MouseCode>();

	// We use a static map, but we can't initialize it via a constructor because
	// the enum values are initialized first before any static variables are.
	static {
		dictionary.put(MouseEvent.MOUSE_MOVED, MOVE);
		dictionary.put(MouseButton.PRIMARY, BUTTON_PRIMARY);
		dictionary.put(MouseButton.SECONDARY, BUTTON_SECONDARY);
		dictionary.put(MouseButton.MIDDLE, BUTTON_MIDDLE);
	}

	/**
	 * Gets the appropriate {@code MouseCode} based on the triggering
	 * {@code MouseEvent}.
	 * 
	 * @param event
	 *            The triggering {@code MouseEvent}.
	 * @return True
	 */
	public static MouseCode fromMouseEvent(MouseEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("MouseCode error: "
					+ "Cannot retrieve a MouseCode for a null MouseEvent.");
		}
		MouseCode code = dictionary.get(event.getEventType());
		if (code == null) {
			code = dictionary.get(event.getButton());
		}
		return code;
	}
}
