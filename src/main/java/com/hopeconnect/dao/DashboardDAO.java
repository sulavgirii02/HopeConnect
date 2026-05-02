package com.hopeconnect.dao;

import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DashboardDAO
 * Data access for  milestone admin dashboard metrics.
 */
public class DashboardDAO {

    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE status <> 'deleted'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int countPrograms() throws SQLException {
        String sql = "SELECT COUNT(*) FROM aid_programs";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int countPublishedPrograms() throws SQLException {
        String sql = "SELECT COUNT(*) FROM aid_programs WHERE is_published = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int countApplicationsThisMonth() throws SQLException {
        String sql = "SELECT COUNT(*) FROM applications WHERE MONTH(applied_at)=MONTH(CURRENT_DATE()) AND YEAR(applied_at)=YEAR(CURRENT_DATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public double getApprovalRatio() throws SQLException {
        String sql = "SELECT SUM(CASE WHEN status='approved' THEN 1 ELSE 0 END) AS approved, COUNT(*) AS total FROM applications";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return 0.0;
            int approved = rs.getInt("approved");
            int total = rs.getInt("total");
            return total == 0 ? 0.0 : (double) approved / total;
        }
    }

    public List<Map<String, Object>> findRecentPrograms() throws SQLException {
        String sql = "SELECT id, title, is_published FROM aid_programs ORDER BY created_at DESC LIMIT 5";
        List<Map<String, Object>> programs = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("title", rs.getString("title"));
                row.put("category", "");
                row.put("published", rs.getBoolean("is_published"));
                programs.add(row);
            }
        }
        return programs;
    }

    public List<Map<String, Object>> findRecentApplications() throws SQLException {
        String sql = "SELECT id, user_id, program_id, status FROM applications ORDER BY applied_at DESC LIMIT 5";
        List<Map<String, Object>> applications = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("userId", rs.getInt("user_id"));
                row.put("programId", rs.getInt("program_id"));
                row.put("status", rs.getString("status"));
                applications.add(row);
            }
        }
        return applications;
    }
}
