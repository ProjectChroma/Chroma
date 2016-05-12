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
}
