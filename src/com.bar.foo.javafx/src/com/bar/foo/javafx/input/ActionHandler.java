/**
 * 
 */
package com.bar.foo.javafx.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.bar.foo.javafx.IFrameRateManager;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * An {@code ActionHandler} manages multiple {@link AnalogAction}s and
 * {@link ToggleAction}s for a particular key and event type.
 * <p>
 * Multiple actions can be triggered by a single event if they are keyed on the
 * same trigger.
 * </p>
 * <p>
 * This class was created to reduce code inside the {@link ControlManager}.
 * Specifically, the {@code ControlManager} leverages two instances of this
 * class, one each for key and mouse events. This frees the manager from
 * maintaining its own maps of {@code AnalogAction}s and {@code ToggleAction}s
 * and from having to handle events directly.
 * <p>
 * 
 * @author Jordan Deyton
 *
 * @param <K>
 *            The key or "trigger" type, e.g. KeyCode.
 * @param <T>
 *            The event type, e.g., KeyEvent. Multiple event types can be
 *            assigned to the same trigger.
 */
public abstract class ActionHandler<K, T extends Event> implements
		EventHandler<T> {

	/**
	 * The map of {@code AnalogAction}s.
	 */
	private final Map<K, List<AnalogAction<T>>> analogMap;
	/**
	 * The map of {@code ToggleAction}s.
	 */
	private final Map<K, List<ToggleAction<T>>> toggleMap;

	// TODO documentation
	private final IFrameRateManager frameRateManager;
	
	/**
	 * The default constructor.
	 * @param app // TODO documentation.
	 */
	public ActionHandler(IFrameRateManager frameRateManager) {
		// Initialize the maps here to prevent ugly code formatting.
		analogMap = new HashMap<K, List<AnalogAction<T>>>();
		toggleMap = new HashMap<K, List<ToggleAction<T>>>();
		
		this.frameRateManager = frameRateManager;
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

		// FIXME Should this be done here or in the loops? It seems like it
		// might be more accurate to do it below...
		// float timePerFrame = app.getTimePerFrame();

		List<ToggleAction<T>> toggles = toggleMap.get(key);
		if (toggles != null) {
			if (isOn(event)) {
				for (ToggleAction<T> toggle : toggles) {
					toggle.on(frameRateManager.getTPF(), event);
				}
			} else {
				for (ToggleAction<T> toggle : toggles) {
					toggle.off(frameRateManager.getTPF(), event);
				}
			}
		}

		List<AnalogAction<T>> analogs = analogMap.get(key);
		if (analogs != null) {
			float value = getValue(event);
			for (AnalogAction<T> analog : analogs) {
				analog.run(value, frameRateManager.getTPF(), event);
			}
		}

		// This event should not be processed any further by the JavaFX engine.
		event.consume();

		return;
	}

	protected abstract boolean isOn(T event);

	protected abstract K getTrigger(T event);

	protected abstract float getValue(T event);
}
