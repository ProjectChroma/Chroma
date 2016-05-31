package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.settings.Keybind;

public class SettingsMenuState extends GUIState{
	private static SettingsMenuState instance;
	private static Font textFont;
	
	private KeybindButton activeKeybind = null;
	public SettingsMenuState(int id){
		super(id);
		instance = this;
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		add(new RenderedText("Settings", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 50));
		add(new RenderedText("Controls", Chroma.instance().createFont(32), Chroma.WINDOW_WIDTH/2, 100));
		if(textFont == null) textFont = Chroma.instance().createFont(20);
			
		int i = 2;
		for(Keybind keybind : Keybind.bindings()){
			Rectangle textArea = buttonArea(left, i);
			add(new RenderedText(keybind.getName(), textFont, textArea.getMinX() + textFont.getWidth(keybind.getName())/2, textArea.getCenterY()));
			add(new KeybindButton(buttonArea(right, i), keybind));
			++i;
		}
		
		add(new Button(buttonArea(center, 8), "Back", Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.RIGHT));
			}
		});
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.leave(container, game);
		if(Keybind.hasChanged()) Keybind.write();
	}
	@Override
	public void keyPressed(int key, char c){
		if(activeKeybind != null){
			activeKeybind.keybind.setKey(key);
			activeKeybind.setText(Input.getKeyName(key));
			activeKeybind = null;
		}
	}
	private class KeybindButton extends Button{
		private final Color bgActive = new Color(0, 0, 1F), bgNormal = bg;
		private Keybind keybind;
		public KeybindButton(Rectangle area, Keybind keybind) throws SlickException{
			super(area, Input.getKeyName(keybind.getKey()));
			this.keybind = keybind;
		}
		@Override
		public void onclick() throws SlickException{
			activeKeybind = this;
		}
		@Override
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
			if(activeKeybind == this) bg = bgActive; else bg = bgNormal;
			super.render(container, game, g);
		}
	}
	public static SettingsMenuState instance(){
		return instance;
	}
}
