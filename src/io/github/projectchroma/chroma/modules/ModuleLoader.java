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

import io.github.projectchroma.chroma.ChromaContext;
import io.github.projectchroma.chroma.ChromaSecurity;
import io.github.projectchroma.chroma.util.FileIO;

public class ModuleLoader {
	private static ModuleLoader instance;
	private File moduleDir = new File("modules"), extractDir = new File("resources");
	private List<ModuleContext> instances = new ArrayList<>(), validInstances = new ArrayList<>();
	private ModuleContext activeModule = null;
	public static ModuleLoader instance(){
		return instance == null ? instance = new ModuleLoader() : instance;
	}
	private ModuleLoader(){
		instances.add(ChromaContext.INSTANCE);
		validInstances.add(ChromaContext.INSTANCE);
	}
	public void createModules(){
		for(File module : moduleDir.listFiles((parent, name) -> name.endsWith(".jar"))){
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
		List<String> classNames = new ArrayList<String>();
		try(JarFile jar = new JarFile(module)){
			Enumeration<JarEntry> entries = jar.entries();
			while(entries.hasMoreElements()){
				JarEntry entry = entries.nextElement();
				File file = new File(extractDir, entry.getName());
				if(!entry.isDirectory()){
					file.getParentFile().mkdirs();
					file.createNewFile();
					FileIO.write(jar.getInputStream(entry), file);
					if(entry.getName().endsWith(".class")){
						String className = entry.getName().replace('/', '.');
						className = className.substring(0, className.length()-6);
						if(ChromaSecurity.isSecurityExempt(className)){
							System.err.println("Class " + className + " (module " + module.toString() + ") is attempting to impersonate a security-exempt class.");
							System.err.println("THIS IS VERY, VERY BAD. This module is almost certainly malware and should be uninstalled immediately.");
							System.err.println("This module will not be loaded.");
							return;
						}else classNames.add(className);
					}
				}
			}
		}
		try(URLClassLoader loader = new URLClassLoader(new URL[]{extractDir.toURI().toURL()})){
			for(String className : classNames){
				try{
					Class<?> clazz = loader.loadClass(className);
					if(Module.class.isAssignableFrom(clazz)){
						ModuleContext context = new ModuleContext((Class<? extends Module>)clazz);
						instances.add(context);
						validInstances.add(context);
					}
				}catch(ClassNotFoundException ex){
					System.err.println("Error loading class " + className);
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
	protected void setActiveModule(ModuleContext module){activeModule = module;}
	public ModuleContext getActiveModule(){return activeModule;}
	public int getModuleCount(){return instances.size();}
	public int getValidModuleCount(){return validInstances.size();}
	public List<ModuleContext> getLoadedModules(){return new ArrayList<>(validInstances);}
}
