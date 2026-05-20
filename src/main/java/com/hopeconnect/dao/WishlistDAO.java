package com.hopeconnect.dao;

import com.hopeconnect.model.AidProgram;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WishlistDAO {

    /**
     * Add a program to a user's wishlist.
     * Uses INSERT IGNORE so duplicates are silently skipped.
     * Returns true on success, false on error.
     */
    public boolean addToWishlist(int userId, int programId) {
        String sql = "INSERT IGNORE INTO wishlists (user_id, program_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, programId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove a program from a user's wishlist.
     * Returns true on success, false on error.
     */
    public boolean removeFromWishlist(int userId, int programId) {
        String sql = "DELETE FROM wishlists WHERE user_id = ? AND program_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, programId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a specific program is in a user's wishlist.
     */
    public boolean isInWishlist(int userId, int programId) {
        String sql = "SELECT id FROM wishlists WHERE user_id = ? AND program_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, programId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all program IDs wishlisted by a user.
     * Used to show filled or empty heart on listing page.
     */
    public Set<Integer> getWishlistedProgramIds(int userId) {
        Set<Integer> ids = new HashSet<>();
        String sql = "SELECT program_id FROM wishlists WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("program_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * Get full AidProgram objects wishlisted by a user.
     * Used on wishlist.jsp page to display saved programs.
     */
    public List<AidProgram> getWishlistedPrograms(int userId) {
        List<AidProgram> programs = new ArrayList<>();
        String sql = "SELECT ap.* FROM aid_programs ap " +
                "JOIN wishlists w ON ap.id = w.program_id " +
                "WHERE w.user_id = ? ORDER BY w.created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AidProgram p = new AidProgram();

                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setDescription(rs.getString("description"));
                p.setCategory(rs.getString("category"));
                p.setEligibility(rs.getString("eligibility"));

                programs.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return programs;
    }
}
