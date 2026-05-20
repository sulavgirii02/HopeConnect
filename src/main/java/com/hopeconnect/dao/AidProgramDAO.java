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

    private AidProgram mapResultSetToAidProgram(ResultSet rs) throws SQLException {
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

        try {
            int remCap = rs.getInt("remaining_capacity");
            if (!rs.wasNull()) p.setRemainingCapacity(remCap);
        } catch (SQLException ignored) {}

        p.setProgramStatus(rs.getString("status"));

        return p;
    }

    /**
     * Inserts a new AidProgram and returns generated id or -1 on failure.
     */
    public int insert(AidProgram program) {
        String sql = "INSERT INTO aid_programs (title,slug,description,category,eligibility,start_date,end_date,capacity,remaining_capacity,status,created_by,is_published) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, program.getTitle());
            ps.setString(2, program.getSlug());
            ps.setString(3, program.getDescription());
            ps.setString(4, program.getCategory());
            ps.setString(5, program.getEligibility());
            if (program.getStartDate() != null) ps.setDate(6, program.getStartDate()); else ps.setNull(6, Types.DATE);
            if (program.getEndDate() != null) ps.setDate(7, program.getEndDate()); else ps.setNull(7, Types.DATE);
            if (program.getCapacity() != null) ps.setInt(8, program.getCapacity()); else ps.setNull(8, Types.INTEGER);
            if (program.getRemainingCapacity() != null) ps.setInt(9, program.getRemainingCapacity()); else ps.setNull(9, Types.INTEGER);
            ps.setString(10, program.getProgramStatus());
            if (program.getCreatedBy() != null) ps.setInt(11, program.getCreatedBy()); else ps.setNull(11, Types.INTEGER);
            ps.setBoolean(12, program.isPublished());
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
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
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAidProgram(rs);
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
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToAidProgram(rs));
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
        String sql = "SELECT * FROM aid_programs WHERE is_published = 1 AND status = 'open' ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToAidProgram(rs));
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

        StringBuilder sql = new StringBuilder("SELECT * FROM aid_programs WHERE is_published = 1 AND status = 'open'");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + name.trim() + "%");
        }

        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category LIKE ?");
            params.add("%" + category.trim() + "%");
        }

        if (eligibility != null && !eligibility.trim().isEmpty()) {
            sql.append(" AND eligibility LIKE ?");
            params.add("%" + eligibility.trim() + "%");
        }

        sql.append(" ORDER BY created_at DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAidProgram(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("AidProgram searchPublished failed: " + e.getMessage());
        }
        return list;
    }

    public List<AidProgram> findPublishedPaginated(String keyword, String category, String status, int limit, int offset) {
        List<AidProgram> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM aid_programs WHERE is_published = 1");
        List<Object> params = buildPublishedFilter(sql, keyword, category, status);
        sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            bindParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapResultSetToAidProgram(rs));
            }
        } catch (SQLException e) {
            System.err.println("AidProgram findPublishedPaginated failed: " + e.getMessage());
        }
        return list;
    }

    public int countPublishedPrograms(String keyword, String category, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM aid_programs WHERE is_published = 1");
        List<Object> params = buildPublishedFilter(sql, keyword, category, status);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            bindParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("AidProgram countPublishedPrograms failed: " + e.getMessage());
        }
        return 0;
    }

    private List<Object> buildPublishedFilter(StringBuilder sql, String keyword, String category, String status) {
        List<Object> params = new ArrayList<>();
        if (keyword != null) {
            sql.append(" AND (title LIKE ? OR description LIKE ? OR eligibility LIKE ?)");
            String like = "%" + keyword + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (category != null && !"all".equalsIgnoreCase(category)) {
            sql.append(" AND category LIKE ?");
            params.add("%" + category + "%");
        }
        if ("open".equals(status)) {
            sql.append(" AND status = 'open'");
        } else if ("closed".equals(status)) {
            sql.append(" AND (status = 'closed' OR remaining_capacity = 0)");
        } else if ("available".equals(status) || status == null) {
            sql.append(" AND status = 'open' AND (remaining_capacity IS NULL OR remaining_capacity > 0)");
        }
        return params;
    }

    private void bindParams(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
    }

    /**
     * Updates an existing aid program.
     */
    public boolean update(AidProgram program) {
        String sql = "UPDATE aid_programs SET title=?,slug=?,description=?,category=?,eligibility=?,start_date=?,end_date=?,capacity=?,remaining_capacity=?,status=?,created_by=?,is_published=?,updated_at=NOW() WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, program.getTitle());
            ps.setString(2, program.getSlug());
            ps.setString(3, program.getDescription());
            ps.setString(4, program.getCategory());
            ps.setString(5, program.getEligibility());
            if (program.getStartDate() != null) ps.setDate(6, program.getStartDate()); else ps.setNull(6, Types.DATE);
            if (program.getEndDate() != null) ps.setDate(7, program.getEndDate()); else ps.setNull(7, Types.DATE);
            if (program.getCapacity() != null) ps.setInt(8, program.getCapacity()); else ps.setNull(8, Types.INTEGER);
            if (program.getRemainingCapacity() != null) ps.setInt(9, program.getRemainingCapacity()); else ps.setNull(9, Types.INTEGER);
            ps.setString(10, program.getProgramStatus());
            if (program.getCreatedBy() != null) ps.setInt(11, program.getCreatedBy()); else ps.setNull(11, Types.INTEGER);
            ps.setBoolean(12, program.isPublished());
            ps.setInt(13, program.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("AidProgram update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Decrements remaining_capacity by 1 within a given transaction connection.
     * Note: Removed status logic as the status column does not exist.
     */
    public boolean decrementCapacity(int programId, Connection conn) throws SQLException {
        String sql = "UPDATE aid_programs SET remaining_capacity = remaining_capacity - 1, updated_at = NOW() WHERE id = ? AND remaining_capacity > 0 AND is_published = 1 AND status = 'open'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, programId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean closeIfFull(int programId, Connection conn) throws SQLException {
        String sql = "UPDATE aid_programs SET status = 'closed', updated_at = NOW() WHERE id = ? AND remaining_capacity = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, programId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE aid_programs SET status = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("AidProgram updateStatus failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a program by id.
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
}
