package io.github.projectchroma.chroma.util;

public class BoundingBox{
	private float x, y, width, height;
	public BoundingBox(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getLeft(){
		return x;
	}
	public float getRight(){
		return x + width;
	}
	public float getTop(){
		return y;
	}
	public float getBottom(){
		return y + height;
	}
	public float getCenterX(){
		return x + width/2;
	}
	public float getCenterY(){
		return y + height/2;
	}
	public float getWidth(){
		return width;
	}
	public float getHeight(){
		return height;
	}
	public boolean intersects(BoundingBox other){
		if ((x > (other.x + other.width)) || ((x + width) < other.x)) {
			return false;
		}
		if ((y > (other.y + other.height)) || ((y + height) < other.y)) {
			return false;
		}
        return true;
	}
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public void setLocation(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void setLeft(float left){
		x = left;
	}
	public void setRight(float right){
		x = right - width;
	}
	public void setTop(float top){
		y = top;
	}
	public void setBottom(float bottom){
		y = bottom - height;
	}
	public void setCenterX(float x){
		this.x = x - width/2;
	}
	public void setCenterY(float y){
		this.y = y - height/2;
	}
	public void setWidth(float width){
		this.width = width;
	}
	public void setHeight(float height){
		this.height = height;
	}
	public void translate(float dx, float dy){
		x += dx;
		y += dy;
	}
	public boolean contains(float x, float y){
		return getLeft() <= x && x <= getRight() && getTop() <= y && y <= getBottom();
	}
	public BoundingBox clone(){
		return new BoundingBox(x, y, width, height);
	}
}
