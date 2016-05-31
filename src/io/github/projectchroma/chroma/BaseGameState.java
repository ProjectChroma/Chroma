package io.github.projectchroma.chroma;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.util.FileIO;

public abstract class BaseGameState implements GameState{
	protected final int id;
	protected BaseGameState(int id){
		this.id = id;
	}
	@Override
	public int getID(){
		return id;
	}
	
	protected Music getMusic(){
		return Sounds.getMenuMusic();
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		if(getMusic() != null){
			if(!getMusic().equals(Sounds.getCurrentMusic())){
				if(Sounds.getCurrentMusic() != null) Sounds.getCurrentMusic().stop();
				getMusic().play();
			}
		}else{
			if(Sounds.getCurrentMusic() != null)
				Sounds.getCurrentMusic().stop();
		}
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{}
	
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
	public void keyPressed(int key, char c){
		if(key == Input.KEY_F2){
			try{
				Image target = new Image(Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);
				Chroma.instance().getContainer().getGraphics().copyArea(target, 0, 0);
				File file = FileIO.saveScreenshot(target);
				System.out.println("Saved screenshot to " + file);
			}catch(SlickException ex){
				System.err.println("Error taking screenshot");
				ex.printStackTrace();
			}
		}
	}
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
