package Globe;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Input;
import Atlas.atlas.core.KeyCodes;
import Atlas.atlas.core.Layer;
import Atlas.atlas.events.Event;
import Atlas.atlas.events.Event.EventType;
import Atlas.atlas.events.EventDispatcher;
import Atlas.atlas.events.types.KeyPressedEvent;
import Atlas.atlas.math.Vec2f;
import Atlas.atlas.math.Vec3f;
import Atlas.atlas.math.Vec4f;
import Atlas.atlas.renderer.Framebuffer;
import Atlas.atlas.renderer.FramebufferSpecification;
import Atlas.atlas.renderer.OrthographicCameraController;
import Atlas.atlas.renderer.Renderer2D;
import Atlas.atlas.renderer.Renderer2DStorage.Statistics;
import Atlas.atlas.renderer.RendererAPI;
import Atlas.atlas.renderer.Texture2D;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class EditorLayer extends Layer {
	
	private static boolean dockspsaceOpen = true;
	private static boolean opt_fullscreen = true;
	
	// Scene stuff
	private OrthographicCameraController cameraController;
	private float[] squareCol = { 1f, 1f, 1f, 1f };
	private Texture2D texture;
	
	private Framebuffer framebuffer;
	private Vec2f viewportSize = new Vec2f();
	private boolean viewportFocused = false, viewportHovered = false;
	
	public EditorLayer() {
		super("Globe Editor");
	}

	@Override
	public void OnAttach() {
		cameraController = new OrthographicCameraController(16f / 9f);
		texture = new Texture2D("res/checkerboard.png");
		
		FramebufferSpecification fbSpec = new FramebufferSpecification();
		fbSpec.width = 1280;
		fbSpec.height = 720;
		framebuffer = new Framebuffer(fbSpec);
	}

	@Override
	public void onDetach() {

	}

	@Override
	public void onUpdate(float delta) {
		if ( viewportFocused ) cameraController.onUpdate(delta);
	}

	@Override
	public void onRender() {
		framebuffer.bind();
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
		framebuffer.unbind();
	}

	@Override
	public void onGuiRender() {
		int flags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
		
		if ( opt_fullscreen ) {
			ImGuiViewport viewport = ImGui.getMainViewport();
			ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY());
			ImGui.setNextWindowSize(viewport.getSizeX(), viewport.getSizeY());
			ImGui.setNextWindowViewport(viewport.getID());
			ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
			ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
			flags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
		}
		
		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
		ImGui.begin("Dockspace Demo", flags);
		ImGui.popStyleVar();
		if ( opt_fullscreen ) {
			ImGui.popStyleVar(2);
		}
		
		ImGuiIO io = ImGui.getIO();
		ImGuiStyle style = ImGui.getStyle();
		float minWinSizeX = style.getWindowMinSizeX();
		style.setWindowMinSize(370, 1);
		
		if(io.hasConfigFlags(ImGuiConfigFlags.DockingEnable)) {
			int id = ImGui.getID("MyDockspace");
			ImGui.dockSpace(id);
		}
		style.setWindowMinSize(minWinSizeX, style.getWindowMinSizeY());
		
		
		if(ImGui.beginMenuBar()) {
			if(ImGui.beginMenu("File")) {
				if(ImGui.menuItem("New", "Ctrl+N")) {
					
				}
				if(ImGui.menuItem("Open", "Ctrl+O")) {
					
				}
				if(ImGui.menuItem("Save as...", "Ctrl+Shift+S")) {
	
				}
				if(ImGui.menuItem("Exit", "Ctrl+Q")) {
					Application.getInstance().close();
				}
				ImGui.endMenu();
			}
			ImGui.endMenuBar();
		}
		
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
		
		ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
		ImGui.begin("Viewport");
		viewportFocused = ImGui.isWindowFocused();
		viewportHovered = ImGui.isWindowHovered();
		Application.getInstance().getImGuiLayer().setBlockEvent(!viewportFocused || !viewportHovered);
		ImVec2 viewportPanelsize = ImGui.getContentRegionAvail();
		if( viewportPanelsize.x != viewportSize.getX() || viewportPanelsize.y != viewportSize.getY()) {
			framebuffer.resize((int) viewportPanelsize.x, (int) viewportPanelsize.y);
			viewportSize = new Vec2f(viewportPanelsize.x, viewportPanelsize.y);
			
			cameraController.onResize(viewportSize.getX(), viewportSize.getY());
		}
		
		ImGui.image(framebuffer.getColorAttachmentRendererId(), viewportPanelsize.x, viewportPanelsize.y, 0, 1, 1, 0);
		ImGui.end();
		ImGui.popStyleVar();
		
		ImGui.end();
	}

	@Override
	public void onEvent(Event event) {
		cameraController.onEvent(event);
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(EventType.KeyPressed, (Event e) -> (onKeyPressed((KeyPressedEvent) e)));
	}
	
	public boolean onKeyPressed(KeyPressedEvent event) {
		
		boolean control = Input.isKeyPressed(KeyCodes.AL_KEY_LEFT_CONTROL) | Input.isKeyPressed(KeyCodes.AL_KEY_RIGHT_CONTROL);
		boolean shift = Input.isKeyPressed(KeyCodes.AL_KEY_LEFT_SHIFT) | Input.isKeyPressed(KeyCodes.AL_KEY_RIGHT_SHIFT);
		
		// TODO Implement KeyEvents
		if ( event.keycode == KeyCodes.AL_KEY_N && control )
			return true;
		if ( event.keycode == KeyCodes.AL_KEY_O && control ) 
			return true;
		if ( event.keycode == KeyCodes.AL_KEY_S && shift && control ) 
			return true;
		if ( event.keycode == KeyCodes.AL_KEY_Q && control ) {
			Application.getInstance().close();
			return true;
		}
			
		
		return false;
	}

	public static boolean isDockspsaceOpen() {
		return dockspsaceOpen;
	}

	public static void setDockspsaceOpen(boolean dockspsaceOpen) {
		EditorLayer.dockspsaceOpen = dockspsaceOpen;
	}

	public static boolean isOpt_fullscreen() {
		return opt_fullscreen;
	}

	public static void setOpt_fullscreen(boolean opt_fullscreen) {
		EditorLayer.opt_fullscreen = opt_fullscreen;
	}
}
