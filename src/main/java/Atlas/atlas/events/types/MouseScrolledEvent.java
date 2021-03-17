package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class MouseScrolledEvent extends Event {
	
	public float xOffset;
	public float yOffset;

	public MouseScrolledEvent(float xOffset, float yOffset) {
		super(Event.EventType.MouseScrolled);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		category = EventCategory.EventCategoryMouse;
	}
}
