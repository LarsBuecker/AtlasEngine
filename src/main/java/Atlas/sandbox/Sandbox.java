package Atlas.sandbox;

import Atlas.atlas.core.Application;
import Atlas.atlas.imgui.ImGuiLayer;

public class Sandbox {

	public static void main(String[] args) {
		Application app = new Application();
		
	
		ExampleLayer exampleLayer = new ExampleLayer();
		
		app.pushLayer(exampleLayer);
		
		app.run();
		
	}
}

