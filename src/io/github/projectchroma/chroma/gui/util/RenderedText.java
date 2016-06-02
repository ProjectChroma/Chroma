package io.github.projectchroma.chroma.gui.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RenderedText extends GUIElement{
	private String text;
	private Font font;
	private float cx, cy;
	private Color color;
	public RenderedText(String text, Font font, float x, float y){
		this(text, font, x, y, Color.black);
	}
	public RenderedText(String text, Font font, float cx, float cy, Color color){
		this.text = text;
		this.font = font;
		this.cx = cx;
		this.cy = cy;
		this.color = color;
	}
	public float getLeft(){return cx - getWidth()/2;}
	public float getRight(){return cx + getWidth()/2;}
	public float getTop(){return cy - getHeight()/2;}
	public float getBottom(){return cy + getHeight()/2;}
	public float getCenterX(){return cx;}
	public float getCenterY(){return cy;}
	public float getWidth(){return font.getWidth(text);}
	public float getHeight(){return font.getHeight(text);}
	public Font getFont(){return font;}
	
	public void setLeft(float left){cx = left + getWidth()/2;}
	public void setRight(float right){cx = right - getWidth()/2;}
	public void setTop(float top){cy = top + getHeight()/2;}
	public void setBottom(float bottom){cy = bottom - getHeight()/2;}
	public void setCenterX(float x){cx = x;}
	public void setCenterY(float y){cy = y;}
	public void setFont(Font f){font = f;}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, cx - g.getFont().getWidth(text)/2, cy - g.getFont().getHeight(text)/2);
		g.resetFont();
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{}
}
