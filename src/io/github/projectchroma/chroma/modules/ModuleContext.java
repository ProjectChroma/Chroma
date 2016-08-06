package io.github.projectchroma.chroma.modules;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.newdawn.slick.SlickException;

import io.github.projectchroma.chroma.modules.Module.Instance;

public class ModuleContext {
	private final File moduleFile;
	private final Class<? extends Module> moduleClass;
	private Module instance;
	protected ModuleContext(File moduleFile, Class<? extends Module> moduleClass){
		this.moduleFile = moduleFile;
		this.moduleClass = moduleClass;
	}
	public void createInstance() throws SlickException{
		try {
			Constructor<? extends Module> ctr = moduleClass.getConstructor();
			ctr.setAccessible(true);
			instance = ctr.newInstance();
		} catch (NoSuchMethodException ex) {
			throw new SlickException("Module class " + moduleClass.getName() + " cannot be instantiated (no no-args constructor)", ex);
		} catch (InstantiationException ex) {
			throw new SlickException("Module class " + moduleClass.getName() + "cannot be instantiated (abstract)", ex);
		} catch (IllegalAccessException ex) {
			throw new SlickException("Module class " + moduleClass.getName() + "cannot be instantiated (inaccessible constructor)", ex);
		} catch (InvocationTargetException ex) {
			throw new SlickException("Module class " + moduleClass.getName() + "cannot be instantiated (error in constructor)", ex.getCause());
		}
		for(Field field : moduleClass.getDeclaredFields()){
			if(field.isAnnotationPresent(Instance.class) && Module.class.isAssignableFrom(field.getType())){
				field.setAccessible(true);
				try{
					field.set(instance, instance);
				}catch(IllegalAccessException ex){
					System.err.println("Unable to put instance into field " + moduleClass.getName() + '#' + field.getName());
					ex.printStackTrace();
				}
			}
		}
	}
	public void load(){
		instance.load();
	}
	public File getSourceFile(){return moduleFile;}
	public Class<? extends Module> getModuleClass(){return moduleClass;}
	public Module getInstance(){return instance;}
}
