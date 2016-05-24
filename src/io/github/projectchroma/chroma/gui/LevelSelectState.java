package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Graphics;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.Colors;
import io.github.projectchroma.chroma.util.GraphicsUtils;

public class LevelSelectState extends GUIState{
	public static final int ID = -1;
	private FontResource titleFont, buttonFont;
	public LevelSelectState(){
		super(ID);
		titleFont = Resources.getFont("mysteron", 36);
		buttonFont = Resources.getFont("mysteron", 24);
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		add(new Button(buttonArea(center, 7), "Back", buttonFont.get(), Colors.DARK_RED){
			public void onclick(){
				chroma.enterState(MainMenuState.ID);
			}
		});
	}

	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		super.render(window, chroma, g);
		g.setColor(Color.black);
		g.setFont(titleFont.get());
		g.drawString("Level Select", Window.WINDOW_WIDTH/2 - GraphicsUtils.width(g, "Level Select")/2, 50);
	}
}
