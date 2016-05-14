package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.util.Colors;

public class LevelTransition implements Transition{
	private static final int MAX_DELTA = 1000;//Delta is in ms, so wait for 1s
	private int delta;
	private Color c;
	public LevelTransition(boolean win){
		c = win ? Colors.gold : Colors.red;
	}
	@Override
	public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
		this.delta += delta;
	}
	@Override
	public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
	@Override
	public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
	@Override
	public boolean isComplete(){
		if(delta >= MAX_DELTA){
			Chroma.instance().player().resetRenderColor();
			return true;
		}else
			return false;
	}
	@Override
	public void init(GameState firstState, GameState secondState){
		delta = 0;
		Chroma.instance().player().setRenderColor(c);
	}
}
