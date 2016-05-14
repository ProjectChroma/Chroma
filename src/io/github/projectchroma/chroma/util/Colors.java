package io.github.projectchroma.chroma.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class Colors{
	public static final Color black = Color.black, white = Color.white;
	public static final Color red = Color.red;
	public static final Color gold = Color.yellow.darker(0.2F);
	public static final Color orange = Color.orange;
	public static final Color blue = Color.blue;
	public static Color byName(String color) throws SlickException{
		if(color == null) return null;
		else if(color.charAt(0) == '#') return Color.decode(color);
		else try{
			return (Color)Colors.class.getDeclaredField(color).get(null);
		}catch(NoSuchFieldException | IllegalAccessException ex){throw new SlickException("Error reading color " + color, ex);}
	}
}
