package Atlas.atlas.events.types;

import Atlas.atlas.events.Event;

public class WindowResizeEvent extends Event{
	
	private int width, height;

	public WindowResizeEvent(int width, int height) {
		super(Event.EventType.WindowResize);
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
