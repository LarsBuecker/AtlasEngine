package Atlas.atlas.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL45;

import Atlas.atlas.renderer.BufferLayout;

public class VertexBuffer {
	
	private int rendererId;
	BufferLayout layout;
	
	public VertexBuffer(float[] vertices) {
		rendererId = glGenBuffers();
		bind();
		FloatBuffer buffer = storeDataInFloatBuffer(vertices);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	public VertexBuffer(int size) {
		rendererId = glGenBuffers();
		bind();
		glBufferData(GL_ARRAY_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
	}
	
	public void setData(FloatBuffer buffer) {
		bind();
		GL45.glBufferSubData(rendererId, 0, buffer);
	}
	
	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, rendererId);
	}
	
	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public BufferLayout getLayout() {
		return layout;
		
	}
	
	public void setBufferLayout(BufferLayout layout) {
		this.layout = layout;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
