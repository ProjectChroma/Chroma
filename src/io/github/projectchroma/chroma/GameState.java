package io.github.projectchroma.chroma;

import java.awt.Graphics;

public abstract class GameState{
	protected final int id;
	protected GameState(int id){
		this.id = id;
	}
	public int getID(){
		return id;
	}
	
	public abstract void initialize(Window window, Chroma chroma);
	public abstract void update(Window window, Chroma chroma);
	public abstract void render(Window window, Chroma chroma, Graphics g);
	public void enter(Window window, Chroma chroma){}
	public void leave(Window window, Chroma chroma){}
}
