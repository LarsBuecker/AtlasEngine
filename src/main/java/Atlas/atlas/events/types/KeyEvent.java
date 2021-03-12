package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class KeyEvent extends Event {
	
	int keycode;

	protected KeyEvent(int keycode, EventType type) {
		super(type);
		this.keycode = keycode;
	}
}
