package io.github.projectchroma.chroma.level.block;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.LevelExitTransition;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.SwipeTransition;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.settings.Analytics;
import io.github.projectchroma.chroma.settings.Analytics.EventData;
import io.github.projectchroma.chroma.util.Direction;
import io.github.projectchroma.chroma.util.EntityUtils;

public class HazardBlock extends Block{
	public static final String COLOR_NAME = "red";
	public static final Color COLOR = Color.red;
	private static Sound deathSound;
	public HazardBlock(BlockObject block){
		super(block, COLOR);
	}
	@Override
	public void init(GameContainer container) throws SlickException{
		if(deathSound == null) deathSound = Resources.loadSound(Resources.getSoundPath("death.aif"));
	}
	@Override
	public void onContact(GameContainer container, LevelState level, Entity entity){
		if(EntityUtils.isPlayer(entity)){
			Analytics.write(new EventData("LevelLose").putData("module", level.getDeclaringModule().getID()).putData("level", level.name())
					.putData("x1", getLeft()).putData("y1", getTop()).putData("x2", getRight()).putData("y2", getBottom()));
			Chroma.instance().enterState(level.getID(), new LevelExitTransition(false, deathSound), new SwipeTransition(Direction.RIGHT));
		}
	}
}
