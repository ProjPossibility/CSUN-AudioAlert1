package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.alert.GPSAlert;
import info.ss12.audioalertsystem.notification.CameraLightNotification;
import info.ss12.audioalertsystem.notification.FlashNotification;
import info.ss12.audioalertsystem.notification.NotificationBarNotification;
import info.ss12.audioalertsystem.notification.SMSNotification;
import info.ss12.audioalertsystem.notification.TextToSpeechNotification;
import info.ss12.audioalertsystem.notification.VibrateNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity
{
	public static final String AUDIO_PREF = "AUDIO_PREF";
	public static final String PHONE_LIST = "PHONE_LIST";
	public static final String NOTIFICATION = "NOT";
	public static final String SCREEN_FLASH = "SCR";
	public static final String VIBRATE = "VBR";
	public static final String CAMERA = "CAM";
	public static final String TEXT = "TXT";
	private final String TAG = "Main Activity";
	private boolean alarmActivated = false;
	private boolean screenFlashAlert;
	private boolean vibrateAlert;
	private boolean cameraFlashAlert;
	private boolean notificationsAlert;
	private boolean txtMessageAlert;

	private boolean firstAlarm = true;
	private boolean pastAllotted = false;
	private int alarmCount = 0;
	private CountDownTimer countDownTimer;
	private CountDownTimer silenceTimer;
	private long countDownTime = 10000;
	private TextView timerView;

	private Switch micSwitch;
	private Button testAlert;

	private ListView listView;
	private ArrayAdapter<String> adapter;

	private ButtonController buttonControl;

	private VibrateNotification vibrate;
	private FlashNotification flash;
	private NotificationBarNotification bar;

	private CameraLightNotification cameraLight;
	private SMSNotification text;
	private TextToSpeechNotification TTS;

	private GPSAlert gpsAlert;
	

	private View mainView = null;
	private View settingsView = null;
	private View helpView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (mainView == null)
			mainView = getLayoutInflater()
					.inflate(R.layout.activity_main, null);
		setContentView(mainView);
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(AUDIO_PREF, 0);
		screenFlashAlert = settings.getBoolean(SCREEN_FLASH, true);
		vibrateAlert = settings.getBoolean(VIBRATE, true);
		cameraFlashAlert = settings.getBoolean(CAMERA, true);
		notificationsAlert = settings.getBoolean(NOTIFICATION, true);
		txtMessageAlert = settings.getBoolean(TEXT, true);
		Set<String> phoneList = settings.getStringSet(PHONE_LIST,
				new TreeSet<String>());
		List<String> phones = new ArrayList<String>(phoneList);
		listView = (ListView) findViewById(R.id.phone_list);
		adapter = new ArrayAdapter<String>(this, R.layout.cell_layout,
				R.id.phone_view, phones);

		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3)
			{
				adapter.remove(adapter.getItem(position));
			}

		});
		listView.setAdapter(adapter);

		timerView = (TextView) findViewById(R.id.timer_view);
		
		Button add = (Button) findViewById(R.id.add_phone_button);
		add.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				// Get the layout inflater
				LayoutInflater inflater = getLayoutInflater();

				// Inflate and set the layout for the dialog
				// Pass null as the parent view because its going in the dialog
				// layout

				final View view = inflater.inflate(R.layout.phone_entry_layout,
						null);
				final EditText phoneEntry = (EditText) view
						.findViewById(R.id.phone_entry);
				builder.setView(view);
				// Add action buttons
				builder.setPositiveButton("Add Phone",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{
								adapter.add(phoneEntry.getText().toString());
							}
						});
				builder.setNegativeButton("Cancel", null);
				builder.show();
			}
		});

		buttonControl = new ButtonController(this);
		micSwitch = (Switch) findViewById(R.id.mic_switch);
		micSwitch.setOnClickListener(buttonControl);
		micSwitch.setOnTouchListener(buttonControl);

		testAlert = (Button) findViewById(R.id.test_alert);
		testAlert.setOnClickListener(buttonControl);

		vibrate = new VibrateNotification(this);
		flash = new FlashNotification(this);

		cameraLight = new CameraLightNotification();

		bar = new NotificationBarNotification();

		text = new SMSNotification(this);

		gpsAlert = new GPSAlert(this);
		
		TTS = new TextToSpeechNotification(this);
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
			if (firstAlarm && msg.arg1 == 1)
			{
				firstAlarm = false;
				alarmCount = 0;
				countDownTimer = new CountDownTimer(countDownTime, 1000)
				{
					@Override
					public void onTick(long millisUntilFinished)
					{
						timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
					}

					@Override
					public void onFinish()
					{
						pastAllotted = true;
						timerView.setText("");
						silenceTimer = new CountDownTimer(countDownTime, 10000)
						{

							@Override
							public void onTick(long millisUntilFinished)
							{
								
							}

							@Override
							public void onFinish()
							{
								firstAlarm = true;
								pastAllotted = false;
							}
						};
						silenceTimer.start();
					}
				};
				countDownTimer.start();
			}

			if (pastAllotted)
			{
				
				if (msg.arg1 == 1 && !alarmActivated) // Turn On
				{
					if (mainView != null && !mainView.isShown())
						setContentView(mainView);
					if (notificationsAlert)
						bar.startNotify();
					if (screenFlashAlert)
						flash.startNotify();
					if (vibrateAlert)
						vibrate.startNotify();
					if (cameraFlashAlert)
						cameraLight.startNotify();
					
					TTS.startNotify();
					List<String> phoneNumbers = new ArrayList<String>();
					for (int i = 0; i < adapter.getCount(); i++)
					{
						phoneNumbers.add(adapter.getItem(i));
					}
					if (!phoneNumbers.isEmpty())
					{
						text.setPhoneNumbers(phoneNumbers);
						text.startNotify();
					}
					alarmActivated = true;
					Notification("SS12 Audio Alert", "FIRE ALARM DETECTED");
				}
				
			}
			
			if (msg.arg1 == 0 && alarmActivated)
			{
				if(countDownTimer != null)
				{
					countDownTimer.cancel();
				}
				
				if(silenceTimer!= null)
				{
					silenceTimer.cancel();
				}
				
				if (notificationsAlert)
					bar.stopNotify();
				if (screenFlashAlert)
					flash.stopNotify();
				if (vibrateAlert)
					vibrate.stopNotify();
				if (cameraFlashAlert)
					cameraLight.stopNotify();
				if (txtMessageAlert)
					text.stopNotify();
				TTS.stopNotify();
				firstAlarm = true;
				pastAllotted = false;	
				alarmActivated = false;
			}

		}

	};

	public Handler getHandler()
	{
		return handler;
	}

	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}

	/**
	 * Notification Bar messaging.
	 * 
	 * @param notificationTitle
	 * @param notificationMessage
	 */
	private void Notification(String notificationTitle,
			String notificationMessage)
	{
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				"ALERT!!!", System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(MainActivity.this, notificationTitle,
				notificationMessage, pendingIntent);
		notificationManager.notify(10001, notification);
	}

	public boolean isAlarmActivated()
	{
		return alarmActivated;
	}

	public void setAlarmActivated(boolean alarmActivated)
	{
		this.alarmActivated = alarmActivated;
	}

	@Override
	public void onBackPressed()
	{
		if (settingsView != null && settingsView.isShown())
		{
			this.setContentView(mainView);
			// onSettingScreen = false;
			return;
		}

		if (helpView != null && helpView.isShown())
		{
			setContentView(mainView);
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Exit");
		builder.setMessage("Exit application and disable service monitor? (You can press home to move to background)");
		builder.setPositiveButton("Exit", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
			}
		});
		builder.setNegativeButton("Cancel", null);
		builder.show();
	}

	@Override
	protected void onStop()
	{
		SharedPreferences settings = getSharedPreferences(AUDIO_PREF, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putStringSet(PHONE_LIST, getPhoneNumberList());
		editor.putBoolean(NOTIFICATION, notificationsAlert);
		editor.putBoolean(SCREEN_FLASH, screenFlashAlert);
		editor.putBoolean(VIBRATE, vibrateAlert);
		editor.putBoolean(CAMERA, cameraFlashAlert);
		editor.putBoolean(TEXT, txtMessageAlert);
		editor.commit();
		super.onStop();
	}

	public Set<String> getPhoneNumberList()
	{
		Set<String> phones = new TreeSet<String>();
		for (int i = 0; i < adapter.getCount(); i++)
		{
			phones.add(adapter.getItem(i).toString());
		}
		return phones;
	}

	@Override
	protected void onDestroy()
	{
		gpsAlert.stopGps();
		if (buttonControl != null && buttonControl.getIntent() != null)
		{
			stopService(buttonControl.getIntent());
		}
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_settings)
		{
			if (settingsView == null)
			{
				settingsView = getLayoutInflater().inflate(R.layout.settings,
						null);
				SettingClickListener settingClickListener = new SettingClickListener();
				((CheckBox) settingsView.findViewById(R.id.notifications))
						.setOnClickListener(settingClickListener);
				((CheckBox) settingsView.findViewById(R.id.screen_flash))
						.setOnClickListener(settingClickListener);
				((CheckBox) settingsView.findViewById(R.id.vibrate))
						.setOnClickListener(settingClickListener);
				((CheckBox) settingsView.findViewById(R.id.camera_flash))
						.setOnClickListener(settingClickListener);
				((CheckBox) settingsView.findViewById(R.id.txt_message))
						.setOnClickListener(settingClickListener);
			}
			setContentView(settingsView);
			((CheckBox) findViewById(R.id.notifications))
					.setChecked(notificationsAlert);
			((CheckBox) findViewById(R.id.screen_flash))
					.setChecked(screenFlashAlert);
			((CheckBox) findViewById(R.id.vibrate)).setChecked(vibrateAlert);
			((CheckBox) findViewById(R.id.camera_flash))
					.setChecked(cameraFlashAlert);
			((CheckBox) findViewById(R.id.txt_message))
					.setChecked(txtMessageAlert);
		}
		if (item.getItemId() == R.id.help)
		{
			if (helpView == null)
			{
				helpView = getLayoutInflater().inflate(R.layout.help, null);
			}
			setContentView(helpView);
		}
		return false;
	}

	public class SettingClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			int id = v.getId();
			if (id == R.id.notifications)
			{
				notificationsAlert = ((CheckBox) v).isChecked();
			}
			if (id == R.id.screen_flash)
			{
				screenFlashAlert = ((CheckBox) v).isChecked();
			}
			if (id == R.id.vibrate)
			{
				vibrateAlert = ((CheckBox) v).isChecked();
			}
			if (id == R.id.camera_flash)
			{
				cameraFlashAlert = ((CheckBox) v).isChecked();
			}
			if (id == R.id.txt_message)
			{
				txtMessageAlert = ((CheckBox) v).isChecked();
			}
		}

	}

}
