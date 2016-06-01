package io.github.projectchroma.chroma.util;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import io.github.projectchroma.chroma.settings.Settings;

public class FileIO{
	public static final File SCREENSHOT_DIR = new File("screenshots");
	public static void init(){
		Settings.SETTINGS_DIR.mkdirs();
		SCREENSHOT_DIR.mkdirs();
	}
	public static File saveScreenshot(Image img) throws SlickException{
		File screenshot;
		
		int i = 0;
		do{
			screenshot = new File(SCREENSHOT_DIR, i++ + ".png");
		}while(screenshot.exists());
		ImageOut.write(img, screenshot.toString());
		return screenshot;
	}
}
