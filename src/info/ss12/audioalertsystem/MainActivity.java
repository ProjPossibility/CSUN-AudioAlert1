package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.alert.AbstractAlert;
import info.ss12.audioalertsystem.alert.AlertListener;
import info.ss12.audioalertsystem.notification.FlashNotification;
import info.ss12.audioalertsystem.notification.NotificationBarNotification;
import info.ss12.audioalertsystem.notification.VibrateNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	private boolean alarmActivated = false;
	private LinearLayout mainLayout;
	
	
	private Switch micSwitch;
	private Button testAlert;
	
	private ButtonController buttonControl;
	private VibrateNotification vibrateNotification;
	
	private VibrateNotification vibrate;
	private FlashNotification flash;
	private NotificationBarNotification bar;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonControl = new ButtonController(this);
		micSwitch = (Switch )findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);

		testAlert = (Button)findViewById(R.id.test_alert);
		testAlert.setOnClickListener(buttonControl);
		
		vibrate = new VibrateNotification();
		flash = new FlashNotification();
		bar = new NotificationBarNotification();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onAlarmDetected()
	{
		bar.startNotify();
		flash.startNotify();
		vibrate.startNotify();
	}
}
