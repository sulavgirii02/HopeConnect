package com.hopeconnect.service;

import com.hopeconnect.dao.NotificationDAO;
import com.hopeconnect.model.Notification;

import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO = new NotificationDAO();

    public int notifyUser(int userId, String title, String message, String type) {
        return notificationDAO.createNotification(userId, title, message, type);
    }

    public int notifyAdmins(String title, String message, String type) {
        return notificationDAO.createNotificationForAdmins(title, message, type);
    }

    public List<Notification> getUnreadNotifications(int userId) {
        return notificationDAO.findUnreadByUserId(userId);
    }

    public List<Notification> getAllNotifications(int userId) {
        return notificationDAO.findAllByUserId(userId);
    }

    public int getUnreadCount(int userId) {
        return notificationDAO.countUnreadByUserId(userId);
    }

    public boolean markAsRead(int notificationId, int userId) {
        return notificationDAO.markRead(notificationId, userId);
    }

    public boolean markAllAsRead(int userId) {
        return notificationDAO.markAllRead(userId);
    }
}
