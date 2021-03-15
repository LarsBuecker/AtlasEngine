package Atlas.atlas.core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

import Atlas.atlas.events.types.KeyPressedEvent;
import Atlas.atlas.events.types.KeyReleasedEvent;
import Atlas.atlas.events.types.MousePressedEvent;
import Atlas.atlas.events.types.MouseReleasedEvent;
import Atlas.atlas.events.types.MouseScrolledEvent;
import Atlas.atlas.events.types.WindowCloseEvent;
import Atlas.atlas.events.types.WindowResizeEvent;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.opengl.OpenGLContext;

public class Window {

	private long window;
	
	private String title;
	private int width, height;
	
	private OpenGLContext context;
	
	private GLFWWindowSizeCallback windowSizeCallback;
	private GLFWWindowCloseCallback windowCloseCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWScrollCallback scrollCallback;
	
	public Window(String title, int width, int height) {	
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	private void setLocalCallback() {
		windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				WindowResizeEvent windowResizeEvent = new WindowResizeEvent(argWidth, argHeight);
				Application.getInstance().onEvent(windowResizeEvent);
			}
		}; 
		
		windowCloseCallback = new GLFWWindowCloseCallback() {
			@Override
			public void invoke(long argWindow) {
				WindowCloseEvent windowCloseEvent = new WindowCloseEvent();
				Application.getInstance().onEvent(windowCloseEvent);				
			}
		};
		
		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long argWindow, int key, int scancode, int action, int mods) {
				if ( action == GLFW.GLFW_PRESS ) {
					KeyPressedEvent keyPressedEvent = new KeyPressedEvent(key);
					Application.getInstance().onEvent(keyPressedEvent);
				}
				if ( action == GLFW.GLFW_RELEASE ) {
					KeyReleasedEvent keyReleasedEvent = new KeyReleasedEvent(key);
					Application.getInstance().onEvent(keyReleasedEvent);
				}
				
			}
		};
		
		mouseButtonCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				Vec2f pos = Input.getMousePosition();
				
				if ( action == GLFW.GLFW_PRESS ) {
					MousePressedEvent mousePressedEvent = new MousePressedEvent(button, (int) pos.getX(), (int) pos.getY());
					Application.getInstance().onEvent(mousePressedEvent);
				}
				if ( action == GLFW.GLFW_PRESS ) {
					MouseReleasedEvent mouseReleasedEvent = new MouseReleasedEvent(button, (int) pos.getX(), (int) pos.getY());
					Application.getInstance().onEvent(mouseReleasedEvent);
				}
			}
		};
		
		scrollCallback = new GLFWScrollCallback() {

			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				MouseScrolledEvent scrollEvnet = new MouseScrolledEvent((float) xoffset, (float) yoffset);
				Application.getInstance().onEvent(scrollEvnet);
			}
			
		};
		
		GLFW.glfwSetWindowSizeCallback(window, windowSizeCallback);
		GLFW.glfwSetWindowCloseCallback(window, windowCloseCallback);
		GLFW.glfwSetKeyCallback(window, keyCallback);
		GLFW.glfwSetMouseButtonCallback(window, mouseButtonCallback);
		GLFW.glfwSetScrollCallback(window, scrollCallback);
	}
	
	public void create() {
		if(!GLFW.glfwInit()) {
			System.err.print("Failed to init GLFW!");
			System.exit(-1);
		}
			
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		if ( window == 0 ) {
			System.err.println("Failed to create window!");
			System.exit(-1);
		}
		
		context = new OpenGLContext(window);
		context.init();
		GLFW.glfwSwapInterval(1); // Vsync
		
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, vidMode.width() / 2 - this.width / 2, vidMode.height() / 2 - this.width / 4 );
		GLFW.glfwShowWindow(window);	
		
		setLocalCallback();
	}
	
	
	public void update() {
		GL11.glViewport(0, 0, width, height);
		GLFW.glfwPollEvents();
	}
	
	public void swapBuffers() {
		context.swapBuffers();
	}
	
	public void dispose() {
		
	}
	
	public long getWindow() {
		return window;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		GLFW.glfwSetWindowTitle(window, title);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
