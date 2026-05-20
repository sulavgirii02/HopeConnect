package com.hopeconnect.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ApplicationSuccessServlet
 * Displays success message after application submission.
 */
@WebServlet(name = "ApplicationSuccessServlet", urlPatterns = {"/application-success"})
public class ApplicationSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/applicationSuccess.jsp").forward(req, resp);
    }
}
