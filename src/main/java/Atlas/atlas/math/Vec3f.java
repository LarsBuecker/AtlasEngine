package Atlas.atlas.math;

public class Vec3f {

	private float x;
	private float y;
	private float z;
	
	public Vec3f() {
		setX(0);
		setY(0);
		setZ(0);
	}
	
	public Vec3f(float x, float y, float z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public Vec3f(Vec3f v) {
		setX(v.getX());
		setY(v.getY());
		setZ(v.getZ());
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public float dot(Vec3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public Vec3f normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		
		return this;
	}
	
	public Vec3f cross(Vec3f r)
	{
		float x = y * r.getZ() - z * r.getY();
		float y = z * r.getX() - x * r.getZ();
		float z = x * r.getY() - y * r.getX();
		
		return new Vec3f(x,y,z);
	}
	
	public Vec3f add(Vec3f v) {
		return new Vec3f(getX() + v.getX(), getY() + v.getY(), getZ() + v.getZ());
	}
	
	public Vec3f add(float r) {
		return new Vec3f(getX() + r, getY() + r, getZ() + r);
	}
	
	public Vec3f sub(Vec3f v) {
		return new Vec3f(getX() - v.getX(), getY() - v.getY(), getZ() + v.getZ());
	}
	
	public Vec3f sub(float r) {
		return new Vec3f(getX() - r, getY() - r, getZ() - r);
	}
	
	public Vec3f div(Vec3f v) {
		return new Vec3f(getX() / v.getX(), getY() / v.getY(), getZ() / v.getZ());
	}
	
	public Vec3f div(float r) {
		return new Vec3f(getX() / r, getY() / r, getZ() / r);
	}
	
	public Vec3f mul(Vec3f v) {
		return new Vec3f(getX() * v.getX(), getY() * v.getY(), getZ() * v.getZ());
	}
	
	public Vec3f mul(float r) {
		return new Vec3f(getX() * r, getY() * r, getZ() * r);
	}
	
	public Vec3f abs() {
		return new Vec3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	
	public boolean equal(Vec3f v) {
		if(x == v.getX() && y == v.getY() && z == v.getZ()) 
			return true;
		return false;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "," + this.z + "]";
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
