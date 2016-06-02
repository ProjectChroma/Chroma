package io.github.projectchroma.chroma.util;

import org.newdawn.slick.geom.Rectangle;

public class RectangleUtils{
	public static Rectangle fromVertices(float x1, float y1, float x2, float y2){
		return fromDimensions(x1, y1, x2 - x1, y2 - y1);
	}
	public static Rectangle fromDimensions(float left, float top, float width, float height){
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
	public static Rectangle fromCenter(float cx, float cy, float width, float height){
		return fromDimensions(cx - width / 2, cy - height / 2, width, height);
	}
	public static Rectangle grow(Rectangle rect, float addition){
		return grow(rect, addition, addition, addition, addition);
	}
	public static Rectangle grow(Rectangle rect, float x, float y){
		return grow(rect, x, x, y, y);
	}
	public static Rectangle grow(Rectangle rect, float left, float right, float top, float bottom){
		return fromVertices(rect.getMinX() - left, rect.getMinY() - top, rect.getMaxX() + right, rect.getMaxY() + bottom);
	}
	public static Rectangle clone(Rectangle rect){
		return new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
}
