package Atlas.atlas.renderer;

import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.opengl.IndexBuffer;
import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;
import Atlas.atlas.opengl.VertexBuffer;
import Atlas.atlas.renderer.BufferElement.ShaderDataType;

public class Renderer2D {

	static Renderer2DStorage data;
	
	public static void init() {
		data = new Renderer2DStorage();
		data.quadVertexArray = new VertexArray();
		
		float square[] = {
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f,
			 0.5f,  0.5f, 0.0f,
			-0.5f,  0.5f, 0.0f
		};
		VertexBuffer squareBuffer = new VertexBuffer(square, 3);
		squareBuffer.setBufferLayout(new BufferLayout().addElement(new BufferElement(ShaderDataType.Float3, "a_Position", false)));
		data.quadVertexArray.addVertexBuffer(squareBuffer);
		
		int squareIndices[] = {0, 1, 2, 2, 3, 0 };
		IndexBuffer squareIb = new IndexBuffer(squareIndices, squareIndices.length);
		data.quadVertexArray.setIndexBuffer(squareIb);
		
		data.flatColorShader = new Shader(Shader.loadShader("flatColor.vs"), Shader.loadShader("flatColor.fs"));
	}
	
	public static void shutdown() {
		
	}
	
	public static void beginScene(OrthographicCamera camera) {
		data.flatColorShader.bind();
		data.flatColorShader.UploadUniformMat4("u_ViewProjection", camera.getViewProjectionMatrix());
	}
	
	public static void endScene() {
		
	}
	
	// Prinmitives 
	
	public static void drawQuad(Vec2f position, Vec2f size, Vec4f color) {
		drawQuad(new Vec3f(position.getX(), position.getY(), 0), size, color);
	}
	
	public static void drawQuad(Vec3f position, Vec2f size, Vec4f color) {
		data.flatColorShader.bind();
		data.flatColorShader.UploadUniformFloat4("u_Color", color);
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		data.flatColorShader.UploadUniformMat4("u_Transform", transform);
		
		data.quadVertexArray.bind();
		RendererAPI.drawIndexed(data.quadVertexArray);
	}
}
