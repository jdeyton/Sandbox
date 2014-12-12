package com.bar.foo.javafx.input;

public interface IControlProvider {

	public boolean setControlsEnabled(boolean enabled);

	public boolean controlsEnabled();

	public boolean setControlManager(ControlManager manager)
			throws NullPointerException;

	public boolean unsetControlManager();
}
