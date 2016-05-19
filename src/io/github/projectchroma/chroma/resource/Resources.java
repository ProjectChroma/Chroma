package io.github.projectchroma.chroma.resource;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.DeferredSound;
import org.newdawn.slick.opengl.DeferredTexture;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

public class Resources{
	public static final String ASSET_PATH = "assets/", TEXTURE_PATH = ASSET_PATH + "textures/", FONT_PATH = ASSET_PATH + "fonts/", SOUND_PATH = ASSET_PATH + "sounds/", LEVEL_PATH = "levels/";
	public static String getFontPath(String name){
		return FONT_PATH + name;
	}
	public static DeferredAWTFont loadFont(String name){
		return new DeferredAWTFont(getFontPath(name));
	}
	public static Font loadFont(String name, float size){
		return loadFont(name, size, java.awt.Font.PLAIN);
	}
	public static Font loadFont(String name, float size, int modifiers){
		return new DeferredFont(loadFont(name), size, modifiers);
	}
	public static String getTexturePath(String name){
		return TEXTURE_PATH + name;
	}
	public static Texture loadTexture(String name) throws SlickException{
		String format;
		if(name.contains("."))
			format = name.substring(name.lastIndexOf('.') + 1).toUpperCase();
		else
			format = "PNG";
		return loadTexture(name, format);
	}
	public static Texture loadTexture(String name, String format) throws SlickException{
		return new DeferredTexture(ResourceLoader.getResourceAsStream(getTexturePath(name)), format, false, 0, null);
	}
	
	public static Image loadImage(String name) throws SlickException{
		return new Image(loadTexture(name));
	}
	public static String getSoundPath(String name){
		return SOUND_PATH + name;
	}
	public static Audio loadSound(String name) throws SlickException{
		int type = 0;
		String ext = name.substring(name.lastIndexOf('.')+1).toLowerCase();
		if(ext.equals("ogg")) type = DeferredSound.OGG;
		else if(ext.equals("aif")) type = DeferredSound.AIF;
		else if(ext.equals("mod")) type = DeferredSound.MOD;
		else if(ext.equals("wav")) type = DeferredSound.WAV;
		return new DeferredSound(getSoundPath(name), ResourceLoader.getResourceAsStream(getSoundPath(name)), type);
	}
	
	public static String getLevelPath(int id){
		return LEVEL_PATH + "level" + id + ".json";
	}
	public static DeferredLevel loadLevel(int id){
		return new DeferredLevel(getLevelPath(id));
	}
}
