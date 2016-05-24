package io.github.projectchroma.chroma.gui;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.Colors;

public class MainMenuState extends GUIState{
	public static final int ID = 0;
	private FontResource buttonFont;
	public MainMenuState(){
		super(ID);
		buttonFont = Resources.getFont("mysteron", 24);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		add(new Button(buttonArea(center, 5), "Play", buttonFont.get(), Colors.DARK_GOLD){
			public void onclick(){
				System.out.println("Play");
			}
		});
		add(new Button(buttonArea(center, 6), "Settings", buttonFont.get(), Colors.DARK_BLUE){
			public void onclick(){
				System.out.println("Settings");
			}
		});
		add(new Button(buttonArea(center, 7), "Exit", buttonFont.get(), Colors.DARK_RED){
			public void onclick(){
				window.exit();
			}
		});
	}
}
