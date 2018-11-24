package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TypeAttention;
import com.mycompany.myapp.repository.TypeAttentionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeAttention.
 */
@Service
@Transactional
public class TypeAttentionService {

    private final Logger log = LoggerFactory.getLogger(TypeAttentionService.class);

    private final TypeAttentionRepository typeAttentionRepository;

    public TypeAttentionService(TypeAttentionRepository typeAttentionRepository) {
        this.typeAttentionRepository = typeAttentionRepository;
    }

    /**
     * Save a typeAttention.
     *
     * @param typeAttention the entity to save
     * @return the persisted entity
     */
    public TypeAttention save(TypeAttention typeAttention) {
        log.debug("Request to save TypeAttention : {}", typeAttention);
        return typeAttentionRepository.save(typeAttention);
    }

    /**
     * Get all the typeAttentions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeAttention> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAttentions");
        return typeAttentionRepository.findAll(pageable);
    }


    /**
     * Get one typeAttention by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeAttention> findOne(Long id) {
        log.debug("Request to get TypeAttention : {}", id);
        return typeAttentionRepository.findById(id);
    }

    /**
     * Delete the typeAttention by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAttention : {}", id);
        typeAttentionRepository.deleteById(id);
    }
}
