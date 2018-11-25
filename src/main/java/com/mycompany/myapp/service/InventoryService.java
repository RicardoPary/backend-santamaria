package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Inventory;
import com.mycompany.myapp.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Inventory.
 */
@Service
@Transactional
public class InventoryService {

    private final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Save a inventory.
     *
     * @param inventory the entity to save
     * @return the persisted entity
     */
    public Inventory save(Inventory inventory) {
        log.debug("Request to save Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    /**
     * Get all the inventories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inventory> findAll(Pageable pageable) {
        log.debug("Request to get all Inventories");
        return inventoryRepository.findAll(pageable);
    }


    /**
     * Get one inventory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Inventory> findOne(Long id) {
        log.debug("Request to get Inventory : {}", id);
        return inventoryRepository.findById(id);
    }

    /**
     * Delete the inventory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventory : {}", id);
        inventoryRepository.deleteById(id);
    }


    /**
     * ======================================== Others Methods
     */

    /**
     * By Ricardo Pari
     * Get all inventories by id provider.
     *
     * @param pageable the pagination information nad idProvider
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inventory> getAllByIdProvider(Pageable pageable, Long id) {
        log.debug("Request to get all Inventories by id Provider");
        return inventoryRepository.getAllByIdProvider(pageable, id);
    }


}
