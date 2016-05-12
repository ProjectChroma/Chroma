package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Chroma extends BasicGame {
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private static final Chroma instance = new Chroma();
	
	private Player player;
	public Chroma() {
		super("Chroma");
		player = new Player();
	}

	@Override
	public void init(GameContainer container) throws SlickException {

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(container, delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		player.render(container, g);
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