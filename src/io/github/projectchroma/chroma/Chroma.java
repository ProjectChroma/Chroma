package io.github.projectchroma.chroma;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import io.github.projectchroma.chroma.gui.LevelSelectState;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.SettingsMenuState;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.resource.Resources;

public class Chroma{
	public static final int NUM_LEVELS = 11;
	public static final Gson GSON = new Gson();
	private static Chroma instance;
	
	private GameState state, prevState;
	protected Window window;
	private Map<Integer, GameState> states = new HashMap<>();
	private Chroma(){
		addState(new MainMenuState());
		addState(new LevelSelectState());
		addState(new SettingsMenuState());
		for(int i=1; i<=NUM_LEVELS; ++i)
			addState(new LevelState(i));
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
	public GameState getState(int id){
		return states.get(id);
	}
	public GameState getCurrentState(){
		return state;
	}
	public GameState getPreviousState(){
		return prevState;
	}
	public void enterState(int id){
		try{
			enterState(states.get(id));
		}catch(IllegalArgumentException ex){
			throw new IllegalArgumentException("No state registered with ID " + id);
		}
	}
	public void enterState(GameState state){
		if(state == null) throw new IllegalArgumentException("Cannot enter null state");
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
//		instance.window.startTicking();
		
		while(true){
			instance.getCurrentState().update(instance.window, instance);
			instance.window.repaint();
			instance.window.getInput().clearPresses();
		}
	}
}
