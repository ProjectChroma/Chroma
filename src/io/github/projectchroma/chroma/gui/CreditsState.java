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
import io.github.projectchroma.chroma.util.Direction;

public class CreditsState extends GUIState{
	public static final int ID = -1;
	private static final float LEFT_X = 100, RIGHT_X = Chroma.WINDOW_WIDTH - LEFT_X;
	private static Font titleFont, textFont;
	private Map<String, String> credits = new LinkedHashMap<>(), attributions = new LinkedHashMap<>();
	private float[] y = new float[2];//Pointer for two floats: top and bottom of text
	public CreditsState(){
		super(ID);
	}
	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException{
		add(new RenderedText("Credits", Chroma.instance().createFont(50), Chroma.WINDOW_WIDTH/2, 40, Color.black));
		add(new Button(buttonArea(center, 8), "Back", Color.red.darker()){
			public void onclick(){
				game.enterState(MainMenuState.ID, null, new SwipeTransition(Direction.UP));
			}
		});
		
		if(titleFont == null) titleFont = Chroma.instance().createFont(36);
		if(textFont == null) textFont = Chroma.instance().createFont(24);
		
		credits.put("Website Design", "Duncan");
		credits.put("Engine Design", "Ian");
		credits.put("Level Design", "Jill");
		
		attributions.put("Font", "\"Mysterons\" by Brian Kent\nwww.aenigmafonts.com");
		attributions.put("Menu Music", "\"Fantasy Game Background\"\nby Eric Matyas\nwww.soundimage.org");
		attributions.put("Level Music", "\"Fairyland_120\" by AlienXXX\nwww.freesound.org/people/AlienXXX/");
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(Color.black);
		y[1] = y[0];
		renderCredits("Project:Chroma", credits, g, y);
		renderCredits("Attribution", attributions, g, y);
		
		g.setColor(Color.white);
		RenderedText title = (RenderedText)elements.get(0);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, title.getBottom() + 20);
		
		Button button = (Button)elements.get(1);
		g.fillRect(0, button.getTop() - 20, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT - button.getTop() + 20);
		super.render(container, game, g);//Render title and back button over credits
	}
	private void renderCredits(String title, Map<String, String> credits, Graphics g, float[] y){
		g.setFont(titleFont);
		g.drawString(title, Chroma.WINDOW_WIDTH/2 - titleFont.getWidth(title)/2, y[1]);
		y[1] += 1.2 * titleFont.getLineHeight();
		g.setFont(textFont);
		for(Map.Entry<String, String> entry : credits.entrySet()){
			String[] keyLines = entry.getKey().split("\\R"), valueLines = entry.getValue().split("\\R");
			int offsetK = 0, offsetV = 0;
			if(keyLines.length > valueLines.length){
				offsetV = textFont.getLineHeight() * (keyLines.length - valueLines.length) / 2;
			}else if(valueLines.length > keyLines.length){
				offsetK = textFont.getLineHeight() * (valueLines.length - keyLines.length) / 2;
			}
			float yK = y[1] + offsetK, yV = y[1] + offsetV;
			for(String kLine : keyLines){
				g.drawString(kLine, LEFT_X, yK);
				yK += textFont.getLineHeight();
			}
			for(String vLine : valueLines){
				g.drawString(vLine, RIGHT_X - textFont.getWidth(vLine), yV);
				yV += textFont.getLineHeight();
			}
			y[1] = Math.max(yK, yV) + 0.5F * textFont.getLineHeight();//Lowest point in current block, plus space before next credit
		}
		y[1] += titleFont.getLineHeight();
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		y[0] -= 0.5;
		if(y[1] < ((RenderedText)elements.get(0)).getBottom()) y[0] = ((Button)elements.get(1)).getTop();
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		y[0] = ((Button)elements.get(1)).getTop();
	}
}
