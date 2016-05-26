package io.github.projectchroma.chroma.level;

import static io.github.projectchroma.chroma.util.RectangleUtils.fromDimensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.util.BoundingBox;
import io.github.projectchroma.chroma.util.GraphicsUtils;

public class Player extends LevelElement{
	/**
	 * Size of box used for collision detection. Collisions will be detected if
	 * the edge of the colliding object is within this far of the edge of the
	 * player.
	 */
	private static final float HITBOX = 5;
	/**
	 * Acceleration caused by gravity
	 */
	private static final float gravity = 0.07F;
	/**
	 * Velocities during player-caused motion
	 */
	private static final float VX = 2F, VY = -3.5F;
	/**
	 * Kinematics values for player
	 */
	public float vX = 0, vY = 0, aX = 0, aY = gravity;
	/**
	 * Rectangles used to detect collisions. Each item of this array represents
	 * an edge of the player, excluding the corners.
	 */
	private BoundingBox[] collisionBoxes = new BoundingBox[4];
	/**
	 * Whether the player has landed, and can jump again.
	 */
	private boolean landed = false;
	/**
	 * Color to render the player, regardless of scheme (null uses scheme color)
	 */
	private Color renderCol = null;
	/**
	 * Sound effects
	 */
//	private static Sound jump, win, death;
	
	public Player(){
		super(0, 0, 50, 50);
		moveTo(0, 0);
		resetKinematics();
	}
	
	@Override
	public void init(Window window, Chroma chroma){
//		if(jump == null) jump = Resources.loadSound("jump.aif");
//		if(win == null) win = Resources.loadSound("win.aif");
//		if(death == null) death = Resources.loadSound("death.aif");
	}
	
	@Override
	public void update(Window window, Chroma chroma, LevelState level){
		float oldX = bounds.getX(), oldY = bounds.getY();
		
		landed = false;
		
		for(LevelElement element : level.elements()){
			if(element == this || !element.isTangible(level)) continue;
			if(collisionBoxes[0].intersects(element.getBounds())){//Collision above
				if(vY <= 0) bounds.setTop(element.getBounds().getBottom());
				if(vY < 0) vY = 0;//Stop moving up
				if(aY < 0) aY = 0;//Stop accelerating up
			}
			if(collisionBoxes[1].intersects(element.getBounds())){//Collision on right side
				if(vX >= 0) bounds.setRight(element.getBounds().getLeft());
				if(vX > 0) vX = 0;//Stop moving right
				if(aX > 0) aX = 0;//Stop accelerating right
			}
			if(collisionBoxes[2].intersects(element.getBounds())){//Collision below
				landed = true;
				if(vY >= 0) bounds.setBottom(element.getBounds().getTop());
				if(vY > 0) vY = 0;//Stop moving down
				if(aY > 0) aY = 0;//Stop accelerating down
			}
			if(collisionBoxes[3].intersects(element.getBounds())){//Collision on left side
				if(vX <= 0) bounds.setLeft(element.getBounds().getRight());
				if(vX < 0) vX = 0;//Stop moving left
				if(aX < 0) aX = 0;//Stop accelerating left
			}
			if(element instanceof Block && bounds.intersects(element.bounds)){
				((Block)element).onContact(window, chroma, level, this);
			}
		}
		
		bounds.translate(vX, vY);
		
		float dx = bounds.getX() - oldX, dy = bounds.getY() - oldY;
		for(BoundingBox collisionBox : collisionBoxes)
			collisionBox.setLocation(collisionBox.getX() + dx, collisionBox.getY() + dy);
		
		if(window.getInput().isKeyDown(KeyEvent.VK_LEFT))
			vX = -VX;
		else if(window.getInput().isKeyDown(KeyEvent.VK_RIGHT))
			vX = VX;
		else
			vX = 0;
		
		if(window.getInput().isKeyPressed(KeyEvent.VK_SPACE) && landed){//keyPressed, not keyDown; only apply the velocity once
			vY = VY;
//			jump.play();
		}
		
		vX += aX;
		vY += aY;
		if(!landed) aY = gravity;
	}
	
	public void render(Window window, Chroma chroma, LevelState level, Graphics g){
		g.setColor(renderCol != null ? renderCol : getColor(level));
		GraphicsUtils.fill(g, bounds);
	}
	public void moveTo(float x, float y){
		bounds.setLeft(x);
		bounds.setTop(y);
		
		float w = bounds.getWidth() - 2 * HITBOX, h = bounds.getHeight() - 2 * HITBOX;
		collisionBoxes[0] = fromDimensions(bounds.getLeft() + HITBOX, bounds.getTop() - 1, w, HITBOX);//Top
		collisionBoxes[1] = fromDimensions(bounds.getRight() + 1, bounds.getTop() + HITBOX, -HITBOX, h);//Right
		collisionBoxes[2] = fromDimensions(bounds.getLeft() + HITBOX, bounds.getBottom() + 1, w, -HITBOX);//Bottom
		collisionBoxes[3] = fromDimensions(bounds.getLeft() - 1, bounds.getTop() + HITBOX, HITBOX, h);//Left
	}
	
	public void resetKinematics(){
		vX = 0;
		vY = 0;
		aX = 0;
		aY = gravity;
	}
	
	public void setRenderColor(Color c){
		renderCol = c;
	}
	public void resetRenderColor(){
		renderCol = null;
	}
}
