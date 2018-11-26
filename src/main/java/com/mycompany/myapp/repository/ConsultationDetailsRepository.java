package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConsultationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Spring Data  repository for the ConsultationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultationDetailsRepository extends JpaRepository<ConsultationDetails, Long> {
    Page<ConsultationDetails> getAllByIdConsultation(Pageable pageable, Long id);
}
