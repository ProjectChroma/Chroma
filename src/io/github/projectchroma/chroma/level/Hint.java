package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hint extends LevelElement{
	private String text;
	private float x, y;
	public Hint(String text, float x, float y){
		this(text, null, x, y);
	}
	public Hint(String text, Color color, float x, float y){
		super(null, color);
		this.text = text;
		this.x = x;
		this.y = y;
	}
	@Override public void update(GameContainer container, int delta) throws SlickException{}
	@Override
	public void render(GameContainer container, Graphics g){
		g.setColor(getColor());
		g.drawString(text, x, y);
	}
}
