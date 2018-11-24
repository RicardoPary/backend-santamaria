package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Contract;
import com.mycompany.myapp.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Contract.
 */
@Service
@Transactional
public class ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractService.class);

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    /**
     * Save a contract.
     *
     * @param contract the entity to save
     * @return the persisted entity
     */
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);
        return contractRepository.save(contract);
    }

    /**
     * Get all the contracts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Contract> findAll(Pageable pageable) {
        log.debug("Request to get all Contracts");
        return contractRepository.findAll(pageable);
    }


    /**
     * Get one contract by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Contract> findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findById(id);
    }

    /**
     * Delete the contract by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.deleteById(id);
    }
}
