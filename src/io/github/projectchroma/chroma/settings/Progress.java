package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.modules.ModuleContext;

public class Progress{
	private static final File file = new File(Settings.SETTINGS_DIR, "progress.dat");
	private static Map<String, List<String>> data;
	@SuppressWarnings("unchecked")
	public static void read(){
		try {
			data = Resources.gson.fromJson(new InputStreamReader(new FileInputStream(file)), Map.class);
		}catch(FileNotFoundException ex){
			data = new HashMap<>();
		} catch (JsonSyntaxException | JsonIOException ex) {
			System.err.println("Error reading progress");
			ex.printStackTrace();
			data = new HashMap<>();
		}
	}
	public static void write() throws FileNotFoundException{
		file.getParentFile().mkdirs();
		try(PrintStream out = new PrintStream(file)){
			out.println(Resources.gson.toJson(data));
		}
	}
	public static boolean setLevelComplete(LevelState level){
		List<String> levels = getCompletedLevels(level.getDeclaringModule());
		boolean newlyComplete = !levels.contains(level.name());
		if(newlyComplete) levels.add(level.name());
		return newlyComplete;
	}
	public static boolean isLevelComplete(LevelState level){
		List<String> levels = getCompletedLevels(level.getDeclaringModule());
		return levels.contains(level.name());
	}
	public static boolean isLevelLocked(LevelState level){
		if(isLevelComplete(level)) return false;//The level is now either incomplete and locked, or incomplete and unlocked
//		System.out.println("Level incomplete: " + level.name());
		List<LevelState> levels = LevelState.getLevels(level.getDeclaringModule());//All levels in this module
		int index = levels.indexOf(level);
//		System.out.println("Index: " + index);
		if(index == 0) return false;//First level is never locked
		LevelState prev = levels.get(index-1);//If the previous level is complete, this level should be unlocked
//		System.out.println("Prev: " + prev.name());
		return !getCompletedLevels(level.getDeclaringModule()).contains(prev.name());//If previous level is incomplete, this level is locked
	}
	private static List<String> getCompletedLevels(ModuleContext parent){
		return data.computeIfAbsent(parent.getID(), (module) -> new ArrayList<>());
	}
}
