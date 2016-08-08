package io.github.projectchroma.chroma.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.github.projectchroma.chroma.Chroma;

public class LevelObject{
	public String name;
	public int width = Chroma.WINDOW_WIDTH, height = Chroma.WINDOW_HEIGHT;
	public List<String> schemes = Arrays.asList("white", "black");//Default to alternating schemes, starting with white
	public Camera camera = new Camera();
	public PlayerObject player;
	public List<BlockObject> blocks;
	public List<HintObject> hints = new ArrayList<>();//Initialize to empty list, to prevent null issues if the level has no hints
	public List<EntityObject> entities = new ArrayList<>();
	
	private LevelObject(){}
	public static class Camera{
		public int width = Chroma.WINDOW_WIDTH, height = Chroma.WINDOW_HEIGHT;
	}
	public static class PlayerObject{
		public float x, y;
		public boolean allowSwitching = true;
	}
	public static class BlockObject{
		public float x, y, width, height;
		public String color, scheme;
		public Map<String, Object> props;
		public <T> T getProperty(String name, Class<T> type){return props == null ? null : type.cast(props.get(name));}
		public boolean hasProperty(String name){return props != null && props.containsKey(name);}
		//Gson makes all numbers doubles, so these utilities make getting other types easier
		public float getFloatProperty(String name){return getProperty(name, Double.class).floatValue();}
		public int getIntProperty(String name){return getProperty(name, Double.class).intValue();}
	}
	public static class HintObject{
		public float x, y;
		public String text;
		public String color, scheme;
	}
	public static class EntityObject{
		public float x, y;
		public String type;
		public EntityObject(float x, float y){
			this.x = x;
			this.y = y;
		}
	}
}
