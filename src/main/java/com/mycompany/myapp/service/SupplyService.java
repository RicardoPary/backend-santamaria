package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Supply;
import com.mycompany.myapp.repository.SupplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Supply.
 */
@Service
@Transactional
public class SupplyService {

    private final Logger log = LoggerFactory.getLogger(SupplyService.class);

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    /**
     * Save a supply.
     *
     * @param supply the entity to save
     * @return the persisted entity
     */
    public Supply save(Supply supply) {
        log.debug("Request to save Supply : {}", supply);
        return supplyRepository.save(supply);
    }

    /**
     * Get all the supplies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Supply> findAll(Pageable pageable) {
        log.debug("Request to get all Supplies");
        return supplyRepository.findAll(pageable);
    }


    /**
     * Get one supply by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Supply> findOne(Long id) {
        log.debug("Request to get Supply : {}", id);
        return supplyRepository.findById(id);
    }

    /**
     * Delete the supply by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Supply : {}", id);
        supplyRepository.deleteById(id);
    }
}
