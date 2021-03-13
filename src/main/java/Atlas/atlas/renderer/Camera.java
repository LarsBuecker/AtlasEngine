package Atlas.atlas.renderer;

import Atlas.atlas.math.Mat4f;

public abstract class Camera {

	protected Mat4f projectionMatrix = new Mat4f();
	protected Mat4f viewMatrix = new Mat4f();
	protected Mat4f viewProjectionMatrix = new Mat4f();

	public abstract void recaculateViewProjectionMatrix();

	public Mat4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public Mat4f getViewMatrix() {
		return viewMatrix;
	}

	public Mat4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}	
}
