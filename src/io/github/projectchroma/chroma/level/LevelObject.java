package io.github.projectchroma.chroma.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelObject{
	public String name;
	public List<String> schemes = Arrays.asList("white", "black");//Default to alternating schemes, starting with white
	public List<BlockObject> blocks;
	public PlayerObject player;
	public List<HintObject> hints = new ArrayList<>();//Initialize to empty list, to prevent null issues if the level has no hints
	private LevelObject(){}
	
	public static class BlockObject{
		public float x, y, width, height;
		public String color, scheme;
	}
	public static class PlayerObject{
		public float x, y;
		public boolean allowSwitching = true;
	}
	public static class HintObject{
		public float x, y;
		public String text;
		public String color, scheme;
	}
}
