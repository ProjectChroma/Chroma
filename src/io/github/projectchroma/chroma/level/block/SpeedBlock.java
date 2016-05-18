package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class SpeedBlock extends Block{
	public static final String COLOR_NAME = "orange";
	public static final Color COLOR = Color.orange.darker(0.3F);
	private static final float SPEED_MULTIPLIER = 1.5F;
	public SpeedBlock(float x, float y, float width, float height){
		super(x, y, width, height, COLOR);
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player) throws SlickException{
		player.vX *= SPEED_MULTIPLIER;
	}
}
