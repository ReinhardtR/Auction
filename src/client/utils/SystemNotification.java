package client.utils;


import java.awt.*;

public class SystemNotification {
	private static SystemNotification instance;

	private TrayIcon trayIcon;

public class SystemNotifcation {
	private static SystemNotifcation instance;
	private final TrayIcon trayIcon;

	// Initialize necessary classes, with options,
	// that allows the program to send system notifications.
	private SystemNotifcation() {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/resources/a-logo.png"));

		trayIcon = new TrayIcon(image, "Soft Warehouse");
		trayIcon.setImageAutoSize(true);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static SystemNotification getInstance() {
		if (instance == null) {
			instance = new SystemNotification();
		}

		return instance;
	}

	public void send(String caption, String message) {
		trayIcon.displayMessage(caption, message, TrayIcon.MessageType.INFO);
	}
}
