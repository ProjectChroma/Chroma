package io.github.projectchroma.chroma.util;

import java.awt.Color;

public class Colors{
	public static final Color DARK_RED = new Color(0.5F, 0, 0), DARK_BLUE = new Color(0, 0, 0.5F), DARK_GOLD = new Color(0.4F, 0.4F, 0);
	public static Color byName(String color){
		if(color == null) return null;
		else if(color.startsWith("#")) return Color.decode(color);
		else try{
			return (Color)Color.class.getField(color).get(null);
		}catch(ReflectiveOperationException ex){
			return null;
		}
	}
}
