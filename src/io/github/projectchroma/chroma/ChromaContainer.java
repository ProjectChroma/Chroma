package io.github.projectchroma.chroma;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class ChromaContainer extends AppGameContainer{
	public ChromaContainer(Game game) throws SlickException{
		super(game, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT, false);//Width, height, fullscreen
	}
	@Override
	protected void setup() throws SlickException{
		super.setup();
		Chroma game = (Chroma)this.game;
		for(BaseGameState state : game.states()) state.initialize(this, game);
		for(BaseGameState state : game.states()) state.postInit(this, game);
	}
	@Override
	public void reinit() throws SlickException{
		super.reinit();
		Chroma game = (Chroma)this.game;
		for(BaseGameState state : game.states()) state.initialize(this, game);
		for(BaseGameState state : game.states()) state.postInit(this, game);
	}
}
