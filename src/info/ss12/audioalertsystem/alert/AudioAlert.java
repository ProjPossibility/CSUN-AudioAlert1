package info.ss12.audioalertsystem.alert;
/**
 * Sets up the Audio Alert Type
 */
public class AudioAlert extends AbstractAlert
{
	/**
	 * Alerts user on an Audiable Alert
	 */
	public AudioAlert()
	{
		super.setAlertType("Audio Alert");
		super.sendAlert();
	}

}
