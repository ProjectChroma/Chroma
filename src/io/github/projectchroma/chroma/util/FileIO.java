package io.github.projectchroma.chroma.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;

import io.github.projectchroma.chroma.settings.Settings;

public class FileIO{
	public static final File SCREENSHOT_DIR = new File("screenshots");
	private static int screenshotIndex = 0;
	public static void init(){
		Settings.SETTINGS_DIR.mkdirs();
		SCREENSHOT_DIR.mkdirs();
		while(new File(SCREENSHOT_DIR, screenshotIndex + ".png").exists()) ++screenshotIndex;
		System.out.println("Starting at screenshot index " + screenshotIndex);
	}
	public static File saveScreenshot(Image img) throws SlickException{
		File screenshot = new File(SCREENSHOT_DIR, screenshotIndex++ + ".png");
		
		try{
			screenshot.createNewFile();
		}catch(IOException ex){
			throw new SlickException("Error creating screenshot file " + screenshot, ex);
		}
		try(FileOutputStream out = new FileOutputStream(screenshot)){
			ImageOut.write(img, "PNG", out, true);
		}catch(IOException ex){
			throw new SlickException("Error taking screenshot " + screenshot, ex);
		}
		return screenshot;
	}
	public static void write(InputStream input, File output) throws IOException{
		try(InputStream in = input; OutputStream out = new FileOutputStream(output)){
			byte[] buffer = new byte[4096];
			int read;
			while((read = in.read(buffer)) > 0) out.write(buffer, 0, read);
		}
	}
}
