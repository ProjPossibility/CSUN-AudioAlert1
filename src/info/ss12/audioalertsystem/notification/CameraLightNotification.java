package info.ss12.audioalertsystem.notification;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import info.ss12.audioalertsystem.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class CameraLightNotification extends AbstractNotification {

	private Camera camera;
	private Parameters pars;
	private Timer timer;
	private MainActivity activity;
	private boolean lightToggle;
	
	public CameraLightNotification(MainActivity a) {
		camera = Camera.open();
		pars = camera.getParameters();
		timer = new Timer();
		activity = a;
		lightToggle = false;
	}
	
	@Override
	public void startNotify() {
		pars.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(pars);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (lightToggle)
							camera.startPreview();
						else
							camera.stopPreview();
						lightToggle ^= true;
					}
				});
			}
		}, 0, 500);
	}

	@Override
	public void stopNotify() {
		camera.stopPreview();
		camera.release();
	}
}
