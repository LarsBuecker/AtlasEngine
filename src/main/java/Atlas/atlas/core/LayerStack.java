package Atlas.atlas.core;

import java.util.Vector;

public class LayerStack {
	
	private Vector<Layer> layers = new Vector<Layer>();

	public void PushLayer(Layer layer) {
		layers.add(layer);
	}
	
	public void PushOverlay(Layer overlay) {
		layers.insertElementAt(overlay,  0);
	}
	
	public void PopLayer(Layer layer) {
		layers.remove(layer);
	}
	
	public void PopOverlay(Layer overlay) {
		layers.remove(overlay);
	}
	
	public Vector<Layer> getLayers() {
		return layers;
	}
	
}
