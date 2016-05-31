package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.newdawn.slick.GameContainer;

import io.github.projectchroma.chroma.Resources;

public class Settings{
	public static final File SETTINGS_DIR = new File("data");
	private static final File file = new File(SETTINGS_DIR, "settings.cfg");
	private static Settings instance;
	private Settings(){}
	private boolean soundsEnabled, musicEnabled;
	
	public static boolean soundsEnabled(){return instance.soundsEnabled;}
	public static boolean musicEnabled(){return instance.musicEnabled;}
	public static void toggleSounds(){instance.soundsEnabled = !instance.soundsEnabled;}
	public static void toggleMusic(){instance.musicEnabled = !instance.musicEnabled;}
	
	public static void read(){
		try(InputStreamReader reader = new InputStreamReader(new FileInputStream(file))){
			instance = Resources.gson.fromJson(reader, Settings.class);
		}catch(FileNotFoundException ex){
			System.out.println("No settings file");
			instance = new Settings();
		}catch(IOException ex){
			System.err.println("Error closing settings file (read)");
			ex.printStackTrace();
			instance = new Settings();
		}
	}
	public static void update(GameContainer c){
		c.setSoundOn(instance.soundsEnabled);
		c.setMusicOn(instance.musicEnabled);
	}
	public static void write(){
		try(PrintStream out = new PrintStream(file)){
			out.println(Resources.gson.toJson(instance));
		}catch(FileNotFoundException ex){
			System.err.println("Unable to create settings file");
			ex.printStackTrace();
		}
	}
}
