package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	/**
	 * The batch used to update or render the sprite.
	 */
	private SpriteBatch spriteBatch;

	/**
	 * The sprite itself.
	 */
	private Sprite sprite;
	/**
	 * The dimensions of the (square) sprite.
	 */
	private float spriteSize;
	/**
	 * The sprite's texture.
	 */
	private Texture spriteTexture;
	/**
	 * The x-coordinate of the bottom-left corner of the sprite.
	 */
	private float spriteX;
	/**
	 * The y-coordinate of the bottom-right corner of the sprite.
	 */
	private float spriteY;

	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		spriteTexture = new Texture("images/blue_sun_1.jpg");
		sprite = new Sprite(spriteTexture);

		// Disable continuous rendering.
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();

		return;
	}

	@Override
	public void render() {

		// Makes the background blue.
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the sprite.
		spriteBatch.begin();
		sprite.setBounds(spriteX, spriteY, spriteSize, spriteSize);
		sprite.draw(spriteBatch);
		spriteBatch.end();

		return;
	}

	@Override
	public void resize(int width, int height) {

		// Size the sprite to 80% of the smaller of window width or height.
		final int windowWidth = Gdx.graphics.getWidth();
		final int windowHeight = Gdx.graphics.getHeight();
		spriteSize = 0.8f * (windowWidth < windowHeight ? windowWidth : windowHeight);
		// Get the location of the bottom-left corner of the sprite.
		spriteX = (windowWidth - spriteSize) * 0.5f;
		spriteY = (windowHeight - spriteSize) * 0.5f;

		// Update the sprite's "camera" to adjust for the current screen size.
		spriteBatch.getProjectionMatrix().setToOrtho2D(0f, 0f, windowWidth, windowHeight);

		// We must request a render op if the app was resized.
		Gdx.graphics.requestRendering();

		return;
	}
}
