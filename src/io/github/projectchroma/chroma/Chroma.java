package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Chroma extends BasicGame {
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
	private GamePiece[] pieces;

	public Chroma() {
		super("Chroma");
		player = new Player();
		pieces = new GamePiece[]{
			player,
			new Block(0, WINDOW_HEIGHT - Block.WALL_WIDTH, WINDOW_WIDTH, Block.WALL_WIDTH, Color.white),//Floor
			new Block(0, 0, Block.WALL_WIDTH, WINDOW_HEIGHT, Color.white),//Left wall
			new Block(WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, WINDOW_HEIGHT, Color.white),//Right wall
			//Platforms
			new Block(600, 300, 100, 20, Color.white),
			new Block(100, 500, 100, 20, Color.white),
			new Block(50, 200, 100, 20, Color.white),
		};
	}

	@Override
	public void init(GameContainer container) throws SlickException {

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		for(GamePiece piece : pieces){
			piece.update(container, delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		for(GamePiece piece : pieces){
			piece.render(container, g);
		}
	}
	public GamePiece[] pieces(){
		return pieces;
	}
	
	public static Chroma instance(){
		return instance;
	}
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(instance);
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);//Width, height, fullscreen
			app.setTargetFrameRate(DEBUG_MODE ? 25 : 100);//Defaults to as many as possible, which is ~3000 on a medium-good PC
			app.setShowFPS(DEBUG_MODE);//Hide FPS counter
			app.start();

		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}