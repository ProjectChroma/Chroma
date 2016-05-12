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
		super(000, 400, 50, 50);
		rect = new Rectangle(x, y, width, height);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		boolean landed = false;
		for (GamePiece piece : Chroma.instance().pieces()) {
			if (piece == this)
				continue;
			if(rect.intersects(piece.getBounds())){
				//Check for sides
				if(piece.getX() > x){//Collision on right side
					if(vX > 0) vX = 0;//Stop moving right
					if(aX > 0) aX = 0;//Stop accelerating right
				}
				if(piece.getX() < x){//Collision on left side
					if(vX < 0) vX = 0;//Stop moving left
					if(aX < 0) aX = 0;//Stop accelerating left
				}
				if(piece.getY() < y){//Collision above
					if(vY < 0) vY = 0;//Stop moving up
					if(aY < 0) aY = 0;//Stop accelerating up
				}
				if(piece.getY() > y){//Collision below
					landed = true;
					if(vY > 0) vY = 0;//Stop moving down
					if(aY > 0) aY = 0;//Stop accelerating down
				}
			}
		}
		vX += aX;
		vY += aY;
		if(!landed) aY = gravity;
		rect.setLocation(x += vX, y += vY);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fill(rect);
	}
}
