package io.github.projectchroma.chroma;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.util.Colors;

public class LevelExitTransition implements Transition{
	private static final int LENGTH_VICTORY = 500, LENGTH_DEATH = 1300;//ms
	private int delta, length;
	private Color c;
	private Sound sound;
	public LevelExitTransition(boolean win, Sound sound){
		length = win ? LENGTH_VICTORY : LENGTH_DEATH;
		c = win ? Colors.gold : Colors.red;
		this.sound = sound;
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
		if(delta >= length){
			Chroma.instance().player().resetRenderColor();
			return true;
		}else
			return false;
	}
	@Override
	public void init(GameState firstState, GameState secondState){
		delta = 0;
		Chroma.instance().player().setRenderColor(c);
		if(sound != null) sound.play();
	}
}
