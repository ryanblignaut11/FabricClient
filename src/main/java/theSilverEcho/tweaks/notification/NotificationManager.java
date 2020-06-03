package theSilverEcho.tweaks.notification;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager
{
	private static final LinkedBlockingQueue<Notification> notifications = new LinkedBlockingQueue<>();
	private static Notification currentNotification = null;

	public static void show(Notification notification)
	{
		notifications.add(notification);
	}

	private static void update()
	{
		if (currentNotification != null && !currentNotification.isShow())
			currentNotification = null;
		if (currentNotification == null && !notifications.isEmpty())
		{
			currentNotification = notifications.poll();
			currentNotification.show();
		}
	}

	public static void render()
	{

		if (currentNotification != null)
		{
			currentNotification.render();
		}
		update();

	}

}
