package info.ss12.audioalertsystem.alert;
/**
 * Weather Alert Class
 */
public abstract class WeatherAlert extends AbstractAlert
{
/**
 * Weather Alert Constructor, sets the alert type.
 */
	public WeatherAlert()
	{
		super.setAlertType("Weather Alert");
		super.sendAlert();
	}

}
