package io.github.projectchroma.chroma.level.block;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class Blocks{
	private static final Map<String, Class<? extends Block>> classByColor = new HashMap<>();
	static{
		register(BarrierBlock.COLOR_NAME, BarrierBlock.class);
		register(BlackBlock.COLOR_NAME, BlackBlock.class);
		register(WhiteBlock.COLOR_NAME, WhiteBlock.class);
		register(HazardBlock.COLOR_NAME, HazardBlock.class);
		register(GoalBlock.COLOR_NAME, GoalBlock.class);
		register(SpeedBlock.COLOR_NAME, SpeedBlock.class);
		register(SlowBlock.COLOR_NAME, SlowBlock.class);
		register(InvisaBlock.COLOR_NAME, InvisaBlock.class);
		register(InvisaBlockHelp.COLOR_NAME, InvisaBlockHelp.class);
		register(SwitchBlock.COLOR_NAME, SwitchBlock.class);
		register(PullBlock.COLOR_NAME, PullBlock.class);
		register(PushBlock.COLOR_NAME, PushBlock.class);
		register(BreakableBlock.COLOR_NAME, BreakableBlock.class);
	}
	public static void register(String colorName, Class<? extends Block> blockClass){
		classByColor.put(colorName, blockClass);
	}
	public static Block createBlock(String color, float x, float y, float width, float height, Color scheme) throws SlickException{
		if(classByColor.containsKey(color)){
			try{
				return classByColor.get(color).getConstructor(Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Color.class).newInstance(x, y, width, height, scheme);
			}catch(ReflectiveOperationException ex){
				throw new SlickException("Error creating block of color " + color, ex);
			}
		}else throw new SlickException("Unknown color " + color);
	}
}
