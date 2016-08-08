package io.github.projectchroma.chroma.level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;
import io.github.projectchroma.chroma.level.entity.Entities;
import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.level.entity.Player;
import io.github.projectchroma.chroma.modules.ModuleContext;
import io.github.projectchroma.chroma.modules.ModuleLoader;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends BaseGameState{
	private static final Map<ModuleContext, List<LevelState>> levels = new HashMap<>();
	private static Font nameFont;
	protected static Music music;
	private static Sound soundSwitch;
	private static Keybind keyPause, keySwitch;
	
	private ModuleContext module;
	private LevelObject data;
	private String name;
	private boolean allowSwitching;
	private Player player;
	private List<LevelElement> elements = new ArrayList<>();
	private Color[] schemes;
	private int scheme = 0;
	private int width, height;
	private Rectangle camera;
	protected LevelState(LevelObject data){
		this.module = ModuleLoader.instance().getActiveModule();
		levels.computeIfAbsent(module, (ctx) -> new ArrayList<>()).add(this);
		
		this.data = data;
		name = data.name;
		allowSwitching = data.player.allowSwitching;
		width = data.width;
		height = data.height;
		camera = new Rectangle(0, 0, data.camera.width, data.camera.height);
		
		schemes = new Color[data.schemes.size()];
		for(int j = 0; j < schemes.length; ++j){
			schemes[j] = Colors.byName(data.schemes.get(j));
		}
	}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		if(nameFont == null) nameFont = Chroma.instance().createFont(30F);
		if(music == null) music = Resources.loadMusic(Resources.getSoundPath("levelMusic.aiff"));
		if(soundSwitch == null) soundSwitch = Resources.loadSound(Resources.getSoundPath("switch.aif"));
		if(keyPause == null) keyPause = Keybind.get("level.pause", Input.KEY_P);
		if(keySwitch == null) keySwitch = Keybind.get("player.switch", Input.KEY_UP);
		
		elements.add(Blocks.createBlock(0, data.height - Block.WALL_WIDTH, data.width, Block.WALL_WIDTH, null, null));//Floor
		elements.add(Blocks.createBlock(0, 0, Block.WALL_WIDTH, data.height, null, null));//Left wall
		elements.add(Blocks.createBlock(data.width - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, data.height, null, null));//Right wall
		for(BlockObject block : data.blocks)
			elements.add(Blocks.createBlock(block));
		for(HintObject hint : data.hints)
			elements.add(new Hint(hint.text, hint.x, hint.y, Colors.byName(hint.color), Colors.byName(hint.scheme)));
		elements.add(player = new Player(data.player));
		for(EntityObject entity : data.entities)
			elements.add(Entities.createEntity(entity));
		
		for(LevelElement element : elements)
			element.init(container);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		camera.setCenterX(player.getCenterX());
		if(camera.getMinX() < 0) camera.setCenterX(camera.getCenterX() - camera.getMinX());
		if(camera.getMaxX() > width) camera.setCenterX(camera.getCenterX() + width - camera.getMaxX());
		camera.setCenterY(player.getCenterY());
		if(camera.getMinY() < 0) camera.setCenterY(camera.getCenterY() - camera.getMinY());
		if(camera.getMaxY() > height) camera.setCenterY(camera.getCenterY() + height - camera.getMaxY());
		
		
		float scaleX = Chroma.WINDOW_WIDTH / camera.getWidth(), scaleY = Chroma.WINDOW_HEIGHT / camera.getHeight();
		g.scale(scaleX, scaleY);
		g.translate(-camera.getMinX(), -camera.getMinY());
		
		g.setColor(background());
		g.fillRect(0, 0, width, height);//Draw background
		for(LevelElement element : elements){
			if(!element.getColor(this).equals(background())) element.render(container, this, g);
		}
		g.setColor(foreground());
		g.setFont(nameFont);
		g.drawString(name, Block.WALL_WIDTH, 0);
		
		g.translate(camera.getMinX(), camera.getMinY());
		g.scale(1 / scaleX, 1 / scaleY);
		
		if(Chroma.isDebugMode()){
			Input input = container.getInput();
			String mouse = input.getMouseX() + "," + input.getMouseY();
			g.drawString(mouse, Chroma.WINDOW_WIDTH - Block.WALL_WIDTH - g.getFont().getWidth(mouse) - 5, 0);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(keyPause.isPressed()) game.enterState(PausedState.instance.getID(), null, new PausedState.Enter());
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
	@Override protected Music getMusic(){return music;}
	
	public String name(){return name;}
	public int width(){return width;}
	public int height(){return height;}
	public List<LevelElement> elements(){return elements;}
	public Player player(){return player;}
	public void setScheme(int scheme){this.scheme = scheme % schemes.length;}
	public int getScheme(){return scheme;}
	public void cycleScheme(){setScheme(scheme + 1);}
	public Color background(){return schemes[scheme];}
	public Color foreground(){
		Color bg = background();
		float avg = (bg.r + bg.g + bg.b) / 3;
		return avg > 0.5 ? Color.black : Color.white;//If the average RGB value is greater than 0.5, this is a bright background, so use a dark foreground.
	}
	public ModuleContext getDeclaringModule(){return module;}
	public static List<LevelState> getLevels(ModuleContext module){
		return levels.get(module);
	}
	public static LevelState create(String path) throws SlickException{return create(Resources.loadLevel(path));}
	@SuppressWarnings("unchecked")
	public static LevelState create(LevelObject level) throws SlickException{
		try{
			Class<?> clazz = Class.forName(level.levelClass);
			if(!LevelState.class.isAssignableFrom(clazz))
				throw new SlickException(String.format("Level class %s for level %s (module %s) does not extend LevelState", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()));
			Class<? extends LevelState> levelClass = (Class<? extends LevelState>)clazz;
			Constructor<? extends LevelState> ctr = levelClass.getDeclaredConstructor(LevelObject.class);
			ctr.setAccessible(true);
			return ctr.newInstance(level);
		}catch(ClassNotFoundException ex){
			throw new SlickException(String.format("Level class %s for level %s (module %s) does not exist", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()), ex);
		}catch(NoSuchMethodException ex){
			throw new SlickException(String.format("Level class %s for level %s (module %s) does not have a 1-arg (LevelObject) constructor", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()), ex);
		} catch (InstantiationException ex) {
			throw new SlickException(String.format("Level class %s for level %s (module %s) is abstract", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()), ex);
		} catch (IllegalAccessException ex) {
			throw new SlickException(String.format("Level class %s for level %s (module %s) is private", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()), ex);
		} catch (InvocationTargetException ex) {
			throw new SlickException(String.format("Error constructing class %s for level %s (module %s)", level.levelClass, level.name, ModuleLoader.instance().getActiveModule().getID()), ex.getCause());
		}
	}
}
