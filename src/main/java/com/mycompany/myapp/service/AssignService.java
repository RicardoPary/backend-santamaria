package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Assign;
import com.mycompany.myapp.repository.AssignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Assign.
 */
@Service
@Transactional
public class AssignService {

    private final Logger log = LoggerFactory.getLogger(AssignService.class);

    private final AssignRepository assignRepository;

    public AssignService(AssignRepository assignRepository) {
        this.assignRepository = assignRepository;
    }

    /**
     * Save a assign.
     *
     * @param assign the entity to save
     * @return the persisted entity
     */
    public Assign save(Assign assign) {
        log.debug("Request to save Assign : {}", assign);
        return assignRepository.save(assign);
    }

    /**
     * Get all the assigns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Assign> findAll(Pageable pageable) {
        log.debug("Request to get all Assigns");
        return assignRepository.findAll(pageable);
    }


    /**
     * Get one assign by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Assign> findOne(Long id) {
        log.debug("Request to get Assign : {}", id);
        return assignRepository.findById(id);
    }

    /**
     * Delete the assign by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Assign : {}", id);
        assignRepository.deleteById(id);
    }
}
