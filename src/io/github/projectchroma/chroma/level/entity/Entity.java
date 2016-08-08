package io.github.projectchroma.chroma.level.entity;

import static io.github.projectchroma.chroma.util.Direction.DOWN;
import static io.github.projectchroma.chroma.util.Direction.LEFT;
import static io.github.projectchroma.chroma.util.Direction.RIGHT;
import static io.github.projectchroma.chroma.util.Direction.UP;
import static io.github.projectchroma.chroma.util.MathUtils.sameSign;
import static io.github.projectchroma.chroma.util.RectangleUtils.fromCenter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class Entity extends LevelElement{
	/**Acceleration caused by gravity*/
	public static final float gravity = 0.07F;
	
	/**Starting coordinates*/
	protected float startX, startY;
	/**Hitboxes for collision detection*/
	protected Rectangle[] hitboxes = new Rectangle[4];
	/**Stores whether or not the player has collided with an object on each of its sides*/
	protected boolean[] colliding = new boolean[4];
	/**Kinematics*/
	public Vector2f v = new Vector2f(), a = new Vector2f();
	/**Steed*/
	protected Steed steed = null;
	
	public Entity(EntityObject entity, float width, float height){
		super(entity.x, entity.y, width, height);
		startX = entity.x;
		startY = entity.y;
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		resetPosition();
		v.x = v.y = a.x = a.y = 0;
		if(steed != null){
			steed.rider = null;
			steed = null;
		}
	}
	@Override
	public void update(GameContainer container, LevelState level, int delta) throws SlickException{
		//Reset collisions
		for(int i=0; i<colliding.length; i++) colliding[i] = false;
		//Update level elements on contact
		Rectangle collisionBounds = RectangleUtils.grow(bounds, 1);
		for(LevelElement element : level.elements()){
			if(element == this || !element.isTangible(level)) continue;
			if(element instanceof Block && collisionBounds.intersects(element.getBounds()))
				((Block)element).onContact(container, level, this);
			else if(element instanceof Entity && collisionBounds.intersects(element.getBounds()))
				((Entity)element).onContact(container, level, this);
		}
		//Detect collisions
		for(LevelElement element : level.elements()){
			if(element == this || !element.isTangible(level) || (!(element instanceof Block))) continue;
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
				if(steed != null){
					this.setCenterX(steed.getCenterX());
					this.setBottom(steed.getCenterY());
					if(steed.hitboxes[d.ordinal()].intersects(element.getBounds())){
						if(d.isHorizontal()){
							if(sameSign(v.x, d.offsetX)) v.x = 0;
							if(sameSign(a.x, d.offsetX)) a.x = 0;
						}else{
							if(sameSign(v.y, d.offsetY)) v.y = 0;
							if(sameSign(a.y, d.offsetY)) a.y = 0;
						}
					}
				}
			}
		}
		setLeft(getLeft() + v.x);
		setTop(getTop() + v.y);
		v.add(a);
	}
	public void setKinematics(GameContainer container, LevelState level) throws SlickException{
		a.y = gravity;
	}
	protected void sizeHitboxes(){
		hitboxes[UP.ordinal()] = fromCenter(getCenterX(), getCenterY() - (getHeight()-v.y)/2, getWidth()-2, -v.y);
		hitboxes[DOWN.ordinal()] = fromCenter(getCenterX(), getCenterY() + (getHeight()+v.y)/2, getWidth()-2, v.y);
		hitboxes[LEFT.ordinal()] = fromCenter(getCenterX() - (getWidth()-v.x)/2, getCenterY(), -v.x, getHeight()-2);
		hitboxes[RIGHT.ordinal()] = fromCenter(getCenterX() + (getWidth()+v.x)/2, getCenterY(), v.x, getHeight()-2);
	}
	public void onContact(GameContainer container, LevelState level, Entity entity) throws SlickException{
		if(entity instanceof Steed && steed == null) mount((Steed)entity);
	}
	public void resetPosition(){
		moveTo(startX, startY);
	}
	protected void moveTo(float x, float y){
		setLeft(x);
		setTop(y);
	}
	public void mount(Steed steed){
		if(!canRide(steed) || !steed.canBeRidden(this)) return;
		if(this.steed != null) dismount();//Backup in case a subclass overrides canRide to ignore current steed
		this.steed = steed;
		this.steed.rider = this;
		this.setCenterX(steed.getCenterX());
		this.setBottom(steed.getCenterY());
	}
	public boolean canRide(Steed steed){return this.steed == null;}
	public void dismount(){
		if(steed == null) return;
		setBottom(steed.getTop());
		steed.onDismount();
		steed.rider = null;
		steed = null;
	}
	public Steed getSteed(){return steed;}
}
