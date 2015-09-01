package com.mygdx.game.board;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HexTile {

	private float width;
	private float height;
	private float x;
	private float y = 200;

	public HexTile() {

	}

	public void setWidth(float width) {
		this.width = width;
		this.height = 2f * width / (float) Math.tan(Math.PI / 3.0);
	}

	public void setPosition(float x) {
		this.x = x;
	}

	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);

		float x2 = x + width * 0.5f;
		float x3 = x + width;
		float y2 = y + height * 0.25f;
		float y3 = y + height * 0.75f;
		float y4 = y + height;
		
		shapeRenderer.triangle(x, y2, x2, y4, x2, y);
		shapeRenderer.triangle(x, y2, x, y3, x2, y4);
		shapeRenderer.triangle(x3, y3, x2, y, x2, y4);
		shapeRenderer.triangle(x3, y3, x3, y2, x2, y);
		
		
		shapeRenderer.end();
	}

}
