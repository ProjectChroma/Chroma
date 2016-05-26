package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class InvisaBlockHelp extends Block{
	public static final String COLOR_NAME = "clear2";
	public static final Color COLOR = new Color(0, 0, 0, 0);
	private static final float SPEED_MULTIPLIER = 1.5F;
	public InvisaBlockHelp(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){
		player.vX *= SPEED_MULTIPLIER;
	}
}
