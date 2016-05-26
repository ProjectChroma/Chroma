package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.util.BoundingBox;
import io.github.projectchroma.chroma.util.GraphicsUtils;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class Button extends GUIElement{
	private BoundingBox area;
	private String text;
	private Font font;
	private Color bg, border, bgHover, fg;
	private boolean mouseOver = false, mouseDown = false;
	public Button(BoundingBox area, String text, Font font){
		this(area, text, font, null, null, null, null);
	}
	public Button(BoundingBox area, String text, Font font, Color bgHover){
		this(area, text, font, null, null, bgHover, null);
	}
	public Button(BoundingBox area, String text, Font font, Color bg, Color bgHover){
		this(area, text, font, bg, null, bgHover, null);
	}
	public Button(BoundingBox area, String text, Font font, Color bg, Color border, Color bgHover, Color fg){
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
		GraphicsUtils.fill(g, area);
		g.setColor(mouseOver ? bgHover : bg);
		GraphicsUtils.fill(g, RectangleUtils.grow(area, -3));
		
		g.setFont(font);
		g.setColor(fg);
		g.drawString(text, (int)(area.getCenterX() - GraphicsUtils.width(g, text)/2), (int)(area.getCenterY() + GraphicsUtils.height(g)/4));
	}
	public void update(Window window, Chroma chroma){
		mouseOver = area.contains(window.getInput().getMouseX(), window.getInput().getMouseY());
		boolean oldMouseDown = mouseDown;
		mouseDown = window.getInput().isMouseDown(MouseEvent.BUTTON1);
		if(mouseOver && oldMouseDown && !mouseDown) onclick();//Activate on release to switching to a screen and immediately pressing a button
	}
	public abstract void onclick();
	public BoundingBox getArea(){
		return area;
	}
}
