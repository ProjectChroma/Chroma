package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RenderedImage extends GUIElement{
	private float cx, cy, width, height, u1, v1, u2, v2;
	private Image image;
	public RenderedImage(Image image, float cx, float cy){
		this(image, cx, cy, image.getWidth(), image.getHeight());
	}
	public RenderedImage(Image image, float cx, float cy, float width, float height){
		this(image, cx, cy, width, height, 0, 0, image.getWidth(), image.getHeight());
	}
	public RenderedImage(Image image, float cx, float cy, float width, float height, float u1, float v1, float u2, float v2){
		this.image = image;
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;
	}
	public float getLeft(){return cx - width/2;}
	public float getRight(){return cx + width/2;}
	public float getTop(){return cy - height/2;}
	public float getBottom(){return cy + height/2;}
	public float getCenterX(){return cx;}
	public float getCenterY(){return cy;}
	public float getWidth(){return width;}
	public float getHeight(){return height;}
	
	public void setLeft(float left){cx = left + width/2;}
	public void setRight(float right){cx = right - width/2;}
	public void setTop(float top){cy = top + height/2;}
	public void setBottom(float bottom){cy = bottom - height/2;}
	public void setCenterX(float x){cx = x;}
	public void setCenterY(float y){cy = y;}
	public void setWidth(float w){width = w;}
	public void setHeight(float h){height = h;}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.drawImage(image, cx - width / 2, cy - height / 2, cx + width / 2, cy + height / 2, u1, v1, u2, v2);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{}
}
