package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Chroma extends BasicGame {
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private static final Chroma instance = new Chroma();

	public Chroma() {
		super("Chroma");
	}

	@Override
	public void init(GameContainer container) throws SlickException {

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.green);
		g.fill(new Rectangle(0, 0, 100, 100));//Draw a green 100x100 square in the top-left corner of the screen
		g.setColor(Color.blue);
		g.fill(new Rectangle(100, 0, 100, 100));
		g.setColor(Color.red);
		g.fill(new Rectangle(200, 0, 100, 100));
		g.setColor(Color.yellow);
		g.fill(new Rectangle(300, 0, 100, 100));
		g.setColor(Color.cyan);
		g.fill(new Rectangle(400, 0, 100, 100));
		g.setColor(Color.magenta);
		g.fill(new Rectangle(500, 0, 100, 100));
		g.setColor(Color.gray);
		g.fill(new Rectangle(600, 0, 100, 100));
		g.setColor(Color.white);
		g.fill(new Rectangle(700, 0, 100, 100));
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