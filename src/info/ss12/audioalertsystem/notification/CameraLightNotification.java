package info.ss12.audioalertsystem.notification;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class CameraLightNotification extends AbstractNotification {

	Camera camera;
	Parameters pars;
	
	public CameraLightNotification() {
		camera = Camera.open();
		pars = camera.getParameters();
	}
	
	@Override
	public void startNotify() {
		pars.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(pars);
		camera.startPreview();
	}

	@Override
	public void stopNotify() {
		camera.stopPreview();
		camera.release();
	}
}
