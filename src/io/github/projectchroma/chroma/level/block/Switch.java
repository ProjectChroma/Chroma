package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class Switch extends Block{
	public static final String COLOR_NAME = "green";
	public static final Color COLOR = Color.green;
//	private static Sound switchSound;
	public Switch(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(Window window, Chroma chroma){
//		if(switchSound == null) switchSound = Resources.getSound("switch.aif");
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){
		level.cycleScheme();
	}
}
