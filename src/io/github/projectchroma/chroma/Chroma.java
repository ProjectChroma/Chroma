package io.github.projectchroma.chroma;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import io.github.projectchroma.chroma.gui.GameEndState;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.SettingsMenuState;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
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
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private static final int NUM_LEVELS = 9;
	private static Chroma instance;
	
	private Font javaFont;
	
	private Player player;
	private boolean scheme = true;//True for light color scheme, false for dark color scheme
	
	private Chroma() throws SlickException{
		super("Chroma");
		player = new Player();
		try{
			javaFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/mysteron.ttf"));
		}catch(FontFormatException | IOException ex){
			throw new SlickException("Error loading font assets/plasmatic.ttf", ex);
		}
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException{
		addState(new MainMenuState());
		addState(new SettingsMenuState());
		for(int i = 1; i <= NUM_LEVELS; i++)
			addState(new LevelState(i));
		addState(new GameEndState(NUM_LEVELS + 1));//Add game end after all levels
	}
	
	public Color foreground(){
		return scheme ? Colors.black : Colors.white;
	}
	public Color background(){
		return scheme ? Colors.white : Colors.black;
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
		
		ret.getEffects().add(new ColorEffect(Colors.awtColor(Colors.black)));
		ret.getEffects().add(new ColorEffect(Colors.awtColor(Colors.white)));
		
		ret.loadGlyphs();
		return ret;
	}
	
	public static Chroma instance(){
		return instance;
	}
	public static void main(String[] args){
		try{
			instance = new Chroma();
			AppGameContainer app = new AppGameContainer(instance);
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);//Width, height, fullscreen
			app.setTargetFrameRate(DEBUG_MODE ? 25 : 100);//Defaults to as many as possible, which is ~3000 on a medium-good PC
			app.setShowFPS(DEBUG_MODE);//Hide FPS counter
			app.setIcons(new String[]{"assets/icon32.png", "assets/icon24.png", "assets/icon16.png"});
			app.start();
		}catch(SlickException ex){
			ex.printStackTrace();
		}
	}
}
