package com.hopeconnect.controller;

import com.hopeconnect.dao.ContactMessageDAO;
import com.hopeconnect.model.ContactMessage;
import com.hopeconnect.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ContactServlet
 * Handles public contact form display and submission.
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {
    private final ContactMessageDAO contactMessageDAO = new ContactMessageDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = clean(req.getParameter("name"));
        String email = clean(req.getParameter("email"));
        String subject = clean(req.getParameter("subject"));
        String message = clean(req.getParameter("message"));

        boolean hasError = false;
        if (!ValidationUtil.isValidName(name)) {
            req.setAttribute("errorName", "Name must contain letters and spaces only");
            hasError = true;
        }
        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("errorEmail", "Enter a valid email address");
            hasError = true;
        }
        if (!ValidationUtil.hasTextWithin(subject, 255)) {
            req.setAttribute("errorSubject", "Subject is required and must be 255 characters or less");
            hasError = true;
        }
        if (!ValidationUtil.hasTextWithin(message, 2000)) {
            req.setAttribute("errorMessage", "Message is required and must be 2000 characters or less");
            hasError = true;
        }

        req.setAttribute("formName", name);
        req.setAttribute("formEmail", email);
        req.setAttribute("formSubject", subject);
        req.setAttribute("formMessage", message);

        if (hasError) {
            req.getRequestDispatcher("/views/contact.jsp").forward(req, resp);
            return;
        }

        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setSubject(subject);
        contactMessage.setMessage(message);

        if (contactMessageDAO.insert(contactMessage)) {
            req.setAttribute("success", "Your message has been sent successfully.");
            req.setAttribute("formName", "");
            req.setAttribute("formEmail", "");
            req.setAttribute("formSubject", "");
            req.setAttribute("formMessage", "");
        } else {
            req.setAttribute("error", "Unable to send your message. Please try again later.");
        }

        req.getRequestDispatcher("/views/contact.jsp").forward(req, resp);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
