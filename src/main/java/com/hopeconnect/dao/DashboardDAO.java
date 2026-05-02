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
 * Data access for first milestone admin dashboard metrics.
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


}
