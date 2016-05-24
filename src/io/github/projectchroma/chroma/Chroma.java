package io.github.projectchroma.chroma;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.resource.Resources;

public class Chroma{
	public static final int NUM_LEVELS = 11;
	private static Chroma instance;
	
	private GameState state, prevState;
	protected Window window;
	private Map<Integer, GameState> states = new HashMap<>();
	private Chroma(){
		addState(new MainMenuState());
	}
	private void addState(GameState state){
		states.put(state.getID(), state);
		if(this.state == null) this.state = state;
	}
	private void initialize(){
		for(GameState state : states.values()){
			state.initialize(window, this);
		}
	}
	public GameState getCurrentState(){
		return state;
	}
	public void enterState(int id){
		enterState(states.get(id));
	}
	public void enterState(GameState state){
		prevState = this.state;
		prevState.leave(this.window, this);
		this.state = state;
		this.state.enter(this.window, this);
	}
	public static void main(String[] args) throws IOException{
		instance = new Chroma();
		instance.window = new Window(instance);
		
		//TODO loading screen
		while(Resources.getRemainingResources() > 0){
			Resources.getNextResource().load();
		}
		
		instance.initialize();
		instance.window.startTicking();
	}
}
