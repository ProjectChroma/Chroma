package io.github.projectchroma.chroma;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class SwipeTransition implements Transition{
	public static final boolean LEFT = true, RIGHT = false;
	private static final float LENGTH = 500;//ms
	
	private GameState prev = null, next = null;
	private float translation, rate = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS;
	private int direction;
	public SwipeTransition(boolean direction){
		this.direction = direction ? 1 : -1;
	}
	@Override
	public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
		translation = Math.min(translation + rate, Chroma.WINDOW_WIDTH);
	}
	
	@Override
	public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{
		g.translate(translation * direction, 0);
		prev.render(container, game, g);
		g.translate(Chroma.WINDOW_WIDTH * -direction, 0);
		next.render(container, game, g);
		g.resetTransform();
		g.translate(0, Chroma.WINDOW_HEIGHT);//Hide the actual level by pushing it off the bottom of the screen
		
	}
	
	@Override
	public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{
		g.resetTransform();//Reset so that the level is now rendered
	}
	
	@Override
	public boolean isComplete(){
		return translation >= Chroma.WINDOW_WIDTH;
	}
	@Override
	public void init(GameState firstState, GameState secondState){
		prev = secondState;
		next = firstState;
		translation = 0;
	}
}
