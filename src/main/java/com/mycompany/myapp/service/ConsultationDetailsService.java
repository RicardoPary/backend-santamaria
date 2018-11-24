package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ConsultationDetails;
import com.mycompany.myapp.repository.ConsultationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ConsultationDetails.
 */
@Service
@Transactional
public class ConsultationDetailsService {

    private final Logger log = LoggerFactory.getLogger(ConsultationDetailsService.class);

    private final ConsultationDetailsRepository consultationDetailsRepository;

    public ConsultationDetailsService(ConsultationDetailsRepository consultationDetailsRepository) {
        this.consultationDetailsRepository = consultationDetailsRepository;
    }

    /**
     * Save a consultationDetails.
     *
     * @param consultationDetails the entity to save
     * @return the persisted entity
     */
    public ConsultationDetails save(ConsultationDetails consultationDetails) {
        log.debug("Request to save ConsultationDetails : {}", consultationDetails);
        return consultationDetailsRepository.save(consultationDetails);
    }

    /**
     * Get all the consultationDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConsultationDetails> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultationDetails");
        return consultationDetailsRepository.findAll(pageable);
    }


    /**
     * Get one consultationDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ConsultationDetails> findOne(Long id) {
        log.debug("Request to get ConsultationDetails : {}", id);
        return consultationDetailsRepository.findById(id);
    }

    /**
     * Delete the consultationDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsultationDetails : {}", id);
        consultationDetailsRepository.deleteById(id);
    }
}
