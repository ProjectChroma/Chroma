package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.level.block.GoalBlock;
import io.github.projectchroma.chroma.level.block.SlowBlock;
import io.github.projectchroma.chroma.resource.Resources;

public class MainMenuState extends GUIState{
	public static final int ID = -1;
	private Font titleFont, buttonFont;
	public MainMenuState(){
		super(ID);
	}
	@Override
	public void loadResources(final GameContainer container, final StateBasedGame game) throws SlickException{
		titleFont = Resources.loadFont("mysteron.ttf", 50);
		buttonFont = Resources.loadFont("mysteron.ttf", 24);
	}
	@Override
	public void initialize(final GameContainer container, final StateBasedGame game) throws SlickException{
		RenderedImage logo = new RenderedImage(Resources.loadImage("logo.png"), 0, 100, 100, 100);
		RenderedText title = new RenderedText("Chroma", titleFont, 100, 100, Color.black);
		float totalWidth = logo.getWidth() + MARGIN + title.getWidth();
		logo.setLeft(CENTER - totalWidth/2);
		title.setRight(CENTER + totalWidth/2);
		add(logo);
		add(title);
		
		add(new Button(buttonArea(center, 5), "Play", buttonFont, GoalBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(LevelSelectState.instance().getID(), null, new SwipeTransition(SwipeTransition.RIGHT));//Enter level select
			}
		});
		add(new Button(buttonArea(center, 6), "Settings", buttonFont, SlowBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(SettingsMenuState.ID, null, new SwipeTransition(SwipeTransition.LEFT));
			}
		});
		add(new Button(buttonArea(center, 7), "Exit", buttonFont, Color.red.darker()){
			public void onclick(){
				container.exit();
			}
		});
	}
}
