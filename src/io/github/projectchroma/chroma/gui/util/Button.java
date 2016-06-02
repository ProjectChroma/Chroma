package io.github.projectchroma.chroma.gui.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class Button extends GUIElement{
	private Rectangle area;
	private String text;
	protected Font font;
	protected Color bg, bgHover, fg;
	protected boolean mouseOver = false;
	protected boolean mouseDown = false;
	public Button(Rectangle area, String text) throws SlickException{
		this(area, text, null, null, null, null, null);
	}
	public Button(Rectangle area, String text, Color bgHover) throws SlickException{
		this(area, text, null, null, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bgHover) throws SlickException{
		this(area, text, font, null, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bg, Color bgHover) throws SlickException{
		this(area, text, font, bg, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bg, Color border, Color bgHover, Color fg) throws SlickException{
		this.area = area;
		this.text = text;
		this.font = font != null ? font : Chroma.instance().createFont(24);
		this.bg = bg != null ? bg : Color.gray;
		this.bgHover = bgHover != null ? bgHover : this.bg.brighter(0.5F);
		this.fg = fg != null ? fg : Color.white;
	}
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(getBackground().darker(0.4F));
		g.fill(area);
		g.setColor(getBackground());
		g.fill(RectangleUtils.grow(area, -3));
		
		g.setFont(font);
		g.setColor(fg);
		g.drawString(text, area.getCenterX() - font.getWidth(text)/2, area.getCenterY() - font.getHeight(text)/2);
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		mouseOver = area.contains(container.getInput().getMouseX(), container.getInput().getMouseY());
		boolean oldMouseDown = mouseDown;
		mouseDown = mouseOver && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		if(oldMouseDown && !mouseDown) onclick();//Activate on release to switching to a screen and immediately pressing a button
	}
	public Color getBackground(){return mouseOver ? bgHover : bg;}
	public abstract void onclick() throws SlickException;
	
	public float getLeft(){
		return area.getMinX();
	}
	public float getTop(){
		return area.getMinY();
	}
	public float getCenterX(){
		return area.getCenterX();
	}
	public float getCenterY(){
		return area.getCenterY();
	}
	public float getRight(){
		return area.getMaxX();
	}
	public float getBottom(){
		return area.getMaxY();
	}
	public float getWidth(){
		return area.getWidth();
	}
	public float getHeight(){
		return area.getHeight();
	}
	public String getText(){
		return text;
	}
	
	public void setLeft(float x){
		area.setX(x);
	}
	public void setTop(float y){
		area.setY(y);
	}
	public void setCenterX(float x){
		area.setCenterX(x);
	}
	public void setCenterY(float y){
		area.setCenterY(y);
	}
	public void setRight(float x){
		area.setX(x - area.getWidth());
	}
	public void setBottom(float y){
		area.setY(y - area.getHeight());
	}
	public void setWidth(float width){
		area.setWidth(width);
	}
	public void setHeight(float height){
		area.setHeight(height);
	}
	public void setText(String text){
		this.text = text;
	}
}
