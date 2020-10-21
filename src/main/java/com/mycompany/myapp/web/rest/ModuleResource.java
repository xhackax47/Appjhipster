package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ModuleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ModuleDTO;
import com.mycompany.myapp.service.dto.ModuleCriteria;
import com.mycompany.myapp.service.ModuleQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Module}.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);

    private static final String ENTITY_NAME = "module";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleService moduleService;

    private final ModuleQueryService moduleQueryService;

    public ModuleResource(ModuleService moduleService, ModuleQueryService moduleQueryService) {
        this.moduleService = moduleService;
        this.moduleQueryService = moduleQueryService;
    }

    /**
     * {@code POST  /modules} : Create a new module.
     *
     * @param moduleDTO the moduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleDTO, or with status {@code 400 (Bad Request)} if the module has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modules")
    public ResponseEntity<ModuleDTO> createModule(@Valid @RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        log.debug("REST request to save Module : {}", moduleDTO);
        if (moduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleDTO result = moduleService.save(moduleDTO);
        return ResponseEntity.created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modules} : Updates an existing module.
     *
     * @param moduleDTO the moduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleDTO,
     * or with status {@code 400 (Bad Request)} if the moduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modules")
    public ResponseEntity<ModuleDTO> updateModule(@Valid @RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        log.debug("REST request to update Module : {}", moduleDTO);
        if (moduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleDTO result = moduleService.save(moduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modules} : get all the modules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modules in body.
     */
    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDTO>> getAllModules(ModuleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Modules by criteria: {}", criteria);
        Page<ModuleDTO> page = moduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modules/count} : count all the modules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/modules/count")
    public ResponseEntity<Long> countModules(ModuleCriteria criteria) {
        log.debug("REST request to count Modules by criteria: {}", criteria);
        return ResponseEntity.ok().body(moduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /modules/:id} : get the "id" module.
     *
     * @param id the id of the moduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable Long id) {
        log.debug("REST request to get Module : {}", id);
        Optional<ModuleDTO> moduleDTO = moduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleDTO);
    }

    /**
     * {@code DELETE  /modules/:id} : delete the "id" module.
     *
     * @param id the id of the moduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.debug("REST request to delete Module : {}", id);
        moduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
