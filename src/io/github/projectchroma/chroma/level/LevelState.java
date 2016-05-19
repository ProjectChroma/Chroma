package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.GameMusic;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;
import io.github.projectchroma.chroma.resource.DeferredLevel;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends BaseGameState{
	private static Font nameFont;
	private static Audio soundSwitch;
	
	private DeferredLevel level;
	private String name;
	private float playerX, playerY;
	private LevelElement[] elements;
	public LevelState(int id){
		super(id);
		level = Resources.loadLevel(id);
	}
	@Override
	public void loadResources(GameContainer container, StateBasedGame game) throws SlickException{
		if(nameFont == null) nameFont = Resources.loadFont("mysteron.ttf", 30);
		if(soundSwitch == null) soundSwitch = Resources.loadSound("switch.aif");
		level = Resources.loadLevel(id);
	}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		LevelObject level = this.level.getLevel();
		name = level.name;
		playerX = level.playerStart.x;
		playerY = level.playerStart.y;
		
		elements = new LevelElement[level.blocks.size() + level.hints.size() + 4];//The blocks, the hints, three barriers, and the player
		elements[0] = Blocks.createBlock(null, 0, Chroma.WINDOW_HEIGHT - Block.WALL_WIDTH, Chroma.WINDOW_WIDTH, Block.WALL_WIDTH, null);//Floor
		elements[1] = Blocks.createBlock(null, 0, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null);//Left wall
		elements[2] = Blocks.createBlock(null, Chroma.WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null);//Right wall
		elements[3] = Chroma.instance().player();
		int i = 4;
		for(BlockObject block : level.blocks) elements[i++] = Blocks.createBlock(block.color, block.x, block.y, block.width, block.height, Colors.byName(block.scheme));
		for(HintObject hint : level.hints) elements[i++] = new Hint(hint.text, Colors.byName(hint.color), hint.x, hint.y);
		
		for(LevelElement element : elements) element.init(container);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(Chroma.instance().background());
		g.setFont(nameFont);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);//Draw background
		g.setColor(Chroma.instance().foreground());
		g.drawString(name, Block.WALL_WIDTH, 0);
		for(LevelElement element : elements){
			if(!element.getColor().equals(Chroma.instance().background())) element.render(container, g);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		if(container.getInput().isKeyPressed(Input.KEY_P)) game.enterState(PausedState.ID, null, new PausedState.Enter());
		if(!container.isPaused()){
			for(LevelElement element : elements)
				element.update(container, delta);
			if(container.getInput().isKeyPressed(Input.KEY_UP)){
				soundSwitch.playAsSoundEffect(0, 0, false);
				Chroma.instance().toggleScheme();
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
		Chroma.instance().setScheme(true);
	}
	@Override
	protected Audio getMusic(){
		return GameMusic.getLevelMusic();
	}
	
	public LevelElement[] elements(){
		return elements.clone();//Defensive copy
	}
	public String name(){
		return name;
	}
}
