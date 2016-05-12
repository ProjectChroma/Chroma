package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player extends GamePiece {
	private static final float gravity = 0.0007F;
	private float vX = 0.1F, vY = -0.4F, aX = 0, aY = gravity;
	private Rectangle rect;

	public Player() {
		super(0, 400, 50, 50);
		rect = new Rectangle(x, y, width, height);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		vX += aX;
		vY += aY;

		float newX = x + vX, newY = y + vY;
		GamePiece obstacle;
		
		obstacle = obstacleDown(newX, newY);
		x = newX;
		if (obstacle == null) {
			aY = gravity;
			y = newY;
		} else {
			y = obstacle.getY() - height;
			vY = 0;
			aY = 0;
		}

		rect.setLocation(x, y);
	}

	private GamePiece obstacleDown(float x, float y) {
		float myLeft = x, myRight = x + width, myBottom = y + height;
		for (GamePiece piece : Chroma.instance().pieces()) {
			if (piece == this)
				continue;
			float otherLeft = piece.getX(), otherRight = otherLeft + piece.getWidth(), otherTop = piece.getY(), otherBottom = otherTop + piece.getHeight();
			if(myRight > otherLeft && myLeft < otherRight//Above obstacle
					&& myBottom > otherTop//My bottom is below their top
					&& myBottom < otherBottom//My bottom is above their bottom
					) return piece;
		}
		return null;
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fill(rect);
	}
}
