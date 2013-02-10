package info.ss12.audioalertsystem.notification;

import info.ss12.audioalertsystem.MainActivity;
import info.ss12.audioalertsystem.alert.GPSAlert;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

public class SMSNotification extends AbstractNotification
{
	MainActivity main;
	ArrayList<String> phoneNumbers;

	public SMSNotification(MainActivity main, ArrayList<String> phoneNumbers)
	{
		this.main = main;
		this.phoneNumbers = phoneNumbers;
	}

	@Override
	public void startNotify()
	{
		if(GPSAlert.CUR_LATITUDE == null || GPSAlert.CUR_LONGITUDE == null)
			return;
		
		PendingIntent pi = PendingIntent.getActivity(main, 0, new Intent(main,
				MainActivity.class), 0);

		SmsManager sms = SmsManager.getDefault();

		for (String number : phoneNumbers)
		{
			sms.sendTextMessage(number, null, createMapsString(GPSAlert.CUR_LATITUDE, GPSAlert.CUR_LONGITUDE), pi,
					null);
		}

	}

	@Override
	public void stopNotify()
	{

	}

	private String createMapsString(double latitude, double longitude)
	{
		String url = "https://maps.google.com/?q=";
		url = url + latitude + "," + longitude;
		return url;
	}

}
