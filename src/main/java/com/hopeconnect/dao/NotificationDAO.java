package com.hopeconnect.dao;

import com.hopeconnect.model.Notification;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NotificationDAO
 * Data Access Object for notifications table.
 */
public class NotificationDAO {

    /**
     * Inserts a new Notification and returns generated id or -1 on failure.
     */
    public int insert(Notification n) {
        String sql = "INSERT INTO notifications (user_id,title,message,type,is_read) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (n.getUserId() != null) ps.setInt(1, n.getUserId()); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getMessage());
            ps.setString(4, n.getType());
            ps.setBoolean(5, n.isRead());
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        } catch (SQLException e) {
            System.err.println("Notification insert failed: " + e.getMessage());
        }
        return -1;
    }

    public int createNotification(int userId, String title, String message, String type) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setRead(false);
        return insert(n);
    }

    /**
     * Finds a Notification by id.
     */
    public Notification findById(int id) {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Notification n = new Notification();
                    n.setId(rs.getInt("id"));
                    int uid = rs.getInt("user_id"); if (!rs.wasNull()) n.setUserId(uid);
                    n.setTitle(rs.getString("title"));
                    n.setMessage(rs.getString("message"));
                    n.setType(rs.getString("type"));
                    n.setRead(rs.getBoolean("is_read"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    return n;
                }
            }
        } catch (SQLException e) {
            System.err.println("Notification findById failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all Notifications (recent first).
     */
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Notification n = new Notification();
                n.setId(rs.getInt("id"));
                int uid = rs.getInt("user_id"); if (!rs.wasNull()) n.setUserId(uid);
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                n.setRead(rs.getBoolean("is_read"));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.err.println("Notification findAll failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Updates an existing notification.
     */
    public boolean update(Notification n) {
        String sql = "UPDATE notifications SET user_id=?,title=?,message=?,type=?,is_read=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (n.getUserId() != null) ps.setInt(1, n.getUserId()); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getMessage());
            ps.setString(4, n.getType());
            ps.setBoolean(5, n.isRead());
            ps.setInt(6, n.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Notification update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a notification by id.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Notification delete failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Finds all unread notifications for a user.
     */
    public List<Notification> findUnreadByUserId(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = 0 ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setId(rs.getInt("id"));
                    int uid = rs.getInt("user_id"); if (!rs.wasNull()) n.setUserId(uid);
                    n.setTitle(rs.getString("title"));
                    n.setMessage(rs.getString("message"));
                    n.setType(rs.getString("type"));
                    n.setRead(rs.getBoolean("is_read"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(n);
                }
            }
        } catch (SQLException e) {
            System.err.println("Notification findUnreadByUserId failed: " + e.getMessage());
        }
        return list;
    }

    public List<Notification> findAllByUserId(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Notification findAllByUserId failed: " + e.getMessage());
        }
        return list;
    }

    public int countUnreadByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("Notification countUnreadByUserId failed: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Marks a notification as read.
     */
    public boolean markRead(int id, int userId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Notification markRead failed: " + e.getMessage());
        }
        return false;
    }

    public boolean markAllRead(int userId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE user_id = ? AND is_read = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) {
            System.err.println("Notification markAllRead failed: " + e.getMessage());
        }
        return false;
    }

    public int createNotificationForAdmins(String title, String message, String type) {
        String sql = "SELECT id FROM users WHERE role = 'admin'";
        int created = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Integer> adminIds = new ArrayList<>();
            while (rs.next()) adminIds.add(rs.getInt("id"));
            for (Integer adminId : adminIds) {
                if (createNotification(adminId, title, message, type) > 0) created++;
            }
        } catch (SQLException e) {
            System.err.println("Notification createNotificationForAdmins failed: " + e.getMessage());
        }
        return created;
    }

    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setId(rs.getInt("id"));
        int uid = rs.getInt("user_id"); if (!rs.wasNull()) n.setUserId(uid);
        n.setTitle(rs.getString("title"));
        n.setMessage(rs.getString("message"));
        n.setType(rs.getString("type"));
        n.setRead(rs.getBoolean("is_read"));
        n.setCreatedAt(rs.getTimestamp("created_at"));
        return n;
    }
}
