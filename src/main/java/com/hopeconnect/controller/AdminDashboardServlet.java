package com.hopeconnect.controller;

import com.hopeconnect.dao.DashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * AdminDashboardServlet
 * Computes and displays simple analytics for admin dashboard.
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    private final DashboardDAO dashboardDAO = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("totalUsers", dashboardDAO.countUsers());
            req.setAttribute("totalPrograms", dashboardDAO.countPrograms());
            req.setAttribute("publishedPrograms", dashboardDAO.countPublishedPrograms());
            req.setAttribute("recentPrograms", dashboardDAO.findRecentPrograms());
            req.setAttribute("totalApplicationsThisMonth", dashboardDAO.countApplicationsThisMonth());
            req.setAttribute("approvalRatio", dashboardDAO.getApprovalRatio());
            req.setAttribute("recentApplications", dashboardDAO.findRecentApplications());
        } catch (SQLException e) {
            req.setAttribute("error", "Unable to load dashboard: " + e.getMessage());
        }
        req.getRequestDispatcher("/views/admin/adminDashboard.jsp").forward(req, resp);
    }
}
