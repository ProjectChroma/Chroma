package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class FallingBlocks extends Block {
	public static final String COLOR_NAME = "gray";
	public static final Color COLOR = Color.gray;
	public boolean moving;
	public float orgin;

	public FallingBlocks(BlockObject block) {
		super(block, COLOR);
		orgin = block.y;
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		moving = false;
//		setTop(orgin);
	}
	protected void update(GameContainer container, LevelState level, Player player) throws SlickException{
		if(player.getRight() > this.getLeft() && player.getLeft() < this.getRight()){
			//if(this.getCenterY() < player.getCenterY()){
				moving = true;
			}else{
				moving = false;
			}
		if(moving == true){
			setTop(getTop() + 3);
		}
	}
}
