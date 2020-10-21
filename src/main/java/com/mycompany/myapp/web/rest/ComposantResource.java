package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ComposantService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ComposantDTO;
import com.mycompany.myapp.service.dto.ComposantCriteria;
import com.mycompany.myapp.service.ComposantQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Composant}.
 */
@RestController
@RequestMapping("/api")
public class ComposantResource {

    private final Logger log = LoggerFactory.getLogger(ComposantResource.class);

    private static final String ENTITY_NAME = "composant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComposantService composantService;

    private final ComposantQueryService composantQueryService;

    public ComposantResource(ComposantService composantService, ComposantQueryService composantQueryService) {
        this.composantService = composantService;
        this.composantQueryService = composantQueryService;
    }

    /**
     * {@code POST  /composants} : Create a new composant.
     *
     * @param composantDTO the composantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new composantDTO, or with status {@code 400 (Bad Request)} if the composant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/composants")
    public ResponseEntity<ComposantDTO> createComposant(@Valid @RequestBody ComposantDTO composantDTO) throws URISyntaxException {
        log.debug("REST request to save Composant : {}", composantDTO);
        if (composantDTO.getId() != null) {
            throw new BadRequestAlertException("A new composant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComposantDTO result = composantService.save(composantDTO);
        return ResponseEntity.created(new URI("/api/composants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /composants} : Updates an existing composant.
     *
     * @param composantDTO the composantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated composantDTO,
     * or with status {@code 400 (Bad Request)} if the composantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the composantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/composants")
    public ResponseEntity<ComposantDTO> updateComposant(@Valid @RequestBody ComposantDTO composantDTO) throws URISyntaxException {
        log.debug("REST request to update Composant : {}", composantDTO);
        if (composantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComposantDTO result = composantService.save(composantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, composantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /composants} : get all the composants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of composants in body.
     */
    @GetMapping("/composants")
    public ResponseEntity<List<ComposantDTO>> getAllComposants(ComposantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Composants by criteria: {}", criteria);
        Page<ComposantDTO> page = composantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /composants/count} : count all the composants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/composants/count")
    public ResponseEntity<Long> countComposants(ComposantCriteria criteria) {
        log.debug("REST request to count Composants by criteria: {}", criteria);
        return ResponseEntity.ok().body(composantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /composants/:id} : get the "id" composant.
     *
     * @param id the id of the composantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the composantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/composants/{id}")
    public ResponseEntity<ComposantDTO> getComposant(@PathVariable Long id) {
        log.debug("REST request to get Composant : {}", id);
        Optional<ComposantDTO> composantDTO = composantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(composantDTO);
    }

    /**
     * {@code DELETE  /composants/:id} : delete the "id" composant.
     *
     * @param id the id of the composantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/composants/{id}")
    public ResponseEntity<Void> deleteComposant(@PathVariable Long id) {
        log.debug("REST request to delete Composant : {}", id);
        composantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
