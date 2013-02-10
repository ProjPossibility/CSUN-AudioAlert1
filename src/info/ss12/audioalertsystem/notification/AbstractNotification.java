package info.ss12.audioalertsystem.notification;

public abstract class AbstractNotification {
	
	private NotificationListener notificationListener; 
	
	public void addNotificationListener(NotificationListener notificationListener){
		this.notificationListener = notificationListener;		
	}
	
	protected void startNotify(){
		
	}
	
	protected void stopNotify(){
		
	}
	
}
