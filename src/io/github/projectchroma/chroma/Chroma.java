package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.util.Colors;

public class Chroma extends StateBasedGame{
	/**
	 * Set to true to enable debug mode, which:
	 * <ul>
	 * <li>Slows the game down to 25FPS to watch collisions, etc in detail</li>
	 * <li>Shows the FPS counter</li>
	 * <li>Shows the player's collision boxes</li>
	 * </ul>
	 */
	public static final boolean DEBUG_MODE = false;
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private static final Chroma instance = new Chroma();
	
	private Player player;
	private boolean scheme = true;//True for light color scheme, false for dark color scheme
	
	public Chroma(){
		super("Chroma");
		player = new Player();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException{
		addState(new LevelState("/levels/debug.json"));
	}
	
	public Color foreground(){
		return scheme ? Colors.black : Colors.white;
	}
	public Color background(){
		return scheme ? Colors.white : Colors.black;
	}
	public void toggleScheme(){
		scheme = !scheme;
	}
	public Player player(){
		return player;
	}
	
	public static Chroma instance(){
		return instance;
	}
	public static void main(String[] args){
		try{
			AppGameContainer app = new AppGameContainer(instance);
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);//Width, height, fullscreen
			app.setTargetFrameRate(DEBUG_MODE ? 25 : 100);//Defaults to as many as possible, which is ~3000 on a medium-good PC
			app.setShowFPS(DEBUG_MODE);//Hide FPS counter
			app.start();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
}
