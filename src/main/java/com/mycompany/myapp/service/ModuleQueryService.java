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

import com.mycompany.myapp.domain.Module;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ModuleRepository;
import com.mycompany.myapp.service.dto.ModuleCriteria;
import com.mycompany.myapp.service.dto.ModuleDTO;
import com.mycompany.myapp.service.mapper.ModuleMapper;

/**
 * Service for executing complex queries for {@link Module} entities in the database.
 * The main input is a {@link ModuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ModuleDTO} or a {@link Page} of {@link ModuleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModuleQueryService extends QueryService<Module> {

    private final Logger log = LoggerFactory.getLogger(ModuleQueryService.class);

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleQueryService(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    /**
     * Return a {@link List} of {@link ModuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ModuleDTO> findByCriteria(ModuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleMapper.toDto(moduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ModuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ModuleDTO> findByCriteria(ModuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleRepository.findAll(specification, page)
            .map(moduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleRepository.count(specification);
    }

    /**
     * Function to convert {@link ModuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Module> createSpecification(ModuleCriteria criteria) {
        Specification<Module> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Module_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Module_.name));
            }
        }
        return specification;
    }
}
