package com.hopeconnect.controller;

import com.hopeconnect.model.User;
import com.hopeconnect.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegisterServlet
 * Handles user registration requests.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String ageStr = req.getParameter("age");

        boolean hasError = false;
        if (!com.hopeconnect.util.ValidationUtil.isValidName(fullName)) {
            req.setAttribute("errorFullName", "Name must contain letters and spaces only");
            hasError = true;
        }
        if (!com.hopeconnect.util.ValidationUtil.isValidEmail(email)) {
            req.setAttribute("errorEmail", "Invalid email format");
            hasError = true;
        }
        if (!com.hopeconnect.util.ValidationUtil.isValidPhone(phone)) {
            req.setAttribute("errorPhone", "Invalid phone format");
            hasError = true;
        }
        Integer age = null;
        if (ageStr == null || ageStr.trim().isEmpty()) {
            req.setAttribute("errorAge", "Age is required");
            hasError = true;
        } else {
            try {
                age = Integer.parseInt(ageStr);
                if (!com.hopeconnect.util.ValidationUtil.isValidAge(age)) {
                    req.setAttribute("errorAge", "Age must be between 1 and 120");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("errorAge", "Age must be numeric");
                hasError = true;
            }
        }
        if (password == null || password.length() < 6) {
            req.setAttribute("errorPassword", "Password must be at least 6 characters");
            hasError = true;
        }
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            req.setAttribute("errorConfirmPassword", "Passwords do not match");
            hasError = true;
        }
        if (hasError) {
            req.setAttribute("formFullName", fullName);
            req.setAttribute("formEmail", email);
            req.setAttribute("formPhone", phone);
            req.setAttribute("formAge", ageStr);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        User u = new User();
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPasswordHash(password); // will be hashed by service
        u.setAge(age);
        u.setRole("user");
        u.setStatus("active");

        try {
            String msg = userService.registerUser(u);
            // on success redirect to login with success message
            req.getSession().setAttribute("flash_success", msg);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if ("Email already registered".equals(message)) req.setAttribute("errorEmail", message);
            else if ("Phone number already registered".equals(message)) req.setAttribute("errorPhone", message);
            else if ("Invalid age".equals(message)) req.setAttribute("errorAge", "Age must be between 1 and 120");
            else req.setAttribute("error", message);
            req.setAttribute("formFullName", fullName);
            req.setAttribute("formEmail", email);
            req.setAttribute("formPhone", phone);
            req.setAttribute("formAge", ageStr);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}