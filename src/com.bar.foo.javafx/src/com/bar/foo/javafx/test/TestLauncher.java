package com.bar.foo.javafx.test;

import javafx.application.Application;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.bar.foo.javafx.app.TestApplication;

public class TestLauncher {

	public static void main(String[] args) {
		Application.launch(TestApplication.class, args);
	}

	private Shell createShell() {
		// Create the Display.
		Display display = new Display();
		// Create the Shell (window).
		Shell shell = new Shell(display);
		shell.setText("JavaFX Test Launcher");
		shell.setSize(1024, 768);
		shell.setLayout(new FillLayout());

		return shell;
	}

	private void runShell(Shell shell) {

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
