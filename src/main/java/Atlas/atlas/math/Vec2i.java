package Atlas.atlas.math;

public class Vec2i {

	private int x;
	private int y;
	
	public Vec2i() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vec2i(int v) {
		this.x = v;
		this.y = v;
	}
	
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2i(Vec2i v) {
		this.x = v.getX();
		this.y = v.getY();
	}
	
	public int length() {
		return (int) Math.sqrt(x*x + y*y);
	}
	
	public Vec2i normalize() {
		int length = length();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vec2i add(Vec2i v) {
		return new Vec2i(getX() + v.getX(), getY() + v.getY());
	}
	
	public Vec2i add(int r) {
		return new Vec2i(getX() + r, getY() + r);
	}
	
	public Vec2i sub(Vec2i v) {
		return new Vec2i(getX() - v.getX(), getY() - v.getY());
	}
	
	public Vec2i sub(int r) {
		return new Vec2i(getX() - r, getY() - r);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
