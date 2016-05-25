package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class Switch extends Block{
	public static final String COLOR_NAME = "green";
	public static final Color COLOR = Color.green;
	private static Sound switchSound;
	public Switch(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(switchSound == null) switchSound = Resources.loadSound("switch.aif");
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player){
		level.cycleScheme();
	}
}
