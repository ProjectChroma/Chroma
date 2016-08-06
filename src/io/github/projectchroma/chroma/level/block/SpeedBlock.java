package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;

public class SpeedBlock extends Block{
	public static final String COLOR_NAME = "orange";
	public static final Color COLOR = Color.orange.darker(0.3F);
	private float speed = 1.5F;
	public SpeedBlock(BlockObject block){
		super(block, COLOR);
		if(block.hasProperty("speed")) speed = block.getFloatProperty("speed");
	}
	@Override
	public void onContact(GameContainer container, LevelState level, Entity entity) throws SlickException{
		entity.v.x *= speed;
	}
}
