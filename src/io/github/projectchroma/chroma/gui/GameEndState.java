package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Sounds;
import io.github.projectchroma.chroma.gui.util.BackButton;
import io.github.projectchroma.chroma.gui.util.GUIState;
import io.github.projectchroma.chroma.level.block.BlackBlock;
import io.github.projectchroma.chroma.level.block.GoalBlock;
import io.github.projectchroma.chroma.util.Direction;

public class GameEndState extends GUIState{
	public GameEndState(int id){
		super(id);
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		super.init(container, game);
		add(new BackButton(MainMenuState.ID, Direction.LEFT, "Back to Menu", GoalBlock.COLOR));
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		super.render(container, game, g);
		g.setColor(BlackBlock.COLOR);
		drawCentered("Congratulations!", 200, g);
		drawCentered("You have completed Chroma!", 300, g);
		drawCentered("Check back as development continues for more levels!", 400, g);
	}
	private void drawCentered(String text, int y, Graphics g){
		g.drawString(text, Chroma.WINDOW_WIDTH / 2 - g.getFont().getWidth(text) / 2, y);
	}
	@Override
	protected Music getMusic(){
		return Sounds.getLevelMusic();
	}
}
