package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.StarterApp;

public class DesktopLauncher {

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Configure the size of the screen at startup.
		DisplayMode desktop = LwjglApplicationConfiguration.getDesktopDisplayMode();
		// Use 80% of the width and height for now.
		config.width = (int) (desktop.width * 0.8);
		config.height = (int) (desktop.height * 0.8);
		// config.fullscreen = true;

		// Set the icons used by the application.
		config.addIcon("images/icon_blue_sun_128x128.png", FileType.Internal);
		config.addIcon("images/icon_blue_sun_32x32.png", FileType.Internal);
		config.addIcon("images/icon_blue_sun_16x16.png", FileType.Internal);

		// Target FPS for running in the background should remain high enough to
		// not be jittery.
		config.backgroundFPS = 30;

//		new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new StarterApp(), config);
	}
}
