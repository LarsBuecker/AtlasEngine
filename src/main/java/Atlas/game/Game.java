package Atlas.game;

import java.util.HashMap;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.Renderer2DStorage.Statistics;
import Atlas.atlas.renderer.RendererAPI;
import Atlas.atlas.renderer.SubTexture2D;
import Atlas.atlas.renderer.Texture2D;
import imgui.ImGui;

public class Game extends Layer {
	
	static int mapWidth = 24;
	static String MapTiles = "WWWWWWWWWWWWWWWWWWWWWWWW"
						   + "WWWWWWWWWDDDDDDWWWWWWWWW"
						   + "WWWWWWWWDDDDDDDDDDWWWWWW"
						   + "WWWWWWWDDDDDDDDDDDDDWWWW"
						   + "WWWWWWWDDDDDDDDDDDDDDWWW"
						   + "WWWWDDDDDDDDDDDDDDDDDWWW"
						   + "WWWDDDDDDDDDDDDDDDDDDWWW"
						   + "WWDDDDDDWWWDDDDDDDDDWWWW"
						   + "WWDDDDDDWWWDDDDDDDDDWWWW"
						   + "WWWDDDDDDDDDDDDDDDDDDWWW"
						   + "WWWWDDDDDDDDDDDDDDDDDWWW"
						   + "WWWWDDDDDDDDDDDDDDDDDDWW"
						   + "WWWWWDDDDDDDDDDDDDDDDWWW"
						   + "WWWWWWWWDDDDDDDDDDDWWWWW"
	 					   + "WWWWWWWWWWDDDDDDWWWWWWWW"
	 					   + "WWWWWWWWWWWWWWWWWWWWWWWW";
	
	private OrthographicCameraController cameraController;
	private Texture2D spriteSheet;
	private SubTexture2D textureStairs, textureTree;
	private SubTexture2D textureDirt, textureWater;
	private HashMap<String, SubTexture2D> textureMap;

	public Game() {
		super("TestGame");
	}

	@Override
	public void OnAttach() {
		cameraController = new OrthographicCameraController(16f / 9f);
		cameraController.setZoomLevel(8f);
		spriteSheet = new Texture2D("res/game/RPGpack_sheet_2X.png");
		textureStairs = SubTexture2D.createFromCoords(spriteSheet, new Vec2f(0, 9), new Vec2f(128, 128));
		textureTree = SubTexture2D.createFromCoords(spriteSheet, new Vec2f(0,10), new Vec2f(128, 128), new Vec2f(1, 2));
		
		textureDirt = SubTexture2D.createFromCoords(spriteSheet, new Vec2f(6, 1), new Vec2f(128, 128));
		textureWater = SubTexture2D.createFromCoords(spriteSheet, new Vec2f(11, 1), new Vec2f(128, 128));
		textureMap = new HashMap<String, SubTexture2D>();
		textureMap.put("D", textureDirt);
		textureMap.put("W", textureWater);
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
//		Renderer2D.drawQuad(new Vec2f(0, 0), new Vec2f(1, 1), textureStairs, 1, new Vec4f(1, 1, 1, 1));
//		Renderer2D.drawQuad(new Vec2f(1, 0), new Vec2f(1, 2), textureTree, 1, new Vec4f(1, 1, 1, 1));
//		
//		Renderer2D.drawQuad(new Vec2f(3, 0), new Vec2f(1, 1), textureDirt, 1, new Vec4f(1, 1, 1, 1));
//		Renderer2D.drawQuad(new Vec2f(5, 0), new Vec2f(1, 1), textureWater, 1, new Vec4f(1, 1, 1, 1));
		for (int y = 0; y < MapTiles.length() / mapWidth; y++) {
			for (int x = 0; x < mapWidth; x++) {
				char tiletype = MapTiles.charAt(x + y * mapWidth);
				SubTexture2D texture;
				if (textureMap.containsKey(tiletype +"")) {
					texture = textureMap.get(tiletype + "");
				} else {
					texture = this.textureStairs;
				}
				Renderer2D.drawQuad(new Vec2f(x - mapWidth / 2, y - MapTiles.length() / mapWidth / 2), new Vec2f(1, 1), texture, 1, new Vec4f(1, 1, 1, 1));
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
	
		ImGui.end();
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
	}

}
