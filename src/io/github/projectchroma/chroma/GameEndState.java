package io.github.projectchroma.chroma;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.util.Colors;

public class GameEndState extends BaseGameState{
	public GameEndState(int id){
		super(id);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(Colors.white);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);
		
		g.setColor(Colors.black);
		drawCentered("Congratulations!", 200, g);
		drawCentered("You have completed Chroma!", 300, g);
		drawCentered("Check back as development continues for more levels!", 400, g);
	}
	private void drawCentered(String text, int y, Graphics g){
		g.drawString(text, Chroma.WINDOW_WIDTH/2 - g.getFont().getWidth(text)/2, y);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{}
}
