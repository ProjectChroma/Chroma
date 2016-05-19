package io.github.projectchroma.chroma;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;

import io.github.projectchroma.chroma.resource.Resources;

public class GameMusic{
	private static Audio menuMusic, levelMusic;
	public static void init() throws SlickException{
		menuMusic = Resources.loadSound("menuMusic.aif");
		levelMusic = Resources.loadSound("levelMusic.aif");
	}
	public static Audio getMenuMusic(){
		return menuMusic;
	}
	public static Audio getLevelMusic(){
		return levelMusic;
	}
	public static Audio getCurrentMusic(){
		if(menuMusic.isPlaying()) return menuMusic;
		else if(levelMusic.isPlaying()) return levelMusic;
		else return null;
	}
}
