package io.github.projectchroma.chroma.level;

import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;

public class LevelObject{
	private static Gson gson = new Gson();
	public int id;
	public List<BlockObject> blocks;
	public PointObject playerStart;
	private LevelObject(){}
	
	public static class BlockObject{
		public float x, y, width, height;
		public String color;
	}
	public static class PointObject{
		public float x, y;
	}
	public static LevelObject read(String path){
		return gson.fromJson(new InputStreamReader(LevelObject.class.getResourceAsStream(path)), LevelObject.class);
	}
}
