package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TypeAttention;
import com.mycompany.myapp.service.TypeAttentionService;
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
 * REST controller for managing TypeAttention.
 */
@RestController
@RequestMapping("/api")
public class TypeAttentionResource {

    private final Logger log = LoggerFactory.getLogger(TypeAttentionResource.class);

    private static final String ENTITY_NAME = "typeAttention";

    private final TypeAttentionService typeAttentionService;

    public TypeAttentionResource(TypeAttentionService typeAttentionService) {
        this.typeAttentionService = typeAttentionService;
    }

    /**
     * POST  /type-attentions : Create a new typeAttention.
     *
     * @param typeAttention the typeAttention to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeAttention, or with status 400 (Bad Request) if the typeAttention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-attentions")
    @Timed
    public ResponseEntity<TypeAttention> createTypeAttention(@RequestBody TypeAttention typeAttention) throws URISyntaxException {
        log.debug("REST request to save TypeAttention : {}", typeAttention);
        if (typeAttention.getId() != null) {
            throw new BadRequestAlertException("A new typeAttention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAttention result = typeAttentionService.save(typeAttention);
        return ResponseEntity.created(new URI("/api/type-attentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-attentions : Updates an existing typeAttention.
     *
     * @param typeAttention the typeAttention to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeAttention,
     * or with status 400 (Bad Request) if the typeAttention is not valid,
     * or with status 500 (Internal Server Error) if the typeAttention couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-attentions")
    @Timed
    public ResponseEntity<TypeAttention> updateTypeAttention(@RequestBody TypeAttention typeAttention) throws URISyntaxException {
        log.debug("REST request to update TypeAttention : {}", typeAttention);
        if (typeAttention.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeAttention result = typeAttentionService.save(typeAttention);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeAttention.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-attentions : get all the typeAttentions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeAttentions in body
     */
    @GetMapping("/type-attentions")
    @Timed
    public ResponseEntity<List<TypeAttention>> getAllTypeAttentions(Pageable pageable) {
        log.debug("REST request to get a page of TypeAttentions");
        Page<TypeAttention> page = typeAttentionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-attentions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /type-attentions/:id : get the "id" typeAttention.
     *
     * @param id the id of the typeAttention to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeAttention, or with status 404 (Not Found)
     */
    @GetMapping("/type-attentions/{id}")
    @Timed
    public ResponseEntity<TypeAttention> getTypeAttention(@PathVariable Long id) {
        log.debug("REST request to get TypeAttention : {}", id);
        Optional<TypeAttention> typeAttention = typeAttentionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAttention);
    }

    /**
     * DELETE  /type-attentions/:id : delete the "id" typeAttention.
     *
     * @param id the id of the typeAttention to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-attentions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeAttention(@PathVariable Long id) {
        log.debug("REST request to delete TypeAttention : {}", id);
        typeAttentionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
