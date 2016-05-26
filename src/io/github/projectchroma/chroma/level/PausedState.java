package io.github.projectchroma.chroma.level;

import java.awt.Color;
import java.awt.Graphics;

import com.sun.glass.events.KeyEvent;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.GameState;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.gui.Button;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.BoundingBox;
import io.github.projectchroma.chroma.util.Colors;
import io.github.projectchroma.chroma.util.GraphicsUtils;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class PausedState extends GameState{
	public static final int ID = -3;
	public static final PausedState instance = new PausedState(ID);
	public static final Color BACKGROUND = new Color(0.5F, 0.5F, 0.5F, 0.7F);
	private LevelState level;
	private BoundingBox area;
	private final float yStart, yEnd;
	private FontResource titleFont, textFont;
	private Button resume, restart, exit;
	private PausedState(int id){
		super(id);
		area = new BoundingBox(0, 0, 400, 300);
		area.setCenterX(Window.WINDOW_WIDTH / 2);
		yStart = -area.getHeight() / 2;//Just offscreen above
		yEnd = Window.WINDOW_HEIGHT / 2;
		area.setCenterY(yStart);
		
		titleFont = Resources.getFont("msyteron", 45);
		textFont = Resources.getFont("mysteron", 24);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		float buttonWidth = area.getWidth() - 40;
		resume = new Button(new BoundingBox(0, 0, (buttonWidth - 10) / 2, 50), "Resume", textFont.get(), Colors.DARK_GOLD){
			public void onclick(){
//				chroma.enterState(level.getID(), new Leave(), null);
				chroma.enterState(level);
			}
		};
		resume.getArea().setRight(area.getCenterX() - 5);
		restart = new Button(new BoundingBox(0, 0, (buttonWidth - 10) / 2, 50), "Restart", textFont.get(), Colors.DARK_BLUE){
			public void onclick(){
//				chroma.enterState(level.getID(), new Leave(true), new SwipeTransition(SwipeTransition.RIGHT));
				chroma.enterState(level);
			}
		};
		restart.getArea().setLeft(area.getCenterX() + 5);
		exit = new Button(new BoundingBox(0, 0, buttonWidth, 50), "Exit to Main Menu", textFont.get(), Colors.DARK_RED){
			public void onclick(){
//				chroma.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.LEFT));
				chroma.enterState(MainMenuState.ID);
			}
		};
		exit.getArea().setCenterX(area.getCenterX());
	}
	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		level.render(window, chroma, g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
		g.setColor(Color.white);
		GraphicsUtils.fill(g, area);
		BoundingBox contentArea = RectangleUtils.grow(area, -20, -30);
		g.setColor(new Color(0.9F, 0.9F, 0.9F));
		GraphicsUtils.fill(g, contentArea);
		g.setColor(Color.black);
		g.setFont(titleFont.get());
		g.drawString("Paused", (int)(area.getCenterX() - GraphicsUtils.width(g, "Paused") / 2), (int)contentArea.getTop());
		
		resume.getArea().setBottom(contentArea.getBottom() - 60);
		resume.render(window, chroma, g);
		
		restart.getArea().setTop(resume.getArea().getTop());
		restart.render(window, chroma, g);
		
		exit.getArea().setBottom(contentArea.getTop());
		exit.render(window, chroma, g);
	}
	
	@Override
	public void update(Window window, Chroma chroma){
		if(window.getInput().isKeyPressed(KeyEvent.VK_P))
//			chroma.enterState(level.getID(), new Leave(), null);
			chroma.enterState(level);
		resume.update(window, chroma);
		restart.update(window, chroma);
		exit.update(window, chroma);
	}
	
//	public static class Enter implements Transition{
//		private static final float LENGTH = 500;//ms
//		private float rate = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS;
//		@Override
//		public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
//			instance.area.setY(instance.area.getY() + rate);
//		}
//		@Override
//		public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
//		@Override
//		public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
//		
//		@Override
//		public boolean isComplete(){
//			return instance.area.getCenterY() >= instance.yEnd;
//		}
//		@Override
//		public void init(GameState firstState, GameState secondState){
//			instance.level = (LevelState)secondState;//Previous state
//			instance.area.setCenterY(instance.yStart);
//		}
//	}
//	public static class Leave implements Transition{
//		private static final float LENGTH = 500;//ms
//		private float rate = Chroma.WINDOW_WIDTH / (LENGTH / 1000) / Chroma.FPS;
//		private GameState newState;
//		private boolean restart;
//		public Leave(){this(false);}
//		public Leave(boolean restart){
//			this.restart = restart;
//		}
//		@Override
//		public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException{
//			
//			instance.area.setY(instance.area.getY() - rate);
//		}
//		@Override
//		public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
//		@Override
//		public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException{}
//		
//		@Override
//		public boolean isComplete(){
//			if(instance.area.getCenterY() <= instance.yStart){
//				if(restart && newState instanceof LevelState) ((LevelState)newState).restart();
//				return true;
//			}else{
//				return false;
//			}
//		}
//		@Override
//		public void init(GameState firstState, GameState secondState){
//			instance.area.setCenterY(instance.yEnd);
//			newState = secondState;
//		}
//	}
//	@Override
//	protected Music getMusic(){
//		return Sounds.getLevelMusic();
//	}
}
