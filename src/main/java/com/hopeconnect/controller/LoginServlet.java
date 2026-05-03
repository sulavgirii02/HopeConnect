package com.hopeconnect.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginServlet
 * Placeholder servlet for handling login requests.
 * Currently simple: renders a login JSP and handles POST to set a session attribute.
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
            session.invalidate();
            session = req.getSession(true);
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            Cookie emailCookie = new Cookie("last_login_email", user.getEmail());
            emailCookie.setHttpOnly(true);
            emailCookie.setMaxAge(7 * 24 * 60 * 60);
            emailCookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            resp.addCookie(emailCookie);

            if ("admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("formEmail", email);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
