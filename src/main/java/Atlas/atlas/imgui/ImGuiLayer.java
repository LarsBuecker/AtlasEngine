package Atlas.atlas.imgui;

import Atlas.atlas.core.Layer;
import Atlas.atlas.core.Log;
import Atlas.atlas.events.Event;

public class ImGuiLayer extends Layer {
	
//	 private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
//	 private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

	public ImGuiLayer() {
		super("ImGuiLayer");
	}

	@Override
	public void OnAttach() {
		Log.coreLog(getName() + " Attached!");
//		
//		ImGui.createContext();
//		ImGuiIO io = ImGui.getIO();
//		io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
//		io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
//		io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
//		
//		ImGui.styleColorsDark();
//		
//		ImGuiStyle style = ImGui.getStyle();
//		if(io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
//			style.setWindowRounding(0);
//			
//		}
//		
////		setDarkThemeColors();
//		
//		long window = Application.getInstance().getWindow().getWindow();
//		imGuiGlfw.init(window, true);
//		imGuiGl3.init("#version 410");
	}

	@Override
	public void onDetach() {
//		imGuiGl3.dispose();
//		imGuiGlfw.dispose();
//		ImGui.destroyContext();
	}
	
	public void begin() {
		
	}
	
	public void end() {
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
