package br.com.cidha.web.rest;

import br.com.cidha.domain.FundamentacaoLegal;
import br.com.cidha.repository.FundamentacaoLegalRepository;
import br.com.cidha.service.FundamentacaoLegalQueryService;
import br.com.cidha.service.FundamentacaoLegalService;
import br.com.cidha.service.criteria.FundamentacaoLegalCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.FundamentacaoLegal}.
 */
@RestController
@RequestMapping("/api")
public class FundamentacaoLegalResource {

    private final Logger log = LoggerFactory.getLogger(FundamentacaoLegalResource.class);

    private static final String ENTITY_NAME = "fundamentacaoLegal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FundamentacaoLegalService fundamentacaoLegalService;

    private final FundamentacaoLegalRepository fundamentacaoLegalRepository;

    private final FundamentacaoLegalQueryService fundamentacaoLegalQueryService;

    public FundamentacaoLegalResource(
        FundamentacaoLegalService fundamentacaoLegalService,
        FundamentacaoLegalRepository fundamentacaoLegalRepository,
        FundamentacaoLegalQueryService fundamentacaoLegalQueryService
    ) {
        this.fundamentacaoLegalService = fundamentacaoLegalService;
        this.fundamentacaoLegalRepository = fundamentacaoLegalRepository;
        this.fundamentacaoLegalQueryService = fundamentacaoLegalQueryService;
    }

    /**
     * {@code POST  /fundamentacao-legals} : Create a new fundamentacaoLegal.
     *
     * @param fundamentacaoLegal the fundamentacaoLegal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fundamentacaoLegal, or with status {@code 400 (Bad Request)} if the fundamentacaoLegal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fundamentacao-legals")
    public ResponseEntity<FundamentacaoLegal> createFundamentacaoLegal(@RequestBody FundamentacaoLegal fundamentacaoLegal)
        throws URISyntaxException {
        log.debug("REST request to save FundamentacaoLegal : {}", fundamentacaoLegal);
        if (fundamentacaoLegal.getId() != null) {
            throw new BadRequestAlertException("A new fundamentacaoLegal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FundamentacaoLegal result = fundamentacaoLegalService.save(fundamentacaoLegal);
        return ResponseEntity
            .created(new URI("/api/fundamentacao-legals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fundamentacao-legals/:id} : Updates an existing fundamentacaoLegal.
     *
     * @param id the id of the fundamentacaoLegal to save.
     * @param fundamentacaoLegal the fundamentacaoLegal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundamentacaoLegal,
     * or with status {@code 400 (Bad Request)} if the fundamentacaoLegal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fundamentacaoLegal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fundamentacao-legals/{id}")
    public ResponseEntity<FundamentacaoLegal> updateFundamentacaoLegal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundamentacaoLegal fundamentacaoLegal
    ) throws URISyntaxException {
        log.debug("REST request to update FundamentacaoLegal : {}, {}", id, fundamentacaoLegal);
        if (fundamentacaoLegal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundamentacaoLegal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundamentacaoLegalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FundamentacaoLegal result = fundamentacaoLegalService.save(fundamentacaoLegal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundamentacaoLegal.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fundamentacao-legals/:id} : Partial updates given fields of an existing fundamentacaoLegal, field will ignore if it is null
     *
     * @param id the id of the fundamentacaoLegal to save.
     * @param fundamentacaoLegal the fundamentacaoLegal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundamentacaoLegal,
     * or with status {@code 400 (Bad Request)} if the fundamentacaoLegal is not valid,
     * or with status {@code 404 (Not Found)} if the fundamentacaoLegal is not found,
     * or with status {@code 500 (Internal Server Error)} if the fundamentacaoLegal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fundamentacao-legals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FundamentacaoLegal> partialUpdateFundamentacaoLegal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundamentacaoLegal fundamentacaoLegal
    ) throws URISyntaxException {
        log.debug("REST request to partial update FundamentacaoLegal partially : {}, {}", id, fundamentacaoLegal);
        if (fundamentacaoLegal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundamentacaoLegal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundamentacaoLegalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FundamentacaoLegal> result = fundamentacaoLegalService.partialUpdate(fundamentacaoLegal);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundamentacaoLegal.getId().toString())
        );
    }

    /**
     * {@code GET  /fundamentacao-legals} : get all the fundamentacaoLegals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fundamentacaoLegals in body.
     */
    @GetMapping("/fundamentacao-legals")
    public ResponseEntity<List<FundamentacaoLegal>> getAllFundamentacaoLegals(FundamentacaoLegalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FundamentacaoLegals by criteria: {}", criteria);
        Page<FundamentacaoLegal> page = fundamentacaoLegalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fundamentacao-legals/count} : count all the fundamentacaoLegals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fundamentacao-legals/count")
    public ResponseEntity<Long> countFundamentacaoLegals(FundamentacaoLegalCriteria criteria) {
        log.debug("REST request to count FundamentacaoLegals by criteria: {}", criteria);
        return ResponseEntity.ok().body(fundamentacaoLegalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fundamentacao-legals/:id} : get the "id" fundamentacaoLegal.
     *
     * @param id the id of the fundamentacaoLegal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fundamentacaoLegal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fundamentacao-legals/{id}")
    public ResponseEntity<FundamentacaoLegal> getFundamentacaoLegal(@PathVariable Long id) {
        log.debug("REST request to get FundamentacaoLegal : {}", id);
        Optional<FundamentacaoLegal> fundamentacaoLegal = fundamentacaoLegalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fundamentacaoLegal);
    }

    /**
     * {@code DELETE  /fundamentacao-legals/:id} : delete the "id" fundamentacaoLegal.
     *
     * @param id the id of the fundamentacaoLegal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fundamentacao-legals/{id}")
    public ResponseEntity<Void> deleteFundamentacaoLegal(@PathVariable Long id) {
        log.debug("REST request to delete FundamentacaoLegal : {}", id);
        fundamentacaoLegalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
