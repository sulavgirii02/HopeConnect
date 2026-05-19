package com.hopeconnect.controller;

import com.hopeconnect.model.User;
import com.hopeconnect.service.NotificationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * NotificationServlet
 * Shows and updates user notifications.
 */
@WebServlet(name = "NotificationServlet", urlPatterns = {"/notifications"})
public class NotificationServlet extends HttpServlet {

    /**
     * Loads unread notifications for the logged-in user.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        NotificationService service = new NotificationService();
        List<com.hopeconnect.model.Notification> notifications = service.getAllNotifications(user.getId());
        int unreadCount = service.getUnreadCount(user.getId());
        req.setAttribute("notifications", notifications);
        req.setAttribute("unreadCount", unreadCount);
        session.setAttribute("unreadCount", unreadCount);
        req.getRequestDispatcher("/views/notifications.jsp").forward(req, resp);
    }

    /**
     * Marks a notification as read.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");
        NotificationService service = new NotificationService();
        if ("markRead".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            service.markAsRead(id, user.getId());
        } else if ("markAllRead".equals(action)) {
            service.markAllAsRead(user.getId());
        }
        resp.sendRedirect(req.getContextPath() + "/notifications");
    }
}
