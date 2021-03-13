package Atlas.sandbox;

import java.util.ArrayList;
import java.util.List;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.core.Log;
import Atlas.atlas.core.Window;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec3f;
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

public class ExampleLayer extends Layer {
	
	private VertexArray vertexArray;
	private Shader shader;
	private OrthographicCameraController cameraController;
	
	private Mat4f transform;
	private Vec3f rot = new Vec3f();
	
	private int fps;

	public ExampleLayer() {
		super("Example Layer");
		
		vertexArray = new VertexArray();
		
//		float vertices[] = {
//				-0.5f, -0.5f, 0.0f, 0.8f, 0.2f, 0.8f, 1.0f,
//				 0.5f, -0.5f, 0.0f, 0.2f, 0.3f, 0.8f, 1.0f,
//				 0.0f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f
//		};
		
		float square[] = {
			-0.5f, -0.5f, 0.0f, 0.8f, 0.2f, 0.8f, 1.0f,
		 	 0.5f, -0.5f, 0.0f, 0.2f, 0.3f, 0.8f, 1.0f,
		 	 0.5f,  0.5f, 0.0f, 0.8f, 0.8f, 0.2f, 1.0f,
		 	-0.5f,  0.5f, 0.0f, 0.3f, 0.8f, 0.5f, 1.0f
		};
		
		VertexBuffer vertexBuffer = new VertexBuffer(square, 3);
		
		BufferElement position = new BufferElement(ShaderDataType.Float3, "a_Position", false);
		BufferElement color = new BufferElement(ShaderDataType.Float4, "a_Color", false);
		List<BufferElement> elementList = new ArrayList<BufferElement>();
		elementList.add(position);
		elementList.add(color);
		BufferLayout layout = new BufferLayout(elementList);
		
		vertexBuffer.setBufferLayout(layout);
		vertexArray.addVertexBuffer(vertexBuffer);
		
//		int indices[] = { 0, 1, 2 };
		int indices1[] = { 0, 1, 2, 2, 3, 0 };
		IndexBuffer indexBuffer = new IndexBuffer(indices1, indices1.length);
		vertexArray.setIndexBuffer(indexBuffer);
	
	
		shader = new Shader(Shader.loadShader("vertex.vs"), Shader.loadShader("fragment.fs"));
		
		Window window = Application.getInstance().getWindow();
		
		cameraController = new OrthographicCameraController((float) window.getWidth() / (float) window.getHeight());
		
		transform = new Mat4f().Identity();
	}

	@Override
	public void OnAttach() {
		Log.clientLog("Example Layer Attached");
	}

	@Override
	public void onDetach() {
		Log.clientLog(this.getName() + " Detached");
	}

	@Override
	public void onUpdate(float delta) {
		rot.setX(rot.getX() + delta * 0.01f);
		rot.setY(rot.getY() + delta * 0.04f);
		rot.setZ(rot.getZ() + delta * 0.06f);
		transform.Rotation(rot);
		
		cameraController.onUpdate(delta);
		
		if(fps>10) {
			Window window = Application.getInstance().getWindow();
			window.setTitle("Atlas | Sandbox | OpenGL | FPS: " + (int) (1 / (delta / 1000)));
			fps = 0;
		}
		fps++;
	}

	@Override
	public void onRender() {
		RendererAPI.setClearColor(new Vec4f( 0.1f, 0.1f, 0.1f, 1));
		RendererAPI.clear();
		
		Renderer.BeginScene(cameraController.getCamera());
		shader.bind();
		shader.UploadUniformFloat3("u_Color", new Vec3f(0.5f, 0.2f, 0.7f));
		Renderer.submit(shader, vertexArray, transform);
		Renderer.EndScene();
	}

	@Override
	public void onGuiRender() {
		
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
	}
	
}

