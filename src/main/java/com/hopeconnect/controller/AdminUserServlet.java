package com.hopeconnect.controller;

import com.hopeconnect.util.FlashMessageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminUserServlet
 * Admin operations on users: approve registration, verify, deactivate.
 */
@WebServlet(name = "AdminUserServlet", urlPatterns = {"/admin/users"})
public class AdminUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<com.hopeconnect.model.User> users = new com.hopeconnect.dao.UserDAO().findAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/views/admin/adminUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        com.hopeconnect.dao.UserDAO dao = new com.hopeconnect.dao.UserDAO();
        com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
        try {
            int id = Integer.parseInt(req.getParameter("userId"));
            com.hopeconnect.model.User u = dao.findById(id);
            if (u == null) {
                FlashMessageUtil.error(req, "User not found.");
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }
            if (admin == null) {
                FlashMessageUtil.error(req, "Admin session not found.");
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            if (admin.getId() == u.getId()) {
                FlashMessageUtil.error(req, "You cannot change your own account status.");
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }
            if ("admin".equalsIgnoreCase(u.getRole())) {
                FlashMessageUtil.error(req, "You cannot change another admin account status.");
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }

            String newStatus;
            String successMessage;
            String failureMessage;
            String auditAction;
            String notificationTitle;
            String notificationMessage;
            String notificationType;

            switch (action == null ? "" : action) {
                case "approve":
                case "verify":
                    newStatus = "active";
                    successMessage = "User approved successfully.";
                    failureMessage = "Failed to approve user. Please try again.";
                    auditAction = "APPROVED_USER";
                    notificationTitle = "Account Approved";
                    notificationMessage = "Your HopeConnect account has been approved. You can now access all user features.";
                    notificationType = "account_approved";
                    break;
                case "activate":
                case "reactivate":
                    newStatus = "active";
                    successMessage = "User reactivated successfully.";
                    failureMessage = "Failed to reactivate user. Please try again.";
                    auditAction = "REACTIVATED_USER";
                    notificationTitle = "Account Reactivated";
                    notificationMessage = "Your HopeConnect account has been reactivated. You can now log in again.";
                    notificationType = "account_reactivated";
                    break;
                case "suspend":
                case "block":
                    newStatus = "suspended";
                    successMessage = "User suspended successfully.";
                    failureMessage = "Failed to suspend user. Please try again.";
                    auditAction = "SUSPENDED_USER";
                    notificationTitle = "Account Suspended";
                    notificationMessage = "Your HopeConnect account has been suspended. Please contact support for assistance.";
                    notificationType = "account_suspended";
                    break;
                case "deactivate":
                    newStatus = "deactivated";
                    successMessage = "User deactivated successfully.";
                    failureMessage = "Failed to deactivate user. Please try again.";
                    auditAction = "DEACTIVATED_USER";
                    notificationTitle = "Account Deactivated";
                    notificationMessage = "Your HopeConnect account has been deactivated.";
                    notificationType = "account_deactivated";
                    break;
                default:
                    FlashMessageUtil.error(req, "Invalid user action.");
                    resp.sendRedirect(req.getContextPath() + "/admin/users");
                    return;
            }

            boolean updated = dao.updateUserStatus(id, newStatus);
            if (updated) {
                new com.hopeconnect.service.AuditLogService().log(
                        admin.getId(),
                        auditAction,
                        "user",
                        id,
                        auditAction.replace('_', ' ') + ": " + u.getEmail()
                );
                new com.hopeconnect.service.NotificationService().notifyUser(
                        id,
                        notificationTitle,
                        notificationMessage,
                        notificationType
                );
                FlashMessageUtil.success(req, successMessage);
            } else {
                FlashMessageUtil.error(req, failureMessage);
            }
        } catch (NumberFormatException e) {
            FlashMessageUtil.error(req, "Invalid user id.");
        } catch (Exception e) {
            FlashMessageUtil.error(req, e.getMessage() == null ? "An error occurred." : e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
