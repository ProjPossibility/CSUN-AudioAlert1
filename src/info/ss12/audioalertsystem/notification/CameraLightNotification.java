package info.ss12.audioalertsystem.notification;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

/**
 * The Camera light notification class
 * 
 */
public class CameraLightNotification extends AbstractNotification
{

	/** The camera */
	private Camera camera;

	/** Parameter setting for camera light ON */
	private Parameters light_ON;

	/** Parameter setting for camera light OFF */
	private Parameters light_OFF;

	/** Timer to control camera light */
	private Timer timer;

	/** Toggle for light */
	private boolean lightToggle;

	/**
	 * Constructor for Camera light notification
	 */
	public CameraLightNotification()
	{

	}

	/**
	 * Start notification for Camera light
	 */
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

	/**
	 * Stop notification for Camera light
	 */
	@Override
	public void stopNotify()
	{
		timer.cancel();
		camera.stopPreview();
		camera.release();
	}

	/**
	 * Configuration for Camera
	 */
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
