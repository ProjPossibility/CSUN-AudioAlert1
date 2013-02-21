package info.ss12.audioalertsystem.alert;
/**
 * interface for Alerting and Dimissing any type of Alert
 */
public interface AlertListener
{
	public void onAlert();
	public void onDismiss();
}
