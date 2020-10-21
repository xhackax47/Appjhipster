package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Composant;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ComposantRepository;
import com.mycompany.myapp.service.dto.ComposantCriteria;
import com.mycompany.myapp.service.dto.ComposantDTO;
import com.mycompany.myapp.service.mapper.ComposantMapper;

/**
 * Service for executing complex queries for {@link Composant} entities in the database.
 * The main input is a {@link ComposantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComposantDTO} or a {@link Page} of {@link ComposantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComposantQueryService extends QueryService<Composant> {

    private final Logger log = LoggerFactory.getLogger(ComposantQueryService.class);

    private final ComposantRepository composantRepository;

    private final ComposantMapper composantMapper;

    public ComposantQueryService(ComposantRepository composantRepository, ComposantMapper composantMapper) {
        this.composantRepository = composantRepository;
        this.composantMapper = composantMapper;
    }

    /**
     * Return a {@link List} of {@link ComposantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComposantDTO> findByCriteria(ComposantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Composant> specification = createSpecification(criteria);
        return composantMapper.toDto(composantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComposantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComposantDTO> findByCriteria(ComposantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Composant> specification = createSpecification(criteria);
        return composantRepository.findAll(specification, page)
            .map(composantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComposantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Composant> specification = createSpecification(criteria);
        return composantRepository.count(specification);
    }

    /**
     * Function to convert {@link ComposantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Composant> createSpecification(ComposantCriteria criteria) {
        Specification<Composant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Composant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Composant_.name));
            }
        }
        return specification;
    }
}
