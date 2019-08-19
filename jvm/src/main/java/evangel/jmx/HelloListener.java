package evangel.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

import evangel.jmx.service.JmxHello;

public class HelloListener implements NotificationListener {
	@Override
	public void handleNotification(Notification notification, Object handback) {
		if (handback instanceof JmxHello) {
			JmxHello hello = (JmxHello) handback;
			hello.printHello(notification.getMessage());
		}
	}
}