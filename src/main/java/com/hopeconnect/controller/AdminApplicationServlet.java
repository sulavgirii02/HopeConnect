package com.hopeconnect.controller;

import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.model.User;
import com.hopeconnect.service.ApplicationService;
import com.hopeconnect.service.AuditLogService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet controller for managing applications from the admin panel.
 *
 * <p>This servlet allows administrators to view submitted applications and
 * perform administrative actions such as approving or rejecting applications.</p>
 *
 * <p>The servlet is mapped to {@code /admin/applications}.</p>
 *
 * @author HopeConnect
 * @since 1.0
 */
@WebServlet(name = "AdminApplicationServlet", urlPatterns = {"/admin/applications"})
public class AdminApplicationServlet extends HttpServlet {

    /**
     * Service used to perform application approval and rejection operations.
     */
    private final ApplicationService service = new ApplicationService();

    /**
     * Handles GET requests for the admin applications page.
     *
     * <p>This method retrieves all applications with related user and program
     * details, stores them as a request attribute, and forwards the request to
     * the admin applications JSP view.</p>
     *
     * @param req  the HTTP request containing client request data
     * @param resp the HTTP response used to return data to the client
     * @throws ServletException if forwarding to the JSP view fails
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ApplicationDAO applicationDAO = new ApplicationDAO();

        List<Map<String, Object>> applications = applicationDAO.findAllWithDetails();

        req.setAttribute("applications", applications);
        req.getRequestDispatcher("/views/admin/adminApplications.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests for admin application actions.
     *
     * <p>This method processes form submissions for approving or rejecting an
     * application. It identifies the logged-in admin user from the session,
     * performs the requested action, writes an audit log entry, and redirects
     * back to the admin applications page.</p>
     *
     * <p>Supported actions:</p>
     * <ul>
     *   <li>{@code approve} - approves the selected application</li>
     *   <li>{@code reject} - rejects the selected application with a reason</li>
     * </ul>
     *
     * @param req  the HTTP request containing action, application ID, and reason data
     * @param resp the HTTP response used to redirect the client
     * @throws ServletException if servlet processing fails
     * @throws IOException      if redirecting the response fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("approve".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int actor = getCurrentAdminId(req);

                service.approveApplication(id, actor);

                new AuditLogService().logAction(
                        actor,
                        "APPLICATION_APPROVE",
                        "Approved application id=" + id
                );

                req.getSession().setAttribute("flash_success", "Application approved");

            } else if ("reject".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int actor = getCurrentAdminId(req);
                String reason = req.getParameter("reason");

                service.rejectApplication(id, actor, reason);

                new AuditLogService().logAction(
                        actor,
                        "APPLICATION_REJECT",
                        "Rejected application id=" + id
                );

                req.getSession().setAttribute("flash_success", "Application rejected");
            }

        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/admin/applications");
    }

    /**
     * Retrieves the ID of the currently logged-in admin user.
     *
     * <p>The method reads the {@code user} object from the HTTP session.
     * If no user is found, {@code 0} is returned as a fallback actor ID.</p>
     *
     * @param req the HTTP request containing the current session
     * @return the logged-in admin user's ID, or {@code 0} if no user exists
     */
    private int getCurrentAdminId(HttpServletRequest req) {
        User admin = (User) req.getSession().getAttribute("user");
        return admin == null ? 0 : admin.getId();
    }
}