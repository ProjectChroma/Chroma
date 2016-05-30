package io.github.projectchroma.chroma.gui;

import java.util.LinkedHashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.SwipeTransition;

public class CreditsState extends GUIState{
	public static final int ID = -2;
	private static final float LEFT_X = 100, RIGHT_X = Chroma.WINDOW_WIDTH - LEFT_X;
	private static Font titleFont, textFont;
	private Map<String, String> creditsPC = new LinkedHashMap<>(), creditsExternal = new LinkedHashMap<>();
	public CreditsState(){
		super(ID);
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		add(new RenderedText("Credits", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 40, Color.black));
		add(new Button(buttonArea(center, 8), "Back", Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(SwipeTransition.LEFT));
			}
		});
		
		if(titleFont == null) titleFont = Chroma.instance().createFont(36);
		if(textFont == null) textFont = Chroma.instance().createFont(24);
		
		creditsPC.put("Engine Designer", "Ian");
		creditsPC.put("Level Design", "Jill");
		creditsPC.put("Web Design", "Duncan");
		
		creditsExternal.put("Font", "\"Mysterons\" by Brian Kent\nAenigma Fonts");
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		super.render(container, game, g);
		g.setColor(Color.black);
		float[] y = {100};//float[] so changes to y[0] are passed out
		renderCredits("Project:Chroma", creditsPC, g, y);
		renderCredits("External Credits", creditsExternal, g, y);
	}
	private void renderCredits(String title, Map<String, String> credits, Graphics g, float[] y){
		g.setFont(titleFont);
		g.drawString(title, Chroma.WINDOW_WIDTH/2 - titleFont.getWidth(title)/2, y[0]);
		y[0] += 1.2 * titleFont.getLineHeight();
		g.setFont(textFont);
		for(Map.Entry<String, String> entry : credits.entrySet()){
			String[] lines = entry.getValue().split("\\R");
			g.drawString(entry.getKey(), LEFT_X, y[0] + textFont.getLineHeight() * (lines.length-1) / 2);
			for(String line : lines){
				g.drawString(line, RIGHT_X - textFont.getWidth(line), y[0]);
				y[0] += textFont.getLineHeight();
			}
			y[0] += 0.5 * textFont.getLineHeight();//Space before next credit
		}
		y[0] += titleFont.getLineHeight();
	}
}
