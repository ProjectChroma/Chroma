package io.github.projectchroma.chroma;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class GamePiece {
	protected float x, y, width, height;
	protected GamePiece(){this(0, 0);}
	protected GamePiece(float x, float y){this(x, y, 100, 100);}
	protected GamePiece(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract void update(GameContainer container, int delta) throws SlickException;
	public abstract void render(GameContainer container, Graphics g) throws SlickException;
	public float getX(){return x;}
	public float getY(){return y;}
	public float getWidth(){return width;}
	public float getHeight(){return height;}
	public String toString(){
		return getClass().getCanonicalName() + "@(" + x + "," + y + ")[" + width + "x" + height + "]";
	}
}
