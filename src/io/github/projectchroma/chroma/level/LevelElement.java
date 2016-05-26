package io.github.projectchroma.chroma.level;

import java.awt.Color;
import java.awt.Graphics;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.util.BoundingBox;
import io.github.projectchroma.chroma.util.GraphicsUtils;

public abstract class LevelElement{
	protected BoundingBox bounds;
	protected Color color, scheme;
	protected LevelElement(float x, float y, float width, float height){
		this(x, y, width, height, null);
	}
	protected LevelElement(float x, float y, float width, float height, Color color){
		this(x, y, width, height, color, null);
	}
	protected LevelElement(float x, float y, float width, float height, Color color, Color scheme){
		this.bounds = new BoundingBox(x, y, width, height);
		this.color = color;
		this.scheme = scheme;
	}
	public void init(Window window, Chroma chroma){}
	public abstract void update(Window window, Chroma chroma, LevelState level);
	public void render(Window window, Chroma chroma, LevelState level, Graphics g){
		if(!doRender(level)) return;
		g.setColor(getColor(level));
		GraphicsUtils.fill(g, bounds);
	}
	public BoundingBox getBounds(){
		return bounds;
	}
	public boolean isTangible(LevelState level){
		return doRender(level);
	}
	public boolean doRender(LevelState level){
		return !getColor(level).equals(level.background()) &&//Color is not the same as the current background color, and
				(scheme == null || scheme.equals(level.background()));//the element belongs in the current scheme
	}
	public Color getColor(LevelState level){
		return color == null ? level.foreground() : color;
	}
	public String toString(){
		return getClass().getCanonicalName() + "@(" + bounds.getTop() + "," + bounds.getLeft() + ")[" + bounds.getWidth() + "x" + bounds.getHeight() + "]";
	}
}
