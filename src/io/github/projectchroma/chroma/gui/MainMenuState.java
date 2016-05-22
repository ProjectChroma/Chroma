package io.github.projectchroma.chroma.gui;

import java.awt.Color;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;

public class MainMenuState extends GUIState{
	public static final int ID = 0;
	public MainMenuState(){
		super(ID);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		
		add(new Button(buttonArea(center, 5), "Play", Color.yellow){
			public void onclick(){}
		});
		add(new Button(buttonArea(center, 6), "Settings", Color.blue){
			public void onclick(){}
		});
		add(new Button(buttonArea(center, 7), "Exit", Color.red){
			public void onclick(){
				window.exit();
			}
		});
	}
}
