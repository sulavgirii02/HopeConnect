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

public class AdminDashboardDAO {

    public int countApplicationsThisMonth() {
        return queryCount("SELECT COUNT(*) FROM applications WHERE MONTH(applied_at)=MONTH(CURRENT_DATE()) AND YEAR(applied_at)=YEAR(CURRENT_DATE())");
    }

    public Map<String, Integer> getApprovalTotals() {
        Map<String, Integer> totals = new HashMap<>();
        String sql = "SELECT SUM(CASE WHEN status='approved' THEN 1 ELSE 0 END) AS approved, COUNT(*) AS total FROM applications";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totals.put("approved", rs.getInt("approved"));
                totals.put("total", rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard approval totals failed: " + e.getMessage());
        }
        return totals;
    }

    public List<Map<String, Object>> findTopPrograms() {
        List<Map<String, Object>> topPrograms = new ArrayList<>();
        String sql = "SELECT p.id, p.title, COUNT(a.id) AS cnt FROM aid_programs p LEFT JOIN applications a ON p.id=a.program_id GROUP BY p.id, p.title ORDER BY cnt DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("title", rs.getString("title"));
                row.put("count", rs.getInt("cnt"));
                topPrograms.add(row);
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard top programs failed: " + e.getMessage());
        }
        return topPrograms;
    }

    public Map<String, Integer> getProgramSummary() {
        Map<String, Integer> summary = new HashMap<>();
        String sql = "SELECT COUNT(*) AS total_programs, " +
                "SUM(CASE WHEN is_published = 1 THEN 1 ELSE 0 END) AS published_programs, " +
                "SUM(CASE WHEN status = 'open' THEN 1 ELSE 0 END) AS open_programs, " +
                "SUM(CASE WHEN status = 'closed' OR remaining_capacity = 0 THEN 1 ELSE 0 END) AS closed_programs " +
                "FROM aid_programs";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                summary.put("totalPrograms", rs.getInt("total_programs"));
                summary.put("publishedPrograms", rs.getInt("published_programs"));
                summary.put("openPrograms", rs.getInt("open_programs"));
                summary.put("closedPrograms", rs.getInt("closed_programs"));
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard program summary failed: " + e.getMessage());
        }
        return summary;
    }

    public List<Map<String, Object>> findRecentApplications() {
        List<Map<String, Object>> recentApps = new ArrayList<>();
        String sql = "SELECT id, user_id, program_id, status FROM applications ORDER BY applied_at DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("userId", rs.getInt("user_id"));
                row.put("programId", rs.getInt("program_id"));
                row.put("status", rs.getString("status"));
                recentApps.add(row);
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard recent applications failed: " + e.getMessage());
        }
        return recentApps;
    }

    public List<Map<String, Object>> findCustomerNotifications() {
        List<Map<String, Object>> notifications = new ArrayList<>();
        String sql = "SELECT notification_type, entity_id, customer_name, summary, status, created_at FROM (" +
                "  SELECT 'application' AS notification_type, a.id AS entity_id, u.full_name AS customer_name, " +
                "         CONCAT('Applied for ', p.title) AS summary, a.status AS status, a.applied_at AS created_at " +
                "  FROM applications a " +
                "  JOIN users u ON a.user_id = u.id " +
                "  JOIN aid_programs p ON a.program_id = p.id " +
                "  UNION ALL " +
                "  SELECT 'message' AS notification_type, cm.id AS entity_id, cm.name AS customer_name, " +
                "         CONCAT('Sent message: ', cm.subject) AS summary, cm.status AS status, cm.created_at AS created_at " +
                "  FROM contact_messages cm " +
                ") customer_activity ORDER BY created_at DESC LIMIT 10";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("type", rs.getString("notification_type"));
                row.put("entityId", rs.getInt("entity_id"));
                row.put("customerName", rs.getString("customer_name"));
                row.put("summary", rs.getString("summary"));
                row.put("status", rs.getString("status"));
                row.put("createdAt", rs.getTimestamp("created_at"));
                notifications.add(row);
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard customer notifications failed: " + e.getMessage());
        }
        return notifications;
    }

    public Map<String, Integer> getPendingCounts() {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM applications WHERE status = 'pending') AS pending_applications, " +
                "(SELECT COUNT(*) FROM contact_messages WHERE status = 'new') AS new_messages";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                counts.put("pendingApplicationCount", rs.getInt("pending_applications"));
                counts.put("newMessageCount", rs.getInt("new_messages"));
            }
        } catch (SQLException e) {
            System.err.println("AdminDashboard pending counts failed: " + e.getMessage());
        }
        return counts;
    }

    private int queryCount(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println("AdminDashboard count failed: " + e.getMessage());
        }
        return 0;
    }
}
