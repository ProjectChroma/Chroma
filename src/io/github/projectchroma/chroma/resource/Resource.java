package io.github.projectchroma.chroma.resource;

import java.io.IOException;

public abstract class Resource<T>{
	private boolean loaded = false;
	public void load() throws IOException{
		loadResource();
		loaded = true;
		Resources.removeResource(this);
	}
	protected abstract void loadResource() throws IOException;
	public abstract T get();
	public abstract String getPath();
	public boolean isLoaded(){return loaded;}
}
