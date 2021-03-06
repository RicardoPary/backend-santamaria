package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Access;
import com.mycompany.myapp.service.AccessService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Access.
 */
@RestController
@RequestMapping("/api")
public class AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResource.class);

    private static final String ENTITY_NAME = "access";

    private final AccessService accessService;

    public AccessResource(AccessService accessService) {
        this.accessService = accessService;
    }

    /**
     * POST  /accesses : Create a new access.
     *
     * @param access the access to create
     * @return the ResponseEntity with status 201 (Created) and with body the new access, or with status 400 (Bad Request) if the access has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accesses")
    @Timed
    public ResponseEntity<Access> createAccess(@RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to save Access : {}", access);
        if (access.getId() != null) {
            throw new BadRequestAlertException("A new access cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Access result = accessService.save(access);
        return ResponseEntity.created(new URI("/api/accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accesses : Updates an existing access.
     *
     * @param access the access to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated access,
     * or with status 400 (Bad Request) if the access is not valid,
     * or with status 500 (Internal Server Error) if the access couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accesses")
    @Timed
    public ResponseEntity<Access> updateAccess(@RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to update Access : {}", access);
        if (access.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Access result = accessService.save(access);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, access.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accesses : get all the accesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accesses in body
     */
    @GetMapping("/accesses")
    @Timed
    public List<Access> getAllAccesses() {
        log.debug("REST request to get all Accesses");
        return accessService.findAll();
    }

    /**
     * GET  /accesses/:id : get the "id" access.
     *
     * @param id the id of the access to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the access, or with status 404 (Not Found)
     */
    @GetMapping("/accesses/{id}")
    @Timed
    public ResponseEntity<Access> getAccess(@PathVariable Long id) {
        log.debug("REST request to get Access : {}", id);
        Optional<Access> access = accessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(access);
    }

    /**
     * DELETE  /accesses/:id : delete the "id" access.
     *
     * @param id the id of the access to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccess(@PathVariable Long id) {
        log.debug("REST request to delete Access : {}", id);
        accessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
