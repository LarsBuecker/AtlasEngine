package Atlas.atlas.renderer;

import Atlas.atlas.math.Mat4f;
import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;

public class Renderer {

	private static SceneData sceneData = new SceneData();
	
	public static void init() {
		RendererAPI.init();
		Renderer2D.init();
	}
	
	public static void BeginScene(Camera camera) {
		sceneData.ViewProjectionMatrix = camera.getViewProjectionMatrix();
	}
	
	public static void EndScene() {
		
	}
	
	public static void submit(Shader shader, VertexArray vertexArray, Mat4f transform) {
		shader.bind();
		shader.UploadUniformMat4("u_ViewProjection", sceneData.ViewProjectionMatrix);
		shader.UploadUniformMat4("u_Transform", transform);
		
		vertexArray.bind();
		RendererAPI.drawIndexed(vertexArray);
	}
}
