package Atlas.atlas.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import Atlas.atlas.core.Log;

public class OpenGLContext {

	private long windowHandle;
	
	public OpenGLContext(long windowHandle) {
		this.windowHandle = windowHandle;
	}
	
	public void init() {
		GLFW.glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		Log.coreLog("OpenGL Info: ");
		Log.coreLog("  Vendor: " + GL11.glGetString(GL11.GL_VENDOR));
		Log.coreLog("  Renderer: " + GL11.glGetString(GL11.GL_RENDERER));
		Log.coreLog("  Version: " + GL11.glGetString(GL11.GL_VERSION));
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(windowHandle);
		
		
	}
	
	public static GLCapabilities getGLCapabilities() {
		return GL.getCapabilities();
	}
}
