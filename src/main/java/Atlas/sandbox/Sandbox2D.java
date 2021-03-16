package Atlas.sandbox;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.RendererAPI;
import Atlas.atlas.renderer.Texture2D;
import imgui.ImGui;

public class Sandbox2D extends Layer {

	public Sandbox2D() {
		super("Sandbox2D");
	}

	private OrthographicCameraController cameraController;
	
	private float[] squareCol = { 0.1f, 0.1f, 0.9f, 1 };
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
		Application.getInstance().getWindow().setTitle(getName() + " | FPS: " + Application.getInstance().getFPS());
	}

	@Override
	public void onRender() {
		RendererAPI.setClearColor(new Vec4f( 0.1f, 0.1f, 0.1f, 1));
		RendererAPI.clear();
		
		Renderer2D.beginScene(cameraController.getCamera());
//		Renderer2D.drawQuad(new Vec2f(-1.5f, 0), new Vec2f(2, 1f), new Vec4f(0.8f, 0.2f, 0.1f, 1.0f));
		
		for( int i = 0; i < 50; i++ ) {
			for ( int j = 0; j < 50	; j++ ) {
				Renderer2D.drawQuad(new Vec2f(1f * i, 1f * j), new Vec2f(0.5f, 0.5f), new Vec4f(squareCol[0], squareCol[1], squareCol[2], squareCol[3]));
			}
		}
		
		Renderer2D.drawQuad(new Vec3f(0, 0, 0.1f), new Vec2f(10f, 10f), texture, 10, new Vec4f(1, 1, 1, 1));
		Renderer2D.drawRotatedQuad(new Vec2f(0f, 1f), new Vec2f(1, 2), 0, new Vec4f(0.1f, 0.7f, 0.3f, 1.0f));
		
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
