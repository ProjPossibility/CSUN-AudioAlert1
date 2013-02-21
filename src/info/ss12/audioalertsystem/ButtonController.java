package info.ss12.audioalertsystem;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Switch;

/**
 * ButtonController used to handle the button clicks
 */
public class ButtonController implements OnClickListener, OnTouchListener,
		OnMenuItemClickListener
{

	private final String TAG = "Button Controller";
	private MainActivity mainActivity;
	private Intent intent = null;
	private Messenger messenger;

	/**
	 * ButtonController constructor
	 * @param mainActivity the MainActivity
	 */
	public ButtonController(MainActivity mainActivity)
	{
		this.mainActivity = mainActivity;
		messenger = new Messenger(mainActivity.getHandler());
	}

	/**
	 * Called when a view has been clicked.
	 */
	@Override
	public void onClick(View v)
	{
		Log.d(TAG, "On CLICK CALLED");
		handleButtonToggle(v);
	}

	/**
	 * Called when a touch event is dispatched to a view. This allows listeners
	 * to get a chance to respond before the target view.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		// Log.d(TAG, "On TOUCH CALLED");
		// int eventId = event.getAction();
		//
		// if (eventId == MotionEvent.ACTION_MOVE)
		// {
		// //For UI purposes
		// if(v instanceof Switch)
		// {
		// Switch temp = (Switch) v;
		// if(temp.isChecked())
		// temp.setChecked(false);
		// else
		// temp.setChecked(true);
		// }
		// handleButtonToggle(v);
		// return true;
		// }
		return false;
	}

	/**
	 * Toggle the notification button ON/OFF
	 * @param v the view
	 */
	public void handleButtonToggle(View v)
	{
		int id = v.getId();

		if (id == R.id.test_alert)
		{
			turnOffAllNoti();
		}
		if (id == R.id.mic_switch)
		{
			Log.d(TAG, "press");
			Switch micS = (Switch) v;
			ImageView micImage = (ImageView) mainActivity
					.findViewById(R.id.mic_image);
			if (micS.isChecked())
			{
				Log.d(TAG, "switch on");
				micImage.setImageBitmap(BitmapFactory.decodeResource(
						mainActivity.getResources(), R.drawable.mic_icon_on));
				intent = new Intent(mainActivity, LocalService.class);
				intent.putExtra("HANDLER", messenger);
				mainActivity.startService(intent);
			}
			else
			{
				Log.d(TAG, "switch off");
				micImage.setImageBitmap(BitmapFactory.decodeResource(
						mainActivity.getResources(), R.drawable.mic_icon_off));
				mainActivity.stopService(intent);
				turnOffAllNoti(); // this function call might be breaking the
									// on/off switch
			}

		}
		else
		{
			Log.d("swtich button test", "default");
		}

	}

	/**
	 * Turns off all notifications
	 */
	public void turnOffAllNoti()
	{
		Message msg = Message.obtain();
		msg.arg1 = !mainActivity.isAlarmActivated() ? 0 : 0;
		try
		{
			messenger.send(msg);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Called when a menu item has been invoked. This is the first code that is
	 * executed; if it returns true, no other callbacks will be executed.
	 */
	@Override
	public boolean onMenuItemClick(MenuItem arg0)
	{
		// open new page
		mainActivity.setContentView(R.layout.settings);
		return false;
	}

	/**
	 * Return the intent
	 * @return the intent
	 */
	public Intent getIntent()
	{
		return intent;
	}

}
