package Atlas.atlas.core;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import Atlas.atlas.math.Vec2f;

public class Input {
	
	protected Input() {
		
	}
	
	public static boolean isKeyPressed(int keycode) {
		long window = Application.getInstance().getWindow().getWindow();
		int state = GLFW.glfwGetKey(window, keycode);
		return (state == GLFW.GLFW_PRESS) || (state == GLFW.GLFW_REPEAT);
	}
	
	public static boolean isMouseButtonPressed(int button) {
		long window = Application.getInstance().getWindow().getWindow();
		int state = GLFW.glfwGetMouseButton(window, button);
		return state == GLFW.GLFW_PRESS;
	}
	
	public static Vec2f getMousePosition() {
		long window = Application.getInstance().getWindow().getWindow();
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);
		double x = xBuffer.get(0);
		double y = yBuffer.get(0);
		return new Vec2f((float) x, (float) y);
	}
	
	public static float getMouseX() {
		return getMousePosition().getX();
	}
	
	public static float getMouseY() {
		return getMousePosition().getY();
	}
	
}
