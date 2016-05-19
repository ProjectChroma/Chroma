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
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.settings.Progress;

public class LevelSelectState extends GUIState{
	private static final float GRID_WIDTH = 700, GRID_TOP = 100, ICON_SIZE = 100, NUM_COLUMNS = 6;
	private static final float SIDE_MARGINS = (Chroma.WINDOW_WIDTH - GRID_WIDTH) / 2,
			ICON_MARGINS = (GRID_WIDTH - (NUM_COLUMNS * ICON_SIZE)) / (NUM_COLUMNS - 1);//Width of grid minus the width taken up by the icons, divided by the number of margins
	private static LevelSelectState instance;
	
	private Font titleFont, buttonFont;
	public LevelSelectState(int id){
		super(id);
		if(instance == null) instance = this;
	}
	@Override
	public void loadResources(GameContainer container, StateBasedGame game) throws SlickException{
		titleFont = Resources.loadFont("mysteron.ttf", 50);
		buttonFont = Resources.loadFont("mysteron.ttf", 24);
	}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		int row = 0, column = 0;
		add(new RenderedText("Level Select", titleFont, Chroma.WINDOW_WIDTH/2, 40, Color.black));
		for(int i = 1; i <= Chroma.NUM_LEVELS; i++){//For each level
			LevelState level = (LevelState)Chroma.instance().getState(i);
			add(new LevelIcon(column * (ICON_SIZE + ICON_MARGINS) + SIDE_MARGINS, row * (ICON_SIZE + ICON_MARGINS) + GRID_TOP, level));
			if(column+1 == NUM_COLUMNS){//If this column was the last column
				row++;//Drop down to a new row
				column = 0;//and reset the column
			}else{
				column++;//Otherwise, just move to the right
			}
			
		}
		add(new Button(buttonArea(center, 8), "Back", buttonFont, Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.LEFT));
			}
		});
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		Progress.read();
		Chroma.instance().setScheme(true);//Set to light scheme (as shown when you first enter a level)
	}
	
	public static LevelSelectState instance(){
		return instance;
	}
	
	private static class LevelIcon extends GUIElement{
		private int levelID;
		private float x, y;
		private Block[] blocks;
		private boolean mouseOver = false;
		public LevelIcon(float x, float y, LevelState level){
			this.levelID = level.getID();
			this.x = x;
			this.y = y;
			List<Block> blocks = new ArrayList<>();
			for(LevelElement element : level.elements()){
				if(element instanceof Block) blocks.add((Block)element);
			}
			this.blocks = blocks.toArray(new Block[0]);
		}
		@Override
		public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
			g.translate(x, y);//(0, 0) is now the top-left corner of this icon
			if(Progress.isLevelLocked(levelID)){
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 1F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}else if(mouseOver){
				//If the mouse is over this icon and it's not locked, don't render any background color
			}else if(Progress.isLevelComplete(levelID)){
				g.setColor(new Color(0.5F, 1F, 0.5F, 0.5F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}else{
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.2F));
				g.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
			}
			g.scale(ICON_SIZE / Chroma.WINDOW_WIDTH, ICON_SIZE / Chroma.WINDOW_HEIGHT);//(800, 600) is now (100, 100)
			for(Block block : blocks) block.render(container, g);
			g.scale(Chroma.WINDOW_WIDTH / ICON_SIZE, Chroma.WINDOW_HEIGHT / ICON_SIZE);//Undo scale down
			g.translate(-x, -y);//Undo translate
		}
		@Override
		public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
			float minX = x, minY = y, maxX = x + ICON_SIZE, maxY = y + ICON_SIZE,
					mouseX = container.getInput().getMouseX(), mouseY = container.getInput().getMouseY();
			if(minX <= mouseX && mouseX <= maxX && minY <= mouseY && mouseY <= maxY){//Mouse is over this element 
				mouseOver = true;
				if(!Progress.isLevelLocked(levelID) && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					game.enterState(levelID, null, new SwipeTransition(SwipeTransition.RIGHT));
				}
			}else mouseOver = false;
		}
	}
}
