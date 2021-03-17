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
	
	private Vec3f cameraPosition;
	private float cameraRotation = 0;
	private float cameraTranslationSpeed = 0.01f;
	private float cameraRotationSpeed = 1f;
	
	public OrthographicCameraController(float aspectRatio, boolean rotation) {
		this.cameraPosition = new Vec3f();
		this.aspectRatio = aspectRatio;
		this.rotation = rotation;
		camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
	}
	
	public OrthographicCameraController(float aspectRatio) {
		this.cameraPosition = new Vec3f();
		this.aspectRatio = aspectRatio;
		camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
	}
	
	public void onUpdate(float delta) {
		if (Input.isKeyPressed(KeyCodes.AL_KEY_A)) {
			cameraPosition = cameraPosition.add(new Vec3f(delta * -cameraTranslationSpeed, 0, 0));
		}	
		if (Input.isKeyPressed(KeyCodes.AL_KEY_D)) {
			cameraPosition = cameraPosition.add(new Vec3f(delta * cameraTranslationSpeed, 0, 0));
		}
		if (Input.isKeyPressed(KeyCodes.AL_KEY_W)) {
			cameraPosition = cameraPosition.add(new Vec3f(0, delta * -cameraTranslationSpeed, 0));
		}
		if (Input.isKeyPressed(KeyCodes.AL_KEY_S)) {
			cameraPosition = cameraPosition.add(new Vec3f(0, delta * cameraTranslationSpeed, 0));
		}
		
//		Log.clientLog(cameraPosition.toString());
		
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
	
	public void onResize(float width, float height) {
		aspectRatio = width / height;
		calculateView();
	}
	
	public void onEvent(Event e) {
		EventDispatcher dispatcher = new EventDispatcher(e);
		dispatcher.dispatch(EventType.MouseScrolled, (Event event) -> (onMouseScrolled((MouseScrolledEvent) event)));
		dispatcher.dispatch(EventType.WindowResize, (Event event) -> (onWindowResize((WindowResizeEvent) event)));
	}
	
	private boolean onMouseScrolled(MouseScrolledEvent e) {
		zoomLevel -= e.yOffset;
		if ( zoomLevel < 0.01f ) zoomLevel = 0.01f;
		calculateView();
		return false;
	}
	
	private boolean onWindowResize(WindowResizeEvent e) {
		aspectRatio = (float) e.getWidth() / (float) e.getHeight();
		calculateView();
		return false;
	}
	
	private void calculateView() {
		camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
	}

	public float getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(float zoomLevel) {
		this.zoomLevel = zoomLevel;
		calculateView();
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
