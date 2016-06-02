package io.github.projectchroma.chroma.gui.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PaginatedGrid extends AbstractGrid{
	private List<List<GUIElement>> pages = new ArrayList<>();
	private int page = 0;
	public PaginatedGrid(){super();}
	public PaginatedGrid(float top){super(top);}
	public PaginatedGrid(float top, float columnWidth){super(top, columnWidth);}
	public PaginatedGrid(float top, float columnWidth, float rowHeight){super(top, columnWidth, rowHeight);}
	public PaginatedGrid(float top, float columnWidth, float rowHeight, float margin){super(top, columnWidth, rowHeight, margin);}
	public GUIElement add(GUIElement element, int page){
		while(pages.size() <= page) pages.add(new ArrayList<>());
		pages.get(page).add(element);
		return element;
	}
	public int getPage(){return page;}
	public int setPage(int page){
		while(page < 0) page += pages.size();
		while(page >= pages.size()) page -= pages.size();
		return this.page = page;
	}
	public int nextPage(){return setPage(page + 1);}
	public int prevPage(){return setPage(page - 1);}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		for(GUIElement element : pages.get(page)) element.render(container, game, g);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		for(GUIElement element : pages.get(page)) element.update(container, game, delta);
	}
}
