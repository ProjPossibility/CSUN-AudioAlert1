package info.ss12.audioalertsystem.notification;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class CameraLightNotification extends AbstractNotification
{

	private Camera camera;
	private Parameters light_ON;
	private Parameters light_OFF;
	private Timer timer;
	private boolean lightToggle;

	public CameraLightNotification()
	{

	}

	@Override
	public void startNotify()
	{
		configCamera();
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (lightToggle)
				{
					camera.setParameters(light_ON);
				}
				else
				{
					camera.setParameters(light_OFF);
				}
				lightToggle ^= true;
			}
		}, 0, 500);
	}

	@Override
	public void stopNotify()
	{
		timer.cancel();
		camera.stopPreview();
		camera.release();
	}

	public void configCamera()
	{
		camera = Camera.open();
		camera.startPreview();
		light_ON = camera.getParameters();
		light_OFF = camera.getParameters();
		light_ON.setFlashMode(Parameters.FLASH_MODE_TORCH);
		light_OFF.setFlashMode(Parameters.FLASH_MODE_OFF);
		lightToggle = false;
	}
}
