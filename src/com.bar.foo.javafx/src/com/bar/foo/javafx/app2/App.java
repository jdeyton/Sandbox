package com.bar.foo.javafx.app2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.bar.foo.javafx.IEnableable;
import com.bar.foo.javafx.IStartable;
import com.bar.foo.javafx.app2.CompositeApp.AppRunnable;
import com.bar.foo.javafx.input.ControlManager;
import com.bar.foo.javafx.input.IControlProvider;

// TODO Documentation
public abstract class App implements IStartable, IEnableable, IControlProvider {

	private final AtomicInteger startState = new AtomicInteger(0);
	protected final AtomicBoolean enabled = new AtomicBoolean(true);
	protected final AtomicBoolean controlsEnabled = new AtomicBoolean(true);

	private AtomicReference<ControlManager> controlManager = new AtomicReference<ControlManager>();

	// ---- Implements IControlProvider ---- //
	@Override
	public boolean setControlsEnabled(boolean enabled) {
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		if (controlsEnabled.compareAndSet(!enabled, enabled)) {
			changed = true;

			// We should either enable or disable the app controls as necessary.
			// These methods may return false if the app's controls cannot be
			// enabled/disabled.
			ControlManager manager = getControlManager();
			if (enabled) {
				enableAppControls(manager);
			} else {
				disableAppControls(manager);
			}
		}
		return changed;
	}

	protected boolean enableAppControls(ControlManager manager) {
		return (controlsEnabled() && manager != null && (isStarted() || isStarting()));
	}

	protected boolean disableAppControls(ControlManager manager) {
		return (!controlsEnabled() && manager != null);
	}

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

			// We should either enable or disable the app as necessary. These
			// methods may return false if the app cannot be enabled/disabled.
			if (enabled) {
				enableApp();
			} else {
				disableApp();
			}
		}
		return changed;
	}

	protected boolean enableApp() {
		return (isEnabled() && (isStarted() || isStarting()));
	}

	protected boolean disableApp() {
		return (!isEnabled());
	}

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
		if (setStarting()) {
			changed = true;

			// Call the customizable initialization operation.
			initApp();

			// Enable the app and its controls as necessary. These may return
			// false if the app or its controls should not be enabled yet.
			enableApp();
			enableAppControls(getControlManager());

			// Mark the app as STARTED.
			setStarted();
		}
		return changed;
	}

	protected boolean initApp() {
		return isStarting();
	}

	@Override
	public boolean stop() {
		boolean changed = false;
		// Only proceed if the app is currently STARTED. Mark it as STOPPING.
		if (setStopping()) {

			// Disable the app and its controls as necessary. These may return
			// false if the app or its controls do not need to be disabled.
			disableAppControls(getControlManager());
			disableApp();

			// Call the customizable disposal operation.
			disposeApp();

			// Mark the app as STOPPED.
			setStopped();
		}
		return changed;
	}

	protected boolean disposeApp() {
		return isStopping();
	}

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

	protected final boolean setStarting() {
		return startState.compareAndSet(0, 2);
	}

	protected final boolean setStarted() {
		return startState.compareAndSet(2, 1);
	}

	protected final boolean setStopping() {
		return startState.compareAndSet(1, 3);
	}

	protected final boolean setStopped() {
		return startState.compareAndSet(3, 0);
	}

	// ------------------------------- //

	protected abstract void update(float tpf);
}
