package io.github.projectchroma.chroma.level.entity;

import static io.github.projectchroma.chroma.util.Direction.DOWN;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.level.LevelObject.EntityObject;
import io.github.projectchroma.chroma.level.LevelObject.PlayerObject;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.settings.Keybind;

public class Player extends Entity{
	/**Velocities during player-caused motion*/
	public static final float V_MOVE = 2F, V_JUMP = -3.7F;
	/**Sound effects*/
	private static Sound jump;
	/**Keys*/
	private static Keybind keyLeft, keyRight, keyJump, keyDismount;
	
	/**Color to render the player, regardless of scheme (null uses scheme color)*/
	private Color renderCol = null;
	
	public Player(PlayerObject player){
		super(new EntityObject(player.x, player.y), 50, 50);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException{
		if(jump == null) jump = Resources.loadSound(Resources.getSoundPath("jump.aif"));
		
		if(keyLeft == null) keyLeft = Keybind.get("player.left", Input.KEY_LEFT);
		if(keyRight == null) keyRight = Keybind.get("player.right", Input.KEY_RIGHT);
		if(keyJump == null) keyJump = Keybind.get("player.up", Input.KEY_SPACE);
		if(keyDismount == null) keyDismount = Keybind.get("player.dismount", Input.KEY_RSHIFT);
	}
	
	@Override
	public void setKinematics(GameContainer container, LevelState level) throws SlickException{
		super.setKinematics(container, level);
		if(keyLeft.isDown()) v.x = -V_MOVE;
		else if(keyRight.isDown()) v.x = V_MOVE;
		else v.x = 0;
		if(keyJump.isDown() && (colliding[DOWN.ordinal()] || (steed != null && steed.colliding[DOWN.ordinal()]))){
			v.y = steed == null ? V_JUMP : steed.getJumpVelocity();
			jump.play();
		}
		if(keyDismount.isPressed() && steed != null) dismount();
	}
	
	public void render(GameContainer container, LevelState level, Graphics g) throws SlickException{
		g.setColor(renderCol != null ? renderCol : getColor(level));
		g.fill(bounds);
		
		if(Chroma.isDebugMode()){
			g.translate(v.x, v.y);
			Color[] colors = {Color.red, Color.green, Color.cyan, Color.yellow};
			for(int i=0; i<hitboxes.length; i++){
				if(hitboxes[i] == null) continue;
				g.setColor(colliding[i] ? colors[i] : colors[i].darker());
				g.fill(hitboxes[i]);
			}
			g.translate(-v.x, -v.y);
		}
	}
	
	public void setRenderColor(Color c){
		renderCol = c;
	}
	public void resetRenderColor(){
		renderCol = null;
	}
}
