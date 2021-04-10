package br.com.cidha.web.rest;

import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.repository.EmbargoDeclaracaoAgravoRepository;
import br.com.cidha.service.EmbargoDeclaracaoAgravoQueryService;
import br.com.cidha.service.EmbargoDeclaracaoAgravoService;
import br.com.cidha.service.criteria.EmbargoDeclaracaoAgravoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.EmbargoDeclaracaoAgravo}.
 */
@RestController
@RequestMapping("/api")
public class EmbargoDeclaracaoAgravoResource {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoAgravoResource.class);

    private static final String ENTITY_NAME = "embargoDeclaracaoAgravo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbargoDeclaracaoAgravoService embargoDeclaracaoAgravoService;

    private final EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository;

    private final EmbargoDeclaracaoAgravoQueryService embargoDeclaracaoAgravoQueryService;

    public EmbargoDeclaracaoAgravoResource(
        EmbargoDeclaracaoAgravoService embargoDeclaracaoAgravoService,
        EmbargoDeclaracaoAgravoRepository embargoDeclaracaoAgravoRepository,
        EmbargoDeclaracaoAgravoQueryService embargoDeclaracaoAgravoQueryService
    ) {
        this.embargoDeclaracaoAgravoService = embargoDeclaracaoAgravoService;
        this.embargoDeclaracaoAgravoRepository = embargoDeclaracaoAgravoRepository;
        this.embargoDeclaracaoAgravoQueryService = embargoDeclaracaoAgravoQueryService;
    }

    /**
     * {@code POST  /embargo-declaracao-agravos} : Create a new embargoDeclaracaoAgravo.
     *
     * @param embargoDeclaracaoAgravo the embargoDeclaracaoAgravo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embargoDeclaracaoAgravo, or with status {@code 400 (Bad Request)} if the embargoDeclaracaoAgravo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embargo-declaracao-agravos")
    public ResponseEntity<EmbargoDeclaracaoAgravo> createEmbargoDeclaracaoAgravo(
        @RequestBody EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo
    ) throws URISyntaxException {
        log.debug("REST request to save EmbargoDeclaracaoAgravo : {}", embargoDeclaracaoAgravo);
        if (embargoDeclaracaoAgravo.getId() != null) {
            throw new BadRequestAlertException("A new embargoDeclaracaoAgravo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbargoDeclaracaoAgravo result = embargoDeclaracaoAgravoService.save(embargoDeclaracaoAgravo);
        return ResponseEntity
            .created(new URI("/api/embargo-declaracao-agravos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embargo-declaracao-agravos/:id} : Updates an existing embargoDeclaracaoAgravo.
     *
     * @param id the id of the embargoDeclaracaoAgravo to save.
     * @param embargoDeclaracaoAgravo the embargoDeclaracaoAgravo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoDeclaracaoAgravo,
     * or with status {@code 400 (Bad Request)} if the embargoDeclaracaoAgravo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embargoDeclaracaoAgravo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embargo-declaracao-agravos/{id}")
    public ResponseEntity<EmbargoDeclaracaoAgravo> updateEmbargoDeclaracaoAgravo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo
    ) throws URISyntaxException {
        log.debug("REST request to update EmbargoDeclaracaoAgravo : {}, {}", id, embargoDeclaracaoAgravo);
        if (embargoDeclaracaoAgravo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoDeclaracaoAgravo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoDeclaracaoAgravoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmbargoDeclaracaoAgravo result = embargoDeclaracaoAgravoService.save(embargoDeclaracaoAgravo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoDeclaracaoAgravo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /embargo-declaracao-agravos/:id} : Partial updates given fields of an existing embargoDeclaracaoAgravo, field will ignore if it is null
     *
     * @param id the id of the embargoDeclaracaoAgravo to save.
     * @param embargoDeclaracaoAgravo the embargoDeclaracaoAgravo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoDeclaracaoAgravo,
     * or with status {@code 400 (Bad Request)} if the embargoDeclaracaoAgravo is not valid,
     * or with status {@code 404 (Not Found)} if the embargoDeclaracaoAgravo is not found,
     * or with status {@code 500 (Internal Server Error)} if the embargoDeclaracaoAgravo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/embargo-declaracao-agravos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmbargoDeclaracaoAgravo> partialUpdateEmbargoDeclaracaoAgravo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmbargoDeclaracaoAgravo partially : {}, {}", id, embargoDeclaracaoAgravo);
        if (embargoDeclaracaoAgravo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoDeclaracaoAgravo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoDeclaracaoAgravoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmbargoDeclaracaoAgravo> result = embargoDeclaracaoAgravoService.partialUpdate(embargoDeclaracaoAgravo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoDeclaracaoAgravo.getId().toString())
        );
    }

    /**
     * {@code GET  /embargo-declaracao-agravos} : get all the embargoDeclaracaoAgravos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embargoDeclaracaoAgravos in body.
     */
    @GetMapping("/embargo-declaracao-agravos")
    public ResponseEntity<List<EmbargoDeclaracaoAgravo>> getAllEmbargoDeclaracaoAgravos(
        EmbargoDeclaracaoAgravoCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EmbargoDeclaracaoAgravos by criteria: {}", criteria);
        Page<EmbargoDeclaracaoAgravo> page = embargoDeclaracaoAgravoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embargo-declaracao-agravos/count} : count all the embargoDeclaracaoAgravos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/embargo-declaracao-agravos/count")
    public ResponseEntity<Long> countEmbargoDeclaracaoAgravos(EmbargoDeclaracaoAgravoCriteria criteria) {
        log.debug("REST request to count EmbargoDeclaracaoAgravos by criteria: {}", criteria);
        return ResponseEntity.ok().body(embargoDeclaracaoAgravoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /embargo-declaracao-agravos/:id} : get the "id" embargoDeclaracaoAgravo.
     *
     * @param id the id of the embargoDeclaracaoAgravo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embargoDeclaracaoAgravo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embargo-declaracao-agravos/{id}")
    public ResponseEntity<EmbargoDeclaracaoAgravo> getEmbargoDeclaracaoAgravo(@PathVariable Long id) {
        log.debug("REST request to get EmbargoDeclaracaoAgravo : {}", id);
        Optional<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravo = embargoDeclaracaoAgravoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embargoDeclaracaoAgravo);
    }

    /**
     * {@code DELETE  /embargo-declaracao-agravos/:id} : delete the "id" embargoDeclaracaoAgravo.
     *
     * @param id the id of the embargoDeclaracaoAgravo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embargo-declaracao-agravos/{id}")
    public ResponseEntity<Void> deleteEmbargoDeclaracaoAgravo(@PathVariable Long id) {
        log.debug("REST request to delete EmbargoDeclaracaoAgravo : {}", id);
        embargoDeclaracaoAgravoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
