package io.github.projectchroma.chroma.gui.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Resources;

public abstract class GUIState extends BaseGameState{
	protected static Music music;
	protected static final Boolean left = true, right = false, center = null;
	
	protected List<GUIElement> elements = new ArrayList<>();
	protected Color bg;
	protected GUIState(){this(Color.white);}
	protected GUIState(Color bg){
		this.bg = bg;
		for(GUIElement element : elements) add(element);
	}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		if(music == null) music = Resources.loadMusic(Resources.getSoundPath("menuMusic.aiff"));
	}
	protected void add(GUIElement element){
		elements.add(element);
	}
	@Override protected Music getMusic(){return music;}
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
	public static Music getMenuMusic(){return music;}
}
