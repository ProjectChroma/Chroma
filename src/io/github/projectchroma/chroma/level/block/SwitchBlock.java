package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;

public class SwitchBlock extends Block{
	public static final String COLOR_NAME = "pink";
	public static final Color COLOR = Color.pink;
	private boolean contacting = false, prevContacting = false;
	public SwitchBlock(BlockObject block){
		super(block, COLOR);
	}
	@Override
	public void update(GameContainer container, LevelState level, int delta){
		if(contacting != prevContacting){
			level.cycleScheme();
		}
		prevContacting = contacting;
		contacting = false;
	}
	@Override
	public void onContact(GameContainer container, LevelState level, Entity entity){
		contacting = true;
	}
}
