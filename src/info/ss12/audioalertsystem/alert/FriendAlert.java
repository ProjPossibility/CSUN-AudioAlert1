package info.ss12.audioalertsystem.alert;
/**
 * Sets up the Alert for nearby Friends. If a nearby friend 
 * gets an Alert the GPS picks up that you are near,
 * it will alert your phone
 */
public class FriendAlert extends AbstractAlert
{
	/**
	 * Constructor sets AlertType to Friend Alert and sets up Alert Listener
	 */
	public FriendAlert()
	{
		super.setAlertType("Friend Alert");
		super.sendAlert();
	}

}
