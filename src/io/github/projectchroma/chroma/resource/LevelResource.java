package io.github.projectchroma.chroma.resource;

import java.io.IOException;
import java.io.InputStreamReader;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.level.LevelObject;

public class LevelResource extends Resource<LevelObject>{
	private int id;
	private LevelObject level;
	public LevelResource(int id){
		this.id = id;
	}
	@Override
	protected void loadResource() throws IOException{
		level = Chroma.GSON.fromJson(new InputStreamReader(Resources.getResourceAsStream(getPath())), LevelObject.class);
	}
	@Override
	public LevelObject get(){
		return level;
	}
	@Override
	public String getPath(){
		return "/levels/level" + id + ".json";
	}
}
