package com.bar.foo.javafx.input;

/**
 * This is an interface for objects that maintain a set of controls that can be
 * registered with a {@link ControlManager}.
 * 
 * @author Jordan Deyton
 *
 */
public interface IControlContributor {

	/**
	 * Registers controls with the provided {@link ControlManager}. If the
	 * {@code ControlManager} is hooked up to an active scene, this is the same
	 * as enabling the controls.
	 * <p>
	 * Controls should not be simultaneously registered with multiple
	 * {@code ControlManager}s.
	 * </p>
	 * 
	 * @param controlManager
	 *            The {@code ControlManager} for a scene. If the controls are
	 *            unregistered and this {@code ControlManager} is not null, then
	 *            controls will be registered with it.
	 * @return True if the controls were successfully registered with the
	 *         specified {@code ControlManager}, false otherwise.
	 */
	public boolean registerControls(ControlManager controlManager);

	/**
	 * Unregisters controls from the associated {@link ControlManager} if
	 * controls have been previously registered.
	 * 
	 * @return True if the controls were unregistered, false if the controls
	 *         failed to unregister or there was no associated
	 *         {@code ControlManager}.
	 */
	public boolean unregisterControls();
}
