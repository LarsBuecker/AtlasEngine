package Atlas.sandbox;

import java.util.ArrayList;
import java.util.List;

import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.opengl.IndexBuffer;
import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;
import Atlas.atlas.opengl.VertexBuffer;
import Atlas.atlas.renderer.BufferElement;
import Atlas.atlas.renderer.BufferElement.ShaderDataType;
import Atlas.atlas.renderer.BufferLayout;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer;
import Atlas.atlas.renderer.RendererAPI;
import imgui.ImGui;

public class Sandbox2D extends Layer {

	public Sandbox2D() {
		super("Sandbox2D");
	}
	
	private VertexArray vertexArray;
	private Shader shader;
	private OrthographicCameraController cameraController;
	
	private float[] squareCol = { 0.1f, 0.1f, 0.9f, 1 };

	@Override
	public void OnAttach() {
		vertexArray = new VertexArray();
		float square[] = {
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f,
			 0.5f,  0.5f, 0.0f,
			-0.5f,  0.5f, 0.0f
		};
		
		VertexBuffer vertexBuffer = new VertexBuffer(square, 3);
		
		BufferElement positon = new BufferElement(ShaderDataType.Float3, "a_Position", false);
		List<BufferElement> elementList = new ArrayList<BufferElement>();
		elementList.add(positon);
		BufferLayout layout = new BufferLayout(elementList);
		
		vertexBuffer.setBufferLayout(layout);
		vertexArray.addVertexBuffer(vertexBuffer);
		
		int indices[] = { 0, 1, 2, 2, 3, 0 };
		IndexBuffer indexBuffer = new IndexBuffer(indices, indices.length);
		vertexArray.setIndexBuffer(indexBuffer);
		
		shader = new Shader(Shader.loadShader("flatColor.vs"), Shader.loadShader("flatColor.fs"));
		cameraController = new OrthographicCameraController(16f / 9f);
	}

	@Override
	public void onDetach() {

	}

	@Override
	public void onUpdate(float delta) {
		cameraController.onUpdate(delta);
	}

	@Override
	public void onRender() {
		RendererAPI.setClearColor(new Vec4f( 0.1f, 0.1f, 0.1f, 1));
		RendererAPI.clear();
		
		Renderer.BeginScene(cameraController.getCamera());
		shader.bind();
		shader.UploadUniformFloat4("u_Color", new Vec4f(squareCol[0], squareCol[1], squareCol[2], squareCol[3]));
		Renderer.submit(shader, vertexArray, new Mat4f().Identity());
		Renderer.EndScene();
	}

	@Override
	public void onGuiRender() {
		ImGui.begin("Settings");
		ImGui.colorEdit3("Square Color", squareCol);
		ImGui.end();
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
	}

}
