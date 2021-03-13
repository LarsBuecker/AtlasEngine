package Atlas.atlas.imgui;

import org.lwjgl.glfw.GLFW;

import Atlas.atlas.core.Application;
import Atlas.atlas.core.Layer;
import Atlas.atlas.core.Log;
import Atlas.atlas.core.Window;
import Atlas.atlas.events.Event;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.ImVec2;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class ImGuiLayer extends Layer {
	
	 private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
	 private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

	public ImGuiLayer() {
		super("ImGuiLayer");
	}

	@Override
	public void OnAttach() {
		Log.coreLog(getName() + " Attached!");
		
		ImGui.createContext();
		ImGuiIO io = ImGui.getIO();
		io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
		io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
		io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
		
		ImGui.styleColorsDark();
		
		ImGuiStyle style = ImGui.getStyle();
		if(io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			style.setWindowRounding(0);
			
		}
		
//		setDarkThemeColors();
		
		long window = Application.getInstance().getWindow().getWindow();
		imGuiGlfw.init(window, true);
		imGuiGl3.init("#version 410");
	}

	@Override
	public void onDetach() {
		imGuiGl3.dispose();
		imGuiGlfw.dispose();
		ImGui.destroyContext();
	}
	
	public void begin() {
		imGuiGlfw.newFrame();
		ImGui.newFrame();
	}
	
	public void end() {
		ImGuiIO io = ImGui.getIO();
		Window window = Application.getInstance().getWindow();
		io.getDisplaySize(new ImVec2((float) window.getWidth(), (float) window.getHeight()));
		
		// Rendering 
		ImGui.render();
		imGuiGl3.renderDrawData(ImGui.getDrawData());
		
		if( io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			long window_backup = window.getWindow();
			ImGui.updatePlatformWindows();
			ImGui.renderPlatformWindowsDefault();
			GLFW.glfwMakeContextCurrent(window_backup);
		}
	}

	@Override
	public void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuiRender() {
		ImGui.showDemoWindow();
		
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
