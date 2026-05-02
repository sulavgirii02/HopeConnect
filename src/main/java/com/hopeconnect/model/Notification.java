package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * Notification
 * Represents a notification sent to a specific user or broadcasted.
 */
public class Notification {
    private int id;
    private Integer userId; // null for broadcast
    private String title;
    private String message;
    private String type;
    private boolean isRead;
    private Timestamp createdAt;

    public Notification() {}

    public Notification(int id, Integer userId, String title, String message, String type, boolean isRead, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
