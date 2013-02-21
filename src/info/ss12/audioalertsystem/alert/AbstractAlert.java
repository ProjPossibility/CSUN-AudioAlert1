package info.ss12.audioalertsystem.alert;
/**
 * Abstract Alert Class
 * sets the basic type for all alerts
 */
public class AbstractAlert
{

	private String alertType;
	private AlertListener alertListener;
	/**
	 * Alert Type getter
	 */
	public String getAlertType()
	{
		return alertType;
	}
	/**
	 * Alert Type setter
	 * @param alertType
	 */
	public void setAlertType(String alertType)
	{
		this.alertType = alertType;
	}
	/**
	 * Adds an Alert Listener
	 * @param alertListener
	 */
	public void addAlertListener(AlertListener alertListener)
	{
		this.alertListener = alertListener;
	}
	/**
	 * Sends alert when triggered
	 */
	public void sendAlert()
	{
		if (alertListener != null)
		{
			alertListener.onAlert();
		}
	}

	/**
	 * dismisses the alert
	 */
	public void dismissAlert()
	{
		if (alertListener != null)
		{
			alertListener.onDismiss();
		}
	}
}
