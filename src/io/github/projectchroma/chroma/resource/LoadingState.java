package io.github.projectchroma.chroma.resource;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class LoadingState extends BaseGameState{
	public static final int ID = 0;
	private static final float BAR_WIDTH = Chroma.WINDOW_WIDTH - 200, BAR_HEIGHT = 50, BORDER = 5;
	private DeferredResource currentResource = null;
	private Rectangle loadingBar;
	public LoadingState(){
		super(ID);
		loadingBar = new Rectangle(0, 0, BAR_WIDTH, BAR_HEIGHT);
		loadingBar.setCenterX(Chroma.WINDOW_WIDTH/2);
		loadingBar.setCenterY(Chroma.WINDOW_HEIGHT/2);
	}
	@Override
	public void loadResources(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void initialize(GameContainer container, StateBasedGame game) throws SlickException{}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setBackground(Color.white);
		g.setColor(Color.gray);
		g.fill(loadingBar);
		Rectangle progress = RectangleUtils.grow(loadingBar, -BORDER);//Shrink by border amount
		int numResources = LoadingList.get().getTotalResources(), numLoaded = numResources - LoadingList.get().getRemainingResources();
		progress.setWidth(numLoaded * (BAR_WIDTH - 2 * BORDER) / numResources);
		g.setColor(Color.blue);
		g.fill(progress);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		currentResource = LoadingList.get().getNext();
		if(currentResource == null){
			game.enterState(MainMenuState.ID);
		}else{
			try{
				System.out.println("Loading " + currentResource.getDescription());
				currentResource.load();
			}catch(IOException ex){
				throw new SlickException("Error loading resource " + currentResource.getDescription(), ex);
			}
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		//Override without super() to prevent handling music (RuntimeException: Attempt to use deferred sound before loading)
	}
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		for(BaseGameState state : Chroma.instance().getStates()){
			state.initialize(container, game);
		}
	}
}
