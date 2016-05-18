package io.github.projectchroma.chroma.level;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import io.github.projectchroma.chroma.BaseGameState;
import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Resources;
import io.github.projectchroma.chroma.Sounds;
import io.github.projectchroma.chroma.level.LevelObject.BlockObject;
import io.github.projectchroma.chroma.level.LevelObject.HintObject;
import io.github.projectchroma.chroma.util.Colors;

public class LevelState extends BaseGameState{
	private static Font nameFont;
	private String name;
	private float playerX, playerY;
	private LevelElement[] elements;
	public LevelState(int id){
		super(id);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException{
		LevelObject level = Resources.loadLevel(id);
		name = level.name;
		playerX = level.playerStart.x;
		playerY = level.playerStart.y;
		
		elements = new LevelElement[level.blocks.size() + level.hints.size() + 4];//The blocks, the hints, three barriers, and the player
		elements[0] = new Block(0, Chroma.WINDOW_HEIGHT - Block.WALL_WIDTH, Chroma.WINDOW_WIDTH, Block.WALL_WIDTH);//Floor
		elements[1] = new Block(0, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT);//Left wall
		elements[2] = new Block(Chroma.WINDOW_WIDTH - Block.WALL_WIDTH, 0, Block.WALL_WIDTH, Chroma.WINDOW_HEIGHT);//Right wall
		elements[3] = Chroma.instance().player();
		int i = 4;
		for(BlockObject block : level.blocks){
			elements[i++] = new Block(block.x, block.y, block.width, block.height, Colors.byName(block.color));
		}
		for(HintObject hint : level.hints){
			elements[i++] = new Hint(hint.text, Colors.byName(hint.color), hint.x, hint.y);
		}
		
		if(nameFont == null) nameFont = Chroma.instance().createFont(30F);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException{
		g.setColor(Chroma.instance().background());
		g.setFont(nameFont);
		g.fillRect(0, 0, Chroma.WINDOW_WIDTH, Chroma.WINDOW_HEIGHT);//Draw background
		g.setColor(Chroma.instance().foreground());
		g.drawString(name, Block.WALL_WIDTH, 0);
		for(LevelElement element : elements){
			if(!element.getColor().equals(Chroma.instance().background())) element.render(container, g);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException{
		super.update(container, game, delta);
		if(container.getInput().isKeyPressed(Input.KEY_P)) game.enterState(PausedState.ID, null, new PausedState.Enter());
		if(!container.isPaused()){
			for(LevelElement element : elements)
				element.update(container, delta);
			if(container.getInput().isKeyPressed(Input.KEY_SPACE)) Chroma.instance().toggleScheme();
		}
	}
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException{
		super.enter(container, game);
		if(!(Chroma.instance().previousState() instanceof PausedState)) restart();
	}
	public void restart(){
		Chroma.instance().player().moveTo(playerX, playerY);
		Chroma.instance().player().resetKinematics();
		Chroma.instance().setScheme(true);
	}
	@Override
	protected Music getMusic(){
		return Sounds.getLevelMusic();
	}
	
	public LevelElement[] elements(){
		return elements;
	}
}
