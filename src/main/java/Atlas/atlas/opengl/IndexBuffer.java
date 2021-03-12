package Atlas.atlas.opengl;

import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class IndexBuffer {
	
	private int rendererId;
	private int count;
	
	public IndexBuffer(int[] indices, int count) {
		this.count = count;
		rendererId = glGenBuffers();
		bind();
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void bind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererId);
	}
	
	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public int getCount() {
		return count;
	}
	
	public static IndexBuffer create(int[] indices, int size) {
		return null;
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
