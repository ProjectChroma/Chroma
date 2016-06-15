package io.github.projectchroma.chroma.level;

import java.util.ArrayList;
import java.util.List;

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
import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;
import io.github.projectchroma.chroma.level.entity.Entities;
import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.level.entity.Player;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends BaseGameState{
	private static Font nameFont;
	private static Sound soundSwitch;
	private static Keybind keyPause, keySwitch;
	
	private String name;
	private boolean allowSwitching;
	private Player player;
	private List<LevelElement> elements = new ArrayList<>();
	private Color[] schemes;
	private int scheme = 0;
	public LevelState(int id){
		super(id);
	}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		if(nameFont == null) nameFont = Chroma.instance().createFont(30F);
		if(soundSwitch == null) soundSwitch = Resources.loadSound("switch.aif");
		if(keyPause == null) keyPause = Keybind.get("level.pause", Input.KEY_P);
		if(keySwitch == null) keySwitch = Keybind.get("player.switch", Input.KEY_UP);
		
		LevelObject level = Resources.loadLevel(id);
		name = level.name;
		allowSwitching = level.player.allowSwitching;
		
		elements.add(Blocks.createBlock(0, Chroma.WINDOW_HEIGHT - Block.WALL_WIDTH, Chroma.WINDOW_WIDTH, Block.WALL_WIDTH, null, null));//Floor
		elements.add(Blocks.createBlock(0, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null, null));//Left wall
		elements.add(Blocks.createBlock(Chroma.WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT, null, null));//Right wall
		for(BlockObject block : level.blocks)
			elements.add(Blocks.createBlock(block));
		for(HintObject hint : level.hints)
			elements.add(new Hint(hint.text, hint.x, hint.y, Colors.byName(hint.color), Colors.byName(hint.scheme)));
		elements.add(player = new Player(level.player));
		for(EntityObject entity : level.entities)
			elements.add(Entities.createEntity(entity));
		
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
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);//Draw background
		for(LevelElement element : elements){
			if(!element.getColor(this).equals(background())) element.render(container, this, g);
		}
		g.setColor(foreground());
		g.setFont(nameFont);
		g.drawString(name, Block.WALL_WIDTH, 0);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(keyPause.isPressed()) game.enterState(PausedState.ID, null, new PausedState.Enter());
		for(LevelElement element : elements)
			if(element instanceof Entity && element.isTangible(this)) ((Entity)element).setKinematics(container, this);
		for(LevelElement element : elements)
			if(element.isTangible(this)) element.update(container, this, delta);
		if(allowSwitching && keySwitch.isPressed()){
			soundSwitch.play();
			cycleScheme();
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		if(!(Chroma.instance().previousState() instanceof PausedState)) restart();
		for(LevelElement element : elements) element.enter(container, game);
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.leave(container, game);
		for(LevelElement element : elements) element.leave(container, game);
	}
	public void restart(){
		player.resetPosition();
		setScheme(0);
	}
	@Override
	protected Music getMusic(){
		return Sounds.getLevelMusic();
	}
	
	public String name(){
		return name;
	}
	public List<LevelElement> elements(){
		return elements;
	}
	public Player player(){
		return player;
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
