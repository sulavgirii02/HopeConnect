package com.hopeconnect.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet
 * Handles user login. Verifies credentials, sets session, redirects by role.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        com.hopeconnect.service.UserService service = new com.hopeconnect.service.UserService();
        try {
            com.hopeconnect.model.User user = service.loginUser(email, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            // Audit log on admin login
            if ("admin".equalsIgnoreCase(user.getRole())) {
                new com.hopeconnect.service.AuditLogService().log(
                        user.getId(),
                        "ADMIN_LOGIN",
                        "user",
                        user.getId(),
                        "Admin logged in: " + user.getEmail()
                );
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
