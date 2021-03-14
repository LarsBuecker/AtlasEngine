package Atlas.sandbox;

import java.util.ArrayList;
import java.util.List;

import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.opengl.IndexBuffer;
import Atlas.atlas.opengl.Shader;
import Atlas.atlas.opengl.VertexArray;
import Atlas.atlas.opengl.VertexBuffer;
import Atlas.atlas.renderer.BufferElement;
import Atlas.atlas.renderer.BufferElement.ShaderDataType;
import Atlas.atlas.renderer.BufferLayout;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.RendererAPI;
import imgui.ImGui;

public class Sandbox2D extends Layer {

	public Sandbox2D() {
		super("Sandbox2D");
	}

	private OrthographicCameraController cameraController;
	
	private float[] squareCol = { 0.1f, 0.1f, 0.9f, 1 };

	@Override
	public void OnAttach() {
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
		
		Renderer2D.beginScene(cameraController.getCamera());
		Renderer2D.drawQuad(new Vec2f(1, 0), new Vec2f(2, 0.5f), new Vec4f(squareCol[0], squareCol[1], squareCol[2], squareCol[3]));
		Renderer2D.endScene();
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
