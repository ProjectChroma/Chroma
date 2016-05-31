package io.github.projectchroma.chroma.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class GUIState extends BaseGameState{
	protected static final float CENTER = Chroma.WINDOW_WIDTH / 2, GRID_TOP = 50, COLUMN_WIDTH = 300, BUTTON_HEIGHT = 50, MARGIN = 10;
	protected static final float LEFT = CENTER - (COLUMN_WIDTH + MARGIN), RIGHT = CENTER + (COLUMN_WIDTH + MARGIN);
	
	protected static final Boolean left = true, right = false, center = null;
	
	protected List<GUIElement> elements = new ArrayList<>();
	protected Color bg;
	protected GUIState(int id, GUIElement... elements){this(id, Color.white, elements);}
	protected GUIState(int id, Color bg, GUIElement... elements){
		super(id);
		this.bg = bg;
		for(GUIElement element : elements) add(element);
	}
	protected Rectangle buttonArea(Boolean column, int gridy){
		float cy = GRID_TOP + gridy * (BUTTON_HEIGHT + MARGIN);
		if(column == left) return RectangleUtils.fromDimensions(LEFT, cy - BUTTON_HEIGHT/2, COLUMN_WIDTH, BUTTON_HEIGHT);
		else if(column == right) return RectangleUtils.fromDimensions(RIGHT, cy - BUTTON_HEIGHT/2, -COLUMN_WIDTH, BUTTON_HEIGHT);
		else return RectangleUtils.fromCenter(CENTER, cy, 2 * (COLUMN_WIDTH + MARGIN), BUTTON_HEIGHT);
	}
	protected void add(GUIElement element){
		elements.add(element);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setBackground(bg);
		for(GUIElement element : elements) element.render(container, game, g);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		for(GUIElement element : elements) element.update(container, game, delta);
	}
}
