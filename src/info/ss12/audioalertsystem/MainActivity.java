package info.ss12.audioalertsystem;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Switch;

public class MainActivity extends Activity
{
	private final String TAG = "Main Activity";
	
	
	private Switch micSwitch;
	private ButtonController buttonControl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonControl = new ButtonController();
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

}
