package io.github.projectchroma.chroma.util;

import org.newdawn.slick.Color;

public class Colors{
	public static java.awt.Color awtColor(Color color){
		return new java.awt.Color(color.r, color.g, color.b, color.a);
	}
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
