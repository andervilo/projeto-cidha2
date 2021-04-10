package br.com.cidha.web.rest;

import br.com.cidha.domain.ProcessoConflito;
import br.com.cidha.repository.ProcessoConflitoRepository;
import br.com.cidha.service.ProcessoConflitoQueryService;
import br.com.cidha.service.ProcessoConflitoService;
import br.com.cidha.service.criteria.ProcessoConflitoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.ProcessoConflito}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoConflitoResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoConflitoResource.class);

    private static final String ENTITY_NAME = "processoConflito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessoConflitoService processoConflitoService;

    private final ProcessoConflitoRepository processoConflitoRepository;

    private final ProcessoConflitoQueryService processoConflitoQueryService;

    public ProcessoConflitoResource(
        ProcessoConflitoService processoConflitoService,
        ProcessoConflitoRepository processoConflitoRepository,
        ProcessoConflitoQueryService processoConflitoQueryService
    ) {
        this.processoConflitoService = processoConflitoService;
        this.processoConflitoRepository = processoConflitoRepository;
        this.processoConflitoQueryService = processoConflitoQueryService;
    }

    /**
     * {@code POST  /processo-conflitos} : Create a new processoConflito.
     *
     * @param processoConflito the processoConflito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processoConflito, or with status {@code 400 (Bad Request)} if the processoConflito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processo-conflitos")
    public ResponseEntity<ProcessoConflito> createProcessoConflito(@RequestBody ProcessoConflito processoConflito)
        throws URISyntaxException {
        log.debug("REST request to save ProcessoConflito : {}", processoConflito);
        if (processoConflito.getId() != null) {
            throw new BadRequestAlertException("A new processoConflito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoConflito result = processoConflitoService.save(processoConflito);
        return ResponseEntity
            .created(new URI("/api/processo-conflitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processo-conflitos/:id} : Updates an existing processoConflito.
     *
     * @param id the id of the processoConflito to save.
     * @param processoConflito the processoConflito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoConflito,
     * or with status {@code 400 (Bad Request)} if the processoConflito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processoConflito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processo-conflitos/{id}")
    public ResponseEntity<ProcessoConflito> updateProcessoConflito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessoConflito processoConflito
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessoConflito : {}, {}", id, processoConflito);
        if (processoConflito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoConflito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoConflitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessoConflito result = processoConflitoService.save(processoConflito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoConflito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /processo-conflitos/:id} : Partial updates given fields of an existing processoConflito, field will ignore if it is null
     *
     * @param id the id of the processoConflito to save.
     * @param processoConflito the processoConflito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoConflito,
     * or with status {@code 400 (Bad Request)} if the processoConflito is not valid,
     * or with status {@code 404 (Not Found)} if the processoConflito is not found,
     * or with status {@code 500 (Internal Server Error)} if the processoConflito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/processo-conflitos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProcessoConflito> partialUpdateProcessoConflito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessoConflito processoConflito
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessoConflito partially : {}, {}", id, processoConflito);
        if (processoConflito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoConflito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoConflitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessoConflito> result = processoConflitoService.partialUpdate(processoConflito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoConflito.getId().toString())
        );
    }

    /**
     * {@code GET  /processo-conflitos} : get all the processoConflitos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processoConflitos in body.
     */
    @GetMapping("/processo-conflitos")
    public ResponseEntity<List<ProcessoConflito>> getAllProcessoConflitos(ProcessoConflitoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProcessoConflitos by criteria: {}", criteria);
        Page<ProcessoConflito> page = processoConflitoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processo-conflitos/count} : count all the processoConflitos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/processo-conflitos/count")
    public ResponseEntity<Long> countProcessoConflitos(ProcessoConflitoCriteria criteria) {
        log.debug("REST request to count ProcessoConflitos by criteria: {}", criteria);
        return ResponseEntity.ok().body(processoConflitoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /processo-conflitos/:id} : get the "id" processoConflito.
     *
     * @param id the id of the processoConflito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processoConflito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processo-conflitos/{id}")
    public ResponseEntity<ProcessoConflito> getProcessoConflito(@PathVariable Long id) {
        log.debug("REST request to get ProcessoConflito : {}", id);
        Optional<ProcessoConflito> processoConflito = processoConflitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processoConflito);
    }

    /**
     * {@code DELETE  /processo-conflitos/:id} : delete the "id" processoConflito.
     *
     * @param id the id of the processoConflito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processo-conflitos/{id}")
    public ResponseEntity<Void> deleteProcessoConflito(@PathVariable Long id) {
        log.debug("REST request to delete ProcessoConflito : {}", id);
        processoConflitoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
