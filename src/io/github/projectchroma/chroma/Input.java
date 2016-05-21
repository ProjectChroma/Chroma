package io.github.projectchroma.chroma;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class Input{
	private List<InputListener> listeners = new ArrayList<>();
	private boolean[] keyStates = new boolean[255], keyPressedStates = new boolean[255];
	private boolean[] mouseStates = new boolean[3], mousePressedStates = new boolean[3];
	private Listener listener = new Listener();
	public Input(){}
	
	protected void update(){
		for(int i=keyPressedStates.length-1; i>=0; --i) keyPressedStates[i] = false;
		for(int i=mousePressedStates.length-1; i>=0; --i) mousePressedStates[i] = false;
	}
	protected Listener getListener(){
		return listener;
	}
	private class Listener implements KeyListener, MouseListener, MouseWheelListener{
		@Override public void keyTyped(KeyEvent e){}
		@Override
		public void keyPressed(KeyEvent e){
			for(InputListener listener : listeners){
				listener.keyPressed(e.getKeyChar(), e.getKeyCode());
			}
			keyStates[e.getKeyCode()] = true;
		}
		@Override
		public void keyReleased(KeyEvent e){
			for(InputListener listener : listeners){
				listener.keyReleased(e.getKeyChar(), e.getKeyCode());
			}
			keyStates[e.getKeyCode()] = false;
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			for(InputListener listener : listeners){
				listener.mousePressed(e.getButton(), e.getClickCount());
			}
			mouseStates[e.getButton()] = true;
		}
		@Override
		public void mouseReleased(MouseEvent e){
			for(InputListener listener : listeners){
				listener.mouseReleased(e.getButton());
			}
			mouseStates[e.getButton()] = false;
		}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e){
			for(InputListener listener : listeners){
				listener.mouseScrolled(e.getWheelRotation());
			}
		}
	}
}
