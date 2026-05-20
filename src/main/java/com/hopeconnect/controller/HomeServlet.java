package com.hopeconnect.controller;

import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.dao.HomeStatsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * HomeServlet
 * Loads landing page with featured programs.
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    /**
     * Loads top 3 active programs for the home page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<com.hopeconnect.model.AidProgram> programs = new AidProgramDAO().findAllPublished();
        if (programs.size() > 3) {
            programs = programs.subList(0, 3);
        }
        req.setAttribute("featuredPrograms", programs);

        HomeStatsDAO homeStatsDAO = new HomeStatsDAO();
        int activePrograms = homeStatsDAO.countActivePrograms();
        int applicationsProcessed = homeStatsDAO.countApplicationsProcessed();
        int familiesSupported = homeStatsDAO.countFamiliesSupported();

        req.setAttribute("statActivePrograms", activePrograms);
        req.setAttribute("statApplicationsProcessed", applicationsProcessed);
        req.setAttribute("statFamiliesSupported", familiesSupported);
        req.setAttribute("statPartnerNGOs", 18); // Hardcoded since no NGO table exists

        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
