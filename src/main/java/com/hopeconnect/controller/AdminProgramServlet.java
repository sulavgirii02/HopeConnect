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
import java.util.Objects;

/**
 * AdminProgramServlet
 * Handles CRUD operations for programs in the admin area.
 */
@WebServlet(name = "AdminProgramServlet", urlPatterns = {"/admin/programs"})
public class AdminProgramServlet extends HttpServlet {
    private final AidProgramService service = new AidProgramService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String editId = req.getParameter("edit");
        if (editId != null && !editId.trim().isEmpty()) {
            Integer parsedEditId = parsePositiveInteger(editId);
            if (parsedEditId == null) {
                com.hopeconnect.util.FlashMessageUtil.error(req, "Invalid program selected.");
                resp.sendRedirect(req.getContextPath() + "/admin/programs");
                return;
            }
            AidProgram p = new com.hopeconnect.dao.AidProgramDAO().findById(parsedEditId);
            if (p != null) {
                req.setAttribute("editProgram", p);
                req.getRequestDispatcher("/views/admin/adminProgramEdit.jsp").forward(req, resp);
                return;
            }
            com.hopeconnect.util.FlashMessageUtil.error(req, "Program not found.");
            resp.sendRedirect(req.getContextPath() + "/admin/programs");
            return;
        }
        // Load all programs and forward
        List<AidProgram> programs = new com.hopeconnect.dao.AidProgramDAO().findAll();
        req.setAttribute("programs", programs);
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
                p.setTitle(req.getParameter("title"));
                p.setSlug(req.getParameter("slug"));
                p.setDescription(req.getParameter("description"));
                p.setCategory(req.getParameter("category"));
                p.setEligibility(req.getParameter("eligibility"));
                p.setCapacity(parseInteger(req.getParameter("capacity")));
                p.setRemainingCapacity(parseInteger(req.getParameter("remainingCapacity")));
                p.setStartDate(parseDate(req.getParameter("startDate")));
                p.setEndDate(parseDate(req.getParameter("endDate")));
                p.setProgramStatus(defaultStatus(req.getParameter("programStatus")));
                String publishedParam = req.getParameter("published");
                boolean published = "1".equals(publishedParam);
                p.setPublished(published);
                if (adminId > 0) p.setCreatedBy(adminId);
                validateProgram(p);
                // create
                int id = service.create(p);
                if (id <= 0) throw new IllegalArgumentException("Failed to create program. Please try again.");
                audit.log(adminId, "CREATED_PROGRAM", "program", id, "Created program #" + id);
                com.hopeconnect.util.FlashMessageUtil.success(req, "Program created successfully.");
            } else if ("update".equals(action)) {
                Integer id = parsePositiveInteger(req.getParameter("id"));
                if (id == null) throw new IllegalArgumentException("Invalid program selected.");
                AidProgram p = new com.hopeconnect.dao.AidProgramDAO().findById(id);
                if (p == null) throw new IllegalArgumentException("Program not found.");
                String oldStatus = p.getProgramStatus();
                p.setTitle(req.getParameter("title"));
                p.setSlug(req.getParameter("slug"));
                p.setDescription(req.getParameter("description"));
                p.setCategory(req.getParameter("category"));
                p.setEligibility(req.getParameter("eligibility"));
                String publishedParam = req.getParameter("published");
                p.setPublished("1".equals(publishedParam));
                String capStr = req.getParameter("capacity");
                if (capStr != null && !capStr.trim().isEmpty()) {
                    int oldCapacity = p.getCapacity() == null ? 0 : p.getCapacity();
                    int newCapacity = Integer.parseInt(capStr);
                    int oldRemaining = p.getRemainingCapacity() == null ? oldCapacity : p.getRemainingCapacity();
                    int adjustedRemaining = Math.max(0, oldRemaining + (newCapacity - oldCapacity));
                    p.setCapacity(newCapacity);
                    p.setRemainingCapacity(adjustedRemaining);
                }
                String startStr = req.getParameter("startDate");
                if (startStr != null && !startStr.trim().isEmpty()) {
                    p.setStartDate(java.sql.Date.valueOf(startStr));
                }
                String endStr = req.getParameter("endDate");
                if (endStr != null && !endStr.trim().isEmpty()) {
                    p.setEndDate(java.sql.Date.valueOf(endStr));
                }
                p.setProgramStatus(defaultStatus(req.getParameter("programStatus")));
                validateProgram(p);
                if (!service.update(p)) throw new IllegalArgumentException("Failed to update program. Please try again.");
                audit.log(adminId, "UPDATED_PROGRAM", "program", id, "Updated program #" + id);
                if (!Objects.equals(oldStatus, p.getProgramStatus()) && "closed".equals(p.getProgramStatus())) {
                    com.hopeconnect.util.FlashMessageUtil.success(req, "Program closed successfully.");
                } else if (!Objects.equals(oldStatus, p.getProgramStatus()) && "open".equals(p.getProgramStatus())) {
                    com.hopeconnect.util.FlashMessageUtil.success(req, "Program reopened successfully.");
                } else {
                    com.hopeconnect.util.FlashMessageUtil.success(req, "Program updated successfully.");
                }
            } else if ("delete".equals(action)) {
                Integer id = parsePositiveInteger(req.getParameter("id"));
                if (id == null) throw new IllegalArgumentException("Invalid program selected.");
                if (!service.delete(id)) throw new IllegalArgumentException("Failed to delete program. Please try again.");
                audit.log(adminId, "DELETED_PROGRAM", "program", id, "Deleted program #" + id);
                com.hopeconnect.util.FlashMessageUtil.success(req, "Program deleted successfully.");
            } else if ("toggleStatus".equals(action)) {
                Integer id = parsePositiveInteger(req.getParameter("id"));
                if (id == null) throw new IllegalArgumentException("Invalid program selected.");
                boolean published = "1".equals(req.getParameter("published"));
                if (!service.togglePublished(id, published)) throw new IllegalArgumentException("Failed to update program status. Please try again.");
                audit.log(adminId, "UPDATED_PROGRAM", "program", id, "Toggled program #" + id + " to " + published);
                com.hopeconnect.util.FlashMessageUtil.success(req, published ? "Program published successfully." : "Program unpublished successfully.");
            }
        } catch (Exception e) {
            com.hopeconnect.util.FlashMessageUtil.error(req, e.getMessage() == null ? "Something went wrong while processing the program. Please try again." : e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/programs");
    }

    private Integer parseInteger(String value) {
        return value == null || value.trim().isEmpty() ? null : Integer.parseInt(value.trim());
    }
    private Integer parsePositiveInteger(String value) {
        try {
            if (value == null || value.trim().isEmpty()) return null;
            int parsed = Integer.parseInt(value.trim());
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private java.sql.Date parseDate(String value) {
        return value == null || value.trim().isEmpty() ? null : java.sql.Date.valueOf(value.trim());
    }

    private String defaultStatus(String value) {
        return value == null || value.trim().isEmpty() ? "open" : value.trim();
    }
    private void validateProgram(AidProgram p) {
        if (p.getTitle() == null || p.getTitle().trim().isEmpty()) throw new IllegalArgumentException("Program title is required.");
        if (p.getDescription() == null || p.getDescription().trim().isEmpty()) throw new IllegalArgumentException("Program description is required.");
        if (p.getCategory() == null || p.getCategory().trim().isEmpty()) throw new IllegalArgumentException("Program category is required.");
        if (p.getEligibility() == null || p.getEligibility().trim().isEmpty()) throw new IllegalArgumentException("Eligibility information is required.");
        if (p.getCapacity() == null || p.getCapacity() <= 0) throw new IllegalArgumentException("Capacity must be a positive number.");
        if (p.getRemainingCapacity() != null && p.getRemainingCapacity() < 0) throw new IllegalArgumentException("Remaining capacity cannot be negative.");
        if (p.getStartDate() != null && p.getEndDate() != null && !p.getEndDate().after(p.getStartDate())) throw new IllegalArgumentException("End date must be after start date.");
    }
}
