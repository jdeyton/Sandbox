package com.bar.foo.javafx.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import com.bar.foo.javafx.Node;

public class TestApplication extends Application {

	private final Scene scene;
	private final Group root;

	private final PerspectiveCamera camera = new PerspectiveCamera(true);

	public TestApplication() {
		Node root = new Node();
//		root.rx = new Rotate(180); // Flip the scene around the x-axis.
		scene = new Scene(root, 0.0, 0.0, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.BLACK);
		this.root = root;
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
		initCamera();

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
		Box box = new Box(100.0, 100.0, 100.0);
		PhongMaterial material = new PhongMaterial(Color.GRAY);
		material.setSpecularColor(Color.DARKBLUE);
		box.setMaterial(material);
		box.getTransforms().add(new Translate(300.0, -300.0, 100.0));
		root.getChildren().add(box);

		Circle circle = new Circle(100, Color.WHITE);
		// circle.getTransforms().add(new Translate(100.0, -100.0));
		root.getChildren().add(circle);
	}

	public void initCamera() {

		// camera.setNearClip(CAMERA_NEAR_CLIP);
		// camera.setFarClip(CAMERA_FAR_CLIP);
		// camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

		// Add the camera to the scene. We can only have one at a time.
		scene.setCamera(camera);

		// Add the camera to the scene graph.
		root.getChildren().add(camera);

		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);

		// camera.getTransforms().add(new Translate(0.0, 0.0, 500.0));
		camera.setTranslateZ(-1000);

		// Rotate rotation = new Rotate();
		// rotation.setAxis(Rotate.Z_AXIS);
		// rotation.setAngle(180.0);

		// root.getTransforms().add(rotation);

		return;
	}

	public void disposeScene() {
		// TODO
	}

	public void disposeCamera() {
		// TODO
	}

}
