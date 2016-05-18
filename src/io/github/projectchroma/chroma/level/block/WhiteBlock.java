package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class WhiteBlock extends Block{
	public static final String COLOR_NAME = "white";
	public static final Color COLOR = Color.white;
	public WhiteBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{}
}
