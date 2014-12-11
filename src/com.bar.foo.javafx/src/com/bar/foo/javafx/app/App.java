package com.bar.foo.javafx.app;

import javafx.animation.AnimationTimer;

import com.bar.foo.javafx.IFrameRateManager;

/**
 * TODO document this class...
 * 
 * @author Jordan
 *
 */
public class App implements IFrameRateManager {

	// ---- Framerate management ---- //
	/**
	 * The timer is responsible for two things: determining the current time per
	 * frame (or frames per second) and triggering an update to the app.
	 */
	private final AnimationTimer timer;
	/**
	 * The current rate at which frames are being rendered.
	 */
	private float timePerFrame;
	// ------------------------------ //

	// TODO Add containers for AppModes.

	/**
	 * Whether or not the app has been started.
	 */
	private boolean initialized = false;
	/**
	 * Whether or not the app is enabled. If the app is disabled, all associ.
	 */
	private boolean enabled = true;

	/**
	 * The default constructor.
	 */
	public App() {
		// Initialize the app timer, but do not start it yet.
		timer = new AnimationTimer() {
			// The nanosecond time of the previous frame.
			private long previous;

			@Override
			public void handle(long now) {
				// This method is called in each frame. Compute the time per
				// frame.
				timePerFrame = (now - previous) * 0.000000001f;
				previous = now;
				// Update the app.
				update(timePerFrame);
			}
		};

		return;
	}

	// ---- Lifecycle ---- //
	/**
	 * Gets whether or not the app has been initialized.
	 * 
	 * @return True if the app was initialized, false otherwise.
	 */
	public final boolean isInitialized() {
		return initialized;
	}

	/**
	 * Initializes (starts) the app.
	 * 
	 * @return True if the app is initialized, false otherwise.
	 */
	public final boolean initialize() {
		boolean wasStarted = false;
		// TODO
		return wasStarted;
	}

	/**
	 * Initializes customizable features of this app.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>first</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void initApp() {
		// Nothing to do (yet).
	}

	/**
	 * If the app is both initialized and enabled, then this method updates the
	 * app.
	 * 
	 * @param tpf
	 *            The current time per frame as of the frame that triggered this
	 *            call.
	 */
	private void update(float tpf) {
		// Update the customizable features of this app.
		updateApp(tpf);
	}

	/**
	 * Updates the customizable features of this app.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the sub-class' method
	 * should call this method before returning.
	 * </p>
	 * 
	 * @param tpf
	 *            The current time per frame as of the frame that triggered this
	 *            call.
	 */
	protected void updateApp(float tpf) {
		// Nothing to do (yet).
	}

	/**
	 * Cleans up or un-initializes the app.
	 * 
	 * @return True if the app was un-initialized (in other words, changed from
	 *         initialized to not initialized), false otherwise.
	 */
	public final boolean cleanup() {
		boolean wasStopped = false;
		// TODO
		return wasStopped;
	}

	/**
	 * Cleans up or un-initializes the customizable features of this app.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>last</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void cleanupApp() {
		// Nothing to do (yet).
	}

	// ------------------- //

	// ---- Enabled ---- //
	/**
	 * Gets whether or not the mode is enabled. If the mode is disabled, it is
	 * "paused", or its effects (i.e., controls) have been disabled.
	 * 
	 * @return True if the mode is enabled, false otherwise.
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables the mode. If enabled, the mode is active, and its
	 * effects (i.e., controls) are enabled. If disabled, the mode is "paused",
	 * and its effects are disabled.
	 * <p>
	 * If this is set before the mode is initialized, then when initialized, the
	 * mode is started either enabled or disabled, depending on the current
	 * setting.
	 * </p>
	 * 
	 * @param enabled
	 *            Whether or not the mode should be enabled.
	 * @return True if the mode's enabled state was successfully changed, false
	 *         otherwise.
	 */
	public final boolean setEnabled(boolean enabled) {
		boolean changed = (this.enabled != enabled);

		// If the value has changed and the app has initialized, call the
		// appropriate customizable method depending on the parameter.
		if (changed && isInitialized()) {
			if (enabled) {
				enableMode();
			} else {
				disableMode();
			}
		}

		// Update the flag.
		this.enabled = enabled;

		return changed;
	}

	/**
	 * Enables the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>first</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void enableMode() {
		// Nothing to do (yet).
	}

	/**
	 * Disables the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>last</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void disableMode() {
		// Nothing to do (yet).
	}

	// ----------------- //

	// ---- Implements IFrameRateManager ---- //
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.IFrameRateManager#getFPS()
	 */
	@Override
	public float getFPS() {
		return 1f / timePerFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.javafx.IFrameRateManager#getTPF()
	 */
	@Override
	public float getTPF() {
		return timePerFrame;
	}

	// -------------------------------------- //
}
