package io.github.projectchroma.chroma.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelObject{
	public String name;
	public List<String> schemes = Arrays.asList("white", "black");//Default to alternating schemes, starting with white
	public List<BlockObject> blocks;
	public PointObject playerStart;
	public List<HintObject> hints = new ArrayList<>();//Initialize to empty list, to prevent null issues if the level has no hints
	private LevelObject(){}
	
	public static class BlockObject{
		public int x, y, width, height;
		public String color, scheme;
	}
	public static class PointObject{
		public int x, y;
	}
	public static class HintObject{
		public int x, y;
		public String text;
		public String color;
	}
}
