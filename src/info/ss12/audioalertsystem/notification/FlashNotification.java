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

public class FlashNotification extends AbstractNotification
{
	private MainActivity mainActivity;
	private boolean swap = false;
	private Timer timer;

	public FlashNotification(MainActivity mainActivity)
	{
		this.mainActivity = mainActivity;
	}

	// starts the screen flash notification on run thread
	@Override
	public void startNotify()
	{
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
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

	// stops screen flash and sets background color to white
	@Override
	public void stopNotify()
	{
		timer.cancel();
		LinearLayout layout = (LinearLayout) mainActivity
				.findViewById(R.id.layout_main);
		layout.setBackgroundColor(Color.WHITE);
	}
}
