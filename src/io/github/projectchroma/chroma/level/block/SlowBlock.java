package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class SlowBlock extends Block{
	public static final String COLOR_NAME = "blue";
	public static final Color COLOR = Color.blue;
	private static final float SPEED_MULTIPLIER = 0.6F;
	public SlowBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){
		player.vX *= SPEED_MULTIPLIER;
	}
}
