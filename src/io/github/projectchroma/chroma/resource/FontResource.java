package io.github.projectchroma.chroma.resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class FontResource extends Resource<Font>{
	private String name;
	private float size;
	private int modifiers;
	private Font font;
	public FontResource(String name, float size, int modifiers){
		this.name = name;
		this.size = size;
		this.modifiers = modifiers;
	}
	@Override
	protected void loadResource() throws IOException{
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, Resources.getResourceAsStream(getPath()));
			font = font.deriveFont(modifiers, size);
		}catch(FontFormatException ex){throw new IOException(ex);}
	}

	@Override
	public Font get(){
		return font;
	}

	@Override
	public String getPath(){
		return "/assets/fonts/" + name + ".ttf";
	}
}
