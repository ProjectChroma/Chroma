package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class BreakableBlock extends Block{
	public static final String COLOR_NAME = "lightblue";
	public static final Color COLOR = new Color(0.7F, 0.7F, 1);
	private boolean broken;
	private float minSpeed = 7;
	public BreakableBlock(BlockObject block){
		super(block, COLOR);
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		broken = false;
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		broken = false;//Reset on exit as well, for level select view
	}
	@Override
	public void update(GameContainer container, LevelState level, int delta) throws SlickException{
		for(LevelElement element : level.elements()){
			if(!(element instanceof Entity)) continue;
			Entity entity = (Entity)element;
			if(entity.v.length() > minSpeed){
				Rectangle newBounds = RectangleUtils.clone(entity.getBounds());
				newBounds.setX(newBounds.getX() + entity.v.x);
				newBounds.setY(newBounds.getY() + entity.v.y);
				if(newBounds.intersects(bounds)) broken = true;
			}
		}
	}
	@Override
	public boolean doRender(LevelState level){
		return super.doRender(level) && !broken;
	}
}
