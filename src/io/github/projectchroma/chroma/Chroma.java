package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.util.Colors;

public class Chroma extends BasicGame{
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
	private boolean mode = true;//True for light background, false for dark background
	
	public Chroma(){
		super("Chroma");
		player = new Player();
		pieces = new GamePiece[]{player,
			//Barriers
			new Block(0, WINDOW_HEIGHT - Block.WALL_WIDTH, WINDOW_WIDTH, Block.WALL_WIDTH),//Floor
			new Block(0, 0, Block.WALL_WIDTH, WINDOW_HEIGHT),//Left wall
			new Block(WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, WINDOW_HEIGHT),//Right wall
			//Staircase
			new Block(50, 500, 100, 20, Colors.PRIMARY_1),
			new Block(250, 425, 100, 20, Colors.PRIMARY_2),
			new Block(450, 350, 100, 20, Colors.PRIMARY_1),
			new Block(450, 500, 100, 20, Colors.HAZARD),
			//Racetrack
			new Block(50, 275, 300, 20, Colors.SPEED_DOWN),
			new Block(200, 200, 100, 20, Colors.SPEED_UP),
			new Block(300, 180, 50, 20),//Prevent jumping from blue platform up to orange the wrong way
			new Block(350, 125, 250, 20, Colors.SPEED_UP),
			new Block(475, 0, 20, 125, Colors.PRIMARY_1),//Wall
			
			//Elevator down
			new Block(580, 145, 20, 455 - Block.WALL_WIDTH),//Left wall
			new Block(650, 0, 20, WINDOW_HEIGHT - Block.WALL_WIDTH - 50),//Right wall
			new Block(600, 125, 50, 20, Color.white),
			new Block(600, 195, 50, 20, Color.black),
			new Block(600, 265, 50, 20, Color.white),
			new Block(600, 335, 50, 20, Color.black),
			new Block(600, 405, 50, 20, Color.white),
			new Block(600, 475, 50, 20, Color.black),
			new Block(600, 545, 50, 20, Color.white),
			new Block(600, 615, 50, 20, Color.black),
			
			//Elevator up
			//Left wall is the right wall of the elevator down
			new Block(720, 50, 15, WINDOW_HEIGHT - Block.WALL_WIDTH - 50),//Right wall
			new Block(670, 535, 50, 20, Color.white),
			new Block(670, 460, 50, 20, Color.black),
			new Block(670, 385, 50, 20, Color.white),
			new Block(670, 310, 50, 20, Color.black),
			new Block(670, 235, 50, 20, Color.white),
			new Block(670, 160, 50, 20, Color.black),
			new Block(670, 85, 50, 20, Color.white),
			
			//Goal
			new Block(735, 565, 50, 20, Colors.GOAL),
		};
	}
	
	@Override
	public void init(GameContainer container) throws SlickException{}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException{
		if(container.getInput().isKeyPressed(Input.KEY_P))
			container.setPaused(!container.isPaused());
		if(!container.isPaused()){
			for(GamePiece piece : pieces) piece.update(container, delta);
			if(container.getInput().isKeyPressed(Input.KEY_SPACE)) mode = !mode;
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(background());
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);//Draw background
		for(GamePiece piece : pieces){
			if(!piece.getColor().equals(background())) piece.render(container, g);
		}
	}
	public GamePiece[] pieces(){
		return pieces;
	}
	public Color foreground(){
		return mode ? Colors.PRIMARY_1 : Colors.PRIMARY_2;
	}
	public Color background(){
		return mode ? Colors.PRIMARY_2 : Colors.PRIMARY_1;
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
