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
import io.github.projectchroma.chroma.gui.util.BackButton;
import io.github.projectchroma.chroma.gui.util.Button;
import io.github.projectchroma.chroma.gui.util.BasicGrid;
import io.github.projectchroma.chroma.gui.util.GUIState;
import io.github.projectchroma.chroma.gui.util.RenderedText;
import io.github.projectchroma.chroma.level.block.PullBlock;
import io.github.projectchroma.chroma.level.block.PushBlock;
import io.github.projectchroma.chroma.level.block.SpeedBlock;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.settings.Settings;
import io.github.projectchroma.chroma.util.Direction;

public class SettingsMenuState extends GUIState{
	public static final int ID = -2;
	private static Font textFont;
	
	private KeybindButton activeKeybind = null;
	public SettingsMenuState(){
		super(ID);
	}
	@Override
	public void initialize(GameContainer container, final StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		add(new RenderedText("Settings", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 50));
		if(textFont == null) textFont = Chroma.instance().createFont(20);
		
		BasicGrid grid = new BasicGrid();
		add(grid);
		grid.add(new Button(grid.area(5, BasicGrid.LEFT_COLUMN), "Toggle Sounds", PullBlock.COLOR.darker()){
			public void onclick(){
				Settings.toggleSounds();
				Settings.update(container);
			}
		});
		grid.add(new Button(grid.area(5, BasicGrid.RIGHT_COLUMN), "Toggle Music", PushBlock.COLOR.darker()){
			public void onclick(){
				Settings.toggleMusic();
				Settings.update(container);
			}
		});
			
		grid.add(new Button(grid.area(7, BasicGrid.FULL_WIDTH), "Controls", SpeedBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(KeybindsMenuState.instance().getID(), null, new SwipeTransition(Direction.DOWN));
			}
		});
		
		grid.add(new BackButton(MainMenuState.ID, Direction.RIGHT));
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.leave(container, game);
		if(Keybind.hasChanged()) Keybind.write();
		Settings.write();
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
}
