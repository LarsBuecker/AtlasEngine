package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class MousePressedEvent extends MouseButtonEvent {

	public MousePressedEvent(int button, int x, int y) {
		super(button, x, y, Event.EventType.MouseButtonPressed);
	}

}
