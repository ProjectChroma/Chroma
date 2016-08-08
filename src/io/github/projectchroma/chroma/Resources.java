package io.github.projectchroma.chroma;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.google.gson.Gson;

import io.github.projectchroma.chroma.level.LevelObject;

public class Resources {
	public static final Gson gson = new Gson();
	public static final String ASSET_PATH = "assets/", TEXTURE_PATH = ASSET_PATH + "textures/", FONT_PATH = ASSET_PATH + "fonts/", SOUND_PATH = ASSET_PATH + "sounds/", LEVEL_PATH = "levels/";
	public static String getFontPath(String name){return FONT_PATH + name;}
	public static Font loadFont(String path) throws SlickException{
		try{
			return Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(path));
		}catch(IOException | FontFormatException ex){
			throw new SlickException("Error loading font " + path, ex);
		}
	}
	public static String getTexturePath(String name){return TEXTURE_PATH + name;}
	public static Texture loadTexture(String path) throws SlickException{
		String format;
		if(path.contains(".")) format = path.substring(path.lastIndexOf('.')+1).toUpperCase();
		else format = "PNG";
		return loadTexture(path, format);
	}
	public static Texture loadTexture(String path, String format) throws SlickException{
		try{
			return TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
		}catch(IOException ex){throw new SlickException("Error reading texture " + path + '@' + format, ex);}
	}
	public static Image loadImage(String name) throws SlickException{
		return new Image(loadTexture(name));
	}
	public static String getSoundPath(String name){return SOUND_PATH + name;}
	public static Music loadMusic(String path) throws SlickException{
		Music music = new Music(ResourceLoader.getResourceAsStream(path), path);
		Sounds.registerMusic(music);
		return music;
	}
	public static Sound loadSound(String path) throws SlickException{
		return new Sound(path);
	}
	
	public static LevelObject loadLevel(String path){
		return gson.fromJson(new InputStreamReader(ResourceLoader.getResourceAsStream(path)), LevelObject.class);
	}
}
