package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.newdawn.slick.Input;

import io.github.projectchroma.chroma.Chroma;

public class Keybind{
	private static Map<String, Keybind> bindings = new HashMap<>();
	private static final File file = new File(Settings.SETTINGS_DIR, "keybinds.cfg");
	private static boolean changed = false;
	private String name;
	private int key;
	private boolean conflicting = false;
	private Keybind(String name, int key){
		this.name = name;
		setKey(key);
	}
	public String getName(){
		return name;
	}
	public int getKey(){
		return key;
	}
	public String getKeyName(){
		return Input.getKeyName(key);
	}
	public void setKey(int key){
		if(key != this.key){
			this.key = key;
			changed = true;
			System.out.println("Set keybind " + name + " to " + Input.getKeyName(key));
		}
	}
	public boolean isDown(){
		return Chroma.instance().getContainer().getInput().isKeyDown(key);
	}
	public boolean isPressed(){
		return Chroma.instance().getContainer().getInput().isKeyPressed(key);
	}
	public boolean isConflicting(){
		return conflicting;
	}
	
	public static Keybind get(String name){
		return bindings.get(name);
	}
	public static Keybind get(String name, int defaultKey){
		return bindings.computeIfAbsent(name, (key) -> new Keybind(name, defaultKey));
	}
	public static Collection<Keybind> bindings(){
		return bindings.values();
	}
	
	public static void read(){
		try(Scanner in = new Scanner(file)){
			while(in.hasNext()){
				String name = in.next();
				int key = in.nextInt();
				get(name, key);//Register keybind
			}
		}catch(FileNotFoundException ex){
			System.out.println("No keybinding file");
			bindings = new HashMap<>();
		}
		checkConflicts();
	}
	public static boolean hasChanged(){
		return changed;
	}
	public static void write(){
		try(PrintStream out = new PrintStream(file)){
			for(Keybind keybind: bindings()){
				out.print(keybind.name);
				out.print(' ');
				out.println(keybind.getKey());
			}
		}catch(FileNotFoundException ex){
			System.err.println("Error writing keybinds to file");
			ex.printStackTrace();
		}
	}
	public static void checkConflicts(){
		Map<Integer, Keybind> byKey = new HashMap<>();
		for(Keybind keybind : bindings()) keybind.conflicting = false;
		for(Keybind keybind : bindings()){
			Keybind other = byKey.put(keybind.getKey(), keybind);
			if(other != null){
				other.conflicting = true;
				keybind.conflicting = true;
			}
		}
	}
}
