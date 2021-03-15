package Atlas.atlas.renderer;

import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;
import Atlas.atlas.opengl.VertexBuffer;

public class Renderer2DStorage {
	
	public int maxQuads = 10000;
	public int maxVerts = maxQuads * 4;
	public int maxIndices = maxQuads * 6;
	public int quadVertexSize = 11;

	public VertexArray quadVertexArray;
	public VertexBuffer quadVertexBuffer;
	public Shader textureShader;
	public Texture2D whiteTexture;
	public int maxTextureSlot = 32; // TODO: RenderCaps
	
	public int vertIndex = 0;
	public int vertCount = 0;
	public float[] vertQueue = new float[maxQuads * quadVertexSize];
	
	public Texture2D[] textureSlots = new Texture2D[maxTextureSlot];
	public int textureSlotIndex = 1; // 0 = whiteTexture
}
