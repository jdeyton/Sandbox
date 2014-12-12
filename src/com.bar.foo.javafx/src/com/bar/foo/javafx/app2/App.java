package com.bar.foo.javafx.app2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.bar.foo.javafx.IEnableable;
import com.bar.foo.javafx.IStartable;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.input.IControlProvider;

// TODO Documentation
public abstract class App implements IStartable, IEnableable, IControlProvider {

	private final AtomicInteger startState = new AtomicInteger(0);
	private final AtomicBoolean enabled = new AtomicBoolean(true);
	private final AtomicBoolean controlsEnabled = new AtomicBoolean(true);

	private AtomicReference<ControlManager> controlManager = new AtomicReference<ControlManager>();

	// ---- Implements IControlProvider ---- //
	@Override
	public boolean setControlsEnabled(boolean enabled) {
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		if (controlsEnabled.compareAndSet(!enabled, enabled)) {
			changed = true;

			// If the app is currently running and not stupping, and if the
			// ControlManager is set, then we should either enable or disable
			// the app's controls.
			ControlManager manager;
			if (isStarted() && (manager = getControlManager()) != null) {
				if (enabled) {
					enableAppControls(manager);
				} else {
					disableAppControls(manager);
				}
			}
		}
		return changed;
	}

	protected abstract void enableAppControls(ControlManager manager);

	protected abstract void disableAppControls(ControlManager manager);

	@Override
	public boolean controlsEnabled() {
		return controlsEnabled.get();
	}

	@Override
	public boolean setControlManager(ControlManager manager)
			throws NullPointerException {
		// Check the argument for null.
		if (manager == null) {
			throw new NullPointerException("App error: "
					+ "Cannot set App's ControlManager to null.");
		}
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		ControlManager oldManager = controlManager.getAndSet(manager);
		if (oldManager != manager) {
			changed = true;

			// If the app is currently running and not stopping, and if the
			// controls were enabled, we should disable the old controls and
			// enable the new ones.
			if (isStarted() && controlsEnabled()) {
				disableAppControls(oldManager);
				enableAppControls(manager);
			}
		}
		return changed;
	}

	@Override
	public boolean unsetControlManager() {
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		ControlManager oldManager = controlManager.getAndSet(null);
		if (oldManager != null) {
			changed = true;

			// If the app is currently running and not stopping, and if controls
			// were enabled, we should disable the controls.
			if (isStarted() && controlsEnabled()) {
				disableAppControls(oldManager);
			}
		}
		return changed;
	}

	protected ControlManager getControlManager() {
		return controlManager.get();
	}

	// ------------------------------------- //

	// ---- Implements IEnableable ---- //
	@Override
	public boolean setEnabled(boolean enabled) {
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		if (this.enabled.compareAndSet(!enabled, enabled)) {
			changed = true;

			// If the app is currently running and not stopping, we should
			// either enable or disable the app's customizable features.
			if (isStarted()) {
				if (enabled) {
					enableApp();
				} else {
					disableApp();
				}
			}
		}
		return changed;
	}

	protected abstract void enableApp();

	protected abstract void disableApp();

	@Override
	public boolean isEnabled() {
		return enabled.get();
	}

	// -------------------------------- //

	// ---- Implements IStartable ---- //
	@Override
	public boolean start() {
		boolean changed = false;
		// Only proceed if the app is currently STOPPED. Mark it as STARTING.
		if (startState.compareAndSet(0, 2)) {
			changed = true;

			// Call the customizable initialization operation.
			initApp();

			// If already enabled, we should actually enable the app.
			if (isEnabled()) {
				enableApp();
			}
			// If the controls are already enabled and the ControlManager is
			// set, we should actually enable the controls.
			ControlManager manager;
			if (controlsEnabled() && (manager = getControlManager()) != null) {
				enableAppControls(manager);
			}

			// Mark the app as STARTED.
			startState.set(1);
		}
		return changed;
	}

	protected abstract void initApp();

	@Override
	public boolean stop() {
		boolean changed = false;
		// Only proceed if the app is currently STARTED. Mark it as STOPPING.
		if (startState.compareAndSet(1, 3)) {

			// If the controls are enabled and the ControlManager is set, we
			// should disable the controls.
			ControlManager manager;
			if (controlsEnabled() && (manager = getControlManager()) != null) {
				disableAppControls(manager);
			}
			// If enabled, we should disable the app.
			if (isEnabled()) {
				disableApp();
			}

			// Call the customizable disposal operation.
			disposeApp();

			// Mark the app as STOPPED.
			startState.set(0);
		}
		return changed;
	}

	protected abstract void disposeApp();

	@Override
	public boolean isStarted() {
		return startState.get() == 1;
	}

	@Override
	public boolean isStarting() {
		return startState.get() == 2;
	}

	@Override
	public boolean isStopping() {
		return startState.get() == 3;
	}

	// ------------------------------- //

	protected abstract void update(float tpf);
}
