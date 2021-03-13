package Atlas.sandbox;

import Atlas.atlas.core.Layer;
import Atlas.atlas.core.Log;
import Atlas.atlas.events.Event;

public class TestLayer extends Layer {

	public TestLayer() {
		super("Test Layer");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OnAttach() {
		Log.clientLog(getName() + " Attached!");
		
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuiRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
