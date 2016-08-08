package io.github.projectchroma.chroma.modules;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public abstract class Module {
	private final String id, version;
	protected Module(String id){this(id, "");}
	protected Module(String id, String version){
		if(id == null) throw new NullPointerException("Null module id (class=" + getClass().getName() + ")");
		if(version == null) version = "";
		this.id = id;
		this.version = version;
	}
	public String getID(){return id;}
	public String getVersion(){return version;}
	public abstract void load();
	@Override
	public String toString(){
		if(version.isEmpty()) return id;
		else return id.concat("@").concat(version);
	}
	
	@Retention(RUNTIME)
	@Target(FIELD)
	public static @interface Instance{}
}
