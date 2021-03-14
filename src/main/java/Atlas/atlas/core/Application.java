package Atlas.atlas.core;

import Atlas.atlas.events.Event;
import Atlas.atlas.events.EventDispatcher;
import Atlas.atlas.events.EventListener;
import Atlas.atlas.events.types.WindowCloseEvent;
import Atlas.atlas.imgui.ImGuiLayer;
import Atlas.atlas.renderer.Renderer;

public class Application implements EventListener {

	private static Application instance = null;
	
	public static Application getInstance() {
		return instance;
	}
	
	private Window window;
	private LayerStack layerStack;
	
	private boolean isRunning = true;
	private long lastFrame;
	
	private ImGuiLayer imGuiLayer;
	
	public Application() {
		if (instance != null) {
			System.err.println("Application already exists!");
			System.exit(1);
		}
		
		instance = this;
		
		window = new Window("Atlas", 1280, 720);
		window.create();
		
		Renderer.init();
		
		layerStack = new LayerStack();
	}
	
	public void init() {
		imGuiLayer = new ImGuiLayer();
		pushLayer(imGuiLayer);
	}
	
	public void pushLayer(Layer layer) {
		layerStack.PushLayer(layer);
		layer.OnAttach();
	}
	
	public void pushOverlay(Layer overlay) {
		layerStack.PushOverlay(overlay);
		overlay.OnAttach();
	}
	
	public void onEvent(Event e) {
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(Event.EventType.WindowClose, (Event event) -> (onWindowClose((WindowCloseEvent) event)));
		
		for ( Layer layer : layerStack.getLayers() ) {
			layer.onEvent(e);
		}
	}
	
	public void close() {
		isRunning = false;
	}
	
	public void run() {
		getDelta();
		
		while(isRunning) {
			
			update();
			render();
			
			window.update();
			
			imGuiLayer.begin();
			for (Layer layer : layerStack.getLayers()) {
				layer.onGuiRender();
			}
			imGuiLayer.end();
			
			
			window.swapBuffers();
			
		}
		window.dispose();
	}
	
	private void update() {
		
		for (Layer layer : layerStack.getLayers()) {
			layer.onUpdate(getDelta());
		}
	}
	
	public void render() {
		
		for (Layer layer : layerStack.getLayers()) {
			layer.onRender();
		}
		
		
	}
	
	private boolean onWindowClose(WindowCloseEvent e) {
		isRunning = false;
		return true;
	}
	
	public long getTime() {
	    return System.nanoTime() / 1000000;
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
	
	public Window getWindow() {
		return window;
	}
}
