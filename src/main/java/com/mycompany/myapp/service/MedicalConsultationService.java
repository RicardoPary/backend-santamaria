package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MedicalConsultation;
import com.mycompany.myapp.repository.MedicalConsultationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MedicalConsultation.
 */
@Service
@Transactional
public class MedicalConsultationService {

    private final Logger log = LoggerFactory.getLogger(MedicalConsultationService.class);

    private final MedicalConsultationRepository medicalConsultationRepository;

    public MedicalConsultationService(MedicalConsultationRepository medicalConsultationRepository) {
        this.medicalConsultationRepository = medicalConsultationRepository;
    }

    /**
     * Save a medicalConsultation.
     *
     * @param medicalConsultation the entity to save
     * @return the persisted entity
     */
    public MedicalConsultation save(MedicalConsultation medicalConsultation) {
        log.debug("Request to save MedicalConsultation : {}", medicalConsultation);
        return medicalConsultationRepository.save(medicalConsultation);
    }

    /**
     * Get all the medicalConsultations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MedicalConsultation> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalConsultations");
        return medicalConsultationRepository.findAll(pageable);
    }


    /**
     * Get one medicalConsultation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicalConsultation> findOne(Long id) {
        log.debug("Request to get MedicalConsultation : {}", id);
        return medicalConsultationRepository.findById(id);
    }

    /**
     * Delete the medicalConsultation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalConsultation : {}", id);
        medicalConsultationRepository.deleteById(id);
    }
}
