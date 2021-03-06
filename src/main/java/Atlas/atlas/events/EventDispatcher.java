package Atlas.atlas.events;

public class EventDispatcher {

	private Event event;
	
	public EventDispatcher(Event event) {
		this.event = event;
	}
	
	public void dispatch(Event.EventType type, EventHandler handler) {
		if ( event.handled ) return;
		
		if ( event.getType() == type ) 
			event.handled = handler.onEvent(event);
	}
}
