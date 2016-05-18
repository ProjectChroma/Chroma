package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class BlackBlock extends Block{
	public static final String COLOR_NAME = "black";
	public static final Color COLOR = Color.black;
	public BlackBlock(float x, float y, float width, float height){
		super(x, y, width, height, COLOR);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{}
}
