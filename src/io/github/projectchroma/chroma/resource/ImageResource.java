package io.github.projectchroma.chroma.resource;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResource extends Resource<Image>{
	private String name;
	private Image img;
	public ImageResource(String name){
		this.name = name;
	}
	@Override
	protected void loadResource() throws IOException{
		img = ImageIO.read(Resources.getResourceAsStream(getPath()));
	}
	@Override
	public Image get(){
		return img;
	}
	@Override
	public String getPath(){
		return "/assets/images/" + name + ".png";
	}
}
