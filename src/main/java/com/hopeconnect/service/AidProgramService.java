package com.hopeconnect.service;

import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.util.ValidationUtil;

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
        validate(program, true);
        int id = aidProgramDAO.insert(program);
        if (id <= 0) throw new IllegalArgumentException("Program could not be created. Please check the slug is unique.");
        return id;
    }

    /**
     * Updates an existing program after validation.
     */
    public boolean update(AidProgram program) {
        validate(program, false);
        if (!aidProgramDAO.update(program)) throw new IllegalArgumentException("Program could not be updated");
        return true;
    }

    /**
     * Deletes a program by id.
     */
    public boolean delete(int id) {
        if (id <= 0) throw new IllegalArgumentException("Invalid program id");
        if (!aidProgramDAO.delete(id)) throw new IllegalArgumentException("Program could not be deleted");
        return true;
    }

    /**
     * Toggles published status of a program.
     */
    public boolean togglePublished(int id, boolean published) {
        AidProgram p = aidProgramDAO.findById(id);
        if (p == null) throw new IllegalArgumentException("Program not found");
        p.setPublished(published);
        if (!aidProgramDAO.update(p)) throw new IllegalArgumentException("Program status could not be changed");
        return true;
    }

    private void validate(AidProgram program, boolean requireSlug) {
        if (program == null) throw new IllegalArgumentException("Program cannot be null");
        if (program.getId() <= 0 && !requireSlug) throw new IllegalArgumentException("Program id is required");
        if (!ValidationUtil.hasTextWithin(program.getTitle(), 255)) throw new IllegalArgumentException("Title is required and must be 255 characters or less");
        if (requireSlug && !ValidationUtil.isValidSlug(program.getSlug())) throw new IllegalArgumentException("Slug is required and may contain lowercase letters, numbers, and hyphens only");
        if (program.getSlug() != null && !program.getSlug().trim().isEmpty() && !ValidationUtil.isValidSlug(program.getSlug())) {
            throw new IllegalArgumentException("Slug may contain lowercase letters, numbers, and hyphens only");
        }
        if (!ValidationUtil.hasTextWithin(program.getCategory(), 100)) throw new IllegalArgumentException("Category is required and must be 100 characters or less");
        if (!ValidationUtil.hasTextWithin(program.getEligibility(), 1000)) throw new IllegalArgumentException("Eligibility is required");
        if (!ValidationUtil.hasTextWithin(program.getDescription(), 5000)) throw new IllegalArgumentException("Description is required");
    }
}
