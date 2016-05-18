package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public abstract class Block extends LevelElement{
	public static final float WALL_WIDTH = 15;
	
	protected Block(float x, float y, float width, float height, Color color, Color scheme){
		super(x, y, width, height, color, scheme);
	}
	public void update(GameContainer container, int delta) throws SlickException{
		update(container, (LevelState)Chroma.instance().getCurrentState(), Chroma.instance().player());
	}
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{}
	public void onContact(GameContainer container) throws SlickException{
		onContact(container, (LevelState)Chroma.instance().getCurrentState(), Chroma.instance().player());
	}
	protected void onContact(GameContainer container, LevelState level, Player player) throws SlickException{}
	@Override
	public String toString(){
		return super.toString() + '{' + (color == null ? "null" : (color.r + "," + color.g + "," + color.b)) + '}';
	}
}
