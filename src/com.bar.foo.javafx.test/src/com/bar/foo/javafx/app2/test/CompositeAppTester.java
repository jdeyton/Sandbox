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
		TestCompositeApp composite = new TestCompositeApp();
		composite.setControlManager(new ControlManager(null));

		TestCompositeApp child = new TestCompositeApp();
		composite.addApp(child);

		// Expected behavior for starting the parent:
		// parent started, parent inited, parent enabled (if applicable), parent
		// controls enabled (if applicable)
		// child inited (not started), child enabled (if applicable), child
		// controls enabled (if applicable)

		// Reset the test flags for good measure.
		composite.resetTestFlags();
		child.resetTestFlags();
		// Start the composite and check the behavior.
		composite.start();
		assertTrue(composite.wasStarted);
		assertFalse(child.wasStarted);
		assertTrue(composite.wasInited);
		assertTrue(child.wasInited);
		assertTrue(composite.wasEnabled);
		assertTrue(child.wasEnabled);
		assertTrue(composite.wasControlsEnabled);
		assertTrue(child.wasControlsEnabled);

		// Expected behavior for starting the parent with parent disabled and
		// child controls disabled:
		// parent started, parent inited, parent not enabled, parent controls
		// enabled
		// child inited (not started), child not enabled (not even called),
		// child controls not enabled (but called)
		composite.setEnabled(false);
		child.setControlsEnabled(false);

		// Reset the composite and child for testing purposes.
		composite.stop();
		// Reset the test flags for good measure.
		composite.resetTestFlags();
		child.resetTestFlags();
		// Start the composite and check the behavior.
		composite.start();
		assertTrue(composite.wasStarted);
		// assertFalse(child.wasStarted);
		assertTrue(composite.wasInited);
		assertTrue(child.wasInited);
		assertFalse(composite.wasEnabled);
		assertFalse(child.enableCalled);
		assertFalse(child.wasEnabled);
		assertTrue(composite.wasControlsEnabled);
		assertTrue(child.enableControlsCalled);
		assertFalse(child.wasControlsEnabled);

		// TODO Try nested children

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
