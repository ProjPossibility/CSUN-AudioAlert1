package info.ss12.audioalertsystem;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	private boolean alarmActivated = false;
	private LinearLayout mainLayout;
	
	private Switch micSwitch;
	private ButtonController buttonControl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonControl = new ButtonController(this);
		micSwitch = (Switch )findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) 
		{
			if(alarmActivated)
				return;
			
			alarmActivated = true;
			
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					runOnUiThread(new Runnable() 
					{
						
						@Override
						public void run() 
						{
							mainLayout = (LinearLayout) findViewById(R.id.layout_main);
							
							mainLayout.setBackgroundColor(swap ? Color.RED : Color.WHITE);
							swap ^= true;
						}
					});
					
				}
			}, 0, 500);
			
			
			

			Log.d(TAG, "FIRE ALARM DETECTED");	
		}
		
	};
	boolean swap = false;

	public Handler getHandler() 
	{
		return handler;
	}

	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}


	
}
