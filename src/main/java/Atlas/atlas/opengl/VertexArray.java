package Atlas.atlas.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.List;
import java.util.Vector;

import Atlas.atlas.renderer.BufferElement;
import Atlas.atlas.renderer.BufferElement.ShaderDataType;
import Atlas.atlas.renderer.BufferLayout;

public class VertexArray {
	
	private int rendererId;
	private Vector<VertexBuffer> vertexBuffers = new Vector<VertexBuffer>();
	private IndexBuffer indexBuffer;
	
	public static int ShaderDataTypeToOpenGLBaseType(ShaderDataType type) {
		switch(type) {
		case Bool: return GL_INT;
		case Float: return GL_FLOAT;
		case Float2: return GL_FLOAT;
		case Float3: return GL_FLOAT;
		case Float4: return GL_FLOAT;
		case Int: return GL_INT;
		case Int2: return GL_INT;
		case Int3: return GL_INT;
		case Int4: return GL_INT;
		case Mat3: return GL_FLOAT;
		case Mat4: return GL_FLOAT;
		case None: break;
		default: break;
		}
		System.err.println("Unknown ShaderDataType!");
		return 0;
	}

	public VertexArray() {
		this.rendererId = glGenVertexArrays();		
	}
	
	public void bind() {
		glBindVertexArray(rendererId);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public void addVertexBuffer(VertexBuffer vertexBuffer) {
		if ( vertexBuffer.getLayout().getElements().size() == 0 ) {
			System.err.println("Vertex Buffer has no layout!");
			System.exit(-1);
		}
		
		bind();
		vertexBuffer.bind();
		
		int index = 0;
		BufferLayout layout = vertexBuffer.getLayout();
		for ( BufferElement element : layout.getElements() ) {
			glEnableVertexAttribArray(index);
			glVertexAttribPointer(index, element.getComponentCount(), ShaderDataTypeToOpenGLBaseType(element.type), element.normalized, layout.getStride(), element.offset);
			index++;
		}
		vertexBuffers.insertElementAt(vertexBuffer, 0);
	}
	
	public void setIndexBuffer(IndexBuffer indexBuffer) {
		glBindVertexArray(rendererId);
		indexBuffer.bind();
		
		this.indexBuffer = indexBuffer;
	}
	
	public List<VertexBuffer> getVertexBuffer() {
		return vertexBuffers;
	}
	
	public IndexBuffer getIndexBuffer() {
		return indexBuffer;
	}
	
}
