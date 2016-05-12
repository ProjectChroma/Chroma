package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Block extends GamePiece {
	private Rectangle rect;
	private Color color;

	public Block(float x, float y, float width, float height, Color color) {
		super(x, y, width, height);
		this.color = color;
		this.rect = new Rectangle(x, y, width, height);
	}

	public void update(GameContainer container, int delta) throws SlickException {

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(color);
		g.fill(rect);
	}
}
