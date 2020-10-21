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

import com.mycompany.myapp.domain.Commercial;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CommercialRepository;
import com.mycompany.myapp.service.dto.CommercialCriteria;
import com.mycompany.myapp.service.dto.CommercialDTO;
import com.mycompany.myapp.service.mapper.CommercialMapper;

/**
 * Service for executing complex queries for {@link Commercial} entities in the database.
 * The main input is a {@link CommercialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialDTO} or a {@link Page} of {@link CommercialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialQueryService extends QueryService<Commercial> {

    private final Logger log = LoggerFactory.getLogger(CommercialQueryService.class);

    private final CommercialRepository commercialRepository;

    private final CommercialMapper commercialMapper;

    public CommercialQueryService(CommercialRepository commercialRepository, CommercialMapper commercialMapper) {
        this.commercialRepository = commercialRepository;
        this.commercialMapper = commercialMapper;
    }

    /**
     * Return a {@link List} of {@link CommercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialDTO> findByCriteria(CommercialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commercial> specification = createSpecification(criteria);
        return commercialMapper.toDto(commercialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialDTO> findByCriteria(CommercialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commercial> specification = createSpecification(criteria);
        return commercialRepository.findAll(specification, page)
            .map(commercialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commercial> specification = createSpecification(criteria);
        return commercialRepository.count(specification);
    }

    /**
     * Function to convert {@link CommercialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commercial> createSpecification(CommercialCriteria criteria) {
        Specification<Commercial> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commercial_.id));
            }
            if (criteria.getFirstname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstname(), Commercial_.firstname));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Commercial_.name));
            }
        }
        return specification;
    }
}
