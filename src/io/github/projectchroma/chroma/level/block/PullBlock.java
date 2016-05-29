package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;

public class PullBlock extends Block{
	public static final String COLOR_NAME = "purple";
	public static final Color COLOR = new Color(0.6F, 0, 0.9F);
	private float strength = Player.gravity;//Just as strong as gravity by default
	public PullBlock(BlockObject block){
		super(block, COLOR);
		if(block.hasProperty("strength")) strength = block.getFloatProperty("strength");
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(player.getRight() > this.getLeft() && player.getLeft() < this.getRight()){
			if(this.getCenterY() > player.getCenterY()){//Block is below player
				player.a.y += strength - Player.gravity;
			}else{
				player.a.y -= strength + Player.gravity;
			}
		}
	}
}
