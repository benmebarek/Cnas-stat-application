package com.cnas.stat.web.rest;

import com.cnas.stat.domain.Assures;
import com.cnas.stat.service.AssuresService;
import com.cnas.stat.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cnas.stat.domain.Assures}.
 */
@RestController
@RequestMapping("/api")
public class AssuresResource {

    private final Logger log = LoggerFactory.getLogger(AssuresResource.class);

    private static final String ENTITY_NAME = "assures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssuresService assuresService;

    public AssuresResource(AssuresService assuresService) {
        this.assuresService = assuresService;
    }

    /**
     * {@code POST  /assures} : Create a new assures.
     *
     * @param assures the assures to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assures, or with status {@code 400 (Bad Request)} if the assures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assures")
    public ResponseEntity<Assures> createAssures(@RequestBody Assures assures) throws URISyntaxException {
        log.debug("REST request to save Assures : {}", assures);
        if (assures.getId() != null) {
            throw new BadRequestAlertException("A new assures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assures result = assuresService.save(assures);
        return ResponseEntity.created(new URI("/api/assures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assures} : Updates an existing assures.
     *
     * @param assures the assures to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assures,
     * or with status {@code 400 (Bad Request)} if the assures is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assures couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assures")
    public ResponseEntity<Assures> updateAssures(@RequestBody Assures assures) throws URISyntaxException {
        log.debug("REST request to update Assures : {}", assures);
        if (assures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Assures result = assuresService.save(assures);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assures.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assures} : get all the assures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assures in body.
     */
    @GetMapping("/assures")
    public List<Assures> getAllAssures() {
        log.debug("REST request to get all Assures");
        return assuresService.findAll();
    }

    /**
     * {@code GET  /assures/:id} : get the "id" assures.
     *
     * @param id the id of the assures to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assures, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assures/{id}")
    public ResponseEntity<Assures> getAssures(@PathVariable Long id) {
        log.debug("REST request to get Assures : {}", id);
        Optional<Assures> assures = assuresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assures);
    }

    /**
     * {@code DELETE  /assures/:id} : delete the "id" assures.
     *
     * @param id the id of the assures to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assures/{id}")
    public ResponseEntity<Void> deleteAssures(@PathVariable Long id) {
        log.debug("REST request to delete Assures : {}", id);
        assuresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
