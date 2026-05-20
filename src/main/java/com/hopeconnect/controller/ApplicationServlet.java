package com.hopeconnect.controller;

import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.model.Application;
import com.hopeconnect.model.User;
import com.hopeconnect.service.ApplicationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ApplicationServlet
 * Handles user applications to programs.
 */
@WebServlet(name = "ApplicationServlet", urlPatterns = {"/apply"})
public class ApplicationServlet extends HttpServlet {

    /**
     * Loads program details and checks if user already applied.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "Please log in before applying for a program.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if ("admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }
        Integer programId = parseProgramId(req.getParameter("programId"));
        if (programId == null) {
            com.hopeconnect.util.FlashMessageUtil.error(req, "Invalid program selected.");
            resp.sendRedirect(req.getContextPath() + "/programs");
            return;
        }
        AidProgram program = new AidProgramDAO().findById(programId);
        if (program == null) {
            com.hopeconnect.util.FlashMessageUtil.error(req, "Program not found.");
            resp.sendRedirect(req.getContextPath() + "/programs");
            return;
        }
        if (!program.isPublished()) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "This program is not currently available.");
            resp.sendRedirect(req.getContextPath() + "/programs");
            return;
        }
        if (!"open".equals(program.getProgramStatus())) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "This program is currently closed.");
            resp.sendRedirect(req.getContextPath() + "/programs");
            return;
        }
        if (program.getRemainingCapacity() != null && program.getRemainingCapacity() <= 0) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "This program has reached full capacity.");
            resp.sendRedirect(req.getContextPath() + "/programs");
            return;
        }
        req.setAttribute("program", program);
        Application existing = new ApplicationDAO().findByUserAndProgram(user.getId(), programId);
        if (existing != null) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "You have already applied for this program.");
            resp.sendRedirect(req.getContextPath() + "/my-applications");
            return;
        }
        req.getRequestDispatcher("/views/applyForm.jsp").forward(req, resp);
    }

    /**
     * Submits a new application with validation and notification.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "Please log in before applying for a program.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if ("admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }
        Integer programId = parseProgramId(req.getParameter("programId"));
        String notes = req.getParameter("notes");
        try {
            if (programId == null) throw new IllegalArgumentException("Invalid program selected.");
            boolean autoClosed = new ApplicationService().submitApplication(user.getId(), programId, notes);
            com.hopeconnect.util.FlashMessageUtil.success(req,
                    autoClosed
                            ? "Application submitted successfully. This program has now reached full capacity."
                            : "Application submitted successfully.");
            resp.sendRedirect(req.getContextPath() + "/my-applications");
        } catch (IllegalArgumentException e) {
            String msg = normalizeApplicationMessage(e.getMessage());
            req.setAttribute("error", msg);
            if (programId != null) {
                AidProgram program = new AidProgramDAO().findById(programId);
                req.setAttribute("program", program);
                if (msg.startsWith("You have already applied")) {
                    req.setAttribute("alreadyApplied", true);
                }
            }
            req.getRequestDispatcher("/views/applyForm.jsp").forward(req, resp);
        }
    }
    private String normalizeApplicationMessage(String message) {
        if ("You have already applied to this program".equals(message)) return "You have already applied for this program.";
        if (message != null && message.contains("quota full")) return "This program has reached full capacity.";
        if ("Program not found or no longer available".equals(message)) return "This program is no longer available.";
        if (message != null && message.startsWith("Database error")) return "Failed to submit application. Please try again.";
        return message == null ? "Failed to submit application. Please try again." : message;
    }

    private Integer parseProgramId(String programIdStr) {
        if (programIdStr == null || programIdStr.trim().isEmpty()) {
            return null;
        }
        try {
            int programId = Integer.parseInt(programIdStr.trim());
            return programId > 0 ? programId : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
