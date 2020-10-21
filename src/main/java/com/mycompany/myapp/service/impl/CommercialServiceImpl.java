package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CommercialService;
import com.mycompany.myapp.domain.Commercial;
import com.mycompany.myapp.repository.CommercialRepository;
import com.mycompany.myapp.service.dto.CommercialDTO;
import com.mycompany.myapp.service.mapper.CommercialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commercial}.
 */
@Service
@Transactional
public class CommercialServiceImpl implements CommercialService {

    private final Logger log = LoggerFactory.getLogger(CommercialServiceImpl.class);

    private final CommercialRepository commercialRepository;

    private final CommercialMapper commercialMapper;

    public CommercialServiceImpl(CommercialRepository commercialRepository, CommercialMapper commercialMapper) {
        this.commercialRepository = commercialRepository;
        this.commercialMapper = commercialMapper;
    }

    @Override
    public CommercialDTO save(CommercialDTO commercialDTO) {
        log.debug("Request to save Commercial : {}", commercialDTO);
        Commercial commercial = commercialMapper.toEntity(commercialDTO);
        commercial = commercialRepository.save(commercial);
        return commercialMapper.toDto(commercial);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommercialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commercials");
        return commercialRepository.findAll(pageable)
            .map(commercialMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CommercialDTO> findOne(Long id) {
        log.debug("Request to get Commercial : {}", id);
        return commercialRepository.findById(id)
            .map(commercialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commercial : {}", id);
        commercialRepository.deleteById(id);
    }
}
