package io.github.projectchroma.chroma;

import java.awt.Font;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.gui.CreditsState;
import io.github.projectchroma.chroma.gui.GameEndState;
import io.github.projectchroma.chroma.gui.KeybindsMenuState;
import io.github.projectchroma.chroma.gui.LevelSelectState;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.SettingsMenuState;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.PausedState;
import io.github.projectchroma.chroma.level.block.BlackBlock;
import io.github.projectchroma.chroma.level.block.WhiteBlock;
import io.github.projectchroma.chroma.settings.Analytics;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.settings.Settings;
import io.github.projectchroma.chroma.util.Colors;
import io.github.projectchroma.chroma.util.FileIO;

public class Chroma extends StateBasedGame{
	/**
	 * Set to true to enable debug mode, which:
	 * <ul>
	 * <li>Slows the game down to 25FPS to watch collisions, etc in detail</li>
	 * <li>Shows the FPS counter</li>
	 * <li>Shows the player's collision boxes</li>
	 * </ul>
	 */
	private static boolean debug = false;
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600, NUM_LEVELS = 20, FPS = 100;
	private static Chroma instance;
	
	private Font javaFont;
	private Map<Integer, BaseGameState> states = new HashMap<>();
	private GameState prevState = null;
	
	private Chroma() throws SlickException{
		super("Chroma");
		javaFont = Resources.loadFont("mysteron.ttf");
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException{
		addState(new MainMenuState());
		addState(new LevelSelectState());
		addState(new SettingsMenuState());
		addState(new KeybindsMenuState());
		addState(new CreditsState());
		for(int i = 1; i <= NUM_LEVELS; i++)
			addState(new LevelState(i));
		addState(PausedState.instance);
		addState(new GameEndState(NUM_LEVELS + 1));//Add game end after all levels
	}
	@Override
	public void addState(GameState state){
		if(getState(state.getID()) != null)//State with that ID already exists
			throw new IllegalArgumentException(getState(state.getID()).getClass().getName() + " and " + state.getClass().getName() + " conflict on ID " + state.getID());
		states.put(state.getID(), (BaseGameState)state);
		super.addState(state);
	}
	@Override
	public void enterState(int id, Transition leave, Transition enter){
		prevState = getCurrentState();
		super.enterState(id, leave, enter);
	}
	public UnicodeFont createFont(float size) throws SlickException{
		return createFont(size, Font.PLAIN);
	}
	@SuppressWarnings("unchecked")
	public UnicodeFont createFont(float size, int modifiers) throws SlickException{
		Font f = javaFont.deriveFont(modifiers, size);
		UnicodeFont ret = new UnicodeFont(f);
		ret.addAsciiGlyphs();
		
		ret.getEffects().add(new ColorEffect(Colors.awtColor(BlackBlock.COLOR)));
		ret.getEffects().add(new ColorEffect(Colors.awtColor(WhiteBlock.COLOR)));
		
		ret.loadGlyphs();
		return ret;
	}
	public GameState previousState(){
		return prevState;
	}
	
	public static Chroma instance(){
		return instance;
	}
	public static void main(String[] args){
		int fps = FPS;
		for(String arg : args){
			if(arg.startsWith("fps:")) fps = Integer.parseInt(arg.substring(4));
			else if(arg.startsWith("analytics:")) Analytics.setEnabled(Boolean.parseBoolean(arg.substring(10)));
			else if(arg.startsWith("debug:")) debug = Boolean.parseBoolean(arg.substring(6));
		}
		try{
			instance = new Chroma();
			FileIO.init();
			Settings.read();
			Keybind.read();
			Sounds.init();
			Analytics.init();
			ChromaContainer app = new ChromaContainer(instance);
			app.setTargetFrameRate(fps);
			app.setShowFPS(false);//Hide FPS counter
			app.setIcons(new String[]{Resources.getTexturePath("icon32.png"), Resources.getTexturePath("icon24.png"), Resources.getTexturePath("icon16.png")});
			app.setForceExit(true);//Call System.exit(0) when game is closed
			Settings.update(app);
			System.out.println("Starting game at " + fps + "FPS");
			app.start();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
	public Collection<BaseGameState> states(){
		return states.values();
	}
	public static boolean isDebugMode(){return debug;}
}
