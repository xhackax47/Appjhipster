package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ComposantService;
import com.mycompany.myapp.domain.Composant;
import com.mycompany.myapp.repository.ComposantRepository;
import com.mycompany.myapp.service.dto.ComposantDTO;
import com.mycompany.myapp.service.mapper.ComposantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Composant}.
 */
@Service
@Transactional
public class ComposantServiceImpl implements ComposantService {

    private final Logger log = LoggerFactory.getLogger(ComposantServiceImpl.class);

    private final ComposantRepository composantRepository;

    private final ComposantMapper composantMapper;

    public ComposantServiceImpl(ComposantRepository composantRepository, ComposantMapper composantMapper) {
        this.composantRepository = composantRepository;
        this.composantMapper = composantMapper;
    }

    @Override
    public ComposantDTO save(ComposantDTO composantDTO) {
        log.debug("Request to save Composant : {}", composantDTO);
        Composant composant = composantMapper.toEntity(composantDTO);
        composant = composantRepository.save(composant);
        return composantMapper.toDto(composant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComposantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Composants");
        return composantRepository.findAll(pageable)
            .map(composantMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ComposantDTO> findOne(Long id) {
        log.debug("Request to get Composant : {}", id);
        return composantRepository.findById(id)
            .map(composantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Composant : {}", id);
        composantRepository.deleteById(id);
    }
}
