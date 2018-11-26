package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Assign;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Assign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignRepository extends JpaRepository<Assign, Long> {

}
