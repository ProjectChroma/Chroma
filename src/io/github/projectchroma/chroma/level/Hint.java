package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.Chroma;

public class Hint extends LevelElement{
	private String text;
	private float x, y;
	private Font font;
	public Hint(String text, float x, float y) throws SlickException{
		this(text, null, x, y);
	}
	public Hint(String text, Color color, float x, float y) throws SlickException{
		super(x, y, 0, 0, color);
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = Chroma.instance().createFont(18);
	}
	@Override
	public boolean isTangible(){return false;}
	@Override public void update(GameContainer container, int delta) throws SlickException{}
	@Override
	public void render(GameContainer container, Graphics g){
		g.setFont(font);
		g.setColor(getColor());
		g.drawString(text, x, y);
	}
}
