package io.github.projectchroma.chroma.level.block;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.BlockObject;

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
		register(SwitchBlock.COLOR_NAME, SwitchBlock.class);
		register(PullBlock.COLOR_NAME, PullBlock.class);
		register(PushBlock.COLOR_NAME, PushBlock.class);
		register(BreakableBlock.COLOR_NAME, BreakableBlock.class);
	}
	public static void register(String colorName, Class<? extends Block> blockClass){
		classByColor.put(colorName, blockClass);
	}
	public static Block createBlock(float x, float y, float width, float height, String color, String scheme) throws SlickException{
		BlockObject block = new BlockObject();
		block.x = x; block.y = y; block.width = width; block.height = height; block.color = color; block.scheme = scheme;
		return createBlock(block);
	}
	public static Block createBlock(BlockObject block) throws SlickException{
		if(classByColor.containsKey(block.color)){
			try{
				return classByColor.get(block.color)
						.getConstructor(BlockObject.class)
						.newInstance(block);
			}catch(ReflectiveOperationException ex){
				throw new SlickException("Error creating block of color " + block.color, ex);
			}
		}else throw new SlickException("Unknown color " + block.color);
	}
}
