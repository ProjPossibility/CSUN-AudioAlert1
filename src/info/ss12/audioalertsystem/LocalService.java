package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.alert.AlertListener;
import info.ss12.audioalertsystem.alert.AudioAlert;
import info.ss12.audioalertsystem.notification.NotificationListener;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class LocalService extends Service implements AlertListener,
		NotificationListener {
	public RecorderThread recorder;
	public DetectorThread detector;
	private AudioAlert mAudioAlert;
	private Messenger messenger; //This calls handler in Main Activity
	/**
	 * Implementation for AlertListener.
	 */
	@Override
	public void onAlert() {
		Message msg = Message.obtain();
		msg.arg1 = 1;
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
	 * Implementation for NotificationListener.
	 */
	@Override
	public void onNotification() {
		// TODO Auto-generated method stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Service Created", 300).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Destroy", 300).show();
		recorder.stopRecording();
		detector.stopDetection();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Toast.makeText(this, "Service LowMemory", 300).show();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service start", 300).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Toast.makeText(this, "task perform in service", 300).show();
	    Bundle extra = intent.getExtras();
	    messenger = (Messenger) extra.get("HANDLER");
		mAudioAlert = new AudioAlert();
		mAudioAlert.addAlertListener(this);
		recorder = new RecorderThread();
		recorder.start();
		detector = new DetectorThread(recorder, mAudioAlert);
		detector.start();
		return super.onStartCommand(intent, flags, startId);
	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// showAppNotification();
		}
	};

}
