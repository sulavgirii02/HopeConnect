package com.hopeconnect.dao;

import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * AuditLogDAO
 * Data Access Object for audit_logs table.
 */
public class AuditLogDAO {

    public boolean insert(int adminId, String action, String details) {
        String sql = "INSERT INTO audit_logs (actor_id, action, details) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adminId);
            ps.setString(2, action);
            ps.setString(3, details);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Audit log insert failed: " + e.getMessage());
        }
        return false;
    }
}
