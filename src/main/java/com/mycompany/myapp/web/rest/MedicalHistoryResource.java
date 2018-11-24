package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MedicalHistory;
import com.mycompany.myapp.service.MedicalHistoryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MedicalHistory.
 */
@RestController
@RequestMapping("/api")
public class MedicalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryResource.class);

    private static final String ENTITY_NAME = "medicalHistory";

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryResource(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    /**
     * POST  /medical-histories : Create a new medicalHistory.
     *
     * @param medicalHistory the medicalHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalHistory, or with status 400 (Bad Request) if the medicalHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistory> createMedicalHistory(@RequestBody MedicalHistory medicalHistory) throws URISyntaxException {
        log.debug("REST request to save MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() != null) {
            throw new BadRequestAlertException("A new medicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalHistory result = medicalHistoryService.save(medicalHistory);
        return ResponseEntity.created(new URI("/api/medical-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-histories : Updates an existing medicalHistory.
     *
     * @param medicalHistory the medicalHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalHistory,
     * or with status 400 (Bad Request) if the medicalHistory is not valid,
     * or with status 500 (Internal Server Error) if the medicalHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-histories")
    @Timed
    public ResponseEntity<MedicalHistory> updateMedicalHistory(@RequestBody MedicalHistory medicalHistory) throws URISyntaxException {
        log.debug("REST request to update MedicalHistory : {}", medicalHistory);
        if (medicalHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalHistory result = medicalHistoryService.save(medicalHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-histories : get all the medicalHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalHistories in body
     */
    @GetMapping("/medical-histories")
    @Timed
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistories(Pageable pageable) {
        log.debug("REST request to get a page of MedicalHistories");
        Page<MedicalHistory> page = medicalHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-histories/:id : get the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalHistory, or with status 404 (Not Found)
     */
    @GetMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<MedicalHistory> getMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to get MedicalHistory : {}", id);
        Optional<MedicalHistory> medicalHistory = medicalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalHistory);
    }

    /**
     * DELETE  /medical-histories/:id : delete the "id" medicalHistory.
     *
     * @param id the id of the medicalHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete MedicalHistory : {}", id);
        medicalHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
