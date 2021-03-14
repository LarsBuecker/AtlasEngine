package Atlas.sandbox;

import Atlas.atlas.core.Application;

public class SandboxApp {

	public static void main(String[] args) {
		Application app = new Application();
		
//		ExampleLayer exampleLayer = new ExampleLayer();
//		app.pushLayer(exampleLayer);
		
		Sandbox2D sandboxLayer= new Sandbox2D();
		app.pushLayer(sandboxLayer);
		
		app.init();	
		app.run();
		
	}
}

