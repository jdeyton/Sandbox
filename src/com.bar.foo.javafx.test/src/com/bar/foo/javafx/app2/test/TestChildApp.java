package com.bar.foo.javafx.app2.test;

import com.bar.foo.javafx.app2.App;
import com.bar.foo.javafx.input.ControlManager;

public class TestChildApp extends App {

	public boolean startCalled = false;
	public boolean wasStarted = false;

	public boolean initCalled = false;
	public boolean wasInited = false;

	public boolean stopCalled = false;
	public boolean wasStopped = false;

	public boolean disposeCalled = false;
	public boolean wasDisposed = false;

	public boolean enableCalled = false;
	public boolean wasEnabled = false;

	public boolean disableCalled = false;
	public boolean wasDisabled = false;

	public boolean enableControlsCalled = false;
	public boolean wasControlsEnabled = false;

	public boolean disableControlsCalled = false;
	public boolean wasControlsDisabled = false;

	public ControlManager manager = null;

	public boolean updateCalled = false;

	public void resetTestFlags() {
		startCalled = false;
		wasStarted = false;

		initCalled = false;
		wasInited = false;

		stopCalled = false;
		wasStopped = false;

		disposeCalled = false;
		wasDisposed = false;

		enableCalled = false;
		wasEnabled = false;

		disableCalled = false;
		wasDisabled = false;

		enableControlsCalled = false;
		wasControlsEnabled = false;

		disableControlsCalled = false;
		wasControlsDisabled = false;

		manager = null;

		updateCalled = false;
	}

	@Override
	public boolean start() {
		startCalled = true;
		wasStarted = super.start();
		return wasStarted;
	}

	@Override
	public boolean initApp() {
		initCalled = true;
		wasInited = super.initApp();
		return wasInited;
	}

	@Override
	public boolean stop() {
		stopCalled = true;
		wasStopped = super.stop();
		return wasStopped;
	}

	@Override
	public boolean disposeApp() {
		disposeCalled = true;
		wasDisposed = super.disposeApp();
		return wasDisposed;
	}

	@Override
	public boolean enableApp() {
		enableCalled = true;
		wasEnabled = super.enableApp();
		return wasEnabled;
	}

	@Override
	public boolean disableApp() {
		disableCalled = true;
		wasDisabled = super.disableApp();
		return wasDisabled;
	}

	@Override
	public boolean enableAppControls(ControlManager manager) {
		enableControlsCalled = true;
		wasControlsEnabled = super.enableAppControls(manager);
		this.manager = manager;
		return wasControlsEnabled;
	}

	@Override
	public boolean disableAppControls(ControlManager manager) {
		disableControlsCalled = true;
		wasControlsDisabled = super.disableAppControls(manager);
		this.manager = manager;
		return wasControlsDisabled;
	}

	@Override
	protected void update(float tpf) {
		updateCalled = true;
	}

}
