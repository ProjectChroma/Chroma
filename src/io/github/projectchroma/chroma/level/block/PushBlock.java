package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

public class PushBlock extends Block{
	public static final String COLOR_NAME = "green";
	public static final Color COLOR = Color.green;
	private float strength = Player.gravity;//Just as strong as gravity by default
	public PushBlock(BlockObject block){
		super(block, COLOR);
		if(block.hasProperty("strength")) strength = block.getFloatProperty("strength");
	}
	@Override
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(player.getRight() > this.getLeft() && player.getLeft() < this.getRight()){
			if(this.getCenterY() < player.getCenterY()){//Block is above player
				player.a.y += strength - Player.gravity;
			}else{
				player.a.y -= strength + Player.gravity;
			}
		}
	}
}
