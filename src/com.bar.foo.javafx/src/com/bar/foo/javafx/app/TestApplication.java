package com.bar.foo.javafx.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Configure the default, initial scene.
		Group root = new Group();
		Scene scene = new Scene(root, Color.BLACK);
		primaryStage.setScene(scene);

		// Configure the size of the Application window.
		primaryStage.setResizable(false);
		primaryStage.setWidth(1024);
		primaryStage.setHeight(768);

		// Opens the Application window.
		primaryStage.show();

		return;
	}

}
