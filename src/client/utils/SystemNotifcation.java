package client.utils;


import java.awt.*;

public class SystemNotifcation {
	private static SystemNotifcation instance;

	private TrayIcon trayIcon;

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

	public static SystemNotifcation getInstance() {
		if (instance == null) {
			instance = new SystemNotifcation();
		}

		return instance;
	}

	public void send(String caption, String message) {
		trayIcon.displayMessage(caption, message, TrayIcon.MessageType.INFO);
	}
}
