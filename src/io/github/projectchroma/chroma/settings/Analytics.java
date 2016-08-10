package io.github.projectchroma.chroma.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import io.github.projectchroma.chroma.Resources;

public class Analytics{
	private static final File file = new File(Settings.SETTINGS_DIR, "analytics.dat");
	private static boolean enabled = true;
	private Analytics(){}
	public static void write(EventData event){
		if(!enabled) return;
		if(event == null) throw new NullPointerException("Null analytics event");
		try{
			if(!file.exists()) file.createNewFile();
			PrintStream out = new PrintStream(new FileOutputStream(file, true), true);
			out.println(event);
			out.close();
		}catch(IOException ex){
			System.err.println("Error writing analytics data");
			ex.printStackTrace();
		}
	}
	public static void setEnabled(boolean active){
		enabled = active;
		System.out.println("Analytics " + (active ? "enabled" : "disabled"));
	}
	public static class EventData{
		private static final String NAME_KEY = "eventType";
		private Map<String, Object> data = new HashMap<>();
		public EventData(String name){
			if(name == null) throw new NullPointerException("Null event name");
			data.put(NAME_KEY, name);
		}
		public EventData putData(String key, Object value){
			if(key == null) throw new NullPointerException("Null data key");
			else if(key.equals(NAME_KEY)) throw new IllegalArgumentException("Data key \"" + key + "\" is reserved");
			data.put(key, value);
			return this;
		}
		@Override public String toString(){return Resources.gson.toJson(data);}
	}
}
