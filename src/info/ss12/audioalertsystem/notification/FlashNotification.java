/**
 * @author SS12 Orange Team 
 * The FlashNotification class starts and stops the screen flash 
 * 
 */
package info.ss12.audioalertsystem.notification;

import info.ss12.audioalertsystem.MainActivity;
import info.ss12.audioalertsystem.R;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * The Flash notification class
 */
public class FlashNotification extends AbstractNotification
{
	/** The Main Activity */
	private MainActivity mainActivity;

	/** Toggle for flashing background */
	private boolean swap = false;

	/** The timer to control the flashing */
	private Timer timer;

	/**
	 * Flash Notification constructor
	 * 
	 * @param mainActivity
	 */
	public FlashNotification(MainActivity mainActivity)
	{
		this.mainActivity = mainActivity;
	}

	/**
	 * Starts the screen flash notification on run thread
	 */
	@Override
	public void startNotify()
	{
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			/**
			 * Run method to kick off the thread
			 */
			@Override
			public void run()
			{
				mainActivity.runOnUiThread(new Runnable()
				{

					@Override
					public void run()
					{
						// sets timer to swap color every 500ms
						LinearLayout layout = (LinearLayout) mainActivity
								.findViewById(R.id.layout_main);
						layout.setBackgroundColor(swap ? Color.RED
								: Color.WHITE);
						swap ^= true;
					}
				});

			}
		}, 0, 500);
	}

	/**
	 * Stops screen flash and sets background color to white
	 */
	@Override
	public void stopNotify()
	{
		timer.cancel();
		LinearLayout layout = (LinearLayout) mainActivity
				.findViewById(R.id.layout_main);
		layout.setBackgroundColor(Color.WHITE);
	}
}
