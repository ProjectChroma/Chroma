package io.github.projectchroma.chroma.modules;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.newdawn.slick.SlickException;

public class ModuleLoader {
	private static ModuleLoader instance;
	private File dir = new File("modules");
	private List<ModuleContext> instances = new ArrayList<>(), validInstances = new ArrayList<>();
	public static ModuleLoader instance(){
		return instance == null ? instance = new ModuleLoader() : instance;
	}
	public void createModules(){
		for(File module : dir.listFiles((parent, name) -> name.endsWith(".jar"))){
			try{
				createModule(module);
			}catch(IOException ex){
				System.err.println("Error loading module from file".concat(module.getName()));
				ex.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void createModule(File module) throws IOException{
		try(JarFile jar = new JarFile(module); URLClassLoader loader = new URLClassLoader(new URL[]{new URL("jar:file:" + module.toString() + "!/")})){
			Enumeration<JarEntry> entries = jar.entries();
			while(entries.hasMoreElements()){
				JarEntry entry = entries.nextElement();
				if(entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
				String className = entry.getName().replace('/', '.');
				className = className.substring(0, className.length() - 6);//Remove ".class"
				try{
					Class<?> clazz = loader.loadClass(className);
					if(Module.class.isAssignableFrom(clazz)){
						ModuleContext context = new ModuleContext(module, (Class<? extends Module>)clazz);
						instances.add(context);
						validInstances.add(context);
					}
				}catch(ClassNotFoundException ex){
					System.err.println("Error loading class ".concat(className));
					ex.printStackTrace();
				}
			}
		}
	}
	public void instantiateModules(){
		for(int i=0; i<validInstances.size();){
			ModuleContext context = validInstances.get(i);
			try{
				context.createInstance();
				++i;
			}catch(SlickException ex){
				System.err.println("Error instantiating module " + context.getModuleClass().getName() + ". It will not be loaded.");
				ex.printStackTrace();
				validInstances.remove(context);
			}
		}
	}
	public void loadModules(){
		for(int i=0; i<validInstances.size();){
			ModuleContext context = validInstances.get(i);
			try{
				context.load();
				++i;
			}catch(Throwable ex){
				System.err.println("Error loading module " + context.getModuleClass().getName() + ". It will not be loaded.");
				ex.printStackTrace();
				validInstances.remove(context);
			}
		}
	}
	public int getModuleCount(){return instances.size();}
	public int getValidModuleCount(){return validInstances.size();}
}
