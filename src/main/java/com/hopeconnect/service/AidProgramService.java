package com.hopeconnect.service;

import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.util.ValidationUtil;

import java.sql.SQLException;

/**
 * AidProgramService
 * Business logic for managing aid programs.
 */
public class AidProgramService {

    private final AidProgramDAO aidProgramDAO = new AidProgramDAO();

    /**
     * Creates a new aid program after validating inputs.
     * @param program the program to create
     * @return generated id
     */
    public int create(AidProgram program) {
        if (program == null) throw new IllegalArgumentException("Program cannot be null");
        if (program.getTitle() == null || program.getTitle().trim().isEmpty()) throw new IllegalArgumentException("Title is required");
        if (program.getSlug() == null || program.getSlug().trim().isEmpty()) throw new IllegalArgumentException("Slug is required");
        if (program.getCapacity() != null && program.getCapacity() < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        if (program.getRemainingCapacity() == null) program.setRemainingCapacity(program.getCapacity());
        if (program.getProgramStatus() == null || program.getProgramStatus().trim().isEmpty()) program.setProgramStatus("open");
        return aidProgramDAO.insert(program);
    }

    /**
     * Updates an existing program after validation.
     */
    public boolean update(AidProgram program) {
        if (program == null) throw new IllegalArgumentException("Program cannot be null");
        if (program.getId() <= 0) throw new IllegalArgumentException("Program id is required");
        if (program.getCapacity() != null && program.getCapacity() < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        return aidProgramDAO.update(program);
    }

    /**
     * Deletes a program by id.
     */
    public boolean delete(int id) {
        if (id <= 0) throw new IllegalArgumentException("Invalid program id");
        return aidProgramDAO.delete(id);
    }

    /**
     * Toggles published status of a program.
     */
    public boolean togglePublished(int id, boolean published) {
        AidProgram p = aidProgramDAO.findById(id);
        if (p == null) throw new IllegalArgumentException("Program not found");
        p.setPublished(published);
        return aidProgramDAO.update(p);
    }

    public boolean updateStatus(int id, String status) {
        if (!"open".equals(status) && !"closed".equals(status) && !"archived".equals(status)) {
            throw new IllegalArgumentException("Invalid program status");
        }
        return aidProgramDAO.updateStatus(id, status);
    }
}
