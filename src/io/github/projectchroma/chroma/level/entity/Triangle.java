package io.github.projectchroma.chroma.level.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelState;

public class Triangle extends Steed{
	public static final String TYPE = "triangle";
	private Polygon triangle = new Polygon();
	public Triangle(EntityObject entity){
		super(entity, 50, 50);
		triangle.addPoint(0, getHeight());
		triangle.addPoint(getWidth(), getHeight());
		triangle.addPoint(getWidth()/2, 0);
	}
	@Override protected float getMovementVelocity(){return Player.V_MOVE;}
	@Override protected float getJumpVelocity(){return Player.V_JUMP * 1.4F;}
	@Override
	public void render(GameContainer container, LevelState level, Graphics g) throws SlickException{
		if(!doRender(level)) return;
		g.setColor(getColor(level));
		g.translate(getLeft(), getTop());
		g.fill(triangle);
		g.translate(-getLeft(), -getTop());
	}
}
