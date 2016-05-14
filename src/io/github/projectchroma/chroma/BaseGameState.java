package io.github.projectchroma.chroma;

import org.newdawn.slick.Input;
import org.newdawn.slick.state.GameState;

public abstract class BaseGameState implements GameState{
	protected int id;
	protected BaseGameState(int id){
		this.id = id;
	}
	@Override
	public int getID(){
		return id;
	}
	
	@Override
	public void mouseWheelMoved(int change){}
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount){}
	@Override
	public void mousePressed(int button, int x, int y){}
	@Override
	public void mouseReleased(int button, int x, int y){}
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy){}
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy){}
	
	@Override
	public void setInput(Input input){}
	@Override
	public boolean isAcceptingInput(){
		return true;
	}
	@Override
	public void inputEnded(){}
	@Override
	public void inputStarted(){}
	
	@Override
	public void keyPressed(int key, char c){}
	@Override
	public void keyReleased(int key, char c){}
	@Override
	public void controllerLeftPressed(int controller){}
	
	@Override
	public void controllerLeftReleased(int controller){}
	@Override
	public void controllerRightPressed(int controller){}
	@Override
	public void controllerRightReleased(int controller){}
	@Override
	public void controllerUpPressed(int controller){}
	@Override
	public void controllerUpReleased(int controller){}
	@Override
	public void controllerDownPressed(int controller){}
	@Override
	public void controllerDownReleased(int controller){}
	@Override
	public void controllerButtonPressed(int controller, int button){}
	@Override
	public void controllerButtonReleased(int controller, int button){}
}
