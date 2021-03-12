package Atlas.atlas.renderer;

public class BufferElement {

	public enum ShaderDataType {
		None, Float, Float2, Float3, Float4, Mat3, Mat4, Int, Int2, Int3, Int4, Bool
	}
	 
	public static int ShaderDataTypeSize(ShaderDataType type) {
		switch(type) {
		case Float: return 4;
		case Float2: return 4 * 2;
		case Float3: return 4 * 3;
		case Float4: return 4 * 4;
		case Mat3: return 4 * 3 * 3;
		case Mat4: return 4 * 4 * 4;
		case Int: return 4;
		case Int2: return 4 * 2;
		case Int3: return 4 * 3;
		case Int4: return 4 * 4;
		case Bool: return 1;
		case None: break;
		default: break;
		}
		
		System.err.println("Unkown ShaderDataType!");
		return 0;
	}
	
	String name;
	public ShaderDataType type;
	int size;
	public int offset;
	public boolean normalized;
	
	public BufferElement() {
		
	}
	
	public BufferElement(ShaderDataType type, String name, boolean normalized) {
		this.type = type;
		this.name = name;
		this.normalized = false; //normalized;
		this.offset = 0;
		this.size = ShaderDataTypeSize(type);
	}
	
	public int getComponentCount() {
		switch (type) {
		case Bool: return 1;
		case Float: return 1;
		case Float2: return 2;
		case Float3: return 3;
		case Float4: return 4;
		case Int: return 1;
		case Int2: return 2;
		case Int3: return 3;
		case Int4: return 4;
		case Mat3: return 3*3;
		case Mat4: return 4*4;
		case None: break;
		default:break;	
		}
		System.err.println("Unknown ShaderDataType!");
		return 0;		
	}
}
