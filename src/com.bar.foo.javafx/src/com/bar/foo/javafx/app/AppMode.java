package com.bar.foo.javafx.app;

/**
 * An {@code AppMode} is a "mode of operation" for a JavaFX-based {@link App}.
 * This implementation exhibits three basic properties:
 * <ul>
 * <li>{@link #initialized} - Whether or not the mode has been configured.</li>
 * <li>{@link #enabled} - Whether or not the mode is paused (true means
 * unpaused).</li>
 * <li>{@link #visible} - Whether or not artifacts managed by the mode are
 * rendered in the {@link #app}'s scene.</li>
 * </ul>
 * 
 * @author Jordan
 *
 */
public abstract class AppMode {

	/**
	 * The parent {@link App} that this mode supports.
	 */
	private App app = null;

	/**
	 * Whether or not the mode has been initialized or associated with an
	 * {@link #app}.
	 */
	private boolean initialized = false;
	/**
	 * Whether or not the mode is enabled. If the mode is disabled, it is
	 * "paused", or its effects (i.e., controls) have been disabled.
	 */
	private boolean enabled = true;
	/**
	 * Whether or not the mode is visible. If the mode is invisible, its effects
	 * (i.e., additions to the {@link #app}'s scene) are not rendered.
	 */
	private boolean visible = true;

	// TODO We may want to change the above to atomics.

	/**
	 * The default constructor.
	 */
	public AppMode() {
		// Nothing to do (yet).
	}

	/**
	 * Gets the parent {@link App} that this mode supports.
	 * 
	 * @return The parent {@link #app}. This is null if the mode is not
	 *         initialized.
	 */
	protected App getApp() {
		return app;
	}

	/**
	 * If the mode is both initialized and enabled, then this method updates the
	 * mode.
	 * 
	 * @param tpf
	 *            The current time per frame as of the frame that triggered this
	 *            call.
	 */
	public final void update(float tpf) {
		if (isInitialized() && isEnabled()) {
			updateMode(tpf);
		}
	}

	/**
	 * Updates the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the sub-class' method
	 * should call this method before returning.
	 * </p>
	 * 
	 * @param tpf
	 *            The current time per frame as of the frame that triggered this
	 *            call.
	 */
	protected void updateMode(float tpf) {
		// Nothing to do (yet).
	}

	// ---- Initialized ---- //
	/**
	 * Gets whether or not the mode has been initialized or associated with an
	 * {@link #app}.
	 * 
	 * @return True if the mode was initialized, false otherwise.
	 */
	public final boolean isInitialized() {
		return initialized;
	}

	/**
	 * Initializes the {@code AppMode} to operate with the specified parent
	 * {@link App}.
	 * 
	 * @param app
	 *            The parent app that this mode supports.
	 * @return True if the mode is initialized, false otherwise.
	 * @throws NullPointerException
	 *             if the mode is not initialized and a null app is specified
	 */
	public final boolean initialize(App app) throws NullPointerException {
		boolean wasInitialized = false;
		if (!initialized) {

			// Throw an exception if the argument is null.
			if (app == null) {
				throw new NullPointerException("AppMode error: "
						+ "Cannot initialize from null app.");
			}

			// Set the reference to the app.
			this.app = app;

			// Call the customizable initialization operation.
			initMode();

			// Update the initialized flag.
			initialized = true;

			// If already enabled or visible, then enable or show the custom
			// mode features.
			if (isVisible()) {
				showMode();
			}
			if (isEnabled()) {
				enableMode();
			}

			// Update the return value.
			wasInitialized = true;
		}
		return wasInitialized;
	}

	/**
	 * Initializes customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>first</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void initMode() {
		// Nothing to do (yet).
	}

	/**
	 * Cleans up or un-initializes the {@code AppMode} from its parent
	 * {@link #app}.
	 * 
	 * @return True if the mode was un-initialized (in other words, changed from
	 *         initialized to not initialized), false otherwise.
	 */
	public final boolean cleanup() {
		boolean wasCleaned = false;
		if (isInitialized()) {

			// If already enabled or visible, then disable or hide the custom
			// mode features.
			if (isEnabled()) {
				disableMode();
			}
			if (isVisible()) {
				hideMode();
			}

			// Unset the initialized flag.
			initialized = false;

			// Call the customizable un-initialization operation.
			cleanupMode();

			// Unset the reference to the app.
			this.app = null;

			// Update the return value.
			wasCleaned = true;
		}
		return wasCleaned;
	}

	/**
	 * Cleans up or un-initializes the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>last</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void cleanupMode() {
		// Nothing to do (yet).
	}

	// --------------------- //

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

	// ---- Visible ---- //
	/**
	 * Gets whether or not the mode is visible. If the mode is invisible, its
	 * effects (i.e., additions to the {@link #app}'s scene) are not rendered.
	 * 
	 * @return True if the mode is visible, false otherwise.
	 */
	public final boolean isVisible() {
		return visible;
	}

	/**
	 * Shows or hides the mode. If visible, the mode's effects (e.g., objects in
	 * the {@link #app}'s scene) are visible. If invisible, its effects are
	 * invisible.
	 * <p>
	 * If this is set before the mode is initialized, then when initialized, the
	 * mode is started either visible or invisible, depending on the current
	 * setting.
	 * </p>
	 * 
	 * @param visible
	 *            Whether or not the mode should be visible.
	 * @return True if the mode's visibility state was successfully changed,
	 *         false othwerise.
	 */
	public final boolean setVisible(boolean visible) {
		boolean changed = (this.visible != visible);

		// If the value has changed and the app has initialized, call the
		// appropriate customizable method depending on the parameter.
		if (changed && isInitialized()) {
			if (visible) {
				showMode();
			} else {
				hideMode();
			}
		}

		// Update the flag.
		this.visible = visible;

		return changed;
	}

	/**
	 * Shows the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>first</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void showMode() {
		// Nothing to do (yet).
	}

	/**
	 * Hides the customizable features of this mode.
	 * <p>
	 * <b>Note:</b> If this method is overridden, then the <b><i>last</i></b>
	 * call from the sub-class' method should call this method.
	 * </p>
	 */
	protected void hideMode() {
		// Nothing to do (yet).
	}
	// ----------------- //
}
