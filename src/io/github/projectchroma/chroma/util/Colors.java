package io.github.projectchroma.chroma.util;

import java.util.Map;

import org.newdawn.slick.Color;

import io.github.projectchroma.chroma.level.block.Block;
import io.github.projectchroma.chroma.level.block.Blocks;

public class Colors{
	public static java.awt.Color awtColor(Color color){
		return new java.awt.Color(color.r, color.g, color.b, color.a);
	}
	public static Color byName(String color){
		//1) If the name is null, return null
		if(color == null) return null;
		//2) If the name starts with '#', it's hexadecimal, so go straight to decoding it
		else if(color.startsWith("#")) return Color.decode(color);
		
		//3) Search the color class for something of that name
		Color c = ReflectionUtils.getField(Color.class, null, color, Color.class);
		if(c != null) return c;
		
		//4) Look for blocks of that color
		for(Object clazz : ReflectionUtils.getField(Blocks.class, null, "classByColor", Map.class).values()){
			@SuppressWarnings("unchecked")
			Class<? extends Block> blockClass = (Class<? extends Block>)clazz;
			String name = ReflectionUtils.getField(blockClass, null, "COLOR_NAME", String.class);
			if(!name.equals(color)) continue;
			c = ReflectionUtils.getField(blockClass, null, "COLOR", Color.class);
			if(c != null) return c;
		}
		
		//5) Give up
		throw new IllegalArgumentException("Unknown color " + color);
	}
}
