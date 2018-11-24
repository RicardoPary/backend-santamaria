package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MedicalHistory;
import com.mycompany.myapp.repository.MedicalHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MedicalHistory.
 */
@Service
@Transactional
public class MedicalHistoryService {

    private final Logger log = LoggerFactory.getLogger(MedicalHistoryService.class);

    private final MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    /**
     * Save a medicalHistory.
     *
     * @param medicalHistory the entity to save
     * @return the persisted entity
     */
    public MedicalHistory save(MedicalHistory medicalHistory) {
        log.debug("Request to save MedicalHistory : {}", medicalHistory);
        return medicalHistoryRepository.save(medicalHistory);
    }

    /**
     * Get all the medicalHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MedicalHistory> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalHistories");
        return medicalHistoryRepository.findAll(pageable);
    }


    /**
     * Get one medicalHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicalHistory> findOne(Long id) {
        log.debug("Request to get MedicalHistory : {}", id);
        return medicalHistoryRepository.findById(id);
    }

    /**
     * Delete the medicalHistory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalHistory : {}", id);
        medicalHistoryRepository.deleteById(id);
    }
}
