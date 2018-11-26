package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Assign;
import com.mycompany.myapp.service.AssignService;
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
 * REST controller for managing Assign.
 */
@RestController
@RequestMapping("/api")
public class AssignResource {

    private final Logger log = LoggerFactory.getLogger(AssignResource.class);

    private static final String ENTITY_NAME = "assign";

    private final AssignService assignService;

    public AssignResource(AssignService assignService) {
        this.assignService = assignService;
    }

    /**
     * POST  /assigns : Create a new assign.
     *
     * @param assign the assign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assign, or with status 400 (Bad Request) if the assign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assigns")
    @Timed
    public ResponseEntity<Assign> createAssign(@RequestBody Assign assign) throws URISyntaxException {
        log.debug("REST request to save Assign : {}", assign);
        if (assign.getId() != null) {
            throw new BadRequestAlertException("A new assign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assign result = assignService.save(assign);
        return ResponseEntity.created(new URI("/api/assigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assigns : Updates an existing assign.
     *
     * @param assign the assign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assign,
     * or with status 400 (Bad Request) if the assign is not valid,
     * or with status 500 (Internal Server Error) if the assign couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assigns")
    @Timed
    public ResponseEntity<Assign> updateAssign(@RequestBody Assign assign) throws URISyntaxException {
        log.debug("REST request to update Assign : {}", assign);
        if (assign.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Assign result = assignService.save(assign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assigns : get all the assigns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assigns in body
     */
    @GetMapping("/assigns")
    @Timed
    public ResponseEntity<List<Assign>> getAllAssigns(Pageable pageable) {
        log.debug("REST request to get a page of Assigns");
        Page<Assign> page = assignService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assigns");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /assigns/:id : get the "id" assign.
     *
     * @param id the id of the assign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assign, or with status 404 (Not Found)
     */
    @GetMapping("/assigns/{id}")
    @Timed
    public ResponseEntity<Assign> getAssign(@PathVariable Long id) {
        log.debug("REST request to get Assign : {}", id);
        Optional<Assign> assign = assignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assign);
    }

    /**
     * DELETE  /assigns/:id : delete the "id" assign.
     *
     * @param id the id of the assign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssign(@PathVariable Long id) {
        log.debug("REST request to delete Assign : {}", id);
        assignService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
