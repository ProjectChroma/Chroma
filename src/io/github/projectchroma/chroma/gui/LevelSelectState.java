package io.github.projectchroma.chroma.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.gui.util.BackButton;
import io.github.projectchroma.chroma.gui.util.Button;
import io.github.projectchroma.chroma.gui.util.GUIElement;
import io.github.projectchroma.chroma.gui.util.GUIState;
import io.github.projectchroma.chroma.gui.util.PaginatedGrid;
import io.github.projectchroma.chroma.gui.util.RenderedText;
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.modules.ModuleContext;
import io.github.projectchroma.chroma.modules.ModuleLoader;
import io.github.projectchroma.chroma.settings.Progress;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class LevelSelectState extends GUIState{
	public static final LevelSelectState instance = new LevelSelectState();
	private static final float GRID_WIDTH = 700, GRID_TOP = 110, ICON_SIZE = 85, NUM_COLUMNS = 7;
	private static final float SIDE_MARGINS = (Chroma.WINDOW_WIDTH - GRID_WIDTH) / 2,
			ICON_MARGINS = (GRID_WIDTH - (NUM_COLUMNS * ICON_SIZE)) / (NUM_COLUMNS - 1);//Width of grid minus the width taken up by the icons, divided by the number of margins
	private PaginatedGrid grid;
	private LevelSelectState(){}
	@Override
	public void initialize(GameContainer container, final StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		add(new RenderedText("Level Select", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 40, Color.black));
		add(new BackButton(MainMenuState.instance.getID(), Direction.LEFT));
	}
	@Override
	public void postInit(GameContainer container, StateBasedGame game) throws SlickException{
		super.postInit(container, game);
		grid = new PaginatedGrid();
		add(grid);
		Font moduleFont = Chroma.instance().createFont(40);
		float y2 = GRID_TOP - 10, y1 = y2 - moduleFont.getLineHeight();
		float x = SIDE_MARGINS;
		add(new Button(RectangleUtils.fromVertices(x, y1, x + 100, y2), "<<"){
			public void onclick() throws SlickException{grid.prevPage();}
		});
		x = Chroma.WINDOW_WIDTH - x;
		add(new Button(RectangleUtils.fromVertices(x - 100, y1, x, y2), ">>"){
			public void onclick() throws SlickException{grid.nextPage();}
		});
		List<ModuleContext> modules = ModuleLoader.instance().getLoadedModules();
		for(int page=0, moduleInd=0; moduleInd<modules.size(); ++moduleInd){//++page done after chance of continuing past module
			ModuleContext module = modules.get(moduleInd);
			List<LevelState> levels = LevelState.getLevels(module);
			if(levels == null) continue;//No levels for module
			grid.add(new RenderedText(module.getID(), moduleFont, Chroma.WINDOW_WIDTH/2, (y1 + y2) / 2), page);
			int row = 0, column = 0;
			for(LevelState level : levels){
				grid.add(new LevelIcon(column * (ICON_SIZE + ICON_MARGINS) + SIDE_MARGINS, row * (ICON_SIZE + ICON_MARGINS) + GRID_TOP, level), page);
				if(column+1 == NUM_COLUMNS){//If this column was the last column
					row++;//Drop down to a new row
					column = 0;//and reset the column
				}else{
					column++;//Otherwise, just move to the right
				}
			}
			++page;
		}
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		Progress.read();
		grid.setPage(0);
		for(GUIElement element : grid.getElements())
			if(element instanceof LevelIcon) ((LevelIcon)element).level.setScheme(0);
	}
	@Override
	public void keyPressed(int key, char c){
		if(key == Input.KEY_LEFT) grid.prevPage();
		else if(key == Input.KEY_RIGHT) grid.nextPage();
	}
	
	private static class LevelIcon extends GUIElement{
		private LevelState level;
		private float x, y;
		private Block[] blocks;
		private boolean mouseOver = false;
		public LevelIcon(float x, float y, LevelState level){
			this.level = level;
			this.x = x;
			this.y = y;
			level.setScheme(0);//Set scheme to the view you first see when entering the level
			List<Block> blocks = new ArrayList<>();
			for(LevelElement element : level.elements()){
				if(element instanceof Block) blocks.add((Block)element);
			}
			this.blocks = blocks.toArray(new Block[0]);
		}
		@Override
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
			g.translate(x, y);//(0, 0) is now the top-left corner of this icon
			if(Progress.isLevelLocked(level)){
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 1F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}else if(mouseOver){
				//If the mouse is over this icon and it's not locked, don't render any background color
			}else if(Progress.isLevelComplete(level)){
				g.setColor(new Color(0.5F, 1F, 0.5F, 0.5F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}else{
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.2F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}
			g.scale(ICON_SIZE / level.width(), ICON_SIZE / level.height());//(800, 600) is now (100, 100)
			for(Block block : blocks) block.render(container, level, g);
			g.scale(level.width() / ICON_SIZE, level.height() / ICON_SIZE);//Undo scale down
			g.translate(-x, -y);//Undo translate
		}
		@Override
		public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
			float minX = x, minY = y, maxX = x + ICON_SIZE, maxY = y + ICON_SIZE,
					mouseX = container.getInput().getMouseX(), mouseY = container.getInput().getMouseY();
			if(minX <= mouseX && mouseX <= maxX && minY <= mouseY && mouseY <= maxY){//Mouse is over this element 
				mouseOver = true;
				if(!Progress.isLevelLocked(level) && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					game.enterState(level.getID(), null, new SwipeTransition(Direction.RIGHT));
				}
			}else mouseOver = false;
		}
	}
}
