package Atlas.atlas.renderer;

import java.util.ArrayList;
import java.util.List;

public class BufferLayout {
	
	private List<BufferElement> elements;
	private int stride = 0;

	public BufferLayout() {	
		this.elements = new ArrayList<BufferElement>();
	}
	
	public BufferLayout(List<BufferElement> elements) {
		this.elements = elements;
		calculateOffsetAndStride();
	}
	
	public int getStride() {
		return stride;
	}
	
	public BufferLayout addElement(BufferElement element) {
		elements.add(element);
		calculateOffsetAndStride();
		return this;
	}
	
	public List<BufferElement> getElements() {
		return elements;
	}
	
	private void calculateOffsetAndStride() {
		int offset = 0;
		stride = 0;
		for ( BufferElement element : elements) {
			element.offset = offset;
			offset += element.size;
			stride += element.size;
		}
	}
}
