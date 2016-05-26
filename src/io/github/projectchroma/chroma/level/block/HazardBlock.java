package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class HazardBlock extends Block{
	public static final String COLOR_NAME = "red";
	public static final Color COLOR = Color.red;
//	private static Sound deathSound;
	public HazardBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(Window window, Chroma chroma){
//		if(deathSound == null) deathSound = Resources.loadSound("death.aif");
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){
//		chroma.enterState(level.getID(), new LevelExitTransition(false, deathSound), new SwipeTransition(SwipeTransition.RIGHT));
		chroma.enterState(level.getID());
	}
}
