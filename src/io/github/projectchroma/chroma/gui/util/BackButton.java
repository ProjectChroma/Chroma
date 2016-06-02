package io.github.projectchroma.chroma.gui.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.RectangleUtils;

public class BackButton extends Button{
	private static final Rectangle area = RectangleUtils.fromCenter(Chroma.WINDOW_WIDTH/2, 530, 620, 50);
	private int state;
	private Direction d;
	public BackButton(int state, Direction d) throws SlickException{this(state, d, "Back", null, Color.red);}
	
	public BackButton(int state, Direction d, String text) throws SlickException{this(state, d, text, null, Color.red);}
	public BackButton(int state, Direction d, Font font) throws SlickException{this(state, d, "Back", font, Color.red);}
	public BackButton(int state, Direction d, Color bg) throws SlickException{this(state, d, "Back", null, bg);}
	
	public BackButton(int state, Direction d, String text, Font font) throws SlickException{this(state, d, text, font, Color.red);}
	public BackButton(int state, Direction d, String text, Color bg) throws SlickException{this(state, d, text, null, bg);}
	public BackButton(int state, Direction d, Font font, Color bg) throws SlickException{this(state, d, "Back", font, bg);}
	
	public BackButton(int state, Direction d, String text, Font font, Color bg) throws SlickException{
		super(area, text, font, bg.darker(0.65F), bg.darker(0.8F), bg.darker(0.5F), null);
		this.state = state;
		this.d = d;
	}
	@Override
	public void onclick() throws SlickException{
		Chroma.instance().enterState(state, null, new SwipeTransition(d));
	}
}
