package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ConsultationDetails;
import com.mycompany.myapp.service.ConsultationDetailsService;
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
 * REST controller for managing ConsultationDetails.
 */
@RestController
@RequestMapping("/api")
public class ConsultationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ConsultationDetailsResource.class);

    private static final String ENTITY_NAME = "consultationDetails";

    private final ConsultationDetailsService consultationDetailsService;

    public ConsultationDetailsResource(ConsultationDetailsService consultationDetailsService) {
        this.consultationDetailsService = consultationDetailsService;
    }

    /**
     * POST  /consultation-details : Create a new consultationDetails.
     *
     * @param consultationDetails the consultationDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultationDetails, or with status 400 (Bad Request) if the consultationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultation-details")
    @Timed
    public ResponseEntity<ConsultationDetails> createConsultationDetails(@RequestBody ConsultationDetails consultationDetails) throws URISyntaxException {
        log.debug("REST request to save ConsultationDetails : {}", consultationDetails);
        if (consultationDetails.getId() != null) {
            throw new BadRequestAlertException("A new consultationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultationDetails result = consultationDetailsService.save(consultationDetails);
        return ResponseEntity.created(new URI("/api/consultation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultation-details : Updates an existing consultationDetails.
     *
     * @param consultationDetails the consultationDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultationDetails,
     * or with status 400 (Bad Request) if the consultationDetails is not valid,
     * or with status 500 (Internal Server Error) if the consultationDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultation-details")
    @Timed
    public ResponseEntity<ConsultationDetails> updateConsultationDetails(@RequestBody ConsultationDetails consultationDetails) throws URISyntaxException {
        log.debug("REST request to update ConsultationDetails : {}", consultationDetails);
        if (consultationDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsultationDetails result = consultationDetailsService.save(consultationDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultationDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultation-details : get all the consultationDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultationDetails in body
     */
    @GetMapping("/consultation-details")
    @Timed
    public ResponseEntity<List<ConsultationDetails>> getAllConsultationDetails(Pageable pageable) {
        log.debug("REST request to get a page of ConsultationDetails");
        Page<ConsultationDetails> page = consultationDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultation-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /consultation-details/:id : get the "id" consultationDetails.
     *
     * @param id the id of the consultationDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultationDetails, or with status 404 (Not Found)
     */
    @GetMapping("/consultation-details/{id}")
    @Timed
    public ResponseEntity<ConsultationDetails> getConsultationDetails(@PathVariable Long id) {
        log.debug("REST request to get ConsultationDetails : {}", id);
        Optional<ConsultationDetails> consultationDetails = consultationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultationDetails);
    }

    /**
     * DELETE  /consultation-details/:id : delete the "id" consultationDetails.
     *
     * @param id the id of the consultationDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultation-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultationDetails(@PathVariable Long id) {
        log.debug("REST request to delete ConsultationDetails : {}", id);
        consultationDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
