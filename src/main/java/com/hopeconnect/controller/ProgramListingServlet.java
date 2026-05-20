package com.hopeconnect.controller;

import com.hopeconnect.dao.AidProgramDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ProgramListingServlet
 * Handles browsing and searching of active aid programs.
 */
public class ProgramListingServlet extends HttpServlet {

    /**
     * Loads published programs with optional search filters.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = normalize(req.getParameter("keyword"));
        String category = normalize(req.getParameter("category"));
        String status = normalize(req.getParameter("status"));
        if (status == null) status = "available";
        int pageSize = 6;
        String pageParam = req.getParameter("page");
        int currentPage = parsePositiveInt(pageParam, 1);
        if (pageParam != null && !pageParam.trim().isEmpty() && !isPositiveInt(pageParam)) {
            com.hopeconnect.util.FlashMessageUtil.warning(req, "Invalid page number. Showing the first page.");
        }
        AidProgramDAO dao = new AidProgramDAO();
        int totalRecords = dao.countPublishedPrograms(keyword, category, status);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages > 0 && currentPage > totalPages) currentPage = totalPages;
        int offset = (currentPage - 1) * pageSize;
        List<com.hopeconnect.model.AidProgram> programs = dao.findPublishedPaginated(keyword, category, status, pageSize, offset);
        req.setAttribute("programs", programs);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("selectedCategory", category == null ? "all" : category);
        req.setAttribute("selectedStatus", status);
        req.setAttribute("keyword", keyword == null ? "" : keyword);

        jakarta.servlet.http.HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            com.hopeconnect.model.User user = (com.hopeconnect.model.User) session.getAttribute("user");
            com.hopeconnect.dao.WishlistDAO wishlistDAO = new com.hopeconnect.dao.WishlistDAO();
            java.util.Set<Integer> wishlistedIds = wishlistDAO.getWishlistedProgramIds(user.getId());
            req.setAttribute("wishlistedIds", wishlistedIds);
        }

        req.getRequestDispatcher("/views/programListing.jsp").forward(req, resp);
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    private int parsePositiveInt(String value, int fallback) {
        try { int parsed = Integer.parseInt(value); return parsed > 0 ? parsed : fallback; }
        catch (Exception e) { return fallback; }
    }
    private boolean isPositiveInt(String value) {
        try { return Integer.parseInt(value) > 0; }
        catch (Exception e) { return false; }
    }
}
