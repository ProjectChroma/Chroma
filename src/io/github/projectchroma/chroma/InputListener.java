package io.github.projectchroma.chroma;

public abstract class InputListener{
	public void keyPressed(char key, int codePoint){}
	public void keyReleased(char key, int codePoint){}
	public void mousePressed(int button, int count){}
	public void mouseReleased(int button){}
	public void mouseScrolled(int amount){}
}
