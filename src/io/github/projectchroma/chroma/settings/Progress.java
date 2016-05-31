package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Progress{
	private static final File file = new File(Settings.SETTINGS_DIR, "progress.dat");
	private static int lastCompletedLevel;
	public static void read(){
		try(Scanner in = new Scanner(file)){
			lastCompletedLevel = in.nextInt();
		}catch(FileNotFoundException ex){
			lastCompletedLevel = 0;
		}
	}
	public static void write() throws FileNotFoundException{
		file.getParentFile().mkdirs();
		try(PrintStream out = new PrintStream(file)){
			out.println(lastCompletedLevel);
		}
	}
	public static int getLastCompletedLevel(){
		return lastCompletedLevel;
	}
	public static boolean setLevelComplete(int id){
		if(id > lastCompletedLevel){
			lastCompletedLevel = id;
			return true;
		}else return false;
	}
	public static boolean isLevelComplete(int id){
		return id <= lastCompletedLevel;
	}
	public static boolean isLevelLocked(int id){
		return id > lastCompletedLevel + 1;
	}
}
