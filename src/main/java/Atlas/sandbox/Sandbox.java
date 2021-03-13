package Atlas.sandbox;

import Atlas.atlas.core.Application;

public class Sandbox {

	public static void main(String[] args) {
		Application app = new Application();
		
		ExampleLayer exampleLayer = new ExampleLayer();
		app.pushLayer(exampleLayer);
		
//		TestLayer testLayer = new TestLayer();
//		app.pushLayer(testLayer);
		
		app.init();
		
		
		
		app.run();
		
	}
}

