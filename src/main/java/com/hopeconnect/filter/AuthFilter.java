package com.hopeconnect.filter;

import com.hopeconnect.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AuthFilter
 * Protects routes that require authentication and handles role checks.
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            if (req.getRequestURI().startsWith(req.getContextPath() + "/apply")) {
                com.hopeconnect.util.FlashMessageUtil.warning(req, "Please log in before applying for a program.");
            } else if (req.getRequestURI().startsWith(req.getContextPath() + "/wishlist")) {
                com.hopeconnect.util.FlashMessageUtil.warning(req, "Please log in to save programs to your wishlist.");
            }
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        // Example role check for /admin
        String path = req.getRequestURI();
        User user = (User) session.getAttribute("user");
        User currentUser = user == null ? null : new com.hopeconnect.dao.UserDAO().findById(user.getId());
        if (currentUser == null || !"active".equalsIgnoreCase(currentUser.getStatus())) {
            String message = "Please log in again.";
            if (currentUser != null) {
                if ("pending".equalsIgnoreCase(currentUser.getStatus())) {
                    message = "Your account is pending admin approval.";
                } else if ("suspended".equalsIgnoreCase(currentUser.getStatus())) {
                    message = "Your account has been suspended. Please contact support.";
                } else if ("deactivated".equalsIgnoreCase(currentUser.getStatus())) {
                    message = "Your account has been deactivated. Please contact support.";
                }
            }
            session.invalidate();
            com.hopeconnect.util.FlashMessageUtil.warning(req, message);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        user = currentUser;
        session.setAttribute("user", currentUser);
        if (user != null) {
            int unreadCount = new com.hopeconnect.service.NotificationService().getUnreadCount(user.getId());
            session.setAttribute("unreadCount", unreadCount);
            req.setAttribute("unreadNotificationCount", unreadCount);
        }
        if (path.startsWith(req.getContextPath() + "/admin") && (user == null || !"admin".equals(user.getRole()))) {
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
