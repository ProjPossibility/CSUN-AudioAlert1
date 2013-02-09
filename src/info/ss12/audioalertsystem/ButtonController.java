package info.ss12.audioalertsystem;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ButtonController implements OnClickListener
{

	@Override
	public void onClick(View v)
	{
		int id =  v.getId();
		switch (id)
		{
		case R.id.mic_switch: Log.d("swtich button test", "press");
			
			break;
		
				
			
		default: Log.d("swtich button test", "desfault");
			break;
		}
		
	}
	
	
}
