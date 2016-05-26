package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.Sounds;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends BaseGameState{
	private static Font nameFont;
	private static Sound soundSwitch;
	private String name;
	private float playerX, playerY;
	private boolean allowSwitching;
	private LevelElement[] elements;
	private Color[] schemes;
	private int scheme = 0;
	public LevelState(int id){
		super(id);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{
		if(nameFont == null) nameFont = Chroma.instance().createFont(30F);
		if(soundSwitch == null) soundSwitch = Resources.loadSound("switch.aif");
		
		LevelObject level = Resources.loadLevel(id);
		name = level.name;
		playerX = level.player.x;
		playerY = level.player.y;
		allowSwitching = level.player.allowSwitching;
		
		elements = new LevelElement[level.blocks.size() + level.hints.size() + 4];//The blocks, the hints, three barriers, and the player
		elements[0] = Blocks.createBlock(null, 0, Chroma.WINDOW_HEIGHT - Block.WALL_WIDTH, Chroma.WINDOW_WIDTH, Block.WALL_WIDTH, null);//Floor
		elements[1] = Blocks.createBlock(null, 0, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null);//Left wall
		elements[2] = Blocks.createBlock(null, Chroma.WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null);//Right wall
		elements[3] = Chroma.instance().player();
		int i = 4;
		for(BlockObject block : level.blocks)
			elements[i++] = Blocks.createBlock(block.color, block.x, block.y, block.width, block.height, Colors.byName(block.scheme));
		for(HintObject hint : level.hints)
			elements[i++] = new Hint(hint.text, hint.x, hint.y, Colors.byName(hint.color), Colors.byName(hint.scheme));
		
		schemes = new Color[level.schemes.size()];
		for(int j = 0; j < schemes.length; ++j){
			schemes[j] = Colors.byName(level.schemes.get(j));
		}
		
		for(LevelElement element : elements)
			element.init(container);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(background());
		g.setFont(nameFont);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);//Draw background
		g.setColor(foreground());
		g.drawString(name, Block.WALL_WIDTH, 0);
		for(LevelElement element : elements){
			if(!element.getColor(this).equals(background())) element.render(container, this, g);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(container.getInput().isKeyPressed(Input.KEY_P)) game.enterState(PausedState.ID, null, new PausedState.Enter());
		if(!container.isPaused()){
			for(LevelElement element : elements)
				element.update(container, this, delta);
			if(allowSwitching && container.getInput().isKeyPressed(Input.KEY_UP)){
				soundSwitch.play();
				cycleScheme();
			}
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		if(!(Chroma.instance().previousState() instanceof PausedState)) restart();
	}
	public void restart(){
		Chroma.instance().player().moveTo(playerX, playerY);
		Chroma.instance().player().resetKinematics();
		setScheme(0);
	}
	@Override
	protected Music getMusic(){
		return Sounds.getLevelMusic();
	}
	
	public String name(){
		return name;
	}
	public LevelElement[] elements(){
		return elements.clone();//Defensive copy
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
		float avg = (bg.r + bg.g + bg.b) / 3;
		return avg > 0.5 ? Color.black : Color.white;//If the average RGB value is greater than 0.5, this is a bright background, so use a dark foreground.
	}
}
