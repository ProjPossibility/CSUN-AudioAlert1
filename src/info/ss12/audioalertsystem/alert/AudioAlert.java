package info.ss12.audioalertsystem.alert;

public class AudioAlert extends AbstractAlert
{

	public AudioAlert()
	{
		super.setAlertType("Audio Alert");
		super.sendAlert();
	}

}
