package com.hopeconnect.controller;

import com.hopeconnect.dao.WishlistDAO;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

public class WishlistServlet extends HttpServlet {

    private final WishlistDAO wishlistDAO = new WishlistDAO();

    /**
     * GET /wishlist
     * Shows the logged-in user's saved programs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            com.hopeconnect.util.FlashMessageUtil.warning(request, "Please log in to save programs to your wishlist.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<AidProgram> wishlist = wishlistDAO.getWishlistedPrograms(user.getId());

        request.setAttribute("wishlistPrograms", wishlist);

        request.getRequestDispatcher("/views/wishlist.jsp")
               .forward(request, response);
    }

    /**
     * POST /wishlist
     * Adds or removes a program from the user's wishlist.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            com.hopeconnect.util.FlashMessageUtil.warning(request, "Please log in to save programs to your wishlist.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            String action = request.getParameter("action");
            int programId = Integer.parseInt(request.getParameter("programId"));

            if ("add".equals(action)) {
                boolean ok = wishlistDAO.addToWishlist(user.getId(), programId);
                if (ok) com.hopeconnect.util.FlashMessageUtil.success(request, "Program saved to your wishlist.");
                else com.hopeconnect.util.FlashMessageUtil.error(request, "Failed to save program to wishlist. Please try again.");
            } else if ("remove".equals(action)) {
                boolean ok = wishlistDAO.removeFromWishlist(user.getId(), programId);
                if (ok) com.hopeconnect.util.FlashMessageUtil.success(request, "Program removed from your wishlist.");
                else com.hopeconnect.util.FlashMessageUtil.error(request, "Failed to remove program from wishlist. Please try again.");
            } else {
                com.hopeconnect.util.FlashMessageUtil.error(request, "Something went wrong. Please try again.");
            }

        } catch (NumberFormatException e) {
            com.hopeconnect.util.FlashMessageUtil.error(request, "Invalid program selected.");
        }

        String referer = request.getHeader("Referer");

        response.sendRedirect(
            referer != null
                ? referer
                : request.getContextPath() + "/wishlist"
        );
    }
}
