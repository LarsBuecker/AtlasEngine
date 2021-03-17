package Atlas.sandbox;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.Renderer2DStorage.Statistics;
import Atlas.atlas.renderer.RendererAPI;
import Atlas.atlas.renderer.Texture2D;
import imgui.ImGui;

public class Sandbox2D extends Layer {

	public Sandbox2D() {
		super("Sandbox2D");
	}

	private OrthographicCameraController cameraController;
	private float[] squareCol = { 1f, 1f, 1f, 1f };
	private Texture2D texture;


	@Override
	public void OnAttach() {
		cameraController = new OrthographicCameraController(16f / 9f);
		texture = new Texture2D("res/checkerboard.png");
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
		
		Renderer2D.resetStats();
		RendererAPI.setClearColor(new Vec4f( 0.1f, 0.1f, 0.1f, 1));
		RendererAPI.clear();
		
		Renderer2D.beginScene(cameraController.getCamera());
		
		
		Renderer2D.drawQuad(new Vec2f(-1.5f, 0), new Vec2f(2, 1f), new Vec4f(0.8f, 0.2f, 0.1f, 1.0f));
		Renderer2D.drawQuad(new Vec3f(0, 0, 0.1f), new Vec2f(20f, 20f), texture, 10, new Vec4f(squareCol[0], squareCol[1], squareCol[2], squareCol[3]));
		Renderer2D.drawRotatedQuad(new Vec2f(0f, 1f), new Vec2f(1, 2), 0, new Vec4f(0.1f, 0.7f, 0.3f, 1.0f));
		Renderer2D.endScene();
		
		Renderer2D.beginScene(cameraController.getCamera());
		for (float y = -5f; y < 5.0f; y += 0.5f ) {
			for ( float x = -5f; x < 5f; x += 0.5f ) {
				Vec4f color = new Vec4f((x + 5f) / 10f, 0.4f, (y+5f)/10f, 0.75f);
				Renderer2D.drawQuad(new Vec2f(x, y), new Vec2f(0.45f, 0.45f), color);
			}
		}
		Renderer2D.endScene();	
	}

	@Override
	public void onGuiRender() {
		
		Statistics stats = Renderer2D.getStats();
		
		ImGui.begin("Statistics");		
		ImGui.text("Renderer2D Stats:");
		ImGui.text("FPS: " + Application.getInstance().getFPS());
		ImGui.text("Draw Calls: " + stats.drawCalls);
		ImGui.text("Quad Count: " + stats.QuadCount);
		ImGui.text("Vertices: " + stats.getTotalVertexCount());
		ImGui.text("Indices: " + stats.getTotalIndexCount());
		
		ImGui.colorEdit3("Square Color", squareCol);
		ImGui.end();
		
		
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
	}

}
