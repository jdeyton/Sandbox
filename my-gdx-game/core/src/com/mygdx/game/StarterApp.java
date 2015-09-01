package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.board.Board;

public class StarterApp extends CompositeApp {

	@Override
	public void create() {
		super.create();
		
		attach(new Board());
	}
	
	@Override
	public void render() {
		// Makes the background black.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
}
