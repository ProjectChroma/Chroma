package io.github.projectchroma.chroma.gui.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGrid extends AbstractGrid{
	protected List<GUIElement> elements = new ArrayList<>();
	public BasicGrid(){super();}
	public BasicGrid(float top){super(top);}
	public BasicGrid(float top, float columnWidth){super(top, columnWidth);}
	public BasicGrid(float top, float columnWidth, float rowHeight){super(top, columnWidth, rowHeight, 0);}
	public BasicGrid(float top, float columnWidth, float rowHeight, float margin){super(top, columnWidth, rowHeight, margin);}
	public GUIElement add(GUIElement element){
		elements.add(element);
		return element;
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		for(GUIElement button : elements) button.render(container, game, g);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		for(GUIElement element : elements) element.update(container, game, delta);
	}
}
