package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.util.GraphicsUtils;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class Button extends GUIElement{
	private Rectangle area;
	private String text;
	private Font font;
	private Color bg, border, bgHover, fg;
	private boolean mouseOver = false, mouseDown = false;
	public Button(Rectangle area, String text){
		this(area, text, null, null, null, null, null);
	}
	public Button(Rectangle area, String text, Color bgHover){
		this(area, text, null, null, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bgHover){
		this(area, text, font, null, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bg, Color bgHover){
		this(area, text, font, bg, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bg, Color border, Color bgHover, Color fg){
		this.area = area;
		this.text = text;
		this.font = font;
		this.bg = bg != null ? bg : Color.gray;
		this.border = border != null ? border : this.bg.darker();
		this.bgHover = bgHover != null ? bgHover : this.bg.brighter();
		this.fg = fg != null ? fg : Color.white;
	}
	public void render(Window window, Chroma chroma, Graphics g){
		g.setColor(border);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(mouseOver ? bgHover : bg);
		GraphicsUtils.fill(g, RectangleUtils.grow(area, -3));
		
		g.setFont(font);
		g.setColor(fg);
		g.drawString(text, getCenterX() - GraphicsUtils.width(g, text)/2, getCenterY() - GraphicsUtils.height(g)/2);
	}
	public void update(Window window, Chroma chroma){
		mouseOver = area.contains(window.getInput().getMouseX(), window.getInput().getMouseY());
		boolean oldMouseDown = mouseDown;
		mouseDown = mouseOver && window.getInput().isMousePressed(MouseEvent.BUTTON1);
		System.out.println(text + ": " + mouseDown);
		if(oldMouseDown && !mouseDown) onclick();//Activate on release to switching to a screen and immediately pressing a button
	}
	public abstract void onclick();
	
	public int getLeft(){
		return area.x;
	}
	public int getTop(){
		return area.y;
	}
	public int getCenterX(){
		return area.x + area.width/2;
	}
	public int getCenterY(){
		return area.y + area.height/2;
	}
	public int getRight(){
		return area.x + area.width;
	}
	public int getBottom(){
		return area.y + area.height;
	}
	public int getWidth(){
		return area.width;
	}
	public float getHeight(){
		return area.height;
	}
	public void setLeft(int x){
		area.x = x;
	}
	public void setTop(int y){
		area.y = y;
	}
	public void setCenterX(int x){
		area.x = x - area.width/2;
	}
	public void setCenterY(int y){
		area.y = y - area.height/2;
	}
	public void setRight(int x){
		area.x = x - area.width;
	}
	public void setBottom(int y){
		area.y = y - area.height;
	}
	public void setWidth(int width){
		area.width = width;
	}
	public void setHeight(int height){
		area.height = height;
	}
}
