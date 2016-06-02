package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class InvisaBlock extends Block{
	public static final String COLOR_NAME = "clear";
	public static final Color COLOR = Color.transparent;
	private static Sound deathSound;
	private Block impl;
	public InvisaBlock(BlockObject block) throws SlickException{
		super(block, COLOR);
		block.color = block.getProperty("implColor", String.class);
		impl = Blocks.createBlock(block);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(deathSound == null) deathSound = Resources.loadSound("death.aif");
		impl.init(container);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		impl.update(container, level, player);
	}
	@Override
	protected void onContact(GameContainer container, LevelState level, Player player) throws SlickException{
		impl.onContact(container, level, player);
	}
}
