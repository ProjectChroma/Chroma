package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import io.github.projectchroma.chroma.level.LevelState;

public class Analytics{
	private static final File dir = new File(Settings.SETTINGS_DIR, "analytics");
	private static File file;
	private static boolean enabled = true;
	private Analytics(){}
	public static void init(){
		if(!enabled) return;
		dir.mkdirs();
		int i = 0;
		while((file = new File(dir, i + ".dat")).exists()) ++i;
		System.out.println("Writing analytics data to file " + file);
	}
	public static void levelWon(LevelState level){write(level, "W");}
	public static void levelLost(LevelState level){write(level, "L");}
	public static void levelExited(LevelState level){write(level, "E");}
	private static void write(LevelState level, String code){
		if(!enabled) return;
		try(PrintStream out = new PrintStream(new FileOutputStream(file, true), true)){
			out.println(level.getID() + "[" + level.name() + "]" + code);
		}catch(FileNotFoundException ex){
			System.err.println("Unable to create analytics file");
			ex.printStackTrace();
		}
	}
	public static void setEnabled(boolean active){
		enabled = active;
		System.out.println("Analytics " + (active ? "enabled" : "disabled"));
	}
}
