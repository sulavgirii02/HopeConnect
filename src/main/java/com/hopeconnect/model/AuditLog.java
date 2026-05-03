package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * AuditLog
 * Immutable record of admin actions for accountability and tracing.
 */
public class AuditLog {
    private long id;
    private Integer actorId;
    private String action;
    private String entityType;
    private Integer entityId;
    private String details;
    private Timestamp createdAt;

    public AuditLog() {}

    public AuditLog(long id, Integer actorId, String action, String entityType, Integer entityId, String details, Timestamp createdAt) {
        this.id = id;
        this.actorId = actorId;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Integer getActorId() { return actorId; }
    public void setActorId(Integer actorId) { this.actorId = actorId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public Integer getEntityId() { return entityId; }
    public void setEntityId(Integer entityId) { this.entityId = entityId; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
