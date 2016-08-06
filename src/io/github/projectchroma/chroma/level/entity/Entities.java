package io.github.projectchroma.chroma.level.entity;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.level.LevelObject.EntityObject;

public class Entities{
	private static final Map<String, Class<? extends Entity>> classByType = new HashMap<>();
	static{
		register(Triangle.TYPE, Triangle.class);
		register(Circle.TYPE, Circle.class);
	}
	public static void register(String type, Class<? extends Entity> entityClass){
		classByType.put(type, entityClass);
	}
	public static Entity createEntity(EntityObject entity) throws SlickException{
		if(classByType.containsKey(entity.type)){
			try{
				return classByType.get(entity.type)
						.getConstructor(EntityObject.class)
						.newInstance(entity);
			}catch(ReflectiveOperationException ex){
				throw new SlickException("Error creating entity of type " + entity.type, ex);
			}
		}else throw new SlickException("Unknown type " + entity.type);
	}
}
