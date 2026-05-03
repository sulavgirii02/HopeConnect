package com.hopeconnect.service;

import com.hopeconnect.dao.AuditLogDAO;

/**
 * AuditLogService
 * Simple logger for admin actions.
 */
public class AuditLogService {
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    /**
     * Inserts an audit log entry for an admin action.
     */
    public void logAction(int adminId, String action, String details) {
        auditLogDAO.insert(adminId, action, details);
    }
}
