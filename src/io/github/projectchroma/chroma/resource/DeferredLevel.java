package io.github.projectchroma.chroma.resource;

import java.io.IOException;
import java.io.InputStreamReader;

import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.util.ResourceLoader;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.level.LevelObject;

public class DeferredLevel implements DeferredResource{
	private String ref;
	private LevelObject level;
	public DeferredLevel(String ref){
		this.ref = ref;
		LoadingList.get().add(this);
	}
	@Override
	public void load() throws IOException{
		level = Chroma.GSON.fromJson(new InputStreamReader(ResourceLoader.getResourceAsStream(ref)), LevelObject.class);
	}
	public LevelObject getLevel(){
		return level;
	}
	@Override
	public String getDescription(){
		return ref;
	}
}
