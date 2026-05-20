package com.hopeconnect.controller;

import com.hopeconnect.dao.ContactMessageDAO;
import com.hopeconnect.model.ContactMessage;
import com.hopeconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminMessagesServlet", urlPatterns = {"/admin/messages"})
public class AdminMessagesServlet extends HttpServlet {

    private final ContactMessageDAO messageDAO = new ContactMessageDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        String viewParam = req.getParameter("view");
        if (viewParam != null && !viewParam.trim().isEmpty()) {
            ContactMessage msg = messageDAO.findById(Integer.parseInt(viewParam));
            req.setAttribute("viewMessage", msg);
        }

        List<ContactMessage> messages = messageDAO.findAll();
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/views/admin/adminMessages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        if ("markResponded".equals(action) && idStr != null) {
            messageDAO.updateStatus(Integer.parseInt(idStr), "responded");
            session.setAttribute("flash_success", "Message marked as responded");
        } else if ("markClosed".equals(action) && idStr != null) {
            messageDAO.updateStatus(Integer.parseInt(idStr), "closed");
            session.setAttribute("flash_success", "Message closed");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/messages");
    }
}
