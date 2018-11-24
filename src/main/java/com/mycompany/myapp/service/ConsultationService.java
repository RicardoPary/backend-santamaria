package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Consultation;
import com.mycompany.myapp.repository.ConsultationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Consultation.
 */
@Service
@Transactional
public class ConsultationService {

    private final Logger log = LoggerFactory.getLogger(ConsultationService.class);

    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    /**
     * Save a consultation.
     *
     * @param consultation the entity to save
     * @return the persisted entity
     */
    public Consultation save(Consultation consultation) {
        log.debug("Request to save Consultation : {}", consultation);
        return consultationRepository.save(consultation);
    }

    /**
     * Get all the consultations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Consultation> findAll(Pageable pageable) {
        log.debug("Request to get all Consultations");
        return consultationRepository.findAll(pageable);
    }


    /**
     * Get one consultation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Consultation> findOne(Long id) {
        log.debug("Request to get Consultation : {}", id);
        return consultationRepository.findById(id);
    }

    /**
     * Delete the consultation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Consultation : {}", id);
        consultationRepository.deleteById(id);
    }
}
