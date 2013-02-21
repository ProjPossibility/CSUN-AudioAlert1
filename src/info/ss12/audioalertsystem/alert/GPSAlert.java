package info.ss12.audioalertsystem.alert;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
/**
 * Grabs GPS Coordinates using the users GPS feature on their phone 
 */
public class GPSAlert extends AbstractAlert
{
	Context context;
	private LocationListener mlocListener;
	private LocationManager mlocManager;
	public static Double CUR_LATITUDE = null;
	public static Double CUR_LONGITUDE = null;
	/**
	 * Sets Alert Type and starts GPS
	 */
	public GPSAlert(Context context)
	{
		this.context = context;
		super.setAlertType("GPS Alert");
		super.sendAlert();
		startGps();
	}
	/**
	 * Starts GPS which sends coordinates every 10 seconds
	 */
	public void startGps()
	{
		mlocManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListeners();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
				0, mlocListener);
		mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				10000, 0, mlocListener);
	}
	/**
	 * Stops the GPS updates
	 */
	public void stopGps()
	{
		mlocManager.removeUpdates(mlocListener);
	}
	/**
	 * Location Listener detects change in coordinates 
	 * and  submits location coordinates
	 */
	public class MyLocationListeners implements LocationListener
	{

		@Override
		public void onLocationChanged(Location loc)
		{
			Log.d("GPS ALERT", "LocationChanged");
			CUR_LATITUDE = new Double(loc.getLatitude());
			CUR_LONGITUDE = new Double(loc.getLongitude());
			// Toast.makeText(context, CUR_LATITUDE + " "+ CUR_LONGITUDE,
			// Toast.LENGTH_SHORT).show();
		}

		/**
		 * Alerts use if GPS is disabled
		 */
		@Override
		public void onProviderDisabled(String provider)
		{
			Toast.makeText(context, "Gps Disabled", Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * Alerts use if GPS is enabled
		 */
		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText(context, "Gps Enabled", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{

		}

	}
}
