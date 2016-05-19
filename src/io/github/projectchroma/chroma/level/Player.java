package io.github.projectchroma.chroma.level;

import static io.github.projectchroma.chroma.util.RectangleUtils.fromDimensions;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.resource.Resources;

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
	private Rectangle[] collisionBoxes = new Rectangle[4];
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
	private static Audio jump;
	
	public Player(){
		super(0, 0, 50, 50);
		moveTo(0, 0);
		resetKinematics();
	}
	
	@Override
	public void init(GameContainer container) throws SlickException{
		if(jump == null) jump = Resources.loadSound("jump.aif");
	}
	
	public void update(GameContainer container, int delta) throws SlickException{
		LevelState state = (LevelState)Chroma.instance().getCurrentState();
		float oldX = bounds.getX(), oldY = bounds.getY();
		
		landed = false;
		
		for(LevelElement element : state.elements()){
			if(element == this || !element.isTangible()) continue;
			if(collisionBoxes[0].intersects(element.getBounds())){//Collision above
				if(vY <= 0) setTop(element.getBottom());
				if(vY < 0) vY = 0;//Stop moving up
				if(aY < 0) aY = 0;//Stop accelerating up
			}
			if(collisionBoxes[1].intersects(element.getBounds())){//Collision on right side
				if(vX >= 0) setRight(element.getLeft());
				if(vX > 0) vX = 0;//Stop moving right
				if(aX > 0) aX = 0;//Stop accelerating right
			}
			if(collisionBoxes[2].intersects(element.getBounds())){//Collision below
				landed = true;
				if(vY >= 0) setBottom(element.getTop());
				if(vY > 0) vY = 0;//Stop moving down
				if(aY > 0) aY = 0;//Stop accelerating down
			}
			if(collisionBoxes[3].intersects(element.getBounds())){//Collision on left side
				if(vX <= 0) setLeft(element.getRight());
				if(vX < 0) vX = 0;//Stop moving left
				if(aX < 0) aX = 0;//Stop accelerating left
			}
			if(element instanceof Block && bounds.intersects(element.bounds)){
				((Block)element).onContact(container);
			}
		}
		
		translate(vX, vY);
		
		float dx = bounds.getX() - oldX, dy = bounds.getY() - oldY;
		for(Rectangle collisionBox : collisionBoxes)
			collisionBox.setLocation(collisionBox.getX() + dx, collisionBox.getY() + dy);
		
		if(container.getInput().isKeyDown(Input.KEY_LEFT))
			vX = -VX;
		else if(container.getInput().isKeyDown(Input.KEY_RIGHT))
			vX = VX;
		else
			vX = 0;
		
		if(container.getInput().isKeyPressed(Input.KEY_SPACE) && landed){//keyPressed, not keyDown; only apply the velocity once
			vY = VY;
			jump.playAsSoundEffect(0, 0, false);
		}
		
		vX += aX;
		vY += aY;
		if(!landed) aY = gravity;
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(renderCol != null ? renderCol : getColor());
		g.fill(bounds);
		
		if(Chroma.DEBUG_MODE){
			if(landed){
				g.setColor(Color.cyan);
				g.fillRect(getLeft(), getBottom() - 5, getWidth(), 5);
			}
			
			g.setColor(Color.red);
			g.fill(collisionBoxes[0]);
			
			g.setColor(Color.green);
			g.fill(collisionBoxes[1]);
			
			g.setColor(Color.blue);
			g.fill(collisionBoxes[2]);
			
			g.setColor(Color.yellow);
			g.fill(collisionBoxes[3]);
			
			g.setColor(Chroma.instance().background());
			g.drawString(getLeft() + "", getLeft(), getTop());
			g.drawString(getTop() + "", getLeft(), getTop() + g.getFont().getLineHeight());
		}
	}
	public void moveTo(float x, float y){
		setLeft(x);
		setTop(y);
		
		float w = getWidth() - 2 * HITBOX, h = getHeight() - 2 * HITBOX;
		collisionBoxes[0] = fromDimensions(getLeft() + HITBOX, getTop() - 1, w, HITBOX);//Top
		collisionBoxes[1] = fromDimensions(getRight() + 1, getTop() + HITBOX, -HITBOX, h);//Right
		collisionBoxes[2] = fromDimensions(getLeft() + HITBOX, getBottom() + 1, w, -HITBOX);//Bottom
		collisionBoxes[3] = fromDimensions(getLeft() - 1, getTop() + HITBOX, HITBOX, h);//Left
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
