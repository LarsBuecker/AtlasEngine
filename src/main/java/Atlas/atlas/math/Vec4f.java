package Atlas.atlas.math;

public class Vec4f {

	private float x;
	private float y;
	private float z;
	private float w;
	
	public Vec4f() {
		setX(0);
		setY(0);
		setZ(0);
		setW(0);
	}
	
	public Vec4f(float x, float y, float z, float w) {
		setX(x);
		setY(y);
		setZ(z);
		setW(w);
	}
	
	public Vec4f(Vec4f v) {
		setX(v.getX());
		setY(v.getY());
		setZ(v.getZ());
		setW(v.getW());
	}
	
	public Vec4f(Vec3f v, float w) {
		setX(v.getX());
		setY(v.getY());
		setZ(v.getZ());
		setW(w);
	}
	
	private float length() {
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}
	
	public float dot(Vec4f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public Vec4f normalize() {
		float length = length();
		x /= length;
		y /= length;
		z /= length;
		w /= length;
		
		return this;
	}
	
	public Vec4f conjugate() {
		return new Vec4f(-x, -y, -z, w);
	}
	
	public Vec4f add(Vec4f v) {
		return new Vec4f(getX() + v.getX(), getY() + v.getY(), getZ() + v.getZ(), getW() + v.getW());
	}
	
	public Vec4f add(float r) {
		return new Vec4f(getX() + r, getY() + r, getZ() + r, getW() + r);
	}
	
	public Vec4f sub(Vec4f v) {
		return new Vec4f(getX() - v.getX(), getY() - v.getY(), getZ() + v.getZ(), getW() - v.getW());
	}
	
	public Vec4f sub(float r) {
		return new Vec4f(getX() - r, getY() - r, getZ() - r, getW() - r);
	}
	
	public Vec4f div(Vec4f v) {
		return new Vec4f(getX() / v.getX(), getY() / v.getY(), getZ() / v.getZ(), getW() / v.getW());
	}
	
	public Vec4f div(float r) {
		return new Vec4f(getX() / r, getY() / r, getZ() / r, getW() / r);
	}
	
	public Vec4f mul(Vec4f v) {
		return new Vec4f(getX() * v.getX(), getY() * v.getY(), getZ() * v.getZ(), getW() * v.getW());
	}
	
	public Vec4f mul(float r) {
		return new Vec4f(getX() * r, getY() * r, getZ() * r, getW() * r);
	}
	
	public Vec4f abs() {
		return new Vec4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}
	
	public boolean equal(Vec4f v) {
		if(x == v.getX() && y == v.getY() && z == v.getZ() && w == v.getW()) 
			return true;
		return false;
	}
	
	public Vec3f xyz() {
		return new Vec3f(x,y,z);
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
}
