package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

public class BarrierBlock extends Block{
	public static final String COLOR_NAME = null;
	public static final Color COLOR = null;
	public BarrierBlock(BlockObject block){
		super(block, COLOR);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player){}
}
