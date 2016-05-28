package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class PullBlock extends Block{
	public static final String COLOR_NAME = "purple";
	public static final Color COLOR = new Color(0.6F, 0, 0.9F);
	public PullBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(player.getRight() > this.getLeft() && player.getLeft() < this.getRight()){
			if(this.getCenterY() > player.getCenterY()){//Block is above player
				player.a.y += 0.03F;
			}else{
				player.a.y -= 0.17F;
			}
		}
	}
}
