package com.hopeconnect.dao;

import com.hopeconnect.model.UserProfile;
import com.hopeconnect.util.DBConnection;

import java.sql.*;

/**
 * UserProfileDAO
 * Simple Data Access Object for the user_profiles table.
 * Supports find-by-user, insert, and update (upsert pattern).
 */
public class UserProfileDAO {

    /**
     * Find a profile by user id. Returns null if none exists yet.
     */
    public UserProfile findByUserId(int userId) {
        String sql = "SELECT * FROM user_profiles WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserProfile p = new UserProfile();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setDateOfBirth(rs.getDate("date_of_birth"));
                    p.setGender(rs.getString("gender"));
                    p.setAddressLine1(rs.getString("address_line1"));
                    p.setAddressLine2(rs.getString("address_line2"));
                    p.setCity(rs.getString("city"));
                    p.setState(rs.getString("state"));
                    p.setPostalCode(rs.getString("postal_code"));
                    p.setCountry(rs.getString("country"));
                    int hh = rs.getInt("household_size"); if (!rs.wasNull()) p.setHouseholdSize(hh);
                    p.setMonthlyIncome(rs.getBigDecimal("monthly_income"));
                    p.setPreferredCategory(rs.getString("preferred_category"));
                    p.setAdditionalInfo(rs.getString("additional_info"));
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    p.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("UserProfile findByUserId failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Insert a new profile. Returns generated id or -1 on failure.
     */
    public int insert(UserProfile p) {
        String sql = "INSERT INTO user_profiles " +
                "(user_id,date_of_birth,gender,address_line1,address_line2,city,state,postal_code,country,household_size,monthly_income,preferred_category,additional_info) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, p);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("UserProfile insert failed: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Update an existing profile (looked up by user_id).
     */
    public boolean update(UserProfile p) {
        String sql = "UPDATE user_profiles SET " +
                "date_of_birth=?,gender=?,address_line1=?,address_line2=?,city=?,state=?,postal_code=?,country=?,household_size=?,monthly_income=?,preferred_category=?,additional_info=? " +
                "WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (p.getDateOfBirth() != null) ps.setDate(1, p.getDateOfBirth()); else ps.setNull(1, Types.DATE);
            ps.setString(2, p.getGender());
            ps.setString(3, p.getAddressLine1());
            ps.setString(4, p.getAddressLine2());
            ps.setString(5, p.getCity());
            ps.setString(6, p.getState());
            ps.setString(7, p.getPostalCode());
            ps.setString(8, p.getCountry());
            if (p.getHouseholdSize() != null) ps.setInt(9, p.getHouseholdSize()); else ps.setNull(9, Types.INTEGER);
            if (p.getMonthlyIncome() != null) ps.setBigDecimal(10, p.getMonthlyIncome()); else ps.setNull(10, Types.DECIMAL);
            ps.setString(11, p.getPreferredCategory());
            ps.setString(12, p.getAdditionalInfo());
            ps.setInt(13, p.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("UserProfile update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Upsert helper used by ProfileServlet: insert if missing, update if exists.
     */
    public boolean save(UserProfile p) {
        UserProfile existing = findByUserId(p.getUserId());
        if (existing == null) return insert(p) > 0;
        return update(p);
    }

    private void bind(PreparedStatement ps, UserProfile p) throws SQLException {
        ps.setInt(1, p.getUserId());
        if (p.getDateOfBirth() != null) ps.setDate(2, p.getDateOfBirth()); else ps.setNull(2, Types.DATE);
        ps.setString(3, p.getGender());
        ps.setString(4, p.getAddressLine1());
        ps.setString(5, p.getAddressLine2());
        ps.setString(6, p.getCity());
        ps.setString(7, p.getState());
        ps.setString(8, p.getPostalCode());
        ps.setString(9, p.getCountry());
        if (p.getHouseholdSize() != null) ps.setInt(10, p.getHouseholdSize()); else ps.setNull(10, Types.INTEGER);
        if (p.getMonthlyIncome() != null) ps.setBigDecimal(11, p.getMonthlyIncome()); else ps.setNull(11, Types.DECIMAL);
        ps.setString(12, p.getPreferredCategory());
        ps.setString(13, p.getAdditionalInfo());
    }
}
