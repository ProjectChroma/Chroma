package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.gui.util.BackButton;
import io.github.projectchroma.chroma.gui.util.Button;
import io.github.projectchroma.chroma.gui.util.BasicGrid;
import io.github.projectchroma.chroma.gui.util.GUIState;
import io.github.projectchroma.chroma.gui.util.RenderedImage;
import io.github.projectchroma.chroma.gui.util.RenderedText;
import io.github.projectchroma.chroma.level.block.GoalBlock;
import io.github.projectchroma.chroma.level.block.PushBlock;
import io.github.projectchroma.chroma.level.block.SlowBlock;
import io.github.projectchroma.chroma.util.Direction;

public class MainMenuState extends GUIState{
	public static final int ID = 0;
	public MainMenuState(){
		super(ID);
	}
	@Override
	public void initialize(final GameContainer container, final StateBasedGame game) throws SlickException{
		super.initialize(container, game);
		RenderedImage logo = new RenderedImage(Resources.loadImage("logo.png"), 0, 100, 100, 100);
		RenderedText title = new RenderedText("Chroma", Chroma.instance().createFont(50F), 100, 100, Color.black);
		float totalWidth = logo.getWidth() + 20 + title.getWidth();
		logo.setLeft((Chroma.WINDOW_WIDTH - totalWidth)/2);
		title.setRight((Chroma.WINDOW_WIDTH+ totalWidth)/2);
		add(logo);
		add(title);
		
		BasicGrid grid = new BasicGrid();
		add(grid);
		grid.add(new Button(grid.area(4, BasicGrid.FULL_WIDTH), "Play", GoalBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(LevelSelectState.instance().getID(), null, new SwipeTransition(Direction.RIGHT));
			}
		});
		grid.add(new Button(grid.area(5, BasicGrid.FULL_WIDTH), "Settings", SlowBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(SettingsMenuState.ID, null, new SwipeTransition(Direction.LEFT));
			}
		});
		grid.add(new Button(grid.area(6, BasicGrid.FULL_WIDTH), "Credits", PushBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(CreditsState.ID, null, new SwipeTransition(Direction.DOWN));
			}
		});
		grid.add(new BackButton(0, null, "Exit"){
			public void onclick(){
				container.exit();
			}
		});
	}
}
