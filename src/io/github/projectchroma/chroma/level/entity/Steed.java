package io.github.projectchroma.chroma.level.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelState;

public abstract class Steed extends Entity{
	protected int maxCooldown = 200;
	protected Entity rider = null;
	protected int cooldown = 0;
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
		if(cooldown >= 0) --cooldown;
	}
	protected abstract float getJumpVelocity();
	protected abstract float getMovementVelocity();
	public Entity getRider(){return rider;}
	public boolean canBeRidden(Entity rider){return cooldown == -1;}
	public void onMount(){}
	public void onDismount(){
		cooldown = maxCooldown;
		v.x -= rider.v.x;
		v.y -= rider.v.y;
		a.x -= rider.a.x;
		a.y -= rider.a.y;
	}
}
