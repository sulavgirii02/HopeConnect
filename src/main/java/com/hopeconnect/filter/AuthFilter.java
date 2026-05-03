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
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        // Example role check for /admin
        String path = req.getRequestURI();
        User user = (User) session.getAttribute("user");
        if (path.startsWith(req.getContextPath() + "/admin") && (user == null || !"admin".equals(user.getRole()))) {
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}

