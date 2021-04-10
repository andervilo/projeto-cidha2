package br.com.cidha.web.rest;

import br.com.cidha.domain.TipoRecurso;
import br.com.cidha.repository.TipoRecursoRepository;
import br.com.cidha.service.TipoRecursoQueryService;
import br.com.cidha.service.TipoRecursoService;
import br.com.cidha.service.criteria.TipoRecursoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.TipoRecurso}.
 */
@RestController
@RequestMapping("/api")
public class TipoRecursoResource {

    private final Logger log = LoggerFactory.getLogger(TipoRecursoResource.class);

    private static final String ENTITY_NAME = "tipoRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoRecursoService tipoRecursoService;

    private final TipoRecursoRepository tipoRecursoRepository;

    private final TipoRecursoQueryService tipoRecursoQueryService;

    public TipoRecursoResource(
        TipoRecursoService tipoRecursoService,
        TipoRecursoRepository tipoRecursoRepository,
        TipoRecursoQueryService tipoRecursoQueryService
    ) {
        this.tipoRecursoService = tipoRecursoService;
        this.tipoRecursoRepository = tipoRecursoRepository;
        this.tipoRecursoQueryService = tipoRecursoQueryService;
    }

    /**
     * {@code POST  /tipo-recursos} : Create a new tipoRecurso.
     *
     * @param tipoRecurso the tipoRecurso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoRecurso, or with status {@code 400 (Bad Request)} if the tipoRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-recursos")
    public ResponseEntity<TipoRecurso> createTipoRecurso(@RequestBody TipoRecurso tipoRecurso) throws URISyntaxException {
        log.debug("REST request to save TipoRecurso : {}", tipoRecurso);
        if (tipoRecurso.getId() != null) {
            throw new BadRequestAlertException("A new tipoRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRecurso result = tipoRecursoService.save(tipoRecurso);
        return ResponseEntity
            .created(new URI("/api/tipo-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-recursos/:id} : Updates an existing tipoRecurso.
     *
     * @param id the id of the tipoRecurso to save.
     * @param tipoRecurso the tipoRecurso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecurso,
     * or with status {@code 400 (Bad Request)} if the tipoRecurso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecurso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-recursos/{id}")
    public ResponseEntity<TipoRecurso> updateTipoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecurso tipoRecurso
    ) throws URISyntaxException {
        log.debug("REST request to update TipoRecurso : {}, {}", id, tipoRecurso);
        if (tipoRecurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecurso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoRecurso result = tipoRecursoService.save(tipoRecurso);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoRecurso.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-recursos/:id} : Partial updates given fields of an existing tipoRecurso, field will ignore if it is null
     *
     * @param id the id of the tipoRecurso to save.
     * @param tipoRecurso the tipoRecurso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRecurso,
     * or with status {@code 400 (Bad Request)} if the tipoRecurso is not valid,
     * or with status {@code 404 (Not Found)} if the tipoRecurso is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoRecurso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-recursos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoRecurso> partialUpdateTipoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRecurso tipoRecurso
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoRecurso partially : {}, {}", id, tipoRecurso);
        if (tipoRecurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRecurso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoRecurso> result = tipoRecursoService.partialUpdate(tipoRecurso);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoRecurso.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-recursos} : get all the tipoRecursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRecursos in body.
     */
    @GetMapping("/tipo-recursos")
    public ResponseEntity<List<TipoRecurso>> getAllTipoRecursos(TipoRecursoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoRecursos by criteria: {}", criteria);
        Page<TipoRecurso> page = tipoRecursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-recursos/count} : count all the tipoRecursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-recursos/count")
    public ResponseEntity<Long> countTipoRecursos(TipoRecursoCriteria criteria) {
        log.debug("REST request to count TipoRecursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoRecursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-recursos/:id} : get the "id" tipoRecurso.
     *
     * @param id the id of the tipoRecurso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoRecurso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-recursos/{id}")
    public ResponseEntity<TipoRecurso> getTipoRecurso(@PathVariable Long id) {
        log.debug("REST request to get TipoRecurso : {}", id);
        Optional<TipoRecurso> tipoRecurso = tipoRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRecurso);
    }

    /**
     * {@code DELETE  /tipo-recursos/:id} : delete the "id" tipoRecurso.
     *
     * @param id the id of the tipoRecurso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-recursos/{id}")
    public ResponseEntity<Void> deleteTipoRecurso(@PathVariable Long id) {
        log.debug("REST request to delete TipoRecurso : {}", id);
        tipoRecursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
