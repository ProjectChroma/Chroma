package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.GameState;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class GUIState extends GameState{
	protected static final int CENTER = Window.WINDOW_WIDTH / 2, GRID_TOP = 50, COLUMN_WIDTH = 300, BUTTON_HEIGHT = 50, MARGIN = 10;
	protected static final int LEFT_CENTER = CENTER - (COLUMN_WIDTH + MARGIN), RIGHT_CENTER = CENTER + (COLUMN_WIDTH + MARGIN);
	
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
		int cx, cy = GRID_TOP + gridy * (BUTTON_HEIGHT + MARGIN), w = column == center ? 2 * (COLUMN_WIDTH + MARGIN) : COLUMN_WIDTH;
		if(column == left) cx = LEFT_CENTER;
		else if(column == right) cx = RIGHT_CENTER;
		else cx = CENTER;
		return RectangleUtils.fromCenter(cx, cy, w, BUTTON_HEIGHT);
	}
	protected void add(GUIElement element){
		elements.add(element);
	}
	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		g.setColor(bg);
		g.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		for(GUIElement element : elements) element.render(window, chroma, g);
	}
	@Override
	public void update(Window window, Chroma chroma){
		for(GUIElement element : elements) element.update(window, chroma);
	}
}
