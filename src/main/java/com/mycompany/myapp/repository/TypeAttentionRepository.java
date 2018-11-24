package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TypeAttention;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeAttention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAttentionRepository extends JpaRepository<TypeAttention, Long> {

}
