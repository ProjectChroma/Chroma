package io.github.projectchroma.chroma.gui.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;

public abstract class GUIState extends BaseGameState{
	protected static final Boolean left = true, right = false, center = null;
	
	protected List<GUIElement> elements = new ArrayList<>();
	protected Color bg;
	protected GUIState(int id){this(id, Color.white);}
	protected GUIState(int id, Color bg){
		super(id);
		this.bg = bg;
		for(GUIElement element : elements) add(element);
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
