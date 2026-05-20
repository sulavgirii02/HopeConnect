package com.hopeconnect.controller;

import com.hopeconnect.dao.UserProfileDAO;
import com.hopeconnect.model.User;
import com.hopeconnect.model.UserProfile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * ProfileServlet
 * Handles viewing and updating user profile (user_profiles table).
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private final UserProfileDAO profileDAO = new UserProfileDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        UserProfile profile = profileDAO.findByUserId(user.getId());
        req.setAttribute("profile", profile);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        UserProfile profile = profileDAO.findByUserId(user.getId());
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(user.getId());
        }

        String dobStr = req.getParameter("dateOfBirth");
        if (dobStr != null && !dobStr.trim().isEmpty()) {
            profile.setDateOfBirth(Date.valueOf(dobStr));
        }
        profile.setGender(req.getParameter("gender"));
        profile.setAddressLine1(req.getParameter("addressLine1"));
        profile.setAddressLine2(req.getParameter("addressLine2"));
        profile.setCity(req.getParameter("city"));
        profile.setState(req.getParameter("state"));
        profile.setPostalCode(req.getParameter("postalCode"));
        profile.setCountry(req.getParameter("country"));

        String hhStr = req.getParameter("householdSize");
        if (hhStr != null && !hhStr.trim().isEmpty()) {
            profile.setHouseholdSize(Integer.parseInt(hhStr));
        }

        String incomeStr = req.getParameter("monthlyIncome");
        if (incomeStr != null && !incomeStr.trim().isEmpty()) {
            profile.setMonthlyIncome(new BigDecimal(incomeStr));
        }

        profile.setPreferredCategory(req.getParameter("preferredCategory"));

        profile.setAdditionalInfo(req.getParameter("additionalInfo"));

        boolean saved = profileDAO.save(profile);
        if (saved) {
            req.getSession().setAttribute("flash_success", "Profile saved successfully");
        } else {
            req.setAttribute("error", "Failed to save profile");
        }

        req.setAttribute("profile", profile);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }
}
