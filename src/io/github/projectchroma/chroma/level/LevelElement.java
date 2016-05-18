package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import io.github.projectchroma.chroma.Chroma;

public abstract class LevelElement{
	protected Rectangle bounds;
	protected Color color, scheme;
	protected LevelElement(float x, float y, float width, float height){
		this(x, y, width, height, null);
	}
	protected LevelElement(float x, float y, float width, float height, Color color){
		this(x, y, width, height, color, null);
	}
	protected LevelElement(float x, float y, float width, float height, Color color, Color scheme){
		this.bounds = new Rectangle(x, y, width, height);
		this.color = color;
		this.scheme = scheme;
	}
	public void init(GameContainer container) throws SlickException{}
	public abstract void update(GameContainer container, int delta) throws SlickException;
	public void render(GameContainer container, Graphics g) throws SlickException{
		if(!doRender()) return;
		g.setColor(getColor());
		g.fill(bounds);
	}
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
	public boolean isTangible(){
		if(getColor().equals(Chroma.instance().background())) return false;
		else return doRender();
	}
	protected boolean doRender(){
		return scheme == null || scheme.equals(Chroma.instance().background());
	}
	public Color getColor(){
		return color == null ? Chroma.instance().foreground() : color;
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
		return getClass().getCanonicalName() + "@(" + getTop() + "," + getLeft() + ")[" + getWidth() + "x" + getHeight() + "]";
	}
}
