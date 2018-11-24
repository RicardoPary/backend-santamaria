package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConsultationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsultationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultationDetailsRepository extends JpaRepository<ConsultationDetails, Long> {

}
