package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Sounds;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.util.Button;
import io.github.projectchroma.chroma.level.block.GoalBlock;
import io.github.projectchroma.chroma.level.block.HazardBlock;
import io.github.projectchroma.chroma.level.block.SlowBlock;
import io.github.projectchroma.chroma.settings.Keybind;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class PausedState extends BaseGameState{
	public static final int ID = -3;
	public static final PausedState instance = new PausedState();
	public static final Color BACKGROUND = new Color(0.5F, 0.5F, 0.5F, 0.7F);
	private static Keybind pause;
	
	private LevelState level;
	private Rectangle area;
	private final float yStart, yEnd;
	private Font titleFont, textFont;
	private Button resume, restart, exit;
	private PausedState(){
		super(ID);
		area = new Rectangle(0, 0, 400, 300);
		area.setCenterX(Chroma.WINDOW_WIDTH / 2);
		yStart = -area.getHeight() / 2;//Just offscreen above
		yEnd = Chroma.WINDOW_HEIGHT / 2;
		area.setCenterY(yStart);
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		super.init(container, game);
		if(pause == null) pause = Keybind.get("level.unpause", Input.KEY_P);
		titleFont = Chroma.instance().createFont(45);
		textFont = Chroma.instance().createFont(24);
		float buttonWidth = area.getWidth() - 40;
		resume = new Button(new Rectangle(0, 0, (buttonWidth - 10) / 2, 50), "Resume", textFont, GoalBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(level.getID(), new Leave(), null);
			}
		};
		resume.setRight(area.getCenterX() - 5);
		restart = new Button(new Rectangle(0, 0, (buttonWidth - 10) / 2, 50), "Restart", textFont, SlowBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(level.getID(), new Leave(true), new SwipeTransition(Direction.RIGHT));
			}
		};
		restart.setLeft(area.getCenterX() + 5);
		exit = new Button(new Rectangle(0, 0, buttonWidth, 50), "Exit to Main Menu", textFont, HazardBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(Direction.LEFT));
			}
		};
		exit.setCenterX(area.getCenterX());
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		level.render(container, game, g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);
		g.setColor(Color.white);
		g.fill(area);
		Rectangle contentArea = RectangleUtils.grow(area, -20, -30);
		g.setColor(Color.white.darker(0.1F));
		g.fill(contentArea);
		g.setColor(Color.black);
		g.setFont(titleFont);
		g.drawString("Paused", area.getCenterX() - g.getFont().getWidth("Paused") / 2, contentArea.getMinY());
		
		resume.setBottom(contentArea.getMaxY() - 60);
		resume.render(container, game, g);
		
		restart.setTop(resume.getTop());
		restart.render(container, game, g);
		
		exit.setBottom(contentArea.getMaxY());
		exit.render(container, game, g);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(pause.isPressed()) game.enterState(level.getID(), new Leave(), null);
		resume.update(container, game, delta);
		restart.update(container, game, delta);
		exit.update(container, game, delta);
	}
	
	public static class Enter implements Transition{
		private static final float LENGTH = 500;//ms
		private float rate = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS;
		@Override
		public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
			instance.area.setY(instance.area.getY() + rate);
		}
		@Override
		public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
		@Override
		public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
		
		@Override
		public boolean isComplete(){
			return instance.area.getCenterY() >= instance.yEnd;
		}
		@Override
		public void init(GameState firstState, GameState secondState){
			instance.level = (LevelState)secondState;//Previous state
			instance.area.setCenterY(instance.yStart);
		}
	}
	public static class Leave implements Transition{
		private static final float LENGTH = 500;//ms
		private float rate = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS;
		private GameState newState;
		private boolean restart;
		public Leave(){this(false);}
		public Leave(boolean restart){
			this.restart = restart;
		}
		@Override
		public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
			
			instance.area.setY(instance.area.getY() - rate);
		}
		@Override
		public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
		@Override
		public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
		
		@Override
		public boolean isComplete(){
			if(instance.area.getCenterY() <= instance.yStart){
				if(restart && newState instanceof LevelState) ((LevelState)newState).restart();
				return true;
			}else{
				return false;
			}
		}
		@Override
		public void init(GameState firstState, GameState secondState){
			instance.area.setCenterY(instance.yEnd);
			newState = secondState;
		}
	}
	@Override
	protected Music getMusic(){
		return Sounds.getLevelMusic();
	}
}
