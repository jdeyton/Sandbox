package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Sprite sprite;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("images/blue_sun_1.jpg");
		sprite = new Sprite(img);
	}

	@Override
	public void render() {
		// Makes the background blue.
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		resizeSprite();

		return;
	}
	
	private void resizeSprite() {
		// Size the sprite to 80% of the smaller of window width or height.
		final int windowWidth = Gdx.graphics.getWidth();
		final int windowHeight = Gdx.graphics.getHeight();
		float spriteSize = windowWidth < windowHeight ? windowWidth : windowHeight;
		spriteSize *= 0.8;
		// Get the location of the bottom-left corner of the sprite.
		final float x = (windowWidth - spriteSize) * 0.5f;
		final float y = (windowHeight - spriteSize) * 0.5f;

		// Update the sprite's "camera" to adjust for the current screen size.
		batch.getProjectionMatrix().setToOrtho2D(0f, 0f, windowWidth, windowHeight);

		// Update the sprite.
		batch.begin();
		sprite.setBounds(x, y, spriteSize, spriteSize);
		sprite.draw(batch);
		batch.end();
		
		return;
	}
}
