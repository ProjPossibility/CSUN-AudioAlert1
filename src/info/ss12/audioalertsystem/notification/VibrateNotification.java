package info.ss12.audioalertsystem.notification;

import android.content.Context;
import android.os.Vibrator;

/**
 * The Vibrate Notification class used to notifcy the user through haptic
 * feedback
 */
public class VibrateNotification extends AbstractNotification
{
	/** The vibrator */
	Vibrator vibrator;

	/**
	 * Vibrate Notification constructor
	 * 
	 * @param context
	 *            the Context
	 */
	public VibrateNotification(Context context)
	{
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * Start the Vibrate notification
	 */
	@Override
	public void startNotify()
	{
		vibrator.vibrate(new long[]
		{ 0, 1000 }, 0);
	}

	/**
	 * Stop the vibrate notification
	 */
	@Override
	public void stopNotify()
	{
		vibrator.cancel();
	}

}
