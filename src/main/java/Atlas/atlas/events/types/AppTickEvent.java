package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class AppTickEvent extends Event {

	private int delta;
	
	public AppTickEvent(int delta) {
		super(Event.EventType.AppTick);
		this.delta = delta;
	}

	public int getDelta() {
		return delta;
	}

}
