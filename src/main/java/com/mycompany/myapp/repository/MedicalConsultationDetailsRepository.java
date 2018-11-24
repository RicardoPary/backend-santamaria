package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MedicalConsultationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalConsultationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalConsultationDetailsRepository extends JpaRepository<MedicalConsultationDetails, Long> {

}
