package com.hopeconnect.controller;

import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * ApplicationHistoryServlet
 * Shows application history for the logged-in user.
 */
@WebServlet(name = "ApplicationHistoryServlet", urlPatterns = {"/my-applications"})
public class ApplicationHistoryServlet extends HttpServlet {

    /**
     * Loads all applications for the current user.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        List<com.hopeconnect.model.Application> apps = new ApplicationDAO().findByUserId(user.getId());
        req.setAttribute("applications", apps);
        req.getRequestDispatcher("/views/applicationHistory.jsp").forward(req, resp);
    }
}
