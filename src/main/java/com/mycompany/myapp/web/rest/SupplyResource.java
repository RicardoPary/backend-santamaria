package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Supply;
import com.mycompany.myapp.service.SupplyService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.SupplyCriteria;
import com.mycompany.myapp.service.SupplyQueryService;
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
 * REST controller for managing Supply.
 */
@RestController
@RequestMapping("/api")
public class SupplyResource {

    private final Logger log = LoggerFactory.getLogger(SupplyResource.class);

    private static final String ENTITY_NAME = "supply";

    private final SupplyService supplyService;

    private final SupplyQueryService supplyQueryService;

    public SupplyResource(SupplyService supplyService, SupplyQueryService supplyQueryService) {
        this.supplyService = supplyService;
        this.supplyQueryService = supplyQueryService;
    }

    /**
     * POST  /supplies : Create a new supply.
     *
     * @param supply the supply to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supply, or with status 400 (Bad Request) if the supply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplies")
    @Timed
    public ResponseEntity<Supply> createSupply(@RequestBody Supply supply) throws URISyntaxException {
        log.debug("REST request to save Supply : {}", supply);
        if (supply.getId() != null) {
            throw new BadRequestAlertException("A new supply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Supply result = supplyService.save(supply);
        return ResponseEntity.created(new URI("/api/supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplies : Updates an existing supply.
     *
     * @param supply the supply to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supply,
     * or with status 400 (Bad Request) if the supply is not valid,
     * or with status 500 (Internal Server Error) if the supply couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplies")
    @Timed
    public ResponseEntity<Supply> updateSupply(@RequestBody Supply supply) throws URISyntaxException {
        log.debug("REST request to update Supply : {}", supply);
        if (supply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Supply result = supplyService.save(supply);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supply.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplies : get all the supplies.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplies in body
     */
    @GetMapping("/supplies")
    @Timed
    public ResponseEntity<List<Supply>> getAllSupplies(SupplyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Supplies by criteria: {}", criteria);
        Page<Supply> page = supplyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supplies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supplies/count : count all the supplies.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supplies/count")
    @Timed
    public ResponseEntity<Long> countSupplies(SupplyCriteria criteria) {
        log.debug("REST request to count Supplies by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supplies/:id : get the "id" supply.
     *
     * @param id the id of the supply to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supply, or with status 404 (Not Found)
     */
    @GetMapping("/supplies/{id}")
    @Timed
    public ResponseEntity<Supply> getSupply(@PathVariable Long id) {
        log.debug("REST request to get Supply : {}", id);
        Optional<Supply> supply = supplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supply);
    }

    /**
     * DELETE  /supplies/:id : delete the "id" supply.
     *
     * @param id the id of the supply to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplies/{id}")
    @Timed
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        log.debug("REST request to delete Supply : {}", id);
        supplyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
