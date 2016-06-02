package io.github.projectchroma.chroma.gui.util;

import org.newdawn.slick.geom.Rectangle;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.util.RectangleUtils;

public abstract class AbstractGrid extends GUIElement{
	public static final int LEFT_COLUMN = 0, RIGHT_COLUMN = 1, FULL_WIDTH = 2;
	protected float top, columnWidth, rowHeight, margin;
	protected static final float GRID_TOP = 50, COLUMN_WIDTH = 300, ROW_HEIGHT = 50, MARGIN = 10;
	protected static final float centerX = Chroma.WINDOW_WIDTH / 2, leftX = centerX - (COLUMN_WIDTH + MARGIN), rightX = centerX + (COLUMN_WIDTH + MARGIN);
	
	public AbstractGrid(){this(0, 0, 0, 0);}
	public AbstractGrid(float top){this(top, 0, 0, 0);}
	public AbstractGrid(float top, float columnWidth){this(top, columnWidth, 0, 0);}
	public AbstractGrid(float top, float columnWidth, float rowHeight){this(top, columnWidth, rowHeight, 0);}
	public AbstractGrid(float top, float columnWidth, float rowHeight, float margin){
		this.top = top == 0 ? GRID_TOP : top;
		this.columnWidth = columnWidth == 0 ? COLUMN_WIDTH : columnWidth;
		this.rowHeight = rowHeight == 0 ? ROW_HEIGHT : rowHeight;
		this.margin = margin == 0 ? MARGIN : margin;
	}
	public Rectangle area(int row, int column){
		float cy = top + row * (rowHeight + margin);
		if(column == LEFT_COLUMN) return RectangleUtils.fromDimensions(leftX, cy - rowHeight/2, columnWidth, rowHeight);
		else if(column == RIGHT_COLUMN) return RectangleUtils.fromDimensions(rightX, cy - rowHeight/2, -columnWidth, rowHeight);
		else if(column == FULL_WIDTH) return RectangleUtils.fromCenter(centerX, cy, 2 * (columnWidth + margin), rowHeight);
		else throw new IllegalArgumentException("Unknown column " + column);
	}
}
