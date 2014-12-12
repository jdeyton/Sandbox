package com.bar.foo.javafx.app2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.bar.foo.javafx.input.ControlManager;

public abstract class CompositeApp extends App {

	/**
	 * A list of sub-apps managed by this composite app.
	 */
	private final List<App> apps = new ArrayList<App>(5);

	private final WriteLock appsWriteLock;
	private final ReadLock appsReadLock;

	// TODO Handle updating, enabling, disabling, etc.

	protected interface AppRunnable {
		public boolean run(App app);
	}

	protected final boolean runOnApps(AppRunnable runnable) {
		boolean success = (runnable != null);
		if (success) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					success &= runnable.run(app);
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return success;
	}

	public CompositeApp() {
		ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
		appsWriteLock = readWriteLock.writeLock();
		appsReadLock = readWriteLock.readLock();
	}

	/**
	 * Adds the specified {@code App} to this {@code CompositeApp}.
	 * 
	 * @param app
	 *            The app to add.
	 * @return False if the specified app is null or is already in the list.
	 *         True otherwise.
	 */
	public boolean addApp(App app) {
		boolean added = false;
		if (app != null) {
			appsReadLock.lock();
			try {
				if (!apps.contains(app)) {
					appsWriteLock.lock();
					try {
						apps.add(app);
						added = true;
					} finally {
						appsWriteLock.unlock();
					}
				}
			} finally {
				appsReadLock.unlock();
			}
		}

		// TODO Start the new app.

		return added;
	}

	/**
	 * Removes the specified {@code App} from this {@code CompositeApp}.
	 * 
	 * @param app
	 *            The app to remove.
	 * @return True if the app was in the {@code CompositeApp} and was removed.
	 *         False otherwise.
	 */
	public boolean removeApp(App app) {
		boolean removed = false;
		if (app != null) {
			appsWriteLock.lock();
			try {
				removed = apps.remove(app);
			} finally {
				appsWriteLock.unlock();
			}
		}

		// TODO Stop the old app.

		return removed;
	}

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

			// Start all of the child apps.
			runOnApps(new AppRunnable() {
				@Override
				public boolean run(App app) {
					return app.start();
				}
			});

			// Mark the app as STARTED.
			setStarted();
		}
		return changed;
	}

	@Override
	public boolean stop() {
		boolean changed = false;
		// Only proceed if the app is currently STARTED. Mark it as STOPPING.
		if (setStopping()) {

			// Stop all of the child apps.
			runOnApps(new AppRunnable() {
				@Override
				public boolean run(App app) {
					return app.stop();
				}
			});

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

	@Override
	public boolean setEnabled(boolean enabled) {
		// Determine if the value has actually changed, while at the same time
		// setting it to the new value.
		boolean changed = false;
		if (this.enabled.compareAndSet(!enabled, enabled)) {
			changed = true;

			// We should either enable or disable the app as necessary. These
			// methods may return false if the app cannot be enabled/disabled.
			AppRunnable runnable = null;
			if (enabled) {
				if (enableApp()) {
					runnable = new AppRunnable() {
						@Override
						public boolean run(App app) {
							return app.enableApp();
						}
					};
				}
			} else if (disableApp()) {
				runnable = new AppRunnable() {
					@Override
					public boolean run(App app) {
						return app.disableApp();
					}
				};
			}
			// Enable/disable all of the child apps as necessary.
			runOnApps(runnable);
		}
		return changed;
	}

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
			AppRunnable runnable = null;
			final ControlManager manager = getControlManager();
			if (enabled) {
				if (enableAppControls(manager)) {
					runnable = new AppRunnable() {
						@Override
						public boolean run(App app) {
							return app.enableAppControls(manager);
						}
					};
				}
			} else if (disableAppControls(manager)) {
				runnable = new AppRunnable() {
					@Override
					public boolean run(App app) {
						return app.disableAppControls(manager);
					}
				};
			}
			// Enable/disable all of the child app controls as necessary.
			runOnApps(runnable);
		}
		return changed;
	}

}
