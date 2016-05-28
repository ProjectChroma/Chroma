package io.github.projectchroma.chroma.level;

import static io.github.projectchroma.chroma.util.Direction.*;
import static io.github.projectchroma.chroma.util.MathUtils.sameSign;
import static io.github.projectchroma.chroma.util.RectangleUtils.fromCenter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class Player extends LevelElement{
	/**Acceleration caused by gravity*/
	private static final float gravity = 0.07F;
	/**Velocities during player-caused motion*/
	private static final float VX = 2F, VY = -3.7F;
	/**Sound effects*/
	private static Sound jump, win, death;
	
	/**Hitboxes for collision detection*/
	private Rectangle[] hitboxes = new Rectangle[4];
	/**Stores whether or not the player has collided with an object on each of its sides*/
	private boolean[] colliding = new boolean[4];
	/**Kinematics*/
	public Vector2f v = new Vector2f(), a = new Vector2f();
	/**Color to render the player, regardless of scheme (null uses scheme color)*/
	private Color renderCol = null;
	
	public Player(){
		super(0, 0, 50, 50);
		moveTo(0, 0);
		resetKinematics();
	}
	
	@Override
	public void init(GameContainer container) throws SlickException{
		if(jump == null) jump = Resources.loadSound("jump.aif");
		if(win == null) win = Resources.loadSound("win.aif");
		if(death == null) death = Resources.loadSound("death.aif");
	}
	
	public void update(GameContainer container, LevelState level, int delta) throws SlickException{
		//Initialize velocity and acceleration to defaults
		a.y = gravity;
		if(container.getInput().isKeyDown(Input.KEY_LEFT)) v.x = -VX;
		else if(container.getInput().isKeyDown(Input.KEY_RIGHT)) v.x = VX;
		else v.x = 0;
		if(container.getInput().isKeyPressed(Input.KEY_SPACE) && colliding[DOWN.ordinal()]) v.y = VY;
		
		//Reset collisions
		for(int i=0; i<colliding.length; i++) colliding[i] = false;
		//Update level elements on contact
		Rectangle collisionBounds = RectangleUtils.grow(bounds, 1);
		for(LevelElement element : level.elements()){
			if(element == this || !element.isTangible(level)) continue;
			element.update(container, level, delta);
			if(element instanceof Block && collisionBounds.intersects(element.getBounds()))
				((Block)element).onContact(container, level);
		}
		//Detect collisions
		for(LevelElement element : level.elements()){
			if(element == this || !element.isTangible(level)) continue;
			sizeHitboxes();
			for(Direction d : Direction.values()){
				if(hitboxes[d.ordinal()].intersects(element.getBounds())){
					colliding[d.ordinal()] = true;
					if(d.isHorizontal()){
						if(sameSign(v.x, d.offsetX) || v.x == 0) set(d, element.get(d.opposite()));
						if(sameSign(v.x, d.offsetX)) v.x = 0;
						if(sameSign(a.x, d.offsetX)) a.x = 0;
					}else{
						if(sameSign(v.y, d.offsetY) || v.y == 0) set(d, element.get(d.opposite()));
						if(sameSign(v.y, d.offsetY)) v.y = 0;
						if(sameSign(a.y, d.offsetY)) a.y = 0;
					}
				}
			}
		}
		setLeft(getLeft() + v.x);
		setTop(getTop() + v.y);
		v.add(a);
	}
	private void sizeHitboxes(){
		hitboxes[UP.ordinal()] = fromCenter(getCenterX(), getCenterY() - (getHeight()-v.y)/2, getWidth()-2, -v.y);
		hitboxes[DOWN.ordinal()] = fromCenter(getCenterX(), getCenterY() + (getHeight()+v.y)/2, getWidth()-2, v.y);
		hitboxes[LEFT.ordinal()] = fromCenter(getCenterX() - (getWidth()-v.x)/2, getCenterY(), -v.x, getHeight()-2);
		hitboxes[RIGHT.ordinal()] = fromCenter(getCenterX() + (getWidth()+v.x)/2, getCenterY(), v.x, getHeight()-2);
	}
	
	public void render(GameContainer container, LevelState level, Graphics g) throws SlickException{
		g.setColor(renderCol != null ? renderCol : getColor(level));
		g.fill(bounds);
		
		if(Chroma.DEBUG_MODE){
			g.translate(v.x, v.y);
			Color[] colors = {Color.red, Color.green, Color.cyan, Color.yellow};
			for(int i=0; i<hitboxes.length; i++){
				if(hitboxes[i] == null) continue;
				g.setColor(colliding[i] ? colors[i] : colors[i].darker());
				g.fill(hitboxes[i]);
			}
			g.translate(-v.x, -v.y);
		}
	}
	public void moveTo(float x, float y){
		setLeft(x);
		setTop(y);
	}
	public void resetKinematics(){
		v.x = v.y = a.x = 0;
		a.y = gravity;
	}
	
	public void setRenderColor(Color c){
		renderCol = c;
	}
	public void resetRenderColor(){
		renderCol = null;
	}
}
