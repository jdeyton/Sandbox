package com.bar.foo.javafx.app2.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bar.foo.javafx.app2.CompositeApp;
import com.bar.foo.javafx.input.ControlManager;

public class CompositeAppTester {

	@Test
	public void checkStart() {
		CompositeApp composite = new CompositeApp() {
			@Override
			protected void update(float tpf) {
				// Nothing to do.
			}
		};
		composite.setControlManager(new ControlManager(null));
		
		TestChildApp child = new TestChildApp();
		composite.addApp(child);
		
		assertFalse(child.startCalled);
		assertFalse(child.initCalled);
		assertFalse(child.enableCalled);
		assertFalse(child.enableControlsCalled);
		
		composite.start();
		
		assertTrue(child.startCalled);
		assertTrue(child.wasStarted);
		assertTrue(child.initCalled);
		assertTrue(child.wasInited);
		assertTrue(child.enableCalled);
		assertTrue(child.wasEnabled);
		assertTrue(child.enableControlsCalled);
		assertTrue(child.wasControlsEnabled);
		
		return;
	}

	@Test
	public void checkStop() {
		fail("not implemented");
	}

	@Test
	public void checkEnable() {
		fail("not implemented");
	}

	@Test
	public void checkEnableControls() {
		fail("not implemented");
	}

	@Test
	public void checkUpdate() {
		fail("not implemented");
	}

	@Test
	public void checkApps() {
		fail("not implemented");
	}
}
