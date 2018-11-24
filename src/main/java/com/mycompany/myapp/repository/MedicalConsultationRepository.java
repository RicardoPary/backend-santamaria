package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MedicalConsultation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalConsultation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConsultationRepository extends JpaRepository<MedicalConsultation, Long> {

}
