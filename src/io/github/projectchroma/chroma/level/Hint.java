package io.github.projectchroma.chroma.level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.settings.Keybind;

public class Hint extends LevelElement{
	private String text, renderedText;
	private float x, y;
	private Font font;
	public Hint(String text, float x, float y) throws SlickException{
		this(text, x, y, null);
	}
	public Hint(String text, float x, float y, Color color) throws SlickException{
		this(text, x, y, color, null);
	}
	public Hint(String text, float x, float y, Color color, Color scheme) throws SlickException{
		super(x, y, 0, 0, color, scheme);
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = Chroma.instance().createFont(18);
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		renderedText = processText(text);
	}
	@Override
	public boolean isTangible(LevelState level){return false;}
	@Override public void update(GameContainer container, LevelState level, int delta) throws SlickException{}
	@Override
	public void render(GameContainer container, LevelState level, Graphics g){
		if(!doRender(level)) return;
		g.setFont(font);
		g.setColor(getColor(level));
		g.drawString(renderedText, x, y);
	}
	private static String processText(String text){
		Pattern p = Pattern.compile("\\{keybind\\:([a-zA-Z\\.]+)\\}");
		Matcher m;
		while((m = p.matcher(text)).find()){
			String keybind = m.group(1);//Name of keybind (the "([a-zA-Z\.]+)" in the regex)
			String keyName = Keybind.get(keybind).getKeyName();
			text = m.replaceFirst(keyName);
		}
		return text;
	}
}
