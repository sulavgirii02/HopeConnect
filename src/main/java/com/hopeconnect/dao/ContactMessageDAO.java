package com.hopeconnect.dao;

import com.hopeconnect.model.ContactMessage;
import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ContactMessageDAO
 * Data access for contact_messages table.
 */
public class ContactMessageDAO {

    public boolean insert(ContactMessage message) {
        String sql = "INSERT INTO contact_messages (name,email,subject,message,status) VALUES (?,?,?,?, 'new')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, message.getName());
            ps.setString(2, message.getEmail());
            ps.setString(3, message.getSubject());
            ps.setString(4, message.getMessage());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Contact message insert failed: " + e.getMessage());
        }
        return false;
    }
}
