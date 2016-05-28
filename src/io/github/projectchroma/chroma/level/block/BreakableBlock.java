package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class BreakableBlock extends Block{
	public static final String COLOR_NAME = "lightblue";
	public static final Color COLOR = new Color(0.7F, 0.7F, 1);
	private boolean broken;
	public BreakableBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
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
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(Math.abs(player.v.y) > 7.5){
			Rectangle newBounds = RectangleUtils.clone(player.getBounds());
			newBounds.setX(newBounds.getX() + player.v.x);
			newBounds.setY(newBounds.getY() + player.v.y);
			if(newBounds.intersects(bounds)){
				broken = true;
			}
		}
	}
	@Override
	public boolean doRender(LevelState level){
		return super.doRender(level) && !broken;
	}
}
