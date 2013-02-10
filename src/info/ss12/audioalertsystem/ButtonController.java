package info.ss12.audioalertsystem;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Switch;

public class ButtonController implements OnClickListener, OnTouchListener
{
	private final String TAG = "Button Controller";
	private Activity mainActivity;
	
	public ButtonController(Activity mainActivity)
	{
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void onClick(View v)
	{
		Log.d(TAG, "On CLICK CALLED");
		handleButtonToggle(v);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
//		Log.d(TAG, "On TOUCH CALLED");
//		int eventId = event.getAction();
//
//		if (eventId == MotionEvent.ACTION_MOVE)
//		{
//			//For UI purposes
//			if(v instanceof Switch)
//			{
//				Switch temp = (Switch) v;
//				if(temp.isChecked())
//					temp.setChecked(false);
//				else
//					temp.setChecked(true);
//			}
//			handleButtonToggle(v);
//			return true;
//		}
		return false;
	}

	public void handleButtonToggle(View v)
	{
		int id = v.getId();

		
		if(id == R.id.mic_switch)
		{
			Log.d(TAG, "press");
			Switch micS = (Switch) v;
			ImageView micImage = (ImageView) mainActivity.findViewById(R.id.mic_image);
			if (micS.isChecked())
			{
				Log.d(TAG, "switch on");
				micImage.setImageBitmap(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.mic_icon_on));
				DetectorServiceHelper.startServices(mainActivity);
			}
			else
			{
				Log.d(TAG, "switch off");
				micImage.setImageBitmap(BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.mic_icon_off));
				DetectorServiceHelper.stopServices();
			}
			
		} 
		else 
		{
			Log.d("swtich button test", "default");
		}
		
		
		
		
	}

}
