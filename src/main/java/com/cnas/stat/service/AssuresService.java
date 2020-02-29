package com.cnas.stat.service;

import com.cnas.stat.domain.Assures;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Assures}.
 */
public interface AssuresService {

    /**
     * Save a assures.
     *
     * @param assures the entity to save.
     * @return the persisted entity.
     */
    Assures save(Assures assures);

    /**
     * Get all the assures.
     *
     * @return the list of entities.
     */
    List<Assures> findAll();

    /**
     * Get the "id" assures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Assures> findOne(Long id);

    /**
     * Delete the "id" assures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
