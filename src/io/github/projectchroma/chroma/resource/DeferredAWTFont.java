package io.github.projectchroma.chroma.resource;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.util.ResourceLoader;

public class DeferredAWTFont implements DeferredResource{
	private String ref;
	private Font font;
	public DeferredAWTFont(String ref){
		this.ref = ref;
		LoadingList.get().add(this);
	}
	@Override
	public void load() throws IOException{
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(ref));
		}catch(FontFormatException ex){
			throw new IOException(ex);
		}
	}
	@Override
	public String getDescription(){
		return ref;
	}
	public Font getFont(){
		return font;
	}
	public org.newdawn.slick.Font createFont(float size, int modifiers){
		return new DeferredFont(this, size, modifiers);
	}
}
