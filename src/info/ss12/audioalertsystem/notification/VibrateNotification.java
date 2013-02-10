package info.ss12.audioalertsystem.notification;

import android.content.Context;
import android.os.Vibrator;

public class VibrateNotification extends AbstractNotification {
	
	Vibrator vibrator;
	
	public VibrateNotification(Context context) {
		vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	@Override
	public void startNotify(){
		vibrator.vibrate(new long[]{0, 1000}, 1);
	}
	
	@Override
	public void stopNotify(){
		vibrator.cancel();
	}

}
