package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class InvisaBlockHelp extends Block{
	public static final String COLOR_NAME = "clear2";
	public static final Color COLOR = Color.transparent;
	private static final float SPEED_MULTIPLIER = 1.5F;
	public InvisaBlockHelp(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player) throws SlickException{
		player.vX *= SPEED_MULTIPLIER;
	}
}
