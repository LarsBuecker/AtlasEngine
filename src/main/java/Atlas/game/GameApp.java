package Atlas.game;

import Atlas.atlas.core.Application;

public class GameApp {

	public static void main(String[] args) {
		Application app = new Application();
		
		Game game = new Game();
		app.pushLayer(game);
		app.init();
		app.run();
	}

}
