package io.github.projectchroma.chroma.level.block;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;

public class WhiteBlock extends Block{
	public static final String COLOR_NAME = "white";
	public static final Color COLOR = Color.white;
	public WhiteBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
}
