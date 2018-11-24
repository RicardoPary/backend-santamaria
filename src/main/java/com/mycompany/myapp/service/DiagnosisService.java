package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Diagnosis;
import com.mycompany.myapp.repository.DiagnosisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Diagnosis.
 */
@Service
@Transactional
public class DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    /**
     * Save a diagnosis.
     *
     * @param diagnosis the entity to save
     * @return the persisted entity
     */
    public Diagnosis save(Diagnosis diagnosis) {
        log.debug("Request to save Diagnosis : {}", diagnosis);
        return diagnosisRepository.save(diagnosis);
    }

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Diagnosis> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnoses");
        return diagnosisRepository.findAll(pageable);
    }


    /**
     * Get one diagnosis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Diagnosis> findOne(Long id) {
        log.debug("Request to get Diagnosis : {}", id);
        return diagnosisRepository.findById(id);
    }

    /**
     * Delete the diagnosis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diagnosis : {}", id);
        diagnosisRepository.deleteById(id);
    }
}
