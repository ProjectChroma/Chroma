package io.github.projectchroma.chroma.util;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

public class FileIO{
	public static final File SCREENSHOT_DIR = new File("screenshots");
	static{
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
