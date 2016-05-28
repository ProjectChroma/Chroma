package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class PushBlock extends Block{
	public static final String COLOR_NAME = "green";
	public static final Color COLOR = Color.green;
	public PushBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(player.getRight() > this.getLeft() && player.getLeft() < this.getRight()){
			if(this.getCenterY() < player.getCenterY()){//Block is below player
				player.a.y += 0.03F;
			}else{
				player.a.y -= 0.17F;
			}
		}
	}
}
