package io.github.projectchroma.chroma;

public class Chroma{
	public static final int NUM_LEVELS = 11;
	private static Chroma instance;
	
	private GameState state, prevState;
	protected Window window;
	private Chroma(){
		
	}
	public GameState getCurrentState(){
		return state;
	}
	public void enterState(GameState state){
		prevState = this.state;
		prevState.leave(this);
		this.state = state;
		this.state.enter(this);
	}
	public static void main(String[] args){
		instance = new Chroma();
		instance.window = new Window(instance);
		instance.window.startTicking();
	}
}
