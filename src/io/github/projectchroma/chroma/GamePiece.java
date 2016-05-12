package io.github.projectchroma.chroma;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class GamePiece{
	protected Shape bounds;
	protected GamePiece(float x, float y, float width, float height){
		this(new Rectangle(x, y, width, height));
	}
	protected GamePiece(Shape bounds){
		this.bounds = bounds;
	}
	public abstract void update(GameContainer container, int delta) throws SlickException;
	public abstract void render(GameContainer container, Graphics g) throws SlickException;
	// Getters
	public float getLeft(){
		return bounds.getMinX();
	}
	public float getTop(){
		return bounds.getMinY();
	}
	public float getCenterX(){
		return bounds.getCenterX();
	}
	public float getCenterY(){
		return bounds.getCenterY();
	}
	public float getRight(){
		return bounds.getMaxX();
	}
	public float getBottom(){
		return bounds.getMaxY();
	}
	public float getWidth(){
		return bounds.getWidth();
	}
	public float getHeight(){
		return bounds.getHeight();
	}
	public Shape getBounds(){
		return bounds;
	}
	
	// Utilities for moving around
	protected void translate(float dx, float dy){
		bounds.setLocation(bounds.getX() + dx, bounds.getY() + dy);
	}
	protected void setLeft(float x){
		bounds.setX(x);
	}
	protected void setTop(float y){
		bounds.setY(y);
	}
	protected void setCenterX(float x){
		bounds.setCenterX(x);
	}
	protected void setCenterY(float y){
		bounds.setCenterY(y);
	}
	protected void setRight(float x){
		bounds.setX(x - bounds.getWidth());
	}
	protected void setBottom(float y){
		bounds.setY(y - bounds.getHeight());
	}
	
	public String toString(){
		return getClass().getCanonicalName() + "@(" + getCenterX() + "," + getCenterY() + ")["
				+ getWidth() + "x" + getHeight() + "]";
	}
}
