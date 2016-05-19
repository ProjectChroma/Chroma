package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.LevelExitTransition;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.resource.Resources;

public class HazardBlock extends Block{
	public static final String COLOR_NAME = "red";
	public static final Color COLOR = Color.red;
	private static Audio deathSound;
	public HazardBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(deathSound == null) deathSound = Resources.loadSound("death.aif");
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player){
		Chroma.instance().enterState(level.getID(), new LevelExitTransition(false, deathSound), new SwipeTransition(SwipeTransition.RIGHT));
	}
}
