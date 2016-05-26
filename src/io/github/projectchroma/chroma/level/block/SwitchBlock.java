package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class SwitchBlock extends Block{
	public static final String COLOR_NAME = "pink";
	public static final Color COLOR = Color.pink;
	private static Sound switchSound;
	private boolean contacting = false, prevContacting = false;
	public SwitchBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(switchSound == null) switchSound = Resources.loadSound("switch.aif");
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player){
		if(contacting != prevContacting){
			level.cycleScheme();
		}
		prevContacting = contacting;
		contacting = false;
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player){
		contacting = true;
	}
}