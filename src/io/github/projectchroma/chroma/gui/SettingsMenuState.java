package io.github.projectchroma.chroma.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.SwipeTransition;

public class SettingsMenuState extends GUIState{
	public static final int ID = -1;
	public SettingsMenuState(){
		super(ID);
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		add(new Button(buttonArea(center, 8), "Back", Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.RIGHT));
			}
		});
	}
}
