package Atlas.atlas.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.opengl.IndexBuffer;
import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;
import Atlas.atlas.opengl.VertexBuffer;
import Atlas.atlas.renderer.BufferElement.ShaderDataType;
import Atlas.atlas.renderer.Renderer2DStorage.Statistics;

public class Renderer2D {

	static Renderer2DStorage data;
	
	public static void init() {
		data = new Renderer2DStorage();
		data.quadVertexArray = new VertexArray();
		
		data.quadVertexBuffer = new VertexBuffer(data.maxQuads * data.quadVertexSize);
		BufferLayout layout = new BufferLayout().addElement(new BufferElement(ShaderDataType.Float3, "a_Position", false));
		layout.addElement(new BufferElement(ShaderDataType.Float4, "a_Color", false));
		layout.addElement(new BufferElement(ShaderDataType.Float2, "a_TexCoord", false));
		layout.addElement(new BufferElement(ShaderDataType.Float, "a_TexIndex", false));
		layout.addElement(new BufferElement(ShaderDataType.Float, "a_TilingFactor", false));
		data.quadVertexBuffer.setBufferLayout(layout);
		data.quadVertexArray.addVertexBuffer(data.quadVertexBuffer);
		
		// Indice generation
		int quadIndices[] = new int[data.maxIndices];
		int offset = 0;
		for ( int i = 0; i < data.maxIndices; i += 6) {
			quadIndices[i + 0] = offset + 0;
			quadIndices[i + 1] = offset + 1;
			quadIndices[i + 2] = offset + 2;
			
			quadIndices[i + 3] = offset + 2;
			quadIndices[i + 4] = offset + 3;
			quadIndices[i + 5] = offset + 0;
			
			offset += 4;
		}
		
		IndexBuffer squareIb = new IndexBuffer(quadIndices, quadIndices.length);
		data.quadVertexArray.setIndexBuffer(squareIb);

		data.whiteTexture = new Texture2D("res/default.png"); // TODO: Texture Generation in Code
		
		int[] samplers = new int[data.maxTextureSlot];
		for ( int i = 0; i < data.maxTextureSlot; i++) {
			samplers[i] = i;
		}
		
		data.textureShader = new Shader("res/shader/texture.glsl");
		data.textureShader.bind();
		data.textureShader.UploadUniformIntArray("u_Textures", samplers);
		
		// Set all TextureSlots to 0
		
		data.textureSlots[0] = data.whiteTexture;
		
		data.quadVertexPositions[0] = new Vec4f( -0.5f, -0.5f, 0.0f, 1.0f );
		data.quadVertexPositions[1] = new Vec4f(  0.5f, -0.5f, 0.0f, 1.0f );
		data.quadVertexPositions[2] = new Vec4f(  0.5f,  0.5f, 0.0f, 1.0f );
		data.quadVertexPositions[3] = new Vec4f( -0.5f,  0.5f, 0.0f, 1.0f );
	}
	
	public static void shutdown() {
		
	}
	
	public static void beginScene(OrthographicCamera camera) {
		data.textureSlotIndex = 1;
		data.vertIndex = 0;
		data.vertCount = 0;
		
		data.textureShader.bind();
		data.textureShader.UploadUniformMat4("u_ViewProjection", camera.getViewProjectionMatrix());
	}
	
	public static void endScene() {
		
		FloatBuffer buffer = storeDataInFloatBuffer(data.vertQueue);
		data.quadVertexBuffer.setData(buffer);
		flush();
	}
	
	public static void flush() {
		
		for( int i = 0; i < data.textureSlotIndex; i++ ) {
			data.textureSlots[i].bind(i);
		}
		
		RendererAPI.drawIndexed(data.quadVertexArray, data.vertQueue.length * 4);
		data.stats.drawCalls++;
	}
	
	private static void flushAndReset() {
		endScene();
		
		data.textureSlotIndex = 1;
		data.vertIndex = 0;
		data.vertCount = 0;
		data.vertQueue = new float[data.maxVerts * data.quadVertexSize];
	}
	
	// Prinmitives 
	
	public static void drawQuad(Vec2f position, Vec2f size, Vec4f color) {
		drawQuad(new Vec3f(position.getX(), position.getY(), 0), size, color);
	}
	
	public static void drawQuad(Vec3f position, Vec2f size, Vec4f color) {
		
		if ( data.vertIndex >= data.maxVerts * data.quadVertexSize) {
			flushAndReset();
		}
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		transform = transform.transpose();
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[0]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[0]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[0]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[1]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[1]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[1]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[2]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[2]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[2]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[3]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[3]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[3]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertCount += 6;
		
		data.stats.QuadCount++;
		
		data.whiteTexture.bind(0);
	}
	
	public static void drawQuad(Vec2f position, Vec2f size, Texture2D texture, float tiling, Vec4f tintColor) {
		drawQuad(new Vec3f(position.getX(), position.getY(), 0), size, texture, tiling, tintColor);
	}
	
	public static void drawQuad(Vec3f position, Vec2f size, Texture2D texture, float tiling, Vec4f tintColor) {
		
		if ( data.vertIndex >= data.maxVerts * data.quadVertexSize) {
			flushAndReset();
		}
		
		float textureIndex = 0;
		for ( int i = 1; i < data.textureSlotIndex; i++ ) {
			if (data.textureSlots[i].equal(texture)) {
				textureIndex = (float) i;
				break;
			}
		}
		
		if(textureIndex == 0) {
			textureIndex = (float) data.textureSlotIndex;
			data.textureSlots[data.textureSlotIndex] = texture;
			data.textureSlotIndex++;
		}
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		transform = transform.transpose();
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[0]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[0]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[0]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[1]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[1]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[1]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[2]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[2]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[2]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[3]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[3]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[3]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertCount += 6;
		
		data.stats.QuadCount++;
	}
	
	public static void drawRotatedQuad(Vec2f position, Vec2f size, float rotation, Vec4f color) {
		drawRotatedQuad(new Vec3f( position.getX(), position.getY(), 0), size, rotation, color);
	}
	
	public static void drawRotatedQuad(Vec3f position, Vec2f size, float rotation, Vec4f color) {
		
		if ( data.vertIndex >= data.maxVerts * data.quadVertexSize) {
			flushAndReset();
		}
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Rotation(new Vec3f(0, 0, rotation)));
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		transform = transform.transpose();
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[0]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[0]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[0]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[1]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[1]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[1]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[2]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[2]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[2]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[3]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[3]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[3]).getZ();
		data.vertQueue[data.vertIndex + 3] = color.getX();
		data.vertQueue[data.vertIndex + 4] = color.getY();
		data.vertQueue[data.vertIndex + 5] = color.getZ();
		data.vertQueue[data.vertIndex + 6] = color.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = 0;
		data.vertQueue[data.vertIndex + 10] = 1;
		data.vertIndex += data.quadVertexSize;
		
		data.vertCount += 6;
		
		data.stats.QuadCount++;
		
		data.whiteTexture.bind(0);
	}
	
	public static void drawRotatedQuad(Vec2f position, Vec2f size, float rotation, Texture2D texture, Vec4f tintColor, float tiling) {
		drawRotatedQuad(new Vec3f(position.getX(), position.getY(), 0), size, rotation, texture, tintColor, tiling);
	}
	
	public static void drawRotatedQuad(Vec3f position, Vec2f size, float rotation, Texture2D texture, Vec4f tintColor, float tiling) {
		
		if ( data.vertIndex >= data.maxVerts * data.quadVertexSize) {
			flushAndReset();
		}
		
		float textureIndex = 0;
		for ( int i = 1; i < data.textureSlotIndex; i++ ) {
			if (data.textureSlots[i].equal(texture)) {
				textureIndex = (float) i;
				break;
			}
		}
		
		if(textureIndex == 0) {
			textureIndex = (float) data.textureSlotIndex;
			data.textureSlots[data.textureSlotIndex] = texture;
			data.textureSlotIndex++;
		}
		
		Mat4f transform = new Mat4f().Translation(position);
		transform = transform.mul(new Mat4f().Rotation(new Vec3f(0, 0, rotation)));
		transform = transform.mul(new Mat4f().Scaling(new Vec3f(size.getX(), size.getY(), 0)));
		transform = transform.transpose();
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[0]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[0]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[0]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[1]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[1]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[1]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 0;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[2]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[2]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[2]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 1;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertQueue[data.vertIndex + 0] = transform.mul(data.quadVertexPositions[3]).getX();
		data.vertQueue[data.vertIndex + 1] = transform.mul(data.quadVertexPositions[3]).getY();
		data.vertQueue[data.vertIndex + 2] = transform.mul(data.quadVertexPositions[3]).getZ();
		data.vertQueue[data.vertIndex + 3] = tintColor.getX();
		data.vertQueue[data.vertIndex + 4] = tintColor.getY();
		data.vertQueue[data.vertIndex + 5] = tintColor.getZ();
		data.vertQueue[data.vertIndex + 6] = tintColor.getW();
		data.vertQueue[data.vertIndex + 7] = 0;
		data.vertQueue[data.vertIndex + 8] = 1;
		data.vertQueue[data.vertIndex + 9] = textureIndex;
		data.vertQueue[data.vertIndex + 10] = tiling;
		data.vertIndex += data.quadVertexSize;
		
		data.vertCount += 6;
		
		data.stats.QuadCount++;
	}
	
	private static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static void resetStats() {
		data.stats.drawCalls = 0;
		data.stats.QuadCount = 0;
	}
	
	public static Statistics getStats() {
		return data.stats;
	}
}
