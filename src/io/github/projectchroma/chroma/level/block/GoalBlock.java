package io.github.projectchroma.chroma.level.block;

import java.awt.Color;
import java.io.FileNotFoundException;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.Player;
import io.github.projectchroma.chroma.settings.Progress;

public class GoalBlock extends Block{
	public static final String COLOR_NAME = "gold";
	public static final Color COLOR = new Color(0.8F, 0.8F, 0);
//	private static Sound winSound;
	public GoalBlock(float x, float y, float width, float height, Color scheme){
		super(x, y, width, height, COLOR, scheme);
	}
	@Override
	public void init(Window window, Chroma chroma){
//		if(winSound == null) winSound = Resources.loadSound("win.aif");
	}
	@Override
	public void update(Window window, Chroma chroma, LevelState level){}
	@Override
	public void onContact(Window window, Chroma chroma, LevelState level, Player player){
		try{
			if(Progress.setLevelComplete(level.getID())) Progress.write();
		}catch(FileNotFoundException ex){
			System.err.println("Error writing game progress to file (progress will not be saved)");
			ex.printStackTrace();
		}
		chroma.enterState(level.getID()+1);
	}
}
