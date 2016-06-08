package io.github.projectchroma.chroma.level.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelState;

public abstract class Steed extends Entity{
	protected Entity rider = null;
	public Steed(EntityObject entity, float width, float height){
		super(entity, width, height);
	}
	@Override
	public void setKinematics(GameContainer container, LevelState level) throws SlickException{
		super.setKinematics(container, level);
		if(rider != null){
			v.x = rider.v.x;
			v.y = rider.v.y;
			a.x = rider.a.x;
			a.y = rider.a.y;
		}
	}
	protected abstract float getJumpVelocity();
	protected abstract float getMovementVelocity();
	public Entity getRider(){return rider;}
}
