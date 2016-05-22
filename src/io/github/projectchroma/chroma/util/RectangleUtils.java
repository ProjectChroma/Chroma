package io.github.projectchroma.chroma.util;

import java.awt.Rectangle;

public class RectangleUtils{
	public static Rectangle fromVertices(int x1, int y1, int x2, int y2){
		return fromDimensions(x1, y1, x2 - x1, y2 - y1);
	}
	public static Rectangle fromDimensions(int left, int top, int width, int height){
		if(width < 0){
			width = -width;
			left -= width;
		}
		if(height < 0){
			height = -height;
			top -= height;
		}
		return new Rectangle(left, top, width, height);
	}
	public static Rectangle fromCenter(int cx, int cy, int width, int height){
		return fromDimensions(cx - width / 2, cy - height / 2, width, height);
	}
	public static Rectangle grow(Rectangle rect, int addition){
		return grow(rect, addition, addition, addition, addition);
	}
	public static Rectangle grow(Rectangle rect, int x, int y){
		return grow(rect, x, x, y, y);
	}
	public static Rectangle grow(Rectangle rect, int left, int right, int top, int bottom){
		return fromVertices(rect.x - left, rect.y - top, rect.x + rect.width + right, rect.y + rect.height + bottom);
	}
}
