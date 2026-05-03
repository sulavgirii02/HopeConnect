package com.hopeconnect.controller;

import com.hopeconnect.model.AidProgram;
import com.hopeconnect.service.AidProgramService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminProgramServlet
 * Handles CRUD operations for programs in the admin area.
 */
@WebServlet(name = "AdminProgramServlet", urlPatterns = {"/admin/programs"})
public class AdminProgramServlet extends HttpServlet {
    private final AidProgramService service = new AidProgramService();
    private final com.hopeconnect.dao.AidProgramDAO aidProgramDAO = new com.hopeconnect.dao.AidProgramDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load all programs and forward
        List<com.hopeconnect.model.AidProgram> programs = aidProgramDAO.findAll();
        req.setAttribute("programs", programs);
        String editId = req.getParameter("editId");
        if (editId != null && !editId.trim().isEmpty()) {
            try {
                AidProgram editProgram = aidProgramDAO.findById(Integer.parseInt(editId));
                if (editProgram != null) req.setAttribute("editProgram", editProgram);
                else req.setAttribute("error", "Program not found");
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid program id");
            }
        }
        req.getRequestDispatcher("/views/admin/adminPrograms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
            int adminId = admin == null ? 0 : admin.getId();
            com.hopeconnect.service.AuditLogService audit = new com.hopeconnect.service.AuditLogService();
            if ("create".equals(action)) {
                AidProgram p = new AidProgram();
                p.setTitle(clean(req.getParameter("title")));
                p.setSlug(clean(req.getParameter("slug")).toLowerCase());
                p.setDescription(clean(req.getParameter("description")));
                p.setCategory(clean(req.getParameter("category")));
                p.setEligibility(clean(req.getParameter("eligibility")));
                String publishedParam = req.getParameter("published");
                boolean published = "1".equals(publishedParam);
                p.setPublished(published);
                if (adminId > 0) p.setCreatedBy(adminId);
                // create
                int id = service.create(p);
                audit.logAction(adminId, "PROGRAM_CREATE", "Created program id=" + id);
                req.getSession().setAttribute("flash_success", "Program created (id=" + id + ")");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                AidProgram p = aidProgramDAO.findById(id);
                if (p == null) throw new IllegalArgumentException("Program not found");
                p.setTitle(clean(req.getParameter("title")));
                p.setSlug(clean(req.getParameter("slug")).toLowerCase());
                p.setDescription(clean(req.getParameter("description")));
                p.setCategory(clean(req.getParameter("category")));
                p.setEligibility(clean(req.getParameter("eligibility")));
                p.setPublished("1".equals(req.getParameter("published")));
                service.update(p);
                audit.logAction(adminId, "PROGRAM_UPDATE", "Updated program id=" + id);
                req.getSession().setAttribute("flash_success", "Program updated");
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                service.delete(id);
                audit.logAction(adminId, "PROGRAM_DELETE", "Deleted program id=" + id);
                req.getSession().setAttribute("flash_success", "Program deleted");
            } else if ("toggleStatus".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean published = "1".equals(req.getParameter("published"));
                service.togglePublished(id, published);
                audit.logAction(adminId, "PROGRAM_TOGGLE", "Toggled program id=" + id + " to " + published);
                req.getSession().setAttribute("flash_success", "Program status changed");
            } else {
                throw new IllegalArgumentException("Invalid admin action");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            req.getSession().setAttribute("flash_error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/programs");
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
