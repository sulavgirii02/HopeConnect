package com.hopeconnect.controller;

import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AvailableProgramsServlet
 * Read-only published program list for logged-in users.
 */
@WebServlet(name = "AvailableProgramsServlet", urlPatterns = {"/available-programs"})
public class AvailableProgramsServlet extends HttpServlet {
    private final AidProgramDAO aidProgramDAO = new AidProgramDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if ("admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/admin/programs");
            return;
        }

        req.setAttribute("programs", aidProgramDAO.findAllPublished());
        req.getRequestDispatcher("/views/availablePrograms.jsp").forward(req, resp);
    }
}
