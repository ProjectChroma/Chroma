package io.github.projectchroma.chroma.level.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelState;

public class Llama extends Steed{
	public static String TYPE = "llama";
	public Llama(EntityObject entity){
		super(entity, 50, 50);
	}
	@Override protected float getMovementVelocity(){return Player.V_MOVE * 1.1F;}
	@Override protected float getJumpVelocity(){return Player.V_JUMP * 1.25F;}
	@Override
	public void render(GameContainer container, LevelState level, Graphics g) throws SlickException{
		if(!doRender(level)) return;
		g.setColor(getColor(level));
		g.fillOval(getLeft(), getTop(), getWidth(), getHeight());
	}
}
