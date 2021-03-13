package Atlas.atlas.renderer;

import Atlas.atlas.core.Input;
import Atlas.atlas.core.KeyCodes;
import Atlas.atlas.events.Event;
import Atlas.atlas.events.Event.EventType;
import Atlas.atlas.events.EventDispatcher;
import Atlas.atlas.events.types.MouseScrolledEvent;
import Atlas.atlas.events.types.WindowResizeEvent;
import Atlas.atlas.math.Vec3f;

public class OrthographicCameraController {

	private float aspectRatio;
	private float zoomLevel = 1;
	private OrthographicCamera camera;
	private boolean rotation = false;
	
	private Vec3f cameraPosition = new Vec3f();
	private float cameraRotation = 0;
	private float cameraTranslationSpeed = 1f;
	private float cameraRotationSpeed = 1f;
	
	public OrthographicCameraController(float aspectRatio, boolean rotation) {
		this.aspectRatio = aspectRatio;
		this.rotation = rotation;
		camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
	}
	
	public OrthographicCameraController(float aspectRatio) {
		this.aspectRatio = aspectRatio;
		camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
	}
	
	public void onUpdate(float delta) {
		if (Input.isKeyPressed(KeyCodes.AL_KEY_A)) {
			cameraPosition.add(new Vec3f(delta * -cameraTranslationSpeed, 0, 0));
		}	
		if (Input.isKeyPressed(KeyCodes.AL_KEY_D)) {
			cameraPosition.add(new Vec3f(delta * cameraTranslationSpeed, 0, 0));
		}
		if (Input.isKeyPressed(KeyCodes.AL_KEY_W)) {
			cameraPosition.add(new Vec3f(0, delta * -cameraTranslationSpeed, 0));
		}
		if (Input.isKeyPressed(KeyCodes.AL_KEY_S)) {
			cameraPosition.add(new Vec3f(cameraPosition.getX() + delta * cameraTranslationSpeed, 0, 0));
		}
		
		if(rotation) {
			if( Input.isKeyPressed(KeyCodes.AL_KEY_Q) ) {
				cameraRotation += cameraRotationSpeed * delta;
			}
			if ( Input.isKeyPressed(KeyCodes.AL_KEY_E) ) {
				cameraRotation -= cameraRotationSpeed * delta;
			}
			camera.setRotation(cameraRotation);
		}
		
		camera.setPosition(cameraPosition);
	}
	
	public void onEvent(Event e) {
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(EventType.MouseScrolled, (Event event) -> (onMouseScrolled((MouseScrolledEvent) event)));
		dispatcher.dispatch(EventType.WindowResize, (Event event) -> (onWindowResize((WindowResizeEvent) event)));
	}
	
	private boolean onMouseScrolled(MouseScrolledEvent e) {
		zoomLevel -= e.yOffset;
		camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
		return false;
	}
	
	private boolean onWindowResize(WindowResizeEvent e) {
		aspectRatio = (float) e.getWidth() / (float) e.getHeight();
		camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
		return false;
	}

	public float getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(float zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public boolean isRotation() {
		return rotation;
	}

	public void setRotation(boolean rotation) {
		this.rotation = rotation;
	}

	public float getCameraTranslationSpeed() {
		return cameraTranslationSpeed;
	}

	public void setCameraTranslationSpeed(float cameraTranslationSpeed) {
		this.cameraTranslationSpeed = cameraTranslationSpeed;
	}

	public float getCameraRotationSpeed() {
		return cameraRotationSpeed;
	}

	public void setCameraRotationSpeed(float cameraRotationSpeed) {
		this.cameraRotationSpeed = cameraRotationSpeed;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}	
}
