package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

public class WhiteBlock extends Block{
	public static final String COLOR_NAME = "white";
	public static final Color COLOR = Color.white;
	public WhiteBlock(BlockObject block){
		super(block, COLOR);
	}
}
