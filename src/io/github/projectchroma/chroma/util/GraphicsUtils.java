package io.github.projectchroma.chroma.util;

import java.awt.Graphics;
import java.awt.Rectangle;

public class GraphicsUtils{
	public static int width(Graphics g, String text){
		return g.getFontMetrics().stringWidth(text);
	}
	public static int height(Graphics g){
		return g.getFontMetrics().getHeight();
	}
	public static void fill(Graphics g, Rectangle rect){
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	public static void fill(Graphics g, BoundingBox bounds){
		g.fillRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
	}
}
