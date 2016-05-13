package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Block extends GamePiece {
	public static final float WALL_WIDTH = 15;

	public Block(float x, float y, float width, float height, Color color) {
		super(x, y, width, height, color);
	}
	public Block(float x, float y, float width, float height){
		this(x, y, width, height, null);
	}

	public void update(GameContainer container, int delta) throws SlickException {
	}
}
