package info.ss12.audioalertsystem.notification;

import info.ss12.audioalertsystem.*;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;

public class FlashNotification extends AbstractNotification {
	private MainActivity mainActivity;
	private boolean swap = false;
	private Timer timer;
	public FlashNotification(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void startNotify(){
		Log.d("In notify","Now");
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mainActivity.runOnUiThread(new Runnable() 
				{
					
					@Override
					public void run() 
					{
						LinearLayout layout = (LinearLayout) mainActivity.findViewById(R.id.layout_main);
						layout.setBackgroundColor(swap ? Color.RED : Color.WHITE);
						swap ^= true;
					}
				});
				
			}
		}, 0, 500);	
	}
	
	@Override
	public void stopNotify(){
		timer.cancel();
		LinearLayout layout = (LinearLayout) mainActivity.findViewById(R.id.layout_main);
		layout.setBackgroundColor(Color.WHITE);
	}
}
