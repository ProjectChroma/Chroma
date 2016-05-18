package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.level.block.SlowBlock;
import io.github.projectchroma.chroma.level.block.GoalBlock;

public class MainMenuState extends GUIState{
	public static final int ID = 0;
	public MainMenuState(){
		super(ID);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{
		RenderedImage logo = new RenderedImage(Resources.loadImage("logo.png"), 0, 100, 100, 100);
		RenderedText title = new RenderedText("Chroma", Chroma.instance().createFont(50F), 100, 100, Color.black);
		float totalWidth = logo.getWidth() + MARGIN + title.getWidth();
		logo.setLeft(CENTER - totalWidth/2);
		title.setRight(CENTER + totalWidth/2);
		add(logo);
		add(title);
		
		add(new Button(buttonArea(center, 5), "Play", GoalBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(1, null, new SwipeTransition(SwipeTransition.RIGHT));//Enter level 1
			}
		});
		add(new Button(buttonArea(center, 6), "Settings", SlowBlock.COLOR.darker()){
			public void onclick(){
				game.enterState(SettingsMenuState.ID, null, new SwipeTransition(SwipeTransition.LEFT));
			}
		});
		add(new Button(buttonArea(center, 7), "Exit", Color.red.darker()){
			public void onclick(){
				container.exit();
			}
		});
	}
}
