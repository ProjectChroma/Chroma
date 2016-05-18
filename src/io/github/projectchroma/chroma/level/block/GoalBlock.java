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

public class GoalBlock extends Block{
	public static final String COLOR_NAME = "gold";
	public static final Color COLOR = Color.yellow.darker(0.2F);
	private static Sound winSound;
	public GoalBlock(float x, float y, float width, float height){
		super(x, y, width, height, COLOR);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(winSound == null) winSound = Resources.loadSound("win.aif");
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player){
		Chroma.instance().enterState(level.getID()+1, new LevelExitTransition(true, winSound), new SwipeTransition(SwipeTransition.RIGHT));
	}
}
