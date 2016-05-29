package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

public class WhiteBlock extends Block{
	public static final String COLOR_NAME = "white";
	public static final Color COLOR = Color.white;
	public WhiteBlock(BlockObject block){
		super(block, COLOR);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{}
}
