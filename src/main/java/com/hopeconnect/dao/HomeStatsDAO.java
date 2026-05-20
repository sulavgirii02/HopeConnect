package com.hopeconnect.dao;

import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeStatsDAO {

    public int countActivePrograms() {
        return queryCount("SELECT COUNT(*) FROM aid_programs WHERE is_published = 1");
    }

    public int countApplicationsProcessed() {
        return queryCount("SELECT COUNT(*) FROM applications WHERE status != 'pending'");
    }

    public int countFamiliesSupported() {
        return queryCount("SELECT COUNT(DISTINCT user_id) FROM applications WHERE status = 'approved'");
    }

    private int queryCount(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println("HomeStats query failed: " + e.getMessage());
        }
        return 0;
    }
}
