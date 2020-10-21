package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Composant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Composant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComposantRepository extends JpaRepository<Composant, Long>, JpaSpecificationExecutor<Composant> {
}
