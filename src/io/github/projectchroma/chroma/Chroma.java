package io.github.projectchroma.chroma;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Chroma {
	private long window;//The window handle
	private final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;

	private Chroma() {
		System.out.println("Starting Chroma");
	}

	public void run() {
		System.out.println("LWJGL v" + Version.getVersion());

		try {
			init();
			loop();
			
			glfwFreeCallbacks(window);//Stop callbacks
			glfwDestroyWindow(window);//Destroy the window
		} finally {
			glfwTerminate();//End GLFW
			glfwSetErrorCallback(null).free();//Stop the error callback
		}
	}

	private void init() {
		//Print errors to the error stream
		GLFWErrorCallback.createPrint(System.err).set();
		
		//Initialize GLFW (window library); returns if it was successful or not
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");//Create window
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);//Hide window
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);//Disallow resizing

		//Create the window
		window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Hello World!", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");//Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);//We will detect this in our rendering loop
		});
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());//Get the resolution of the primary monitor
		glfwSetWindowPos(window, (vidmode.width() - WINDOW_WIDTH) / 2, (vidmode.height() - WINDOW_HEIGHT) / 2);//Center window on screen
		glfwMakeContextCurrent(window);//Make the OpenGL context current
		glfwSwapInterval(1);//Enable v-sync
		glfwShowWindow(window);//Show window
	}

	private void loop() {
		//Initialize default capabilities
		GL.createCapabilities();//Set the clear color
		glClearColor(0F, 0F, 0F, 1F);

		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);//Clear the framebuffer
			
			
			
			glfwSwapBuffers(window);//swap the color buffers
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new Chroma().run();
	}
}