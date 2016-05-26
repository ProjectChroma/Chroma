package io.github.projectchroma.chroma.level;

import java.awt.Color;
import java.awt.Graphics;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.Resources;

public class Hint extends LevelElement{
	private String text;
	private int x, y;
	private FontResource font;
	public Hint(String text, int x, int y){
		this(text, null, x, y);
	}
	public Hint(String text, Color color, int x, int y){
		super(x, y, 0, 0, color);
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = Resources.getFont("mysteron", 18);
	}
	@Override
	public boolean isTangible(LevelState level){return false;}
	@Override public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void render(Window window, Chroma chroma, LevelState level, Graphics g){
		g.setFont(font.get());
		g.setColor(getColor(level));
		g.drawString(text, x, y);
	}
}
