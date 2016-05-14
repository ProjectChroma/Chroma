package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class Block extends LevelElement{
	public static final float WALL_WIDTH = 15;
	
	public Block(float x, float y, float width, float height){
		this(x, y, width, height, null);
	}
	public Block(float x, float y, float width, float height, Color color){
		super(x, y, width, height, color);
	}
	
	public void update(GameContainer container, int delta) throws SlickException{}
	@Override
	public String toString(){
		return super.toString() + '{' + (color == null ? "null" : (color.r + "," + color.g + "," + color.b)) + '}';
	}
}
