package io.github.projectchroma.chroma;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.util.Direction;

public class SwipeTransition implements Transition{
	private static final float LENGTH = 500;//ms
	
	private GameState prev = null, next = null;
	private float translationX, translationY, rateX = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS, rateY = Chroma.WINDOW_HEIGHT / (LENGTH / 1000) / Chroma.FPS;
	private Direction direction;
	public SwipeTransition(Direction direction){
		this.direction = direction;
	}
	@Override
	public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
		translationX = Math.min(translationX + rateX, Chroma.WINDOW_WIDTH);
		translationY = Math.min(translationY + rateY, Chroma.WINDOW_HEIGHT);
	}
	
	@Override
	public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{
		g.translate(translationX * -direction.offsetX, translationY * -direction.offsetY);
		prev.render(container, game, g);
		g.translate(Chroma.WINDOW_WIDTH * direction.offsetX, Chroma.WINDOW_HEIGHT * direction.offsetY);
		next.render(container, game, g);
		g.translate(translationX * direction.offsetX, translationY * direction.offsetY);
		g.translate(0, 2 * Chroma.WINDOW_HEIGHT);//Hide the actual level by pushing it off the bottom of the screen
	}
	
	@Override
	public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{
		g.translate(0, -2 * Chroma.WINDOW_HEIGHT);//Reset so that the level is now rendered
	}
	
	@Override
	public boolean isComplete(){
		return translationX >= Chroma.WINDOW_WIDTH || translationY >= Chroma.WINDOW_HEIGHT;
	}
	@Override
	public void init(GameState firstState, GameState secondState){
		prev = secondState;
		next = firstState;
		translationX = 0;
		translationY = 0;
	}
}
