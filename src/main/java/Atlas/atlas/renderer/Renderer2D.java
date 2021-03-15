package Atlas.atlas.renderer;

import org.lwjgl.opengl.GL11;

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
			-0.5f, -0.5f, 0.0f, 0, 0,
			 0.5f, -0.5f, 0.0f, 1, 0,
			 0.5f,  0.5f, 0.0f, 1, 1,
			-0.5f,  0.5f, 0.0f, 0, 1
		};
		VertexBuffer squareBuffer = new VertexBuffer(square, 3);
		BufferLayout layout = new BufferLayout().addElement(new BufferElement(ShaderDataType.Float3, "a_Position", false));
		layout.addElement(new BufferElement(ShaderDataType.Float2, "a_TexCoord", false));
		squareBuffer.setBufferLayout(layout);
		data.quadVertexArray.addVertexBuffer(squareBuffer);
		
		int squareIndices[] = {0, 1, 2, 2, 3, 0 };
		IndexBuffer squareIb = new IndexBuffer(squareIndices, squareIndices.length);
		data.quadVertexArray.setIndexBuffer(squareIb);

		data.whiteTexture = new Texture2D("res/default.png");
		
		data.textureShader = new Shader("res/shader/texture.glsl");
		data.textureShader.bind();
		data.textureShader.UploadUniformInt("u_Texture", 0);
		
		
	}
	
	public static void shutdown() {
		
	}
	
	public static void beginScene(OrthographicCamera camera) {
		data.textureShader.bind();
		data.textureShader.UploadUniformMat4("u_ViewProjection", camera.getViewProjectionMatrix());
	}
	
	public static void endScene() {
		
	}
	
	// Prinmitives 
	
	public static void drawQuad(Vec2f position, Vec2f size, Vec4f color) {
		drawQuad(new Vec3f(position.getX(), position.getY(), 0), size, color);
	}
	
	public static void drawQuad(Vec3f position, Vec2f size, Vec4f color) {
		data.textureShader.UploadUniformFloat4("u_Color", color);
		data.textureShader.UploadUniformFloat("u_TilingFactor", 1f);
		data.whiteTexture.bind(0);
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		data.textureShader.UploadUniformMat4("u_Transform", transform);
		
		data.quadVertexArray.bind();
		RendererAPI.drawIndexed(data.quadVertexArray);
	}
	
	public static void drawQuad(Vec2f position, Vec2f size, Texture2D texture, float tiling, Vec4f tintColor) {
		drawQuad(new Vec3f(position.getX(), position.getY(), 0), size, texture, tiling, tintColor);
	}
	
	public static void drawQuad(Vec3f position, Vec2f size, Texture2D texture, float tiling, Vec4f tintColor) {
		data.textureShader.UploadUniformFloat4("u_Color", tintColor);
		data.textureShader.UploadUniformFloat("u_TilingFactor", tiling);
		texture.bind(0);
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		data.textureShader.UploadUniformMat4("u_Transform", transform);
		
		data.quadVertexArray.bind();
		RendererAPI.drawIndexed(data.quadVertexArray);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	
	public static void drawRotatedQuad(Vec2f position, Vec2f size, float rotation, Vec4f color) {
		drawRotatedQuad(new Vec3f( position.getX(), position.getY(), 0), size, rotation, color);
	}
	
	public static void drawRotatedQuad(Vec3f position, Vec2f size, float rotation, Vec4f color) {
		data.textureShader.UploadUniformFloat4("u_Color", color);
		data.textureShader.UploadUniformFloat("u_TilingFactor", 1f);
		data.whiteTexture.bind(0);
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Rotation(new Vec3f(0, 0, rotation)));
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		data.textureShader.UploadUniformMat4("u_Transform", transform);
		
		data.quadVertexArray.bind();
		RendererAPI.drawIndexed(data.quadVertexArray);
	}
	
	public static void drawRotatedQuad(Vec2f position, Vec2f size, float rotation, Texture2D texture, float tiling) {
		drawRotatedQuad(new Vec3f(position.getX(), position.getY(), 0), size, rotation, texture, tiling);
	}
	
	public static void drawRotatedQuad(Vec3f position, Vec2f size, float rotation, Texture2D texture, float tiling) {
		data.textureShader.UploadUniformFloat4("u_Color", new Vec4f(1, 1, 1, 1));
		data.textureShader.UploadUniformFloat("u_TilingFactor", tiling);
		texture.bind(0);
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Rotation(new Vec3f(0, 0, rotation)));
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		data.textureShader.UploadUniformMat4("u_Transform", transform);
		
		data.quadVertexArray.bind();
		RendererAPI.drawIndexed(data.quadVertexArray);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
