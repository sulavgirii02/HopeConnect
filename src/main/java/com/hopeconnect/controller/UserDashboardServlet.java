package com.hopeconnect.controller;

import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.dao.UserProfileDAO;
import com.hopeconnect.model.User;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.model.UserProfile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserDashboardServlet
 * Displays a personalized dashboard for a logged-in user with intelligent program recommendations.
 */
@WebServlet(name = "UserDashboardServlet", urlPatterns = {"/dashboard", "/user-dashboard"})
public class UserDashboardServlet extends HttpServlet {

    /**
     * Loads user profile, recent applications, unread notifications, and recommended programs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        
        // Redirect admins to admin dashboard
        if ("admin".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            return;
        }

        // Load user applications and notifications
        List<com.hopeconnect.model.Application> apps = new ApplicationDAO().findByUserId(user.getId());
        int unreadCount = new com.hopeconnect.service.NotificationService().getUnreadCount(user.getId());

        // Load user profile for smart matching
        UserProfileDAO profileDAO = new UserProfileDAO();
        UserProfile profile = profileDAO.findByUserId(user.getId());

        // Score all published programs and return top 3
        AidProgramDAO programDAO = new AidProgramDAO();
        List<AidProgram> allPrograms = programDAO.findAllPublished();
        List<AidProgram> recommended = scoreAndSort(allPrograms, profile);
        if (recommended.size() > 3) recommended = recommended.subList(0, 3);

        // Also load all published programs as fallback
        req.setAttribute("programs", allPrograms);
        req.setAttribute("applications", apps);
        req.setAttribute("unreadCount", unreadCount);
        session.setAttribute("unreadCount", unreadCount);
        req.setAttribute("recommendedPrograms", recommended);
        req.setAttribute("hasProfile", profile != null);
        req.getRequestDispatcher("/views/userDashboard.jsp").forward(req, resp);
    }

    /**
     * Scores programs by relevance to the user's profile.
     * Factors: category match (+40), income eligibility (+30), household fit (+20), availability (+10).
     */
    private List<AidProgram> scoreAndSort(List<AidProgram> programs, UserProfile profile) {
        if (programs == null || programs.isEmpty()) return programs;

        return programs.stream()
                .map(p -> {
                    int score = 0;

                    // Category match
                    if (profile != null && profile.getPreferredCategory() != null
                            && p.getCategory() != null
                            && p.getCategory().equalsIgnoreCase(profile.getPreferredCategory())) {
                        score += 40;
                    }

                    // Income-based: lower income = higher need = higher score
                    if (profile != null && profile.getMonthlyIncome() != null) {
                        BigDecimal income = profile.getMonthlyIncome();
                        if (income.compareTo(new BigDecimal("1000")) < 0) score += 30;
                        else if (income.compareTo(new BigDecimal("2500")) < 0) score += 20;
                        else if (income.compareTo(new BigDecimal("5000")) < 0) score += 10;
                    }

                    // Household size: larger household = higher need
                    if (profile != null && profile.getHouseholdSize() != null) {
                        int hh = profile.getHouseholdSize();
                        if (hh >= 6) score += 20;
                        else if (hh >= 4) score += 10;
                        else if (hh >= 2) score += 5;
                    }

                    // Availability bonus
                    if (p.getRemainingCapacity() != null && p.getRemainingCapacity() > 0) score += 10;

                    p.setMatchScore(score);
                    return p;
                })
                .sorted(Comparator.comparingInt(AidProgram::getMatchScore).reversed())
                .collect(Collectors.toList());
    }
}
