package com.bar.foo.javafx.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestApplication extends Application {

	private final Scene scene;
	private final Group root;

	public TestApplication() {
		root = new Group();
		scene = new Scene(root, Color.BLACK);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Configure the default, initial scene.
		primaryStage.setScene(scene);

		primaryStage.setTitle("Test Application");

		// Configure the size of the Application window.
		primaryStage.setResizable(false);
		primaryStage.setWidth(1024);
		primaryStage.setHeight(768);

		// Initialize the application.
		initScene();

		// Opens the Application window.
		primaryStage.show();

		return;
	}

	@Override
	public void stop() throws Exception {
		// Uninitialize the application.
		disposeScene();

		// Proceed with the normal stop procedure.
		super.stop();
	}

	public void initScene() {
		// TODO
	}

	public void disposeScene() {
		// TODO
	}

}
