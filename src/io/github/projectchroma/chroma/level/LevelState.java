package io.github.projectchroma.chroma.level;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.glass.events.KeyEvent;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.GameState;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.LevelResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends GameState{
	private static FontResource nameFont;
//	private static Sound soundSwitch;
	
	private LevelResource level;
	private String name;
	private float playerX, playerY;
	private Player player;
	private LevelElement[] elements;
	private Color[] schemes;
	private int scheme = 0;
	public LevelState(int id){
		super(id);
		level = Resources.getLevel(id);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		if(nameFont == null) nameFont = Resources.getFont("mysteron.ttf", 30);
//		if(soundSwitch == null) soundSwitch = Resources.loadSound("switch.aif");
		LevelObject level = this.level.get();
		name = level.name;
		playerX = level.playerStart.x;
		playerY = level.playerStart.y;
		
		player = new Player();
		
		elements = new LevelElement[level.blocks.size() + level.hints.size() + 4];//The blocks, the hints, three barriers, and the player
		elements[0] = Blocks.createBlock(null, 0, Window.WINDOW_HEIGHT - Block.WALL_WIDTH, Window.WINDOW_WIDTH, Block.WALL_WIDTH, null);//Floor
		elements[1] = Blocks.createBlock(null, 0, 0, Block.WALL_WIDTH, Window.WINDOW_HEIGHT, null);//Left wall
		elements[2] = Blocks.createBlock(null, Window.WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, Window.WINDOW_HEIGHT, null);//Right wall
		elements[3] = player;
		int i = 4;
		for(BlockObject block : level.blocks) elements[i++] = Blocks.createBlock(block.color, block.x, block.y, block.width, block.height, Colors.byName(block.scheme));
		for(HintObject hint : level.hints) elements[i++] = new Hint(hint.text, Colors.byName(hint.color), hint.x, hint.y);
		
		schemes = new Color[level.schemes.size()];
		for(int j = 0; j < schemes.length; ++j){
			schemes[j] = Colors.byName(level.schemes.get(j));
		}
		
		for(LevelElement element : elements) element.init(window, chroma);
	}
	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		g.setColor(background());
		g.setFont(nameFont.get());
		g.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);//Draw background
		g.setColor(foreground());
		g.drawString(name, Block.WALL_WIDTH, 0);
		for(LevelElement element : elements){
			if(element.doRender(this)) element.render(window, chroma, this, g);
		}
	}
	@Override
	public void update(Window window, Chroma chroma){
//		if(window.getInput().isKeyPressed(KeyEvent.VK_P)) chroma.enterState(PausedState.ID, null, new PausedState.Enter());
		for(LevelElement element : elements)
			element.update(window, chroma, this);
		if(window.getInput().isKeyPressed(KeyEvent.VK_UP)){
//			soundSwitch.play();
			cycleScheme();
		}
	}
	@Override
	public void enter(Window window, Chroma chroma){
		if(!(chroma.getPreviousState() instanceof PausedState)) restart();
	}
	public void restart(){
		player().moveTo(playerX, playerY);
		player().resetKinematics();
		setScheme(0);
	}
//	@Override
//	protected Music getMusic(){
//		return Sounds.getLevelMusic();
//	}
	
	public Player player(){
		return player;
	}
	
	public LevelElement[] elements(){
		return elements.clone();//Defensive copy
	}
	public String name(){
		return name;
	}
	public void setScheme(int scheme){
		this.scheme = scheme % schemes.length;
	}
	public int getScheme(){
		return scheme;
	}
	public void cycleScheme(){
		setScheme(scheme + 1);
	}
	public Color background(){
		return schemes[scheme];
	}
	public Color foreground(){
		Color bg = background();
		float avg = (bg.getRed() + bg.getGreen() + bg.getBlue()) / 3;
		return avg > 0.5 ? Color.black : Color.white;//If the average RGB value is greater than 0.5, this is a bright background, so use a dark foreground.
	}
}
