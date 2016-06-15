package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Player;

public class FallingBlock extends Block{
	public static final String COLOR_NAME = "gray";
	public static final Color COLOR = Color.gray;
	private static final float SPEED = 2;
	private boolean moving;
	private float startY, speed = SPEED;
	private Rectangle raycast = null;
	public FallingBlock(BlockObject block) {
		super(block, COLOR);
		startY = block.y;
		if(block.hasProperty("speed")) speed = block.getFloatProperty("speed");
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		moving = false;
		setTop(startY);
	}
	@Override
	public void update(GameContainer container, LevelState level, int delta) throws SlickException{
		Player player = level.player();
		if(!moving && player.getRight() > this.getLeft() && player.getLeft() < this.getRight() && player.getTop() > this.getBottom()){
			raycast = new Rectangle(this.getLeft(), this.getBottom(), this.getWidth(), player.getTop() - this.getBottom());
			boolean clearFall = true;
			for(LevelElement element : level.elements()){
				if(element != this && element instanceof Block && element.isTangible(level) && raycast.intersects(element.getBounds())){
					clearFall = false;
					break;
				}
			}
			moving = clearFall;
		}else raycast = null;
		if(moving){
			raycast = new Rectangle(this.getLeft()+1, this.getBottom(), this.getWidth()-2, SPEED);
			boolean clearFall = true;
			for(LevelElement element : level.elements()){
				if(element != this && element instanceof Block && element.isTangible(level) && raycast.intersects(element.getBounds())){
					clearFall = false;
					break;
				}
			}
			if(clearFall) setTop(getTop() + speed);
			else moving = false;
		}
	}
	@Override
	public void render(GameContainer container, LevelState level, Graphics g) throws SlickException{
		super.render(container, level, g);
		if(Chroma.isDebugMode() && raycast != null){
			g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.5F));
			g.fill(raycast);
		}
	}
}
