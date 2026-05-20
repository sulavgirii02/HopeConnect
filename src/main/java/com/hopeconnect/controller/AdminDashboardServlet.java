package com.hopeconnect.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.hopeconnect.dao.AdminDashboardDAO;

/**
 * AdminDashboardServlet
 * Computes and displays simple analytics for admin dashboard.
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            AdminDashboardDAO dashboardDAO = new AdminDashboardDAO();
            com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
            if (admin != null) {
                com.hopeconnect.service.NotificationService notificationService = new com.hopeconnect.service.NotificationService();
                req.setAttribute(
                        "recentNotifications",
                        notificationService.getAllNotifications(admin.getId()).stream().limit(5).collect(Collectors.toList())
                );
                req.setAttribute("unreadNotificationCount", notificationService.getUnreadCount(admin.getId()));
            }
            req.setAttribute("totalApplicationsThisMonth", dashboardDAO.countApplicationsThisMonth());
            Map<String, Integer> approvalTotals = dashboardDAO.getApprovalTotals();
            int approved = approvalTotals.getOrDefault("approved", 0);
            int total = approvalTotals.getOrDefault("total", 0);
            double ratio = total == 0 ? 0.0 : ((double) approved / total);
            req.setAttribute("approvalRatio", String.format("%.0f%%", ratio * 100));
            req.setAttribute("approvalRatioNum", (int) (ratio * 100));

            List<Map<String, Object>> topPrograms = dashboardDAO.findTopPrograms();
            int maxCount = topPrograms.stream().mapToInt(row -> (Integer) row.get("count")).max().orElse(1);
            req.setAttribute("topPrograms", topPrograms);
            req.setAttribute("maxProgramCount", Math.max(maxCount, 1));

            // Total registered users
            req.setAttribute("totalUsers", new com.hopeconnect.dao.UserDAO().countRegularUsers());
            com.hopeconnect.dao.UserDAO userDAO = new com.hopeconnect.dao.UserDAO();
            req.setAttribute("pendingUsers", userDAO.countUsersByStatus("pending"));
            req.setAttribute("activeUsers", userDAO.countUsersByStatus("active"));
            req.setAttribute("suspendedUsers", userDAO.countUsersByStatus("suspended"));
            req.setAttribute("deactivatedUsers", userDAO.countUsersByStatus("deactivated"));

            Map<String, Integer> programSummary = dashboardDAO.getProgramSummary();
            req.setAttribute("totalPrograms", programSummary.getOrDefault("totalPrograms", 0));
            req.setAttribute("publishedPrograms", programSummary.getOrDefault("publishedPrograms", 0));
            req.setAttribute("openPrograms", programSummary.getOrDefault("openPrograms", 0));
            req.setAttribute("closedPrograms", programSummary.getOrDefault("closedPrograms", 0));

            req.setAttribute("recentApplications", dashboardDAO.findRecentApplications());
            req.setAttribute("customerNotifications", dashboardDAO.findCustomerNotifications());

            Map<String, Integer> pendingCounts = dashboardDAO.getPendingCounts();
            req.setAttribute("pendingApplicationCount", pendingCounts.getOrDefault("pendingApplicationCount", 0));
            req.setAttribute("newMessageCount", pendingCounts.getOrDefault("newMessageCount", 0));

            // Audit logs (last 10 entries)
            com.hopeconnect.service.AuditLogService auditLogService = new com.hopeconnect.service.AuditLogService();
            java.util.List<com.hopeconnect.model.AuditLog> recentLogs = auditLogService.getRecentLogs(10);
            req.setAttribute("recentLogs", recentLogs);

        } catch (Exception e) {
            req.setAttribute("error", "Unable to load analytics.");
        }
        req.getRequestDispatcher("/views/admin/adminDashboard.jsp").forward(req, resp);
    }
}
