package io.github.projectchroma.chroma;

import static io.github.projectchroma.chroma.util.RectangleUtils.fromDimensions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player extends GamePiece{
	/**
	 * Size of box used for collision detection. Collisions will be detected if
	 * the edge of the colliding object is within this far of the edge of the
	 * player.
	 */
	private static final float HITBOX = 5;
	/**
	 * Acceleration caused by gravity
	 */
	private static final float gravity = 0.05F;
	private float vX = 1.2F, vY = -5F, aX = 0, aY = gravity;
	/**
	 * Rectangles used to detect collisions. Each item of this array represents
	 * an edge of the player, excluding the corners.
	 */
	private Rectangle[] collisionBoxes = new Rectangle[4];
	
	public Player(){
		super(50, 400, 50, 50);
		
		float w = getWidth() - 2 * HITBOX, h = getHeight() - 2 * HITBOX;
		collisionBoxes[0] = fromDimensions(getLeft() + HITBOX, getTop(), w, HITBOX);//Top
		collisionBoxes[1] = fromDimensions(getRight(), getTop() + HITBOX, - HITBOX, h);//Right
		collisionBoxes[2] = fromDimensions(getLeft() + HITBOX, getBottom(), w, - HITBOX);//Bottom
		collisionBoxes[3] = fromDimensions(getLeft(), getTop() + HITBOX, HITBOX, h);//Left
	}
	
	public void update(GameContainer container, int delta) throws SlickException{
		float oldX = bounds.getX(), oldY = bounds.getY();
		boolean landed = false;
		translate(vX, vY);
		
		for(GamePiece piece : Chroma.instance().pieces()){
			if(piece == this) continue;
			if(collisionBoxes[0].intersects(piece.bounds) && vY < 0){//Collision above
				setTop(piece.getBottom());
				if(vY < 0) vY = 0;//Stop moving up
				if(aY < 0) aY = 0;//Stop accelerating up
			}
			if(collisionBoxes[1].intersects(piece.bounds) && vX > 0){//Collision on right side
				setRight(piece.getLeft());
				if(vX > 0) vX = 0;//Stop moving right
				if(aX > 0) aX = 0;//Stop accelerating right
			}
			if(collisionBoxes[2].intersects(piece.bounds) && vY > 0){//Collision below
				landed = true;
				setBottom(piece.getTop());
				if(vY > 0) vY = 0;//Stop moving down
				if(aY > 0) aY = 0;//Stop accelerating down
			}
			if(collisionBoxes[3].intersects(piece.bounds) && vX < 0){//Collision on left side
				setLeft(piece.getRight());
				if(vX < 0) vX = 0;//Stop moving left
				if(aX < 0) aX = 0;//Stop accelerating left
			}
		}
		
		float dx = bounds.getX() - oldX, dy = bounds.getY() - oldY;
		
		for(Rectangle collisionBox : collisionBoxes)
			collisionBox.setLocation(collisionBox.getX() + dx, collisionBox.getY() + dy);
		
		vX += aX;
		vY += aY;
		if( ! landed) aY = gravity;
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.fill(bounds);
		
		if(Chroma.DEBUG_MODE){
			g.setColor(Color.red);
			g.fill(collisionBoxes[0]);

			g.setColor(Color.green);
			g.fill(collisionBoxes[1]);
			
			g.setColor(Color.blue);
			g.fill(collisionBoxes[2]);
			
			g.setColor(Color.yellow);
			g.fill(collisionBoxes[3]);
		}
	}
}
