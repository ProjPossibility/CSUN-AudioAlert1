package info.ss12.audioalertsystem.notification;

import info.ss12.audioalertsystem.MainActivity;
import info.ss12.audioalertsystem.alert.GPSAlert;

import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * The SMS Notification class to send Text Messages
 */
public class SMSNotification extends AbstractNotification
{
	/** The Main Activity */
	MainActivity main;
	/** The phone numbers */
	List<String> phoneNumbers;

	/**
	 * SMS Notification constructor
	 * 
	 * @param main
	 *            set the MainAcctivity
	 */
	public SMSNotification(MainActivity main)
	{
		this.main = main;
	}

	/**
	 * Return list of phone numbers
	 * 
	 * @return the phone numbers
	 */
	public List<String> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	/**
	 * Set the phone numbers
	 * 
	 * @param phoneNumbers
	 *            the phone numbers
	 */
	public void setPhoneNumbers(List<String> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}

	/**
	 * Start the SMS notification
	 */
	@Override
	public void startNotify()
	{
		if (GPSAlert.CUR_LATITUDE == null || GPSAlert.CUR_LONGITUDE == null)
			return;

		PendingIntent pi = PendingIntent.getActivity(main, 0, new Intent(main,
				MainActivity.class), 0);

		SmsManager sms = SmsManager.getDefault();

		for (String number : phoneNumbers)
		{
			sms.sendTextMessage(
					number,
					null,
					"An alert was detected around me. Click for location: "
							+ createMapsString(GPSAlert.CUR_LATITUDE,
									GPSAlert.CUR_LONGITUDE), pi, null);
		}

	}

	/**
	 * Stop the SMS notification
	 */
	@Override
	public void stopNotify()
	{

	}

	/**
	 * Create google map link with coordinates
	 * 
	 * @param latitude
	 *            double
	 * @param longitude
	 *            double
	 * @return
	 */
	private String createMapsString(double latitude, double longitude)
	{
		String url = "https://maps.google.com/?q=";
		url = url + latitude + "," + longitude;
		return url;
	}

}
