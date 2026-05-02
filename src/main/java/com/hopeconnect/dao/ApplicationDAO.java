package com.hopeconnect.dao;

import com.hopeconnect.model.Application;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ApplicationDAO
 * Data Access Object for applications table.
 */
public class ApplicationDAO {

    /**
     * Inserts a new Application and returns generated id or -1 on failure.
     */
    public int insert(Application app) {
        String sql = "INSERT INTO applications (user_id,program_id,applied_at,status,assigned_officer,notes) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {
            ps.setInt(1, app.getUserId());
            ps.setInt(2, app.getProgramId());
            ps.setTimestamp(3, app.getAppliedAt());
            ps.setString(4, app.getStatus());
            if (app.getAssignedOfficer() != null) ps.setInt(5, app.getAssignedOfficer()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, app.getNotes());
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) {
            System.err.println("Application insert failed: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Finds an Application by id.
     */
    public Application findById(int id) {
        String sql = "SELECT * FROM applications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Application a = new Application();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setProgramId(rs.getInt("program_id"));
                    a.setAppliedAt(rs.getTimestamp("applied_at"));
                    a.setStatus(rs.getString("status"));
                    int officer = rs.getInt("assigned_officer");
                    if (!rs.wasNull()) a.setAssignedOfficer(officer);
                    a.setNotes(rs.getString("notes"));
                    a.setLastUpdated(rs.getTimestamp("last_updated"));
                    return a;
                }
            }
        } catch (SQLException e) {
            System.err.println("Application findById failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all Applications.
     */
    public List<Application> findAll() {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM applications ORDER BY applied_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Application a = new Application();
                a.setId(rs.getInt("id"));
                a.setUserId(rs.getInt("user_id"));
                a.setProgramId(rs.getInt("program_id"));
                a.setAppliedAt(rs.getTimestamp("applied_at"));
                a.setStatus(rs.getString("status"));
                int officer = rs.getInt("assigned_officer");
                if (!rs.wasNull()) a.setAssignedOfficer(officer);
                a.setNotes(rs.getString("notes"));
                a.setLastUpdated(rs.getTimestamp("last_updated"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Application findAll failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Updates an existing application.
     */
    public boolean update(Application app) {
        String sql = "UPDATE applications SET user_id=?,program_id=?,applied_at=?,status=?,assigned_officer=?,notes=?,last_updated=NOW() WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, app.getUserId());
            ps.setInt(2, app.getProgramId());
            ps.setTimestamp(3, app.getAppliedAt());
            ps.setString(4, app.getStatus());
            if (app.getAssignedOfficer() != null) ps.setInt(5, app.getAssignedOfficer()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, app.getNotes());
            ps.setInt(7, app.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Application update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes an application by id.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM applications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Application delete failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Finds an application by user and program id.
     */
    public Application findByUserAndProgram(int userId, int programId) {
        String sql = "SELECT * FROM applications WHERE user_id = ? AND program_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, programId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Application a = new Application();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setProgramId(rs.getInt("program_id"));
                    a.setAppliedAt(rs.getTimestamp("applied_at"));
                    a.setStatus(rs.getString("status"));
                    int officer = rs.getInt("assigned_officer"); if (!rs.wasNull()) a.setAssignedOfficer(officer);
                    a.setNotes(rs.getString("notes"));
                    a.setLastUpdated(rs.getTimestamp("last_updated"));
                    return a;
                }
            }
        } catch (SQLException e) {
            System.err.println("Application findByUserAndProgram failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds all applications for a given user id.
     */
    public List<Application> findByUserId(int userId) {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE user_id = ? ORDER BY applied_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Application a = new Application();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setProgramId(rs.getInt("program_id"));
                    a.setAppliedAt(rs.getTimestamp("applied_at"));
                    a.setStatus(rs.getString("status"));
                    int officer = rs.getInt("assigned_officer"); if (!rs.wasNull()) a.setAssignedOfficer(officer);
                    a.setNotes(rs.getString("notes"));
                    a.setLastUpdated(rs.getTimestamp("last_updated"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            System.err.println("Application findByUserId failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Returns application rows with user and program details for admin.
     */
    public List<java.util.Map<String, Object>> findAllWithDetails() {
        List<java.util.Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT a.id, a.status, a.applied_at, u.full_name, u.email, p.title " +
                "FROM applications a JOIN users u ON a.user_id = u.id " +
                "JOIN aid_programs p ON a.program_id = p.id ORDER BY a.applied_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("status", rs.getString("status"));
                row.put("appliedAt", rs.getTimestamp("applied_at"));
                row.put("userName", rs.getString("full_name"));
                row.put("userEmail", rs.getString("email"));
                row.put("programTitle", rs.getString("title"));
                list.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Application findAllWithDetails failed: " + e.getMessage());
        }
        return list;
    }
}
