package com.hopeconnect.controller;

import com.hopeconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * UserDashboardServlet
 * Displays a simple first-milestone dashboard for a logged-in user.
 */
@WebServlet(name = "UserDashboardServlet", urlPatterns = {"/dashboard"})
public class UserDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if ("admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            return;
        }
        req.setAttribute("programs", new com.hopeconnect.dao.AidProgramDAO().findAllPublished());
        req.getRequestDispatcher("/views/userDashboard.jsp").forward(req, resp);
    }
}