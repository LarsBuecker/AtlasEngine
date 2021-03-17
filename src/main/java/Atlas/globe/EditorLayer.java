package Atlas.globe;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Input;
import Atlas.atlas.core.KeyCodes;
import Atlas.atlas.core.Layer;
import Atlas.atlas.core.Window;
import Atlas.atlas.events.Event;
import Atlas.atlas.events.Event.EventType;
import Atlas.atlas.events.EventDispatcher;
import Atlas.atlas.events.types.KeyPressedEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class EditorLayer extends Layer {
	
	private static boolean dockspsaceOpen = true;
	private static boolean opt_fullscreen = true;
	
	
	public EditorLayer() {
		super("Globe Editor");
	}

	@Override
	public void OnAttach() {
		Window window = Application.getInstance().getWindow();
		window.setTitle("Atlas | " + getName());
	}

	@Override
	public void onDetach() {

	}

	@Override
	public void onUpdate(float delta) {
	}

	@Override
	public void onRender() {
		
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
		
		ImGui.end();
	}

	@Override
	public void onEvent(Event event) {
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
