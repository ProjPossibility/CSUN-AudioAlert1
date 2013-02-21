package info.ss12.audioalertsystem.notification;

/**
 * Abstract class for notifications
 */
public abstract class AbstractNotification
{

	/** The notification listener */
	private NotificationListener notificationListener;

	/**
	 * Set the notification listener
	 * 
	 * @param notificationListener
	 *            set the NotificationListener
	 */
	public void addNotificationListener(
			NotificationListener notificationListener)
	{
		this.notificationListener = notificationListener;
	}

	/**
	 * Start the notification
	 */
	protected void startNotify()
	{

	}

	/**
	 * Stop the notification
	 */
	protected void stopNotify()
	{

	}

}
