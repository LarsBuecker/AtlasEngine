package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class KeyPressedEvent extends KeyEvent {

	public KeyPressedEvent(int keycode) {
		super(keycode, Event.EventType.KeyPressed);
		category = EventCategory.EventCategoryKeyboard;
	}

}
