package info.ss12.audioalertsystem.notification;

import java.util.ArrayList;

import info.ss12.audioalertsystem.MainActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;



public class SMSNotification extends AbstractNotification
{
	MainActivity main;
	ArrayList<String> phoneNumbers;
	
	public SMSNotification(MainActivity main,ArrayList<String> phoneNumbers, String message) 
	{
		this.main = main;
		this.phoneNumbers = phoneNumbers;
	}
	
	@Override
	public void startNotify(){
		PendingIntent pi = PendingIntent.getActivity(main, 0,
	            new Intent(main, MainActivity.class), 0);                
	        SmsManager sms = SmsManager.getDefault();
	        
	        for (String number: phoneNumbers)
	        {
	        	sms.sendTextMessage(number, null, "http://goo.gl/maps/IbxHb", pi, null);  
	        }
	        
	            
	}
	
	@Override
	public void stopNotify(){
		
	}
	private String createMapsString(double latitude, double longitude) {
		String url = "https://maps.google.com/?q=";
		url= url + latitude +"," + longitude;
		return url;
	}

}
