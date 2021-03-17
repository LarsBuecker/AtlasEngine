package Atlas.atlas.events;

public class Event {

	public enum EventType {
		None, 
		WindowClose, WindowResize, WindowFocus, WindowLostFocus, WindowMoved,
		AppTick, AppUpdate, AppRender,
		KeyPressed, KeyReleased, KeyTyped,
		MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled
	}
	
	public enum EventCategory {
		EventCategoryMouse,
		EventCategoryKeyboard,
	}
	
	private EventType type;
	protected EventCategory category;
	public boolean handled;
	
	protected Event(EventType type) {
		this.type = type;
	}
	
	public EventType getType() {
		return type;
	}
	
	public EventCategory getCategory() {
		return category;
	}
}
