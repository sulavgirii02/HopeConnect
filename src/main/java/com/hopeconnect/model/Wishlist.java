package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * Wishlist
 * Represents a user's bookmark for an aid program (M:N junction resolved to entity).
 */
public class Wishlist {
    private int id;
    private int userId;
    private int programId;
    private Timestamp createdAt;

    public Wishlist() {}

    public Wishlist(int id, int userId, int programId, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.programId = programId;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
