package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class BarrierBlock extends Block{
	public static final String COLOR_NAME = null;
	public static final Color COLOR = null;
	public BarrierBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player){}
}
