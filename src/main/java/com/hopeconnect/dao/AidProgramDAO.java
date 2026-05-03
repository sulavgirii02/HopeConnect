package com.hopeconnect.dao;

import com.hopeconnect.model.AidProgram;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AidProgramDAO
 * Data Access Object for aid_programs table.
 */
public class AidProgramDAO {
    private static boolean categoryColumnChecked = false;

    /**
     * Inserts a new AidProgram and returns generated id or -1 on failure.
     */
    public int insert(AidProgram program) {
        String sql = "INSERT INTO aid_programs (title,slug,description,category,eligibility,start_date,end_date,capacity,created_by,is_published) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {
                ps.setString(1, program.getTitle());
                ps.setString(2, program.getSlug());
                ps.setString(3, program.getDescription());
                ps.setString(4, program.getCategory());
                ps.setString(5, program.getEligibility());
                if (program.getStartDate() != null) ps.setDate(6, program.getStartDate()); else ps.setNull(6, Types.DATE);
                if (program.getEndDate() != null) ps.setDate(7, program.getEndDate()); else ps.setNull(7, Types.DATE);
                if (program.getCapacity() != null) ps.setInt(8, program.getCapacity()); else ps.setNull(8, Types.INTEGER);
                if (program.getCreatedBy() != null) ps.setInt(9, program.getCreatedBy()); else ps.setNull(9, Types.INTEGER);
                ps.setBoolean(10, program.isPublished());
                int affected = ps.executeUpdate();
                if (affected == 0) return -1;
                try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram insert failed: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Finds an AidProgram by id.
     */
    public AidProgram findById(int id) {
        String sql = "SELECT * FROM aid_programs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapAidProgram(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram findById failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all AidPrograms.
     */
    public List<AidProgram> findAll() {
        List<AidProgram> list = new ArrayList<>();
        String sql = "SELECT * FROM aid_programs ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAidProgram(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram findAll failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Returns all published (active) programs.
     */
    public List<AidProgram> findAllPublished() {
        List<AidProgram> list = new ArrayList<>();
        String sql = "SELECT * FROM aid_programs WHERE is_published = 1 ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAidProgram(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram findAllPublished failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Searches published programs by name, category, or eligibility.
     */
    public List<AidProgram> searchPublished(String name, String category, String eligibility) {
        List<AidProgram> list = new ArrayList<>();
        String sql = "SELECT * FROM aid_programs WHERE is_published = 1 " +
                "AND ( ? IS NULL OR title LIKE ? ) " +
                "AND ( ? IS NULL OR category = ? ) " +
                "AND ( ? IS NULL OR eligibility LIKE ? ) " +
                "ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if (name == null || name.trim().isEmpty()) {
                    ps.setNull(1, Types.VARCHAR); ps.setNull(2, Types.VARCHAR);
                } else {
                    ps.setString(1, name); ps.setString(2, "%" + name + "%");
                }
                if (category == null || category.trim().isEmpty()) {
                    ps.setNull(3, Types.VARCHAR); ps.setNull(4, Types.VARCHAR);
                } else {
                    ps.setString(3, category); ps.setString(4, category);
                }
                if (eligibility == null || eligibility.trim().isEmpty()) {
                    ps.setNull(5, Types.VARCHAR); ps.setNull(6, Types.VARCHAR);
                } else {
                    ps.setString(5, eligibility); ps.setString(6, "%" + eligibility + "%");
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapAidProgram(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram searchPublished failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Updates an existing aid program.
     *
     */
    public boolean update(AidProgram program) {
        String sql = "UPDATE aid_programs SET title=?,slug=?,description=?,category=?,eligibility=?,start_date=?,end_date=?,capacity=?,created_by=?,is_published=?,updated_at=NOW() WHERE id=?";
        try (Connection conn = DBConnection.getConnection()) {
            ensureCategoryColumn(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, program.getTitle());
                ps.setString(2, program.getSlug());
                ps.setString(3, program.getDescription());
                ps.setString(4, program.getCategory());
                ps.setString(5, program.getEligibility());
                if (program.getStartDate() != null) ps.setDate(6, program.getStartDate()); else ps.setNull(6, Types.DATE);
                if (program.getEndDate() != null) ps.setDate(7, program.getEndDate()); else ps.setNull(7, Types.DATE);
                if (program.getCapacity() != null) ps.setInt(8, program.getCapacity()); else ps.setNull(8, Types.INTEGER);
                if (program.getCreatedBy() != null) ps.setInt(9, program.getCreatedBy()); else ps.setNull(9, Types.INTEGER);
                ps.setBoolean(10, program.isPublished());
                ps.setInt(11, program.getId());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("AidProgram update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes an aid program record from the database by its ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM aid_programs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("AidProgram delete failed: " + e.getMessage());
        }
        return false;
    }

    private void ensureCategoryColumn(Connection conn) throws SQLException {
        if (categoryColumnChecked) return;

        String checkSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'aid_programs' AND COLUMN_NAME = 'category'";
        try (PreparedStatement ps = conn.prepareStatement(checkSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement alter = conn.prepareStatement("ALTER TABLE aid_programs ADD COLUMN category VARCHAR(100) DEFAULT NULL AFTER description")) {
                    alter.executeUpdate();
                }
            }
        }

        categoryColumnChecked = true;
    }


    private AidProgram mapAidProgram(ResultSet rs) throws SQLException {
        AidProgram p = new AidProgram();
        p.setId(rs.getInt("id"));
        p.setTitle(rs.getString("title"));
        p.setSlug(rs.getString("slug"));
        p.setDescription(rs.getString("description"));
        p.setCategory(rs.getString("category"));
        p.setEligibility(rs.getString("eligibility"));
        p.setStartDate(rs.getDate("start_date"));
        p.setEndDate(rs.getDate("end_date"));
        int capacity = rs.getInt("capacity");
        if (!rs.wasNull()) p.setCapacity(capacity);
        int createdBy = rs.getInt("created_by");
        if (!rs.wasNull()) p.setCreatedBy(createdBy);
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));
        p.setPublished(rs.getBoolean("is_published"));
        return p;
    }
}
