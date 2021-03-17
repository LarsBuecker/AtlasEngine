package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {

	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.EventType.MouseButtonReleased);
		category = EventCategory.EventCategoryMouse;
	}

}
