package Atlas.atlas.core;

import Atlas.atlas.events.Event;
import Atlas.atlas.events.EventListener;

public abstract class Layer implements EventListener {
	
	private String name;

	public Layer(String name) {
		this.name = name;
	}
	
	public abstract void OnAttach();
	public abstract void onDetach();
	public abstract void onUpdate(float delta);
	public abstract void onRender();
	public abstract void onGuiRender();
	public abstract void onEvent(Event event);
	
	public String getName() {
		return this.name;
	}
}
