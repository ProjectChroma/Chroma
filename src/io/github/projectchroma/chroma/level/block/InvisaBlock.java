package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;

public class InvisaBlock extends Block{
	public static final String COLOR_NAME = "clear";
	public static final Color COLOR = Color.transparent;
	private Block impl;
	public InvisaBlock(BlockObject block) throws SlickException{
		super(block, COLOR);
		block.color = block.getProperty("implColor", String.class);
		impl = Blocks.createBlock(block);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		impl.init(container);
	}
	@Override
	public void update(GameContainer container, LevelState level, int delta) throws SlickException{
		impl.update(container, level, delta);
	}
	@Override
	public void onContact(GameContainer container, LevelState level, Entity entity) throws SlickException{
		impl.onContact(container, level, entity);
	}
}
