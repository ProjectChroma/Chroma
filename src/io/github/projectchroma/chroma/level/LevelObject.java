package io.github.projectchroma.chroma.level;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class LevelObject{
	private static Gson gson = new Gson();
	public String name;
	public List<BlockObject> blocks;
	public PointObject playerStart;
	public List<HintObject> hints = new ArrayList<>();//Initialize to empty list, to prevent null issues if the level has no hints
	private LevelObject(){}
	
	public static class BlockObject{
		public float x, y, width, height;
		public String color;
	}
	public static class PointObject{
		public float x, y;
	}
	public static class HintObject{
		public float x, y;
		public String text;
		public String color;
	}
	public static LevelObject read(String path){
		return gson.fromJson(new InputStreamReader(LevelObject.class.getResourceAsStream(path)), LevelObject.class);
	}
}
