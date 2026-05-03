package com.hopeconnect.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminUserServlet
 * Admin operations on users (list, verify, deactivate).
 */
@WebServlet(name = "AdminUserServlet", urlPatterns = {"/admin/users"})
public class AdminUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<com.hopeconnect.model.User> users = new com.hopeconnect.dao.UserDAO().findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/views/admin/adminUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("verify".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                com.hopeconnect.dao.UserDAO dao = new com.hopeconnect.dao.UserDAO();
                com.hopeconnect.model.User u = dao.findById(id);
                if (u == null) throw new IllegalArgumentException("User not found");
                u.setStatus("active");
                dao.update(u);
                com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
                int adminId = admin == null ? 0 : admin.getId();
                new com.hopeconnect.service.AuditLogService().logAction(adminId, "USER_VERIFY", "Verified user id=" + id);
                req.getSession().setAttribute("flash_success", "User verified");
            } else if ("deactivate".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                com.hopeconnect.dao.UserDAO dao = new com.hopeconnect.dao.UserDAO();
                com.hopeconnect.model.User u = dao.findById(id);
                if (u == null) throw new IllegalArgumentException("User not found");
                u.setStatus("suspended");
                dao.update(u);
                com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
                int adminId = admin == null ? 0 : admin.getId();
                new com.hopeconnect.service.AuditLogService().logAction(adminId, "USER_DEACTIVATE", "Deactivated user id=" + id);
                req.getSession().setAttribute("flash_success", "User deactivated");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
