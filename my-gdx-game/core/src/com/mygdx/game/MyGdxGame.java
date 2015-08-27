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


		int windowWidth = Gdx.graphics.getWidth();
		int windowHeight = Gdx.graphics.getHeight();
		
		// Size of the sprite should be 80% of the screen width.
		float spriteWidth = windowWidth * 0.8f;
		float spriteHeight = windowHeight * 0.8f;
		// Bottom-left corner of the sprite... Sprite should be centered.
		float x = windowWidth * 0.1f;
		float y = windowHeight * 0.1f;

		// Update the sprite's "camera" to adjust for the current screen size.
		batch.getProjectionMatrix().setToOrtho2D(0f, 0f, windowWidth,
				windowHeight);

		// Update the sprite.
		batch.begin();
		sprite.setBounds(x, y, spriteWidth, spriteHeight);
		sprite.draw(batch);
		batch.end();
		
		return;
	}
}
