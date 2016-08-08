package io.github.projectchroma.chroma;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.util.FileIO;

public abstract class BaseGameState implements GameState{
	protected static Keybind keyScreenshot, keyVidcap;
	private static int idCounter = 0;
	protected final int id;
	protected BaseGameState(){
		this.id = idCounter++;
	}
	@Override
	public int getID(){
		return id;
	}
	
	protected abstract Music getMusic();
	
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
		if(keyScreenshot.isPressed() || keyVidcap.isDown()){
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
	public final void init(GameContainer container, StateBasedGame game) throws SlickException{}
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{
		if(keyScreenshot == null) keyScreenshot = Keybind.get("ui.screenshot", Input.KEY_F2);
		if(keyVidcap == null) keyVidcap = Keybind.get("ui.vidcap", Input.KEY_F3);
	}
	public void postInit(GameContainer container, StateBasedGame game) throws SlickException{}
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
