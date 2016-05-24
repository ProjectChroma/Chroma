package io.github.projectchroma.chroma.gui;

import java.awt.Color;
import java.awt.Graphics;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.resource.FontResource;
import io.github.projectchroma.chroma.resource.ImageResource;
import io.github.projectchroma.chroma.resource.Resources;
import io.github.projectchroma.chroma.util.Colors;
import io.github.projectchroma.chroma.util.GraphicsUtils;

public class MainMenuState extends GUIState{
	public static final int ID = 0;
	private static final int LOGO_WIDTH = 150, TITLE_Y = 150, TITLE_SPACING = 10;
	private FontResource titleFont, buttonFont;
	private ImageResource logo;
	public MainMenuState(){
		super(ID);
		titleFont = Resources.getFont("mysteron", 60);
		buttonFont = Resources.getFont("mysteron", 24);
		logo = Resources.getImage("logo");
	}
	@Override
	public void initialize(Window window, Chroma chroma){
		add(new Button(buttonArea(center, 5), "Play", buttonFont.get(), Colors.DARK_GOLD){
			public void onclick(){
				chroma.enterState(LevelSelectState.ID);
			}
		});
		add(new Button(buttonArea(center, 6), "Settings", buttonFont.get(), Colors.DARK_BLUE){
			public void onclick(){
				chroma.enterState(SettingsMenuState.ID);
			}
		});
		add(new Button(buttonArea(center, 7), "Exit", buttonFont.get(), Colors.DARK_RED){
			public void onclick(){
				window.exit();
			}
		});
	}
	@Override
	public void render(Window window, Chroma chroma, Graphics g){
		super.render(window, chroma, g);
		g.setColor(Color.black);
		g.setFont(titleFont.get());
		g.drawString("Chroma", Window.WINDOW_WIDTH/2 + TITLE_SPACING, TITLE_Y);
		
		if(logo.isLoaded()){
			int x2 = Window.WINDOW_WIDTH/2 - TITLE_SPACING, x1 = x2 - LOGO_WIDTH,
					y1 = TITLE_Y - GraphicsUtils.height(g) * 3/2, y2 = y1 + LOGO_WIDTH;
			g.drawImage(logo.get(),//image,
					x1, y1,//x1, y1
					x2, y2,//x2, y2,
					0, 0, logo.get().getWidth(null), logo.get().getHeight(null),//u1, v1, u2, v2,
					null);//observer
		}
	}
}
