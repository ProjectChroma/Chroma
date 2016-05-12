package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player {
	private float x = 0, y = 400, vX = 0.1F, vY = -0.4F, aX = 0, aY = 0.0007F;
	private Rectangle rect = new Rectangle(0, 0, 50, 50);
	public void update(GameContainer container, int delta) throws SlickException {
		x += vX;
		y += vY;
		vX += aX;
		vY += aY;
		rect.setLocation(x, y);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fill(rect);
	}
}
