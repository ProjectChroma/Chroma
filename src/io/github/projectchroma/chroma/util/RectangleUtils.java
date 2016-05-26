package io.github.projectchroma.chroma.util;

public class RectangleUtils{
	public static BoundingBox fromVertices(float x1, float y1, float x2, float y2){
		return fromDimensions(x1, y1, x2 - x1, y2 - y1);
	}
	public static BoundingBox fromDimensions(float left, float top, float width, float height){
		if(width < 0){
			width = -width;
			left -= width;
		}
		if(height < 0){
			height = -height;
			top -= height;
		}
		return new BoundingBox(left, top, width, height);
	}
	public static BoundingBox fromCenter(float cx, float cy, float width, float height){
		return fromDimensions(cx - width / 2, cy - height / 2, width, height);
	}
	public static BoundingBox grow(BoundingBox rect, float addition){
		return grow(rect, addition, addition, addition, addition);
	}
	public static BoundingBox grow(BoundingBox rect, float x, float y){
		return grow(rect, x, x, y, y);
	}
	public static BoundingBox grow(BoundingBox rect, float left, float right, float top, float bottom){
		return fromVertices(rect.getLeft() - left, rect.getTop() - top, rect.getRight() + right, rect.getBottom() + bottom);
	}
}
