package br.com.cidha.web.rest;

import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.repository.InstrumentoInternacionalRepository;
import br.com.cidha.service.InstrumentoInternacionalQueryService;
import br.com.cidha.service.InstrumentoInternacionalService;
import br.com.cidha.service.criteria.InstrumentoInternacionalCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.InstrumentoInternacional}.
 */
@RestController
@RequestMapping("/api")
public class InstrumentoInternacionalResource {

    private final Logger log = LoggerFactory.getLogger(InstrumentoInternacionalResource.class);

    private static final String ENTITY_NAME = "instrumentoInternacional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstrumentoInternacionalService instrumentoInternacionalService;

    private final InstrumentoInternacionalRepository instrumentoInternacionalRepository;

    private final InstrumentoInternacionalQueryService instrumentoInternacionalQueryService;

    public InstrumentoInternacionalResource(
        InstrumentoInternacionalService instrumentoInternacionalService,
        InstrumentoInternacionalRepository instrumentoInternacionalRepository,
        InstrumentoInternacionalQueryService instrumentoInternacionalQueryService
    ) {
        this.instrumentoInternacionalService = instrumentoInternacionalService;
        this.instrumentoInternacionalRepository = instrumentoInternacionalRepository;
        this.instrumentoInternacionalQueryService = instrumentoInternacionalQueryService;
    }

    /**
     * {@code POST  /instrumento-internacionals} : Create a new instrumentoInternacional.
     *
     * @param instrumentoInternacional the instrumentoInternacional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instrumentoInternacional, or with status {@code 400 (Bad Request)} if the instrumentoInternacional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instrumento-internacionals")
    public ResponseEntity<InstrumentoInternacional> createInstrumentoInternacional(
        @RequestBody InstrumentoInternacional instrumentoInternacional
    ) throws URISyntaxException {
        log.debug("REST request to save InstrumentoInternacional : {}", instrumentoInternacional);
        if (instrumentoInternacional.getId() != null) {
            throw new BadRequestAlertException("A new instrumentoInternacional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstrumentoInternacional result = instrumentoInternacionalService.save(instrumentoInternacional);
        return ResponseEntity
            .created(new URI("/api/instrumento-internacionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instrumento-internacionals/:id} : Updates an existing instrumentoInternacional.
     *
     * @param id the id of the instrumentoInternacional to save.
     * @param instrumentoInternacional the instrumentoInternacional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instrumentoInternacional,
     * or with status {@code 400 (Bad Request)} if the instrumentoInternacional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instrumentoInternacional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instrumento-internacionals/{id}")
    public ResponseEntity<InstrumentoInternacional> updateInstrumentoInternacional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstrumentoInternacional instrumentoInternacional
    ) throws URISyntaxException {
        log.debug("REST request to update InstrumentoInternacional : {}, {}", id, instrumentoInternacional);
        if (instrumentoInternacional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instrumentoInternacional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instrumentoInternacionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InstrumentoInternacional result = instrumentoInternacionalService.save(instrumentoInternacional);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instrumentoInternacional.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /instrumento-internacionals/:id} : Partial updates given fields of an existing instrumentoInternacional, field will ignore if it is null
     *
     * @param id the id of the instrumentoInternacional to save.
     * @param instrumentoInternacional the instrumentoInternacional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instrumentoInternacional,
     * or with status {@code 400 (Bad Request)} if the instrumentoInternacional is not valid,
     * or with status {@code 404 (Not Found)} if the instrumentoInternacional is not found,
     * or with status {@code 500 (Internal Server Error)} if the instrumentoInternacional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/instrumento-internacionals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InstrumentoInternacional> partialUpdateInstrumentoInternacional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstrumentoInternacional instrumentoInternacional
    ) throws URISyntaxException {
        log.debug("REST request to partial update InstrumentoInternacional partially : {}, {}", id, instrumentoInternacional);
        if (instrumentoInternacional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instrumentoInternacional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instrumentoInternacionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InstrumentoInternacional> result = instrumentoInternacionalService.partialUpdate(instrumentoInternacional);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instrumentoInternacional.getId().toString())
        );
    }

    /**
     * {@code GET  /instrumento-internacionals} : get all the instrumentoInternacionals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instrumentoInternacionals in body.
     */
    @GetMapping("/instrumento-internacionals")
    public ResponseEntity<List<InstrumentoInternacional>> getAllInstrumentoInternacionals(
        InstrumentoInternacionalCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get InstrumentoInternacionals by criteria: {}", criteria);
        Page<InstrumentoInternacional> page = instrumentoInternacionalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instrumento-internacionals/count} : count all the instrumentoInternacionals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/instrumento-internacionals/count")
    public ResponseEntity<Long> countInstrumentoInternacionals(InstrumentoInternacionalCriteria criteria) {
        log.debug("REST request to count InstrumentoInternacionals by criteria: {}", criteria);
        return ResponseEntity.ok().body(instrumentoInternacionalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /instrumento-internacionals/:id} : get the "id" instrumentoInternacional.
     *
     * @param id the id of the instrumentoInternacional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instrumentoInternacional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instrumento-internacionals/{id}")
    public ResponseEntity<InstrumentoInternacional> getInstrumentoInternacional(@PathVariable Long id) {
        log.debug("REST request to get InstrumentoInternacional : {}", id);
        Optional<InstrumentoInternacional> instrumentoInternacional = instrumentoInternacionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instrumentoInternacional);
    }

    /**
     * {@code DELETE  /instrumento-internacionals/:id} : delete the "id" instrumentoInternacional.
     *
     * @param id the id of the instrumentoInternacional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instrumento-internacionals/{id}")
    public ResponseEntity<Void> deleteInstrumentoInternacional(@PathVariable Long id) {
        log.debug("REST request to delete InstrumentoInternacional : {}", id);
        instrumentoInternacionalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
