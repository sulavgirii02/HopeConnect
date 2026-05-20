package com.hopeconnect.dao;

import com.hopeconnect.model.AuditLog;
import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    public boolean insert(int adminId, String action, String entityType, int entityId, String description) {
        String sql = "INSERT INTO audit_logs (actor_id, action, entity_type, entity_id, details) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adminId);
            ps.setString(2, action);
            ps.setString(3, entityType);
            ps.setInt(4, entityId);
            ps.setString(5, description);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("AuditLog insert failed: " + e.getMessage());
        }
        return false;
    }

    public List<AuditLog> getRecentLogs(int limit) {
        List<AuditLog> logs = new ArrayList<>();

        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AuditLog log = new AuditLog();

                log.setId(rs.getInt("id"));
                log.setActorId(rs.getInt("actor_id"));
                log.setAction(rs.getString("action"));
                log.setEntityType(rs.getString("entity_type"));
                log.setEntityId(rs.getInt("entity_id"));
                log.setDetails(rs.getString("details"));
                log.setCreatedAt(rs.getTimestamp("created_at"));

                logs.add(log);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }
}
