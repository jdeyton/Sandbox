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
		return removed;
	}

	protected void enableAppControls(ControlManager manager) {
		// The super method is abstract.
		if (manager != null && controlsEnabled()) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					app.enableAppControls(manager);
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return;
	}

	@Override
	protected void disableAppControls(ControlManager manager) {
		// The super method is abstract.
		if (manager != null && !controlsEnabled()) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					app.disableAppControls(manager);
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return;
	}

	@Override
	protected void enableApp() {
		// The super method is abstract.
		if (isEnabled()) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					app.enableApp();
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return;
	}
	
	@Override
	protected void disableApp() {
		// The super method is abstract.
		if (!isEnabled()) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					app.disableApp();
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return;		
	}
	
	// TODO Should we override the init or start method?
	@Override
	protected void initApp() {
		// The super method is abstract.
		if (isStarting()) {
			appsReadLock.lock();
			try {
				for (App app : apps) {
					app.initApp();
				}
			} finally {
				appsReadLock.unlock();
			}
		}
		return;		
	}
	
	@Override
	protected void disposeApp() {
		
	}
}
