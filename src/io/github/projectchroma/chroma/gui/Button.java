package io.github.projectchroma.chroma.gui;

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
	private Font font;
	private Color bg, border, bgHover, fg;
	private boolean mouseOver = false, mouseDown = false;
	public Button(Rectangle area, String text) throws SlickException{
		this(area, text, null, null, null, null, null);
	}
	public Button(Rectangle area, String text, Color bgHover) throws SlickException{
		this(area, text, null, null, null, bgHover, null);
	}
	public Button(Rectangle area, String text, Font font, Color bg, Color border, Color bgHover, Color fg) throws SlickException{
		this.area = area;
		this.text = text;
		this.font = font != null ? font : Chroma.instance().createFont(24);
		this.bg = bg != null ? bg : Color.gray;
		this.border = border != null ? border : this.bg.darker();
		this.bgHover = bgHover != null ? bgHover : this.bg.brighter(0.5F);
		this.fg = fg != null ? fg : Color.white;
	}
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(border);
		g.fill(area);
		g.setColor(mouseOver ? bgHover : bg);
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
	public abstract void onclick() throws SlickException;
}
