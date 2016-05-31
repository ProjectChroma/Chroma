package io.github.projectchroma.chroma.settings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.github.projectchroma.chroma.Chroma;

public class Keybind{
	private static Map<String, Keybind> bindings = new HashMap<>();
	private String name;
	private int key;
	private Keybind(String name, int key){
		this.name = name;
		this.key = key;
	}
	public String getName(){
		return name;
	}
	public int getKey(){
		return key;
	}
	public void setKey(int key){
		this.key = key;
	}
	public boolean isDown(){
		return Chroma.instance().getContainer().getInput().isKeyDown(key);
	}
	public boolean isPressed(){
		return Chroma.instance().getContainer().getInput().isKeyPressed(key);
	}
	
	public static Keybind get(String name){
		return bindings.get(name);
	}
	public static Keybind get(String name, int defaultKey){
		return bindings.computeIfAbsent(name, (name_) -> new Keybind(name_, defaultKey));
	}
	public static Collection<Keybind> bindings(){
		return bindings.values();
	}
}
