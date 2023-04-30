package umu.tds.persistence;

import java.util.List;

import umu.tds.model.Notification;

public interface IAdaptadorNotificationDAO {
	public void createNotification(Notification n);
	public Notification readNotification(int notificationCode);
	public void deleteNotification(Notification n);
	public List<Notification> readAllNotifications();
}
