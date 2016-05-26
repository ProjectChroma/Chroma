package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public abstract class Block extends LevelElement{
	public static final int WALL_WIDTH = 15;
	
	protected Block(float x, float y, float width, float height, Color color, Color scheme){
		super(x, y, width, height, color, scheme);
	}
	public void update(Window window, Chroma chroma, LevelState level, Player player){}
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){}
	@Override
	public String toString(){
		return super.toString() + '{' + (color == null ? "null" : color) + '}';
	}
}
