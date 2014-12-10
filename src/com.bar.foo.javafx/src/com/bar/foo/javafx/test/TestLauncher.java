package com.bar.foo.javafx.test;

import javafx.application.Application;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestLauncher {

	private Shell shell;

	public static void main(String[] args) {
		Application.launch(TestApplication.class, args);
	}

	public TestLauncher() {
		// Create the Display.
		Display display = new Display();
		// Create the Shell (window).
		shell = new Shell(display);
		shell.setText("Test");
		shell.setSize(1024, 768);
		shell.setLayout(new FillLayout());

		return;
	}

	public Shell getShell() {
		return shell;
	}

	public void openShell() {

		shell.open();
		Display display = shell.getDisplay();

		// SOP UI loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		return;
	}
}
