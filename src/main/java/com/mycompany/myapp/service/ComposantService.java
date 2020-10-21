package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ComposantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Composant}.
 */
public interface ComposantService {

    /**
     * Save a composant.
     *
     * @param composantDTO the entity to save.
     * @return the persisted entity.
     */
    ComposantDTO save(ComposantDTO composantDTO);

    /**
     * Get all the composants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComposantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" composant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComposantDTO> findOne(Long id);

    /**
     * Delete the "id" composant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
