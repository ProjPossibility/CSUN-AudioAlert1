package info.ss12.audioalertsystem.alert;

public abstract class WeatherAlert extends AbstractAlert
{

	public WeatherAlert()
	{
		super.setAlertType("Weather Alert");
		super.sendAlert();
	}

}
