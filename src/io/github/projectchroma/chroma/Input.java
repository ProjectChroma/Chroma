package io.github.projectchroma.chroma;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class Input{
	private static final int NUM_KEYS = 255, NUM_BUTTONS = 3;
	private List<InputListener> listeners = new ArrayList<>();
	private boolean[] keyStates = new boolean[NUM_KEYS], keyPressedStates = new boolean[NUM_KEYS], keyReleasedStates = new boolean[NUM_KEYS];
	private boolean[] mouseStates = new boolean[NUM_BUTTONS], mousePressedStates = new boolean[NUM_BUTTONS], mouseReleasedStates = new boolean[NUM_BUTTONS];
	private int mouseX, mouseY;
	private Listener listener = new Listener();
	public Input(){}
	
	public void addListener(InputListener l){
		listeners.add(l);
	}
	public void clearListeners(){
		listeners.clear();
	}
	
	protected void clearPresses(){
		for(int i=NUM_KEYS-1; i>=0; --i){
			keyPressedStates[i] = false;
			keyReleasedStates[i] = false;
		}
		for(int i=NUM_BUTTONS-1; i>=0; --i){
			mousePressedStates[i] = false;
			mouseReleasedStates[i] = false;
		}
	}
	protected Listener getListener(){
		return listener;
	}
	
	public boolean isKeyPressed(int key){
		return keyPressedStates[key];
	}
	public boolean isKeyReleased(int key){
		return keyReleasedStates[key];
	}
	public boolean isKeyDown(int key){
		return keyStates[key];
	}
	public boolean isMousePressed(int key){
		return mousePressedStates[key];
	}
	public boolean isMouseReleased(int key){
		return mouseReleasedStates[key];
	}
	public boolean isMouseDown(int key){
		return mouseStates[key];
	}
	public int getMouseX(){
		return mouseX;
	}
	public int getMouseY(){
		return mouseY;
	}
	
	private class Listener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener{
		@Override public void keyTyped(KeyEvent e){}
		@Override
		public void keyPressed(KeyEvent e){
			for(InputListener listener : listeners){
				listener.keyPressed(e.getKeyChar(), e.getKeyCode());
			}
			keyStates[e.getKeyCode()] = true;
			keyPressedStates[e.getKeyCode()] = true;
		}
		@Override
		public void keyReleased(KeyEvent e){
			for(InputListener listener : listeners){
				listener.keyReleased(e.getKeyChar(), e.getKeyCode());
			}
			keyStates[e.getKeyCode()] = false;
			keyReleasedStates[e.getKeyCode()] = true;
		}
		@Override
		public void mouseClicked(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){
			for(InputListener listener : listeners){
				listener.mousePressed(e.getButton(), e.getClickCount());
			}
			mouseStates[e.getButton()] = true;
			mousePressedStates[e.getButton()] = true;
		}
		@Override
		public void mouseReleased(MouseEvent e){
			for(InputListener listener : listeners){
				listener.mouseReleased(e.getButton());
			}
			mouseStates[e.getButton()] = false;
			mouseReleasedStates[e.getButton()] = true;
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
		@Override public void mouseDragged(MouseEvent e){mouseMoved(e);}
		@Override public void mouseMoved(MouseEvent e){
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}
}
