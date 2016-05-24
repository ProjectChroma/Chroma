package io.github.projectchroma.chroma.resource;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class Resources{
	private static List<Resource<?>> resources = new LinkedList<>();
	private static int numResources = 0;
	public static FontResource getFont(String name, float size){
		return getFont(name, size, Font.PLAIN);
	}
	public static FontResource getFont(String name, float size, int modifiers){
		return loadResource(new FontResource(name, size, modifiers));
	}
	private static <T extends Resource<?>> T loadResource(T resource){
		resources.add(resource);
		numResources++;
		return resource;
	}
	public static InputStream getResourceAsStream(String path) throws IOException{
		return Resources.class.getResourceAsStream(path);
	}
	public static int getTotalResources(){
		return numResources;
	}
	public static int getRemainingResources(){
		return resources.size();
	}
	public static Resource<?> getNextResource(){
		return resources.get(0);
	}
	static void removeResource(Resource<?> resource){
		resources.remove(resource);
	}
}
