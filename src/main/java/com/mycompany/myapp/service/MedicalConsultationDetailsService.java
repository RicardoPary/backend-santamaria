package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MedicalConsultationDetails;
import com.mycompany.myapp.repository.MedicalConsultationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MedicalConsultationDetails.
 */
@Service
@Transactional
public class MedicalConsultationDetailsService {

    private final Logger log = LoggerFactory.getLogger(MedicalConsultationDetailsService.class);

    private final MedicalConsultationDetailsRepository medicalConsultationDetailsRepository;

    public MedicalConsultationDetailsService(MedicalConsultationDetailsRepository medicalConsultationDetailsRepository) {
        this.medicalConsultationDetailsRepository = medicalConsultationDetailsRepository;
    }

    /**
     * Save a medicalConsultationDetails.
     *
     * @param medicalConsultationDetails the entity to save
     * @return the persisted entity
     */
    public MedicalConsultationDetails save(MedicalConsultationDetails medicalConsultationDetails) {
        log.debug("Request to save MedicalConsultationDetails : {}", medicalConsultationDetails);
        return medicalConsultationDetailsRepository.save(medicalConsultationDetails);
    }

    /**
     * Get all the medicalConsultationDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MedicalConsultationDetails> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalConsultationDetails");
        return medicalConsultationDetailsRepository.findAll(pageable);
    }


    /**
     * Get one medicalConsultationDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MedicalConsultationDetails> findOne(Long id) {
        log.debug("Request to get MedicalConsultationDetails : {}", id);
        return medicalConsultationDetailsRepository.findById(id);
    }

    /**
     * Delete the medicalConsultationDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalConsultationDetails : {}", id);
        medicalConsultationDetailsRepository.deleteById(id);
    }
}
