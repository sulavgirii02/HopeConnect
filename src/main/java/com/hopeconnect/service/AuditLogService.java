package com.hopeconnect.service;

import com.hopeconnect.dao.AuditLogDAO;
import com.hopeconnect.model.AuditLog;
import java.util.List;

public class AuditLogService {

    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    public void log(int adminId, String action, String entityType, int entityId, String description) {
        auditLogDAO.insert(adminId, action, entityType, entityId, description);
    }

    public List<AuditLog> getRecentLogs(int limit) {
        return auditLogDAO.getRecentLogs(limit);
    }
}
