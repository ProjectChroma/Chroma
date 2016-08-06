package io.github.projectchroma.chroma;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class Sounds{
	private static Music menuMusic, levelMusic;
	public static void init() throws SlickException{
		menuMusic = Resources.loadMusic(Resources.getSoundPath("menuMusic.aiff"));
		levelMusic = Resources.loadMusic(Resources.getSoundPath("levelMusic.aiff"));
	}
	public static Music getMenuMusic(){
		return menuMusic;
	}
	public static Music getLevelMusic(){
		return levelMusic;
	}
	public static Music getCurrentMusic(){
		if(menuMusic.playing()) return menuMusic;
		else if(levelMusic.playing()) return levelMusic;
		else return null;
	}
}
