package io.github.projectchroma.chroma;

import java.awt.Font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.gui.GameEndState;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.SettingsMenuState;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.PausedState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.level.block.BlackBlock;
import io.github.projectchroma.chroma.level.block.WhiteBlock;
import io.github.projectchroma.chroma.util.Colors;

public class Chroma extends StateBasedGame{
	/**
	 * Set to true to enable debug mode, which:
	 * <ul>
	 * <li>Slows the game down to 25FPS to watch collisions, etc in detail</li>
	 * <li>Shows the FPS counter</li>
	 * <li>Shows the player's collision boxes</li>
	 * </ul>
	 */
	public static final boolean DEBUG_MODE = false;
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600, NUM_LEVELS = 11, FPS = 100;
	private static Chroma instance;
	
	private Font javaFont;
	private Player player;
	private boolean scheme = true;//True for light color scheme, false for dark color scheme
	private GameState prevState = null;
	
	private Chroma() throws SlickException{
		super("Chroma");
		player = new Player();
		javaFont = Resources.loadFont("mysteron.ttf");
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException{
		addState(new MainMenuState());
		addState(new SettingsMenuState());
		for(int i = 1; i <= NUM_LEVELS; i++)
			addState(new LevelState(i));
		addState(PausedState.instance);
		addState(new GameEndState(NUM_LEVELS + 1));//Add game end after all levels
	}
	@Override
	public void enterState(int id, Transition leave, Transition enter){
		prevState = getCurrentState();
		super.enterState(id, leave, enter);
	}
	
	public Color foreground(){
		return scheme ? BlackBlock.COLOR : WhiteBlock.COLOR;
	}
	public Color background(){
		return scheme ? WhiteBlock.COLOR : BlackBlock.COLOR;
	}
	public void toggleScheme(){
		scheme = !scheme;
	}
	public void setScheme(boolean light){
		scheme = light;
	}
	public boolean getScheme(){
		return scheme;
	}
	public Player player(){
		return player;
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
		try{
			instance = new Chroma();
			Sounds.init();
			AppGameContainer app = new AppGameContainer(instance);
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);//Width, height, fullscreen
			app.setTargetFrameRate(DEBUG_MODE ? 25 : FPS);
			app.setShowFPS(DEBUG_MODE);//Hide FPS counter
			app.setIcons(new String[]{Resources.getTexturePath("icon32.png"), Resources.getTexturePath("icon24.png"), Resources.getTexturePath("icon16.png")});
			app.start();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
}
