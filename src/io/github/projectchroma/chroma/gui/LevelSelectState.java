package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelElement;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.settings.Progress;
import io.github.projectchroma.chroma.util.BoundingBox;
import io.github.projectchroma.chroma.util.Colors;
import io.github.projectchroma.chroma.util.GraphicsUtils;

public class LevelSelectState extends GUIState{
	private static final float GRID_WIDTH = 700, GRID_TOP = 100, ICON_SIZE = 100, NUM_COLUMNS = 6;
	private static final float SIDE_MARGINS = (Window.WINDOW_WIDTH - GRID_WIDTH) / 2, SCALE_X = ICON_SIZE / Window.WINDOW_WIDTH, SCALE_Y = ICON_SIZE / Window.WINDOW_HEIGHT,
			ICON_MARGINS = (GRID_WIDTH - (NUM_COLUMNS * ICON_SIZE)) / (NUM_COLUMNS - 1);//Width of grid minus the width taken up by the icons, divided by the number of margins
	public static final int ID = Chroma.NUM_LEVELS+1;
	private FontResource titleFont, buttonFont;
	public LevelSelectState(){
		super(ID);
		titleFont = Resources.getFont("mysteron", 36);
		buttonFont = Resources.getFont("mysteron", 24);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		int row = 0, column = 0;
		for(int i = 1; i <= Chroma.NUM_LEVELS; i++){//For each level
			LevelState level = (LevelState)chroma.getState(i);
			add(new LevelIcon(column * (ICON_SIZE + ICON_MARGINS) + SIDE_MARGINS, row * (ICON_SIZE + ICON_MARGINS) + GRID_TOP, level));
			if(column+1 == NUM_COLUMNS){//If this column was the last column
				row++;//Drop down to a new row
				column = 0;//and reset the column
			}else{
				column++;//Otherwise, just move to the right
			}
			
		}
		add(new Button(buttonArea(center, 7), "Back", buttonFont.get(), Colors.DARK_RED){
			public void onclick(){
				chroma.enterState(MainMenuState.ID);
			}
		});
	}
	@Override
	public void enter(Window window, Chroma chroma){
		Progress.read();
	}

	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		super.render(window, chroma, g);
		g.setColor(Color.black);
		g.setFont(titleFont.get());
		g.drawString("Level Select", Window.WINDOW_WIDTH/2 - GraphicsUtils.width(g, "Level Select")/2, 50);
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
		public void render(Window window, Chroma chroma, Graphics g){
			g.translate((int)x, (int)y);//(0, 0) is now the top-left corner of this icon
			if(Progress.isLevelLocked(level.getID())){
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 1F));
				g.fillRect(0, 0, (int)ICON_SIZE, (int)ICON_SIZE);
			}else if(mouseOver){
				//If the mouse is over this icon and it's not locked, don't render any background color
			}else if(Progress.isLevelComplete(level.getID())){
				g.setColor(new Color(0.5F, 1F, 0.5F, 0.5F));
				g.fillRect(0, 0, (int)ICON_SIZE, (int)ICON_SIZE);
			}else{
				g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.2F));
				g.fillRect(0, 0, (int)ICON_SIZE, (int)ICON_SIZE);
			}
			for(Block block : blocks){
//				block.render(window, chroma, level, g);
				if(!block.doRender(level)) continue;
				BoundingBox bounds = block.getBounds().clone();
				bounds.setWidth(bounds.getWidth() * SCALE_X);
				bounds.setHeight(bounds.getHeight() * SCALE_Y);
				bounds.setX(bounds.getX() * SCALE_X);
				bounds.setY(bounds.getY() * SCALE_Y);
				
				g.setColor(block.getColor(level));
				GraphicsUtils.fill(g, bounds);
			}
			g.translate(-(int)x, -(int)y);//Undo translate
		}
		@Override
		public void update(Window window, Chroma chroma){
			float minX = x, minY = y, maxX = x + ICON_SIZE, maxY = y + ICON_SIZE,
					mouseX = window.getInput().getMouseX(), mouseY = window.getInput().getMouseY();
			if(minX <= mouseX && mouseX <= maxX && minY <= mouseY && mouseY <= maxY){//Mouse is over this element 
				mouseOver = true;
				if(window.getInput().isMousePressed(MouseEvent.BUTTON1)){
//					chroma.enterState(level.getID(), null, new SwipeTransition(SwipeTransition.RIGHT));
					chroma.enterState(level.getID());
				}
			}else mouseOver = false;
		}
	}
}
