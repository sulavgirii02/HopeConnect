package com.hopeconnect.dao;

import com.hopeconnect.model.ContactMessage;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactMessageDAO {

    public boolean insert(ContactMessage message) {
        String sql = "INSERT INTO contact_messages (user_id,name,email,phone,subject,message,status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (message.getUserId() != null) ps.setInt(1, message.getUserId()); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, message.getName());
            ps.setString(3, message.getEmail());
            ps.setString(4, message.getPhone());
            ps.setString(5, message.getSubject());
            ps.setString(6, message.getMessage());
            ps.setString(7, message.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ContactMessage insert failed: " + e.getMessage());
        }
        return false;
    }

    public List<ContactMessage> findAll() {
        List<ContactMessage> list = new ArrayList<>();
        String sql = "SELECT * FROM contact_messages ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("ContactMessage findAll failed: " + e.getMessage());
        }
        return list;
    }

    public ContactMessage findById(int id) {
        String sql = "SELECT * FROM contact_messages WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("ContactMessage findById failed: " + e.getMessage());
        }
        return null;
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE contact_messages SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ContactMessage updateStatus failed: " + e.getMessage());
        }
        return false;
    }

    private ContactMessage mapRow(ResultSet rs) throws SQLException {
        ContactMessage m = new ContactMessage();
        m.setId(rs.getInt("id"));
        int uid = rs.getInt("user_id");
        if (!rs.wasNull()) m.setUserId(uid);
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setPhone(rs.getString("phone"));
        m.setSubject(rs.getString("subject"));
        m.setMessage(rs.getString("message"));
        m.setStatus(rs.getString("status"));
        m.setCreatedAt(rs.getTimestamp("created_at"));
        return m;
    }
}
