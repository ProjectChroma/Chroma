package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.LevelExitTransition;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class InvisaBlock extends Block{
	public static final String COLOR_NAME = "clear";
	public static final Color COLOR = Color.transparent;
	private static Sound deathSound;
	public InvisaBlock(float x, float y, float width, float height, Color scheme){
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
