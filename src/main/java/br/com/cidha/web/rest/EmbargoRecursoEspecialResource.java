package br.com.cidha.web.rest;

import br.com.cidha.domain.EmbargoRecursoEspecial;
import br.com.cidha.repository.EmbargoRecursoEspecialRepository;
import br.com.cidha.service.EmbargoRecursoEspecialQueryService;
import br.com.cidha.service.EmbargoRecursoEspecialService;
import br.com.cidha.service.criteria.EmbargoRecursoEspecialCriteria;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.cidha.domain.EmbargoRecursoEspecial}.
 */
@RestController
@RequestMapping("/api")
public class EmbargoRecursoEspecialResource {

    private final Logger log = LoggerFactory.getLogger(EmbargoRecursoEspecialResource.class);

    private static final String ENTITY_NAME = "embargoRecursoEspecial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbargoRecursoEspecialService embargoRecursoEspecialService;

    private final EmbargoRecursoEspecialRepository embargoRecursoEspecialRepository;

    private final EmbargoRecursoEspecialQueryService embargoRecursoEspecialQueryService;

    public EmbargoRecursoEspecialResource(
        EmbargoRecursoEspecialService embargoRecursoEspecialService,
        EmbargoRecursoEspecialRepository embargoRecursoEspecialRepository,
        EmbargoRecursoEspecialQueryService embargoRecursoEspecialQueryService
    ) {
        this.embargoRecursoEspecialService = embargoRecursoEspecialService;
        this.embargoRecursoEspecialRepository = embargoRecursoEspecialRepository;
        this.embargoRecursoEspecialQueryService = embargoRecursoEspecialQueryService;
    }

    /**
     * {@code POST  /embargo-recurso-especials} : Create a new embargoRecursoEspecial.
     *
     * @param embargoRecursoEspecial the embargoRecursoEspecial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embargoRecursoEspecial, or with status {@code 400 (Bad Request)} if the embargoRecursoEspecial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embargo-recurso-especials")
    public ResponseEntity<EmbargoRecursoEspecial> createEmbargoRecursoEspecial(@RequestBody EmbargoRecursoEspecial embargoRecursoEspecial)
        throws URISyntaxException {
        log.debug("REST request to save EmbargoRecursoEspecial : {}", embargoRecursoEspecial);
        if (embargoRecursoEspecial.getId() != null) {
            throw new BadRequestAlertException("A new embargoRecursoEspecial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbargoRecursoEspecial result = embargoRecursoEspecialService.save(embargoRecursoEspecial);
        return ResponseEntity
            .created(new URI("/api/embargo-recurso-especials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embargo-recurso-especials/:id} : Updates an existing embargoRecursoEspecial.
     *
     * @param id the id of the embargoRecursoEspecial to save.
     * @param embargoRecursoEspecial the embargoRecursoEspecial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoRecursoEspecial,
     * or with status {@code 400 (Bad Request)} if the embargoRecursoEspecial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embargoRecursoEspecial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embargo-recurso-especials/{id}")
    public ResponseEntity<EmbargoRecursoEspecial> updateEmbargoRecursoEspecial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoRecursoEspecial embargoRecursoEspecial
    ) throws URISyntaxException {
        log.debug("REST request to update EmbargoRecursoEspecial : {}, {}", id, embargoRecursoEspecial);
        if (embargoRecursoEspecial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoRecursoEspecial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoRecursoEspecialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmbargoRecursoEspecial result = embargoRecursoEspecialService.save(embargoRecursoEspecial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoRecursoEspecial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /embargo-recurso-especials/:id} : Partial updates given fields of an existing embargoRecursoEspecial, field will ignore if it is null
     *
     * @param id the id of the embargoRecursoEspecial to save.
     * @param embargoRecursoEspecial the embargoRecursoEspecial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoRecursoEspecial,
     * or with status {@code 400 (Bad Request)} if the embargoRecursoEspecial is not valid,
     * or with status {@code 404 (Not Found)} if the embargoRecursoEspecial is not found,
     * or with status {@code 500 (Internal Server Error)} if the embargoRecursoEspecial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/embargo-recurso-especials/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmbargoRecursoEspecial> partialUpdateEmbargoRecursoEspecial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoRecursoEspecial embargoRecursoEspecial
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmbargoRecursoEspecial partially : {}, {}", id, embargoRecursoEspecial);
        if (embargoRecursoEspecial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoRecursoEspecial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoRecursoEspecialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmbargoRecursoEspecial> result = embargoRecursoEspecialService.partialUpdate(embargoRecursoEspecial);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoRecursoEspecial.getId().toString())
        );
    }

    /**
     * {@code GET  /embargo-recurso-especials} : get all the embargoRecursoEspecials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embargoRecursoEspecials in body.
     */
    @GetMapping("/embargo-recurso-especials")
    public ResponseEntity<List<EmbargoRecursoEspecial>> getAllEmbargoRecursoEspecials(
        EmbargoRecursoEspecialCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EmbargoRecursoEspecials by criteria: {}", criteria);
        Page<EmbargoRecursoEspecial> page = embargoRecursoEspecialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embargo-recurso-especials/count} : count all the embargoRecursoEspecials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/embargo-recurso-especials/count")
    public ResponseEntity<Long> countEmbargoRecursoEspecials(EmbargoRecursoEspecialCriteria criteria) {
        log.debug("REST request to count EmbargoRecursoEspecials by criteria: {}", criteria);
        return ResponseEntity.ok().body(embargoRecursoEspecialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /embargo-recurso-especials/:id} : get the "id" embargoRecursoEspecial.
     *
     * @param id the id of the embargoRecursoEspecial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embargoRecursoEspecial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embargo-recurso-especials/{id}")
    public ResponseEntity<EmbargoRecursoEspecial> getEmbargoRecursoEspecial(@PathVariable Long id) {
        log.debug("REST request to get EmbargoRecursoEspecial : {}", id);
        Optional<EmbargoRecursoEspecial> embargoRecursoEspecial = embargoRecursoEspecialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embargoRecursoEspecial);
    }

    /**
     * {@code DELETE  /embargo-recurso-especials/:id} : delete the "id" embargoRecursoEspecial.
     *
     * @param id the id of the embargoRecursoEspecial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embargo-recurso-especials/{id}")
    public ResponseEntity<Void> deleteEmbargoRecursoEspecial(@PathVariable Long id) {
        log.debug("REST request to delete EmbargoRecursoEspecial : {}", id);
        embargoRecursoEspecialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
