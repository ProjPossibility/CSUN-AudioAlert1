package info.ss12.audioalertsystem;

import info.ss12.audioalertsystem.alert.AlertListener;
import info.ss12.audioalertsystem.alert.AudioAlert;
import info.ss12.audioalertsystem.notification.NotificationListener;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.format.Time;
import android.widget.Toast;

/**
 * The LocalService class. Used to manage notifications, alerts and background
 * service
 */
public class LocalService extends Service implements AlertListener,
		NotificationListener
{
	/** The RecorderThread class. Used to process audio frequencies */
	public RecorderThread recorder;
	/** The DetectorThread class runs the Recorder processes */
	public DetectorThread detector;
	/** Sets up the Audio Alert Type */
	private AudioAlert mAudioAlert;
	/** Call the handler in Main Activity */
	private Messenger messenger;

	/**
	 * Implementation for AlertListener.
	 */
	@Override
	public void onAlert()
	{
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
	 * On dismiss of service
	 */
	@Override
	public void onDismiss()
	{
		Message msg = Message.obtain();
		msg.arg1 = 0;
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
	public void onNotification()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Return the communication channel to the service. May return null if
	 * clients can not bind to the service. The returned IBinder is usually for
	 * a complex interface that has been described using aidl.
	 */
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	/**
	 * Called by the system when the service is first created. Do not call this
	 * method directly.
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		//Toast.makeText(this, "Service Created", 300).show();
	}

	/**
	 * Called by the system to notify a Service that it is no longer used and is
	 * being removed. The service should clean up any resources it holds
	 * (threads, registered receivers, etc) at this point. Upon return, there
	 * will be no more calls in to this Service object and it is effectively
	 * dead. Do not call this method directly.
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this, "Service Destroy", 300).show();
		recorder.stopRecording();
		detector.stopDetection();
	}

	/**
	 * This is called when the overall system is running low on memory, and
	 * would like actively running process to try to tighten their belt. While
	 * the exact point at which this will be called is not defined, generally it
	 * will happen around the time all background process have been killed, that
	 * is before reaching the point of killing processes hosting service and
	 * foreground UI that we would like to avoid killing.
	 * 
	 * Applications that want to be nice can implement this method to release
	 * any caches or other unnecessary resources they may be holding on to. The
	 * system will perform a gc for you after returning from this method.
	 */
	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		// Toast.makeText(this, "Service LowMemory", 300).show();

	}

	/**
	 * Starting service
	 */
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		 Toast.makeText(this, "Service start", 300).show();
	}

	/**
	 * Called by the system every time a client explicitly starts the service by
	 * calling startService(Intent), providing the arguments it supplied and a
	 * unique integer token representing the start request. Do not call this
	 * method directly.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

		// Toast.makeText(this, "task perform in service", 300).show();
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

}
