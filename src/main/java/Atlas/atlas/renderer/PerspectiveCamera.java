package Atlas.atlas.renderer;

import Atlas.atlas.core.Application;
import Atlas.atlas.math.Mat4f;
import Atlas.atlas.math.Vec3f;

public class PerspectiveCamera extends Camera {

	private Vec3f position;
	private float pitch;
	private float roll;
	private float yaw;
	
	private static final float NEAR = 0.001f;
	private static final float FAR = 10000;
	private static float fov = 70;
	
	public PerspectiveCamera() {
		this.position = new Vec3f(0, 0.0f, -1);
		recaculateViewProjectionMatrix();
		calcualteProjection();
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}
	
	private void calcualteProjection() {
		int width = Application.getInstance().getWindow().getWidth();
		int height = Application.getInstance().getWindow().getHeight();
		projectionMatrix.PerspectiveProjection(fov, width, height, NEAR, FAR);
	}
	
	public void setPosition(Vec3f pos) {
		this.position = pos;
		recaculateViewProjectionMatrix();
	}
	
	public Vec3f getPosition() {
		return position;
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

	@Override
	public void recaculateViewProjectionMatrix() {
		viewMatrix.Identity();
		viewMatrix.Rotation(new Vec3f((float) Math.toRadians(pitch), 0, 0));
		viewMatrix.Rotation(new Vec3f(0, (float) Math.toRadians(pitch), 0));
		viewMatrix.Rotation(new Vec3f(0, 0, (float) Math.toRadians(pitch)));
		Vec3f pos = new Vec3f(-position.getX(), -position.getY(), -position.getZ());
		viewMatrix.Translation(pos);
		viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
	}
}
