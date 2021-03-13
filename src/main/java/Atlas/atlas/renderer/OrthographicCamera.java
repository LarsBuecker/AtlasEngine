package Atlas.atlas.renderer;

import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec3f;

public class OrthographicCamera extends Camera {
	
	private Vec3f position;
	private float rotation;

	public OrthographicCamera(float left, float right, float top, float bottom) {
		projectionMatrix.OrthographicProjection(left, right, bottom, top, -1f, 1);
		viewMatrix.Identity();
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
		position = new Vec3f();
	}

	@Override
	public void recaculateViewProjectionMatrix() {
		viewMatrix.Identity();
		viewMatrix.Rotation(new Vec3f(0, 0, (float) Math.toRadians(rotation)));
		Vec3f pos = new Vec3f(-position.getX(), -position.getY(), -position.getZ());
		viewMatrix.Translation(pos);
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
		recaculateViewProjectionMatrix();
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		recaculateViewProjectionMatrix();
		
	}

	public Mat4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}

	public Mat4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Mat4f getViewMatrix() {
		return viewMatrix;
	}	
}
