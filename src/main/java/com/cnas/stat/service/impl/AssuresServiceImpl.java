package com.cnas.stat.service.impl;

import com.cnas.stat.service.AssuresService;
import com.cnas.stat.domain.Assures;
import com.cnas.stat.repository.AssuresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Assures}.
 */
@Service
@Transactional
public class AssuresServiceImpl implements AssuresService {

    private final Logger log = LoggerFactory.getLogger(AssuresServiceImpl.class);

    private final AssuresRepository assuresRepository;

    public AssuresServiceImpl(AssuresRepository assuresRepository) {
        this.assuresRepository = assuresRepository;
    }

    /**
     * Save a assures.
     *
     * @param assures the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Assures save(Assures assures) {
        log.debug("Request to save Assures : {}", assures);
        return assuresRepository.save(assures);
    }

    /**
     * Get all the assures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Assures> findAll() {
        log.debug("Request to get all Assures");
        return assuresRepository.findAll();
    }

    /**
     * Get one assures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Assures> findOne(Long id) {
        log.debug("Request to get Assures : {}", id);
        return assuresRepository.findById(id);
    }

    /**
     * Delete the assures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assures : {}", id);
        assuresRepository.deleteById(id);
    }
}
