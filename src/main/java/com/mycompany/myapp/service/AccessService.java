package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Access;
import com.mycompany.myapp.repository.AccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Access.
 */
@Service
@Transactional
public class AccessService {

    private final Logger log = LoggerFactory.getLogger(AccessService.class);

    private final AccessRepository accessRepository;

    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    /**
     * Save a access.
     *
     * @param access the entity to save
     * @return the persisted entity
     */
    public Access save(Access access) {
        log.debug("Request to save Access : {}", access);
        return accessRepository.save(access);
    }

    /**
     * Get all the accesses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Access> findAll() {
        log.debug("Request to get all Accesses");
        return accessRepository.findAll();
    }


    /**
     * Get one access by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Access> findOne(Long id) {
        log.debug("Request to get Access : {}", id);
        return accessRepository.findById(id);
    }

    /**
     * Delete the access by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Access : {}", id);
        accessRepository.deleteById(id);
    }
}
