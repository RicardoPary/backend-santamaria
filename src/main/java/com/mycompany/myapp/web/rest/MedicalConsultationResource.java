package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MedicalConsultation;
import com.mycompany.myapp.service.MedicalConsultationService;
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
 * REST controller for managing MedicalConsultation.
 */
@RestController
@RequestMapping("/api")
public class MedicalConsultationResource {

    private final Logger log = LoggerFactory.getLogger(MedicalConsultationResource.class);

    private static final String ENTITY_NAME = "medicalConsultation";

    private final MedicalConsultationService medicalConsultationService;

    public MedicalConsultationResource(MedicalConsultationService medicalConsultationService) {
        this.medicalConsultationService = medicalConsultationService;
    }

    /**
     * POST  /medical-consultations : Create a new medicalConsultation.
     *
     * @param medicalConsultation the medicalConsultation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalConsultation, or with status 400 (Bad Request) if the medicalConsultation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-consultations")
    @Timed
    public ResponseEntity<MedicalConsultation> createMedicalConsultation(@RequestBody MedicalConsultation medicalConsultation) throws URISyntaxException {
        log.debug("REST request to save MedicalConsultation : {}", medicalConsultation);
        if (medicalConsultation.getId() != null) {
            throw new BadRequestAlertException("A new medicalConsultation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalConsultation result = medicalConsultationService.save(medicalConsultation);
        return ResponseEntity.created(new URI("/api/medical-consultations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-consultations : Updates an existing medicalConsultation.
     *
     * @param medicalConsultation the medicalConsultation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalConsultation,
     * or with status 400 (Bad Request) if the medicalConsultation is not valid,
     * or with status 500 (Internal Server Error) if the medicalConsultation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-consultations")
    @Timed
    public ResponseEntity<MedicalConsultation> updateMedicalConsultation(@RequestBody MedicalConsultation medicalConsultation) throws URISyntaxException {
        log.debug("REST request to update MedicalConsultation : {}", medicalConsultation);
        if (medicalConsultation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalConsultation result = medicalConsultationService.save(medicalConsultation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalConsultation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-consultations : get all the medicalConsultations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalConsultations in body
     */
    @GetMapping("/medical-consultations")
    @Timed
    public ResponseEntity<List<MedicalConsultation>> getAllMedicalConsultations(Pageable pageable) {
        log.debug("REST request to get a page of MedicalConsultations");
        Page<MedicalConsultation> page = medicalConsultationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-consultations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-consultations/:id : get the "id" medicalConsultation.
     *
     * @param id the id of the medicalConsultation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalConsultation, or with status 404 (Not Found)
     */
    @GetMapping("/medical-consultations/{id}")
    @Timed
    public ResponseEntity<MedicalConsultation> getMedicalConsultation(@PathVariable Long id) {
        log.debug("REST request to get MedicalConsultation : {}", id);
        Optional<MedicalConsultation> medicalConsultation = medicalConsultationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalConsultation);
    }

    /**
     * DELETE  /medical-consultations/:id : delete the "id" medicalConsultation.
     *
     * @param id the id of the medicalConsultation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-consultations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalConsultation(@PathVariable Long id) {
        log.debug("REST request to delete MedicalConsultation : {}", id);
        medicalConsultationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
