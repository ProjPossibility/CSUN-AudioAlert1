package info.ss12.audioalertsystem.alert;

public class FriendAlert extends AbstractAlert
{

	public FriendAlert()
	{
		super.setAlertType("Friend Alert");
		super.sendAlert();
	}

}
