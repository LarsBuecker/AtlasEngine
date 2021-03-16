package Atlas.game;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.RendererAPI;
import Atlas.atlas.renderer.SubTexture2D;
import Atlas.atlas.renderer.Texture2D;
import Atlas.atlas.renderer.Renderer2DStorage.Statistics;
import imgui.ImGui;

public class Game extends Layer {
	
	private OrthographicCameraController cameraController;
	private Texture2D spriteSheet;
	private SubTexture2D textureStairs;

	public Game() {
		super("TestGame");
	}

	@Override
	public void OnAttach() {
		cameraController = new OrthographicCameraController(16f / 9f);
		spriteSheet = new Texture2D("res/game/RPGpack_sheet_2X.png");
		textureStairs = SubTexture2D.createFromCoords(spriteSheet, new Vec2f(7, 6), new Vec2f(128, 128));
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
		Renderer2D.drawQuad(new Vec2f(0, 0), new Vec2f(1, 1), textureStairs, 1, new Vec4f(1, 1, 1, 1));
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
	
		ImGui.end();
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
	}

}
