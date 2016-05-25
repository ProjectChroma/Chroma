package io.github.projectchroma.chroma.util;

import java.lang.reflect.Field;

public class ReflectionUtils{
	public static <C, T> T getField(Class<C> clazz, C obj, String name, Class<T> fieldClass){
		try{
			Field f = clazz.getDeclaredField(name);
			f.setAccessible(true);//Prevent IllegalAccessException
			return fieldClass.cast(f.get(obj));
		}catch(NoSuchFieldException | IllegalAccessException ex){return null;}
	}
}
