package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Staff;
import com.mycompany.myapp.repository.StaffRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Staff.
 */
@Service
@Transactional
public class StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffService.class);

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * Save a staff.
     *
     * @param staff the entity to save
     * @return the persisted entity
     */
    public Staff save(Staff staff) {
        log.debug("Request to save Staff : {}", staff);
        return staffRepository.save(staff);
    }

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Staff> findAll(Pageable pageable) {
        log.debug("Request to get all Staff");
        return staffRepository.findAll(pageable);
    }


    /**
     * Get one staff by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Staff> findOne(Long id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id);
    }

    /**
     * Delete the staff by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
    }
}
