package br.com.cidha.web.rest;

import br.com.cidha.domain.RepresentanteLegal;
import br.com.cidha.repository.RepresentanteLegalRepository;
import br.com.cidha.service.RepresentanteLegalQueryService;
import br.com.cidha.service.RepresentanteLegalService;
import br.com.cidha.service.criteria.RepresentanteLegalCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.RepresentanteLegal}.
 */
@RestController
@RequestMapping("/api")
public class RepresentanteLegalResource {

    private final Logger log = LoggerFactory.getLogger(RepresentanteLegalResource.class);

    private static final String ENTITY_NAME = "representanteLegal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentanteLegalService representanteLegalService;

    private final RepresentanteLegalRepository representanteLegalRepository;

    private final RepresentanteLegalQueryService representanteLegalQueryService;

    public RepresentanteLegalResource(
        RepresentanteLegalService representanteLegalService,
        RepresentanteLegalRepository representanteLegalRepository,
        RepresentanteLegalQueryService representanteLegalQueryService
    ) {
        this.representanteLegalService = representanteLegalService;
        this.representanteLegalRepository = representanteLegalRepository;
        this.representanteLegalQueryService = representanteLegalQueryService;
    }

    /**
     * {@code POST  /representante-legals} : Create a new representanteLegal.
     *
     * @param representanteLegal the representanteLegal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representanteLegal, or with status {@code 400 (Bad Request)} if the representanteLegal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representante-legals")
    public ResponseEntity<RepresentanteLegal> createRepresentanteLegal(@RequestBody RepresentanteLegal representanteLegal)
        throws URISyntaxException {
        log.debug("REST request to save RepresentanteLegal : {}", representanteLegal);
        if (representanteLegal.getId() != null) {
            throw new BadRequestAlertException("A new representanteLegal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepresentanteLegal result = representanteLegalService.save(representanteLegal);
        return ResponseEntity
            .created(new URI("/api/representante-legals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representante-legals/:id} : Updates an existing representanteLegal.
     *
     * @param id the id of the representanteLegal to save.
     * @param representanteLegal the representanteLegal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representanteLegal,
     * or with status {@code 400 (Bad Request)} if the representanteLegal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representanteLegal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representante-legals/{id}")
    public ResponseEntity<RepresentanteLegal> updateRepresentanteLegal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentanteLegal representanteLegal
    ) throws URISyntaxException {
        log.debug("REST request to update RepresentanteLegal : {}, {}", id, representanteLegal);
        if (representanteLegal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representanteLegal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representanteLegalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepresentanteLegal result = representanteLegalService.save(representanteLegal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representanteLegal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /representante-legals/:id} : Partial updates given fields of an existing representanteLegal, field will ignore if it is null
     *
     * @param id the id of the representanteLegal to save.
     * @param representanteLegal the representanteLegal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representanteLegal,
     * or with status {@code 400 (Bad Request)} if the representanteLegal is not valid,
     * or with status {@code 404 (Not Found)} if the representanteLegal is not found,
     * or with status {@code 500 (Internal Server Error)} if the representanteLegal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/representante-legals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RepresentanteLegal> partialUpdateRepresentanteLegal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RepresentanteLegal representanteLegal
    ) throws URISyntaxException {
        log.debug("REST request to partial update RepresentanteLegal partially : {}, {}", id, representanteLegal);
        if (representanteLegal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representanteLegal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representanteLegalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepresentanteLegal> result = representanteLegalService.partialUpdate(representanteLegal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representanteLegal.getId().toString())
        );
    }

    /**
     * {@code GET  /representante-legals} : get all the representanteLegals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representanteLegals in body.
     */
    @GetMapping("/representante-legals")
    public ResponseEntity<List<RepresentanteLegal>> getAllRepresentanteLegals(RepresentanteLegalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get RepresentanteLegals by criteria: {}", criteria);
        Page<RepresentanteLegal> page = representanteLegalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /representante-legals/count} : count all the representanteLegals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/representante-legals/count")
    public ResponseEntity<Long> countRepresentanteLegals(RepresentanteLegalCriteria criteria) {
        log.debug("REST request to count RepresentanteLegals by criteria: {}", criteria);
        return ResponseEntity.ok().body(representanteLegalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /representante-legals/:id} : get the "id" representanteLegal.
     *
     * @param id the id of the representanteLegal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representanteLegal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representante-legals/{id}")
    public ResponseEntity<RepresentanteLegal> getRepresentanteLegal(@PathVariable Long id) {
        log.debug("REST request to get RepresentanteLegal : {}", id);
        Optional<RepresentanteLegal> representanteLegal = representanteLegalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(representanteLegal);
    }

    /**
     * {@code DELETE  /representante-legals/:id} : delete the "id" representanteLegal.
     *
     * @param id the id of the representanteLegal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representante-legals/{id}")
    public ResponseEntity<Void> deleteRepresentanteLegal(@PathVariable Long id) {
        log.debug("REST request to delete RepresentanteLegal : {}", id);
        representanteLegalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
