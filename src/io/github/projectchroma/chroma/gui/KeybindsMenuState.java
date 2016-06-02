package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.gui.util.BackButton;
import io.github.projectchroma.chroma.gui.util.Button;
import io.github.projectchroma.chroma.gui.util.GUIState;
import io.github.projectchroma.chroma.gui.util.BasicGrid;
import io.github.projectchroma.chroma.gui.util.PaginatedGrid;
import io.github.projectchroma.chroma.gui.util.RenderedText;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.util.Direction;

public class KeybindsMenuState extends GUIState{
	public static final int ID = -3;
	private static final int itemsPerPage = 10;
	private static KeybindsMenuState instance;
	private static Font textFont;
	
	private KeybindButton activeKeybind = null;
	private PaginatedGrid grid;
	public KeybindsMenuState(){
		super(ID);
		instance = this;
	}
	@Override
	public void initialize(GameContainer container, final StateBasedGame game) throws SlickException{
		super.init(container, game);
		add(new RenderedText("Controls", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 50));
		if(textFont == null) textFont = Chroma.instance().createFont(20);
		
		grid = new PaginatedGrid(100, 150, 30);
		add(grid);
		
		add(new BackButton(SettingsMenuState.ID, Direction.UP));
	}
	@Override
	public void postInit(GameContainer container, StateBasedGame game) throws SlickException{
		super.postInit(container, game);
		int i = 0, row, page;
		for(Keybind keybind : Keybind.bindings()){
			row = i % itemsPerPage;
			page = Math.floorDiv(i, itemsPerPage);
			Rectangle textArea = grid.area(row, BasicGrid.LEFT_COLUMN);
			grid.add(new RenderedText(keybind.getName(), textFont, textArea.getMinX() + textFont.getWidth(keybind.getName())/2, textArea.getCenterY()), page);
			grid.add(new KeybindButton(grid.area(row, BasicGrid.RIGHT_COLUMN), keybind), page);
			++i;
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		grid.setPage(0);
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
			Keybind.checkConflicts();
		}else if(key == Input.KEY_LEFT){
			grid.prevPage();
		}else if(key == Input.KEY_RIGHT){
			grid.nextPage();
		}
	}
	private class KeybindButton extends Button{
		private final Color activeDelta = Color.blue, conflictDelta = Color.red;
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
		public Color getBackground(){
			Color c = super.getBackground();
			if(activeKeybind == this) c = c.addToCopy(activeDelta);
			if(keybind.isConflicting()) c = c.addToCopy(conflictDelta);
			return c;
		}
	}
	public static KeybindsMenuState instance(){
		return instance;
	}
}
