package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.alert.AlertListener;
import info.ss12.audioalertsystem.alert.AudioAlert;
import info.ss12.audioalertsystem.notification.NotificationListener;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class LocalService extends Service implements AlertListener,
		NotificationListener {

	/**
	 * Implementation for AlertListener.
	 */
	@Override
	public void onAlert() {
		// TODO Auto-generated method stub
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

		AudioAlert mAudioAlert = new AudioAlert();
		mAudioAlert.addAlertListener(this);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Toast.makeText(this, "task perform in service", 300).show();
		ServiceThread td = new ServiceThread();
		td.start();
		return super.onStartCommand(intent, flags, startId);
	}

	private class ServiceThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				sleep(70 * 1000);

				handler.sendEmptyMessage(0);
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// showAppNotification();
		}
	};

}
