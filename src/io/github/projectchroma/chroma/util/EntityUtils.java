package io.github.projectchroma.chroma.util;

import io.github.projectchroma.chroma.level.entity.Entity;
import io.github.projectchroma.chroma.level.entity.Player;
import io.github.projectchroma.chroma.level.entity.Steed;

public class EntityUtils{
	public static boolean isPlayer(Entity entity){
		if(entity instanceof Player) return true;
		else if(entity instanceof Steed) return isPlayer(((Steed)entity).getRider());
		else return false;
	}
}
