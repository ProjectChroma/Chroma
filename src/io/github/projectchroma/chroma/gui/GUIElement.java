package io.github.projectchroma.chroma.gui;

import java.awt.Graphics;

import io.github.projectchroma.chroma.Chroma;
import io.github.projectchroma.chroma.Window;

public abstract class GUIElement{
	public abstract void render(Window window, Chroma chroma, Graphics g);
	public abstract void update(Window window, Chroma chroma);
}
