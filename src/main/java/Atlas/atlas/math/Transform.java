package Atlas.atlas.math;


public class Transform {

	private Vec3f position; 
	private Vec3f rotation;
	private Vec3f scale;
	
	public Transform() {
		this.position = new Vec3f();
		this.rotation = new Vec3f();
		this.scale = new Vec3f();
	}
	
	public Transform(Vec3f positon, Vec3f rotation, Vec3f scale) {
		this.position = positon;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void add(Vec3f position, Vec3f rotation, Vec3f scale) {
		this.position.add(position);
		this.rotation.add(rotation);
		this.scale.add(scale);
	}
	
	public void sub(Vec3f position, Vec3f rotation, Vec3f scale) {
		this.position.sub(position);
		this.rotation.sub(rotation);
		this.scale.sub(scale);
	}
	
	public Vec3f getPosition() {
		return position;
	}
	public void setPosition(Vec3f position) {
		this.position = position;
	}
	public Vec3f getRotation() {
		return rotation;
	}
	public void setRotation(Vec3f rotaion) {
		this.rotation = rotaion;
	}
	public Vec3f getScale() {
		return scale;
	}
	public void setScale(Vec3f scale) {
		this.scale = scale;
	}
}
