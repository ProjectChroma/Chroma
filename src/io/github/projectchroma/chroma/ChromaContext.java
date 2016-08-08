package io.github.projectchroma.chroma;

import io.github.projectchroma.chroma.gui.CreditsState;
import io.github.projectchroma.chroma.gui.GameEndState;
import io.github.projectchroma.chroma.gui.KeybindsMenuState;
import io.github.projectchroma.chroma.gui.LevelSelectState;
import io.github.projectchroma.chroma.gui.MainMenuState;
import io.github.projectchroma.chroma.gui.SettingsMenuState;
import io.github.projectchroma.chroma.level.LevelState;
import io.github.projectchroma.chroma.level.PausedState;
import io.github.projectchroma.chroma.modules.Module;
import io.github.projectchroma.chroma.modules.ModuleContext;

public class ChromaContext extends ModuleContext{
	public static final ChromaContext INSTANCE = new ChromaContext();
	public ChromaContext(){super(ChromaModule.class);}
	@Override public String toString(){return super.toString().replace("ModuleContext", "ChromaContext");}
	public static class ChromaModule extends Module{
		@Module.Instance
		public static ChromaModule instance;
		private ChromaModule(){super("chroma");}
		@Override
		public void load(){
			Chroma.instance().addState(MainMenuState.instance);
			Chroma.instance().addState(LevelSelectState.instance);
			Chroma.instance().addState(SettingsMenuState.instance);
			Chroma.instance().addState(KeybindsMenuState.instance);
			Chroma.instance().addState(CreditsState.instance);
			for(int i = 1; i <= Chroma.NUM_LEVELS; i++){
				Chroma.instance().addState(new LevelState(Resources.loadLevel(Resources.LEVEL_PATH + "level" + i + ".json")));
			}
			Chroma.instance().addState(GameEndState.instance);
			Chroma.instance().addState(PausedState.instance);
		}
	}
}
