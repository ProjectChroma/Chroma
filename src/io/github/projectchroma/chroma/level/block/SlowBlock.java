package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;

public class SlowBlock extends Block{
	public static final String COLOR_NAME = "blue";
	public static final Color COLOR = Color.blue;
	private float speed = 0.6F;
	public SlowBlock(BlockObject block){
		super(block, COLOR);
		if(block.hasProperty("speed")) speed = block.getFloatProperty("speed");
	}
	@Override
	public void onContact(GameContainer container, LevelState level, Entity entity) throws SlickException{
		entity.v.x *= speed;
	}
}
