package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Chroma extends BasicGame {
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private static final Chroma instance = new Chroma();

	private Player player;
	private GamePiece[] pieces;

	public Chroma() {
		super("Chroma");
		player = new Player();
		pieces = new GamePiece[]{player,
				new Block(0, WINDOW_HEIGHT - 20, WINDOW_WIDTH, 20, Color.white),
				new Block(600, 300, 100, 20, Color.white),
				new Block(100, 500, 100, 20, Color.white),
				new Block(50, 200, 100, 20, Color.white),
				new Block(760, 200, 20, 600, Color.white)
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
			app.setShowFPS(false);//Hide FPS counter
			app.start();

		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}