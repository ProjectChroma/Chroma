package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.util.Colors;

public abstract class Block extends LevelElement{
	public static final float WALL_WIDTH = 15;
	
	protected Block(BlockObject block, Color color){
		this(block.x, block.y, block.width, block.height, color, Colors.byName(block.scheme));
	}
	private Block(float x, float y, float width, float height, Color color, Color scheme){
		super(x, y, width, height, color, scheme);
	}
	@Override public void update(GameContainer container, LevelState level, int delta) throws SlickException{}
	public void onContact(GameContainer container, LevelState level, Entity entity) throws SlickException{}
	@Override
	public String toString(){
		return super.toString() + '{' + (color == null ? "null" : (color.r + "," + color.g + "," + color.b)) + '}';
	}
}
