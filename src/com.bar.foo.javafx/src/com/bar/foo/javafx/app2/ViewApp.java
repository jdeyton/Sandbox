package com.bar.foo.javafx.app2;

import com.bar.foo.javafx.IShowable;
import com.bar.foo.javafx.input.ControlManager;

public abstract class ViewApp extends CompositeApp implements IShowable {

//	@Override
//	public boolean start() {
//		boolean changed = false;
//		// Only proceed if the app is currently STOPPED. Mark it as STARTING.
//		if (setStarting()) {
//			changed = true;
//
//			// Call the customizable initialization operation.
//			initApp();
//
//			// If already enabled, we should actually enable the app.
//			if (isEnabled()) {
//				enableApp();
//			}
//			// If the controls are already enabled and the ControlManager is
//			// set, we should actually enable the controls.
//			ControlManager manager;
//			if (controlsEnabled() && (manager = getControlManager()) != null) {
//				enableAppControls(manager);
//			}
//
//			AppRunnable runnable = new AppRunnable() {
//				@Override
//				public boolean run(App app) {
//					return app.start();
//				}
//			};
//			runOnApps(runnable);
//
//			// Mark the app as STARTED.
//			setStarted();
//		}
//		return changed;
//	}
	
}
