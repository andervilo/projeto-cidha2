package br.com.cidha.web.rest;

import br.com.cidha.domain.Conflito;
import br.com.cidha.repository.ConflitoRepository;
import br.com.cidha.service.ConflitoQueryService;
import br.com.cidha.service.ConflitoService;
import br.com.cidha.service.criteria.ConflitoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.Conflito}.
 */
@RestController
@RequestMapping("/api")
public class ConflitoResource {

    private final Logger log = LoggerFactory.getLogger(ConflitoResource.class);

    private static final String ENTITY_NAME = "conflito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConflitoService conflitoService;

    private final ConflitoRepository conflitoRepository;

    private final ConflitoQueryService conflitoQueryService;

    public ConflitoResource(
        ConflitoService conflitoService,
        ConflitoRepository conflitoRepository,
        ConflitoQueryService conflitoQueryService
    ) {
        this.conflitoService = conflitoService;
        this.conflitoRepository = conflitoRepository;
        this.conflitoQueryService = conflitoQueryService;
    }

    /**
     * {@code POST  /conflitos} : Create a new conflito.
     *
     * @param conflito the conflito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conflito, or with status {@code 400 (Bad Request)} if the conflito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conflitos")
    public ResponseEntity<Conflito> createConflito(@RequestBody Conflito conflito) throws URISyntaxException {
        log.debug("REST request to save Conflito : {}", conflito);
        if (conflito.getId() != null) {
            throw new BadRequestAlertException("A new conflito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conflito result = conflitoService.save(conflito);
        return ResponseEntity
            .created(new URI("/api/conflitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conflitos/:id} : Updates an existing conflito.
     *
     * @param id the id of the conflito to save.
     * @param conflito the conflito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conflito,
     * or with status {@code 400 (Bad Request)} if the conflito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conflito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conflitos/{id}")
    public ResponseEntity<Conflito> updateConflito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Conflito conflito
    ) throws URISyntaxException {
        log.debug("REST request to update Conflito : {}, {}", id, conflito);
        if (conflito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conflito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conflitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Conflito result = conflitoService.save(conflito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conflito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conflitos/:id} : Partial updates given fields of an existing conflito, field will ignore if it is null
     *
     * @param id the id of the conflito to save.
     * @param conflito the conflito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conflito,
     * or with status {@code 400 (Bad Request)} if the conflito is not valid,
     * or with status {@code 404 (Not Found)} if the conflito is not found,
     * or with status {@code 500 (Internal Server Error)} if the conflito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conflitos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Conflito> partialUpdateConflito(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Conflito conflito
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conflito partially : {}, {}", id, conflito);
        if (conflito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conflito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conflitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Conflito> result = conflitoService.partialUpdate(conflito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conflito.getId().toString())
        );
    }

    /**
     * {@code GET  /conflitos} : get all the conflitos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conflitos in body.
     */
    @GetMapping("/conflitos")
    public ResponseEntity<List<Conflito>> getAllConflitos(ConflitoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Conflitos by criteria: {}", criteria);
        Page<Conflito> page = conflitoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conflitos/count} : count all the conflitos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/conflitos/count")
    public ResponseEntity<Long> countConflitos(ConflitoCriteria criteria) {
        log.debug("REST request to count Conflitos by criteria: {}", criteria);
        return ResponseEntity.ok().body(conflitoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conflitos/:id} : get the "id" conflito.
     *
     * @param id the id of the conflito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conflito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conflitos/{id}")
    public ResponseEntity<Conflito> getConflito(@PathVariable Long id) {
        log.debug("REST request to get Conflito : {}", id);
        Optional<Conflito> conflito = conflitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conflito);
    }

    /**
     * {@code DELETE  /conflitos/:id} : delete the "id" conflito.
     *
     * @param id the id of the conflito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conflitos/{id}")
    public ResponseEntity<Void> deleteConflito(@PathVariable Long id) {
        log.debug("REST request to delete Conflito : {}", id);
        conflitoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
