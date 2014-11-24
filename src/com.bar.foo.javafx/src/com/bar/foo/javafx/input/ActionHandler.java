/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author Jordan Deyton
 *
 */
public abstract class ActionHandler<K, T extends Event> implements
		EventHandler<T> {

	private final Map<K, List<AnalogAction<T>>> analogMap;
	private final Map<K, List<ToggleAction<T>>> toggleMap;

	public ActionHandler() {
		analogMap = new HashMap<K, List<AnalogAction<T>>>();
		toggleMap = new HashMap<K, List<ToggleAction<T>>>();
	}

	public boolean addAnalog(K key, AnalogAction<T> action) {
		boolean added = false;
		if (key != null && action != null) {
			List<AnalogAction<T>> controls = analogMap.get(key);
			// If necessary, add a new list of control actions to the map.
			if (controls == null) {
				controls = new ArrayList<AnalogAction<T>>();
				analogMap.put(key, controls);
			}
			// We can now add the control.
			added = controls.add(action);
		}
		return added;
	}

	public boolean addToggle(K key, ToggleAction<T> action) {
		boolean added = false;
		if (key != null && action != null) {
			List<ToggleAction<T>> controls = toggleMap.get(key);
			// If necessary, add a new list of control actions to the map.
			if (controls == null) {
				controls = new ArrayList<ToggleAction<T>>();
				toggleMap.put(key, controls);
			}
			// We can now add the control.
			added = controls.add(action);
		}
		return added;
	}

	// TODO Add remove methods.

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(T event) {
		K key = getTrigger(event);
		float timePerFrame = getTimePerFrame();

		List<ToggleAction<T>> toggles = toggleMap.get(key);
		if (toggles != null) {
			if (isOn(event)) {
				for (ToggleAction<T> toggle : toggles) {
					toggle.on(timePerFrame, event);
				}
			} else {
				for (ToggleAction<T> toggle : toggles) {
					toggle.off(timePerFrame, event);
				}
			}
		}

		List<AnalogAction<T>> analogs = analogMap.get(key);
		if (analogs != null) {
			float value = getValue(event);
			for (AnalogAction<T> analog : analogs) {
				analog.run(value, timePerFrame, event);
			}
		}

		// This event should not be processed any further by the JavaFX engine.
		event.consume();

		return;
	}

	protected float getTimePerFrame() {
		return 0f;
	}

	protected abstract boolean isOn(T event);

	protected abstract K getTrigger(T event);

	protected abstract float getValue(T event);
}
