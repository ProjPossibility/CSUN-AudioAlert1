package info.ss12.audioalertsystem.alert;

public class AbstractAlert {

	private String alertType;

	private AlertListener alertListener;

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public void addAlertListener(AlertListener alertListener) {
		this.alertListener = alertListener;
	}

	public void sendAlert() {
		if (alertListener != null) {
			alertListener.onAlert();
		}
	}
	
	public void dismissAlert() {
		if (alertListener != null) {
			alertListener.onDismiss();
		}
	}
}
