package io.github.projectchroma.chroma.resource;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;

import io.github.projectchroma.chroma.util.Colors;

public class DeferredFont implements DeferredResource, Font{
	private DeferredAWTFont baseFont;
	private float size;
	private int modifiers;
	private UnicodeFont font = null;
	public DeferredFont(DeferredAWTFont baseFont, float size, int modifiers){
		this.baseFont = baseFont;
		this.size = size;
		this.modifiers = modifiers;
		
		LoadingList.get().add(this);
	}
	@Override
	public int getWidth(String str){
		return font.getWidth(str);
	}
	
	@Override
	public int getHeight(String str){
		return font.getHeight(str);
	}
	
	@Override
	public int getLineHeight(){
		return font.getLineHeight();
	}
	
	@Override
	public void drawString(float x, float y, String text){
		font.drawString(x, y, text);
	}
	
	@Override
	public void drawString(float x, float y, String text, Color col){
		font.drawString(x, y, text, col);
	}
	
	@Override
	public void drawString(float x, float y, String text, Color col, int startIndex, int endIndex){
		font.drawString(x, y, text, col, startIndex, endIndex);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void load() throws IOException{
		baseFont.load();
		font = new UnicodeFont(baseFont.getFont().deriveFont(modifiers, size));
		font.addAsciiGlyphs();
		
		font.getEffects().add(new ColorEffect(Colors.awtColor(Color.black)));
		font.getEffects().add(new ColorEffect(Colors.awtColor(Color.white)));
		
		try{
			font.loadGlyphs();
		}catch(SlickException ex){
			throw new IOException(ex);
		}
	}
	
	@Override
	public String getDescription(){
		return baseFont.getDescription();
	}
}
