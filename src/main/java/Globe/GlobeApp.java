package Globe;

import Atlas.atlas.core.Application;

public class GlobeApp {

	public static void main(String[] args) {
		Application app = new Application();
		
		EditorLayer editor = new EditorLayer();
		app.pushLayer(editor);
		
		app.init();
		app.run();
	}
}
