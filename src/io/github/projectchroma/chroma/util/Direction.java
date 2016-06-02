package io.github.projectchroma.chroma.util;

public enum Direction{
	UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);
	public final float offsetX, offsetY;
	private Direction(float x, float y){
		offsetX = x;
		offsetY = y;
	}
	public Direction opposite(){
		return get(ordinal()+2);
	}
	public Direction toLeft(){
		return get(ordinal()-1);
	}
	public Direction toRight(){
		return get(ordinal()+1);
	}
	public boolean isHorizontal(){
		return this == LEFT || this == RIGHT;
	}
	public boolean isVertical(){
		return this == UP || this == DOWN;
	}
	private static Direction get(int index){
		Direction[] values = values();
		while(index < 0) index += values.length;
		return values[index % values.length];
	}
}
