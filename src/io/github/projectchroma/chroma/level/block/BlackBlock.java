package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

public class BlackBlock extends Block{
	public static final String COLOR_NAME = "black";
	public static final Color COLOR = Color.black;
	public BlackBlock(BlockObject block){
		super(block, COLOR);
	}
}
