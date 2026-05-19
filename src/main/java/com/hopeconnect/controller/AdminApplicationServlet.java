package com.hopeconnect.controller;

import com.hopeconnect.service.ApplicationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AdminApplicationServlet
 * Admin operations on applications (approve/reject and list).
 */
@WebServlet(name = "AdminApplicationServlet", urlPatterns = {"/admin/applications"})
public class AdminApplicationServlet extends HttpServlet {
    private final ApplicationService service = new ApplicationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = normalize(req.getParameter("status"));
        if ("all".equals(status)) status = null;
        Integer programId = parseInteger(req.getParameter("programId"));
        int pageSize = 10;
        int currentPage = parsePositiveInt(req.getParameter("page"), 1);
        com.hopeconnect.dao.ApplicationDAO dao = new com.hopeconnect.dao.ApplicationDAO();
        int totalRecords = dao.countApplications(status, programId);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages > 0 && currentPage > totalPages) currentPage = totalPages;
        java.util.List<java.util.Map<String,Object>> apps = dao.findApplicationsPaginated(status, programId, pageSize, (currentPage - 1) * pageSize);
        req.setAttribute("applications", apps);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("selectedStatus", status == null ? "all" : status);
        req.setAttribute("selectedProgramId", programId);
        req.setAttribute("programs", new com.hopeconnect.dao.AidProgramDAO().findAll());
        req.getRequestDispatcher("/views/admin/adminApplications.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("approve".equals(action)) {
                Integer id = parsePositiveInteger(req.getParameter("id"));
                if (id == null) throw new IllegalArgumentException("Invalid application selected.");
                com.hopeconnect.model.Application existing = new com.hopeconnect.dao.ApplicationDAO().findById(id);
                if (existing == null) throw new IllegalArgumentException("Application not found.");
                if ("approved".equals(existing.getStatus())) throw new IllegalArgumentException("This application has already been approved.");
                com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
                int actor = admin == null ? 0 : admin.getId();
                service.approveApplication(id, actor);
                new com.hopeconnect.service.NotificationService().notifyAdmins(
                        "Application Status Updated",
                        "Application #" + id + " was approved.",
                        "admin_action"
                );
                new com.hopeconnect.service.AuditLogService().log(actor, "APPROVED_APPLICATION", "application", id, "Approved application #" + id);
                com.hopeconnect.util.FlashMessageUtil.success(req, "Application approved successfully.");
            } else if ("reject".equals(action)) {
                Integer id = parsePositiveInteger(req.getParameter("id"));
                if (id == null) throw new IllegalArgumentException("Invalid application selected.");
                com.hopeconnect.model.Application existing = new com.hopeconnect.dao.ApplicationDAO().findById(id);
                if (existing == null) throw new IllegalArgumentException("Application not found.");
                if ("rejected".equals(existing.getStatus())) throw new IllegalArgumentException("This application has already been rejected.");
                com.hopeconnect.model.User admin = (com.hopeconnect.model.User) req.getSession().getAttribute("user");
                int actor = admin == null ? 0 : admin.getId();
                String reason = req.getParameter("reason");
                service.rejectApplication(id, actor, reason);
                new com.hopeconnect.service.NotificationService().notifyAdmins(
                        "Application Status Updated",
                        "Application #" + id + " was rejected.",
                        "admin_action"
                );
                new com.hopeconnect.service.AuditLogService().log(actor, "REJECTED_APPLICATION", "application", id, "Rejected application #" + id);
                com.hopeconnect.util.FlashMessageUtil.success(req, "Application rejected successfully.");
            }
        } catch (Exception e) {
            com.hopeconnect.util.FlashMessageUtil.error(req, e.getMessage() == null ? "Something went wrong while processing the application. Please try again." : e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/applications");
    }
    private String normalize(String value){ if(value==null) return null; String v=value.trim(); return v.isEmpty()?null:v; }
    private Integer parseInteger(String value){ try{return value==null||value.trim().isEmpty()?null:Integer.parseInt(value);}catch(Exception e){return null;} }
    private Integer parsePositiveInteger(String value){ try{int n=Integer.parseInt(value); return n>0?n:null;}catch(Exception e){return null;} }
    private int parsePositiveInt(String value,int fallback){ try{int n=Integer.parseInt(value); return n>0?n:fallback;}catch(Exception e){return fallback;} }
}
