package com.hopeconnect.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Applies small idempotent schema upgrades needed by newer application code.
 */
public final class SchemaMigrator {

    private SchemaMigrator() {
    }

    public static void migrate() {
        try (Connection conn = DBConnection.getConnection()) {
            ensureRemainingCapacityColumn(conn);
            ensureProgramStatusColumn(conn);
            ensureUserStatusValues(conn);
        } catch (SQLException e) {
            System.err.println("[SchemaMigrator] Migration failed: " + e.getMessage());
        }
    }

    private static void ensureProgramStatusColumn(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet columns = metaData.getColumns(conn.getCatalog(), null, "aid_programs", "status")) {
            if (!columns.next()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE aid_programs ADD COLUMN status ENUM('open','closed','archived') NOT NULL DEFAULT 'open' AFTER remaining_capacity");
                }
                System.out.println("[SchemaMigrator] Added aid_programs.status");
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE aid_programs SET status = 'open' WHERE status IS NULL OR status = ''")) {
            ps.executeUpdate();
        }
    }

    private static void ensureRemainingCapacityColumn(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet columns = metaData.getColumns(conn.getCatalog(), null, "aid_programs", "remaining_capacity")) {
            if (!columns.next()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("ALTER TABLE aid_programs ADD COLUMN remaining_capacity INT DEFAULT NULL AFTER capacity");
                }
                System.out.println("[SchemaMigrator] Added aid_programs.remaining_capacity");
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE aid_programs SET remaining_capacity = capacity " +
                "WHERE remaining_capacity IS NULL AND capacity IS NOT NULL")) {
            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("[SchemaMigrator] Backfilled remaining_capacity for " + updated + " program(s)");
            }
        }
    }

    private static void ensureUserStatusValues(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE users SET status = 'active' WHERE status = 'verified'")) {
            ps.executeUpdate();
        }
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                    "ALTER TABLE users MODIFY status ENUM('pending','active','suspended','deactivated') NOT NULL DEFAULT 'pending'"
            );
        }
    }
}
