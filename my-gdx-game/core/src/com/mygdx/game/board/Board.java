package com.mygdx.game.board;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.CompositeApp;

public class Board extends CompositeApp {

	private int width = 3;
	private int height = 3;

	private HexTile tile;
	private ShapeRenderer shapeRenderer;

	private List<HexTile> tiles;
	
	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		
		tiles = new ArrayList<HexTile>(width);
		for (int i = 0; i < width; i++) {
			tiles.add(new HexTile());
		}
	}

	@Override
	public void resize(int width, int height) {

		float widthGrid;
		float heightGrid;
		float widthTriangle;
		float heightTriangle;

		widthGrid = (float) width;
		// Determine the height of the grid.
		widthTriangle = widthGrid / (2 * this.width);
		heightTriangle = 2f * widthTriangle / (float) Math.tan(Math.PI / 3);
		heightGrid = (this.height - 1) * 3 / 2 * heightTriangle + 1 / 2 * heightTriangle;
		
		for (int i = 0; i < this.width; i++) {
			HexTile tile = tiles.get(i);
			tile.setWidth(widthTriangle * 2f);
			tile.setPosition(i * widthTriangle * 2f);
		}
		
		super.resize(width, height);
	}

	@Override
	public void render() {
		for (HexTile tile : tiles) {
			tile.draw(shapeRenderer);
		}
	}

	@Override
	public void dispose() {
		tile = null;
		shapeRenderer.dispose();
	}

}
