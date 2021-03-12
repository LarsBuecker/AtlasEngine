package Atlas.atlas.renderer;

import Atlas.atlas.core.Application;
import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec3f;

public class Camera {

	private Mat4f projectionMatrix = new Mat4f();
	private Mat4f viewMatrix = new Mat4f();
	private Mat4f viewProjectionMatrix = new Mat4f();

	private Vec3f position;
	private float pitch;
	private float roll;
	private float yaw;
	
	private static final float NEAR = 0.001f;
	private static final float FAR = 10000;
	private static float fov = 70;
	
	public Camera() {
		this.position = new Vec3f(0, 0.0f, -1);
		recalculateViewMatrix();
		calcualteProjection();
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}
	
	private void recalculateViewMatrix() {
		viewMatrix.Identity();
		viewMatrix.Rotation(new Vec3f((float) Math.toRadians(pitch), 0, 0));
		viewMatrix.Rotation(new Vec3f(0, (float) Math.toRadians(pitch), 0));
		viewMatrix.Rotation(new Vec3f(0, 0, (float) Math.toRadians(pitch)));
		Vec3f pos = new Vec3f(-position.getX(), -position.getY(), -position.getZ());
		viewMatrix.Translation(pos);
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}
	
	private void calcualteProjection() {
		int width = Application.getInstance().getWindow().getWidth();
		int height = Application.getInstance().getWindow().getHeight();
		projectionMatrix.PerspectiveProjection(fov, width, height, NEAR, FAR);
	}
	
	public Mat4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}
	
	public void setPosition(Vec3f pos) {
		this.position = pos;
		recalculateViewMatrix();
	}
	
	public Vec3f getPosition() {
		return position;
	}

	public Mat4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Mat4f getViewMatrix() {
		return viewMatrix;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
}
