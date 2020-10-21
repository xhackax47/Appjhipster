package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CommercialService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CommercialDTO;
import com.mycompany.myapp.service.dto.CommercialCriteria;
import com.mycompany.myapp.service.CommercialQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Commercial}.
 */
@RestController
@RequestMapping("/api")
public class CommercialResource {

    private final Logger log = LoggerFactory.getLogger(CommercialResource.class);

    private static final String ENTITY_NAME = "commercial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommercialService commercialService;

    private final CommercialQueryService commercialQueryService;

    public CommercialResource(CommercialService commercialService, CommercialQueryService commercialQueryService) {
        this.commercialService = commercialService;
        this.commercialQueryService = commercialQueryService;
    }

    /**
     * {@code POST  /commercials} : Create a new commercial.
     *
     * @param commercialDTO the commercialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commercialDTO, or with status {@code 400 (Bad Request)} if the commercial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commercials")
    public ResponseEntity<CommercialDTO> createCommercial(@Valid @RequestBody CommercialDTO commercialDTO) throws URISyntaxException {
        log.debug("REST request to save Commercial : {}", commercialDTO);
        if (commercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialDTO result = commercialService.save(commercialDTO);
        return ResponseEntity.created(new URI("/api/commercials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commercials} : Updates an existing commercial.
     *
     * @param commercialDTO the commercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commercialDTO,
     * or with status {@code 400 (Bad Request)} if the commercialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commercials")
    public ResponseEntity<CommercialDTO> updateCommercial(@Valid @RequestBody CommercialDTO commercialDTO) throws URISyntaxException {
        log.debug("REST request to update Commercial : {}", commercialDTO);
        if (commercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialDTO result = commercialService.save(commercialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commercialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commercials} : get all the commercials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commercials in body.
     */
    @GetMapping("/commercials")
    public ResponseEntity<List<CommercialDTO>> getAllCommercials(CommercialCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commercials by criteria: {}", criteria);
        Page<CommercialDTO> page = commercialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commercials/count} : count all the commercials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commercials/count")
    public ResponseEntity<Long> countCommercials(CommercialCriteria criteria) {
        log.debug("REST request to count Commercials by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commercials/:id} : get the "id" commercial.
     *
     * @param id the id of the commercialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commercialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commercials/{id}")
    public ResponseEntity<CommercialDTO> getCommercial(@PathVariable Long id) {
        log.debug("REST request to get Commercial : {}", id);
        Optional<CommercialDTO> commercialDTO = commercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialDTO);
    }

    /**
     * {@code DELETE  /commercials/:id} : delete the "id" commercial.
     *
     * @param id the id of the commercialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commercials/{id}")
    public ResponseEntity<Void> deleteCommercial(@PathVariable Long id) {
        log.debug("REST request to delete Commercial : {}", id);
        commercialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
