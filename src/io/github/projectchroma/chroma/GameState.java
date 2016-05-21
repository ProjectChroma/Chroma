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
	
	public abstract void update(Chroma chroma);
	public abstract void render(Chroma chroma, Graphics g);
	public abstract void enter(Chroma chroma);
	public abstract void leave(Chroma chroma);
}
