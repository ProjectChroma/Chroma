package io.github.projectchroma.chroma;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Music;

public class Sounds{
	private static List<Music> musics = new ArrayList<>();
	public static void registerMusic(Music music){musics.add(music);}
	public static Music getCurrentMusic(){
		for(Music music : musics)
			if(music.playing()) return music;
		return null;
	}
}
