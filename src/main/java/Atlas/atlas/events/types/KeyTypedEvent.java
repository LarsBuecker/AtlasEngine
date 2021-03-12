package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class KeyTypedEvent extends KeyEvent {

	public KeyTypedEvent(int keycode) {
		super(keycode, Event.EventType.KeyTyped);
	}

}
