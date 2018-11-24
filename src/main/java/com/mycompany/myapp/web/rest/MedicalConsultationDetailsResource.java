package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MedicalConsultationDetails;
import com.mycompany.myapp.service.MedicalConsultationDetailsService;
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
 * REST controller for managing MedicalConsultationDetails.
 */
@RestController
@RequestMapping("/api")
public class MedicalConsultationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MedicalConsultationDetailsResource.class);

    private static final String ENTITY_NAME = "medicalConsultationDetails";

    private final MedicalConsultationDetailsService medicalConsultationDetailsService;

    public MedicalConsultationDetailsResource(MedicalConsultationDetailsService medicalConsultationDetailsService) {
        this.medicalConsultationDetailsService = medicalConsultationDetailsService;
    }

    /**
     * POST  /medical-consultation-details : Create a new medicalConsultationDetails.
     *
     * @param medicalConsultationDetails the medicalConsultationDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalConsultationDetails, or with status 400 (Bad Request) if the medicalConsultationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-consultation-details")
    @Timed
    public ResponseEntity<MedicalConsultationDetails> createMedicalConsultationDetails(@RequestBody MedicalConsultationDetails medicalConsultationDetails) throws URISyntaxException {
        log.debug("REST request to save MedicalConsultationDetails : {}", medicalConsultationDetails);
        if (medicalConsultationDetails.getId() != null) {
            throw new BadRequestAlertException("A new medicalConsultationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalConsultationDetails result = medicalConsultationDetailsService.save(medicalConsultationDetails);
        return ResponseEntity.created(new URI("/api/medical-consultation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-consultation-details : Updates an existing medicalConsultationDetails.
     *
     * @param medicalConsultationDetails the medicalConsultationDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalConsultationDetails,
     * or with status 400 (Bad Request) if the medicalConsultationDetails is not valid,
     * or with status 500 (Internal Server Error) if the medicalConsultationDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-consultation-details")
    @Timed
    public ResponseEntity<MedicalConsultationDetails> updateMedicalConsultationDetails(@RequestBody MedicalConsultationDetails medicalConsultationDetails) throws URISyntaxException {
        log.debug("REST request to update MedicalConsultationDetails : {}", medicalConsultationDetails);
        if (medicalConsultationDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalConsultationDetails result = medicalConsultationDetailsService.save(medicalConsultationDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalConsultationDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-consultation-details : get all the medicalConsultationDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalConsultationDetails in body
     */
    @GetMapping("/medical-consultation-details")
    @Timed
    public ResponseEntity<List<MedicalConsultationDetails>> getAllMedicalConsultationDetails(Pageable pageable) {
        log.debug("REST request to get a page of MedicalConsultationDetails");
        Page<MedicalConsultationDetails> page = medicalConsultationDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-consultation-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /medical-consultation-details/:id : get the "id" medicalConsultationDetails.
     *
     * @param id the id of the medicalConsultationDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalConsultationDetails, or with status 404 (Not Found)
     */
    @GetMapping("/medical-consultation-details/{id}")
    @Timed
    public ResponseEntity<MedicalConsultationDetails> getMedicalConsultationDetails(@PathVariable Long id) {
        log.debug("REST request to get MedicalConsultationDetails : {}", id);
        Optional<MedicalConsultationDetails> medicalConsultationDetails = medicalConsultationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalConsultationDetails);
    }

    /**
     * DELETE  /medical-consultation-details/:id : delete the "id" medicalConsultationDetails.
     *
     * @param id the id of the medicalConsultationDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-consultation-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalConsultationDetails(@PathVariable Long id) {
        log.debug("REST request to delete MedicalConsultationDetails : {}", id);
        medicalConsultationDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
