package t5750.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

import t5750.jmx.service.JmxHello;

public class HelloListener implements NotificationListener {
	@Override
	public void handleNotification(Notification notification, Object handback) {
		if (handback instanceof JmxHello) {
			JmxHello hello = (JmxHello) handback;
			hello.printHello(notification.getMessage());
		}
	}
}