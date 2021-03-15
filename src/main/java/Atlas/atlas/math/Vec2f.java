package Atlas.atlas.math;

public class Vec2f {

	private float x;
	private float y;
	
	public Vec2f() {
		setX(0);
		setY(0);
	}
	
	public Vec2f(float v) {
		setX(v);
		setY(v);
	}
	
	public Vec2f(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Vec2f(Vec2f v) {
		setX(v.getX());
		setY(v.getY());
	}
	
	private float length() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vec2f r) {
		return x * r.getX() + y * r.getY();
	}
	
	public Vec2f normalize() {
		float length = length();
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vec2f add(Vec2f v) {
		return new Vec2f(getX() + v.getX(), getY() + v.getY());
	}
	
	public Vec2f add(float r) {
		return new Vec2f(getX() + r, getY() + r);
	}
	
	public Vec2f sub(Vec2f v) {
		return new Vec2f(getX() - v.getX(), getY() - v.getY());
	}
	
	public Vec2f sub(float r) {
		return new Vec2f(getX() - r, getY() - r);
	}
	
	public Vec2f div(Vec2f v) {
		return new Vec2f(getX() / v.getX(), getY() / v.getY());
	}
	
	public Vec2f div(float r) {
		return new Vec2f(getX() / r, getY() / r);
	}
	
	public Vec2f mul(Vec2f v) {
		return new Vec2f(getX() * v.getX(), getY() * v.getY());
	}
	
	public Vec2f mul(float r) {
		return new Vec2f(getX() * r, getY() * r);
	}
	
	public Vec2f abs() {
		return new Vec2f(Math.abs(x), Math.abs(y));
	}
	
	public boolean equal(Vec2f v) {
		if(x == v.getX() && y == v.getY()) 
			return true;
		return false;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
