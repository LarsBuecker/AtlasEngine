package Atlas.atlas.renderer;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import Atlas.atlas.math.Vec4f;
import Atlas.atlas.opengl.VertexArray;

public class RendererAPI {
	
	public static void init() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_DEPTH_TEST);
	}

	public static void setClearColor(Vec4f color) {
		GL11.glClearColor(color.getX(), color.getY(), color.getZ(), color.getW());
	}
	
	public static void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void drawIndexed(VertexArray vertexArray) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public static void drawIndexed(VertexArray vertexArray, int count) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
}
