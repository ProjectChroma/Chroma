package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.resource.Resources;

public class SettingsMenuState extends GUIState{
	public static final int ID = -2;
	private Font buttonFont;
	public SettingsMenuState(){
		super(ID);
	}
	@Override
	public void loadResources(GameContainer container, final StateBasedGame game) throws SlickException{
		buttonFont = Resources.loadFont("mysteron.ttf", 24);
	}
	@Override
	public void initialize(GameContainer container, final StateBasedGame game) throws SlickException{
		add(new Button(buttonArea(center, 8), "Back", buttonFont, Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.RIGHT));
			}
		});
	}
}
