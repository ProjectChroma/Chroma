package io.github.projectchroma.chroma;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Window{
	public static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600, FPS = 1;
	private Chroma chroma;
	private JFrame window;
	private Input input;
	private Timer updateTimer;
	private TimerTask updateTask;
	protected Window(Chroma chroma){
		this.chroma = chroma;
		this.window = new JFrame("Chroma");
		this.input = new Input();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setResizable(false);
		window.setContentPane(new GameStateWrapper());
		window.setVisible(true);
		
		updateTimer = new Timer();
		updateTask = new TimerTask(){
			public void run(){
				System.out.print('-');
				if(chroma.getCurrentState() != null){
					chroma.getCurrentState().update(chroma);
					window.getContentPane().repaint();
				}
			}
		};
	}
	protected void startTicking(){
		updateTimer.scheduleAtFixedRate(updateTask, 0, (long)(1000F / FPS));
	}
	protected void stopTicking(){
		updateTask.cancel();
	}
	private class GameStateWrapper extends JComponent{
		private static final long serialVersionUID = 1L;
		private GameStateWrapper(){
			addKeyListener(input.getListener());
			addMouseListener(input.getListener());
			addMouseWheelListener(input.getListener());
		}
		@Override
		public void paintComponent(Graphics g){
			if(chroma.getCurrentState() != null){
				chroma.getCurrentState().render(chroma, g);
			}
		}
	}
}
