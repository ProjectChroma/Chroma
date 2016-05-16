package io.github.projectchroma.chroma;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.google.gson.Gson;

import io.github.projectchroma.chroma.level.LevelObject;

public class Resources {
	private static final Gson gson = new Gson();
	public static final String ASSET_PATH = "assets/", TEXTURE_PATH = ASSET_PATH + "textures/", FONT_PATH = ASSET_PATH + "fonts/",
			LEVEL_PATH = "levels/";
	public static String getFontPath(String name){
		return FONT_PATH + name;
	}
	public static Font loadFont(String name) throws SlickException{
		try{
			return Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(getFontPath(name)));
		}catch(IOException | FontFormatException ex){
			throw new SlickException("Error loading font " + name, ex);
		}
	}
	public static String getTexturePath(String name){
		return TEXTURE_PATH + name;
	}
	public static Texture loadTexture(String name) throws SlickException{
		String format;
		if(name.contains(".")) format = name.substring(name.lastIndexOf('.')+1).toUpperCase();
		else format = "PNG";
		return loadTexture(name, format);
	}
	public static Texture loadTexture(String name, String format) throws SlickException{
		try{
			return TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(getTexturePath(name)));
		}catch(IOException ex){throw new SlickException("Error reading texture " + name + '@' + format, ex);}
	}
	public static Image loadImage(String name) throws SlickException{
		return new Image(loadTexture(name));
	}
	public static String getLevelPath(int id){
		return getLevelPath("level" + id + ".json");
	}
	public static String getLevelPath(String name){
		return LEVEL_PATH + name;
	}
	public static LevelObject loadLevel(int id){
		return gson.fromJson(new InputStreamReader(ResourceLoader.getResourceAsStream(getLevelPath(id))), LevelObject.class);
	}
	public static LevelObject loadLevel(String name){
		return gson.fromJson(new InputStreamReader(ResourceLoader.getResourceAsStream(getLevelPath(name))), LevelObject.class);
	}
}
