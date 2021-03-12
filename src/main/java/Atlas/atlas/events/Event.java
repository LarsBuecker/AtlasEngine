package Atlas.atlas.events;

public class Event {

	public enum EventType {
		None, 
		WindowClose, WindowResize, WindowFocus, WindowLostFocus, WindowMoved,
		AppTick, AppUpdate, AppRender,
		KeyPressed, KeyReleased, KeyTyped,
		MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled
	}
	
	private EventType type;
	boolean handled;
	
	protected Event(EventType type) {
		this.type = type;
	}
	
	public EventType getType() {
		return type;
	}
}
