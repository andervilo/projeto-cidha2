package br.com.cidha.web.rest;

import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.repository.OpcaoRecursoRepository;
import br.com.cidha.service.OpcaoRecursoQueryService;
import br.com.cidha.service.OpcaoRecursoService;
import br.com.cidha.service.criteria.OpcaoRecursoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.OpcaoRecurso}.
 */
@RestController
@RequestMapping("/api")
public class OpcaoRecursoResource {

    private final Logger log = LoggerFactory.getLogger(OpcaoRecursoResource.class);

    private static final String ENTITY_NAME = "opcaoRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpcaoRecursoService opcaoRecursoService;

    private final OpcaoRecursoRepository opcaoRecursoRepository;

    private final OpcaoRecursoQueryService opcaoRecursoQueryService;

    public OpcaoRecursoResource(
        OpcaoRecursoService opcaoRecursoService,
        OpcaoRecursoRepository opcaoRecursoRepository,
        OpcaoRecursoQueryService opcaoRecursoQueryService
    ) {
        this.opcaoRecursoService = opcaoRecursoService;
        this.opcaoRecursoRepository = opcaoRecursoRepository;
        this.opcaoRecursoQueryService = opcaoRecursoQueryService;
    }

    /**
     * {@code POST  /opcao-recursos} : Create a new opcaoRecurso.
     *
     * @param opcaoRecurso the opcaoRecurso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opcaoRecurso, or with status {@code 400 (Bad Request)} if the opcaoRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opcao-recursos")
    public ResponseEntity<OpcaoRecurso> createOpcaoRecurso(@RequestBody OpcaoRecurso opcaoRecurso) throws URISyntaxException {
        log.debug("REST request to save OpcaoRecurso : {}", opcaoRecurso);
        if (opcaoRecurso.getId() != null) {
            throw new BadRequestAlertException("A new opcaoRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpcaoRecurso result = opcaoRecursoService.save(opcaoRecurso);
        return ResponseEntity
            .created(new URI("/api/opcao-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opcao-recursos/:id} : Updates an existing opcaoRecurso.
     *
     * @param id the id of the opcaoRecurso to save.
     * @param opcaoRecurso the opcaoRecurso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRecurso,
     * or with status {@code 400 (Bad Request)} if the opcaoRecurso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRecurso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opcao-recursos/{id}")
    public ResponseEntity<OpcaoRecurso> updateOpcaoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpcaoRecurso opcaoRecurso
    ) throws URISyntaxException {
        log.debug("REST request to update OpcaoRecurso : {}, {}", id, opcaoRecurso);
        if (opcaoRecurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRecurso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpcaoRecurso result = opcaoRecursoService.save(opcaoRecurso);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRecurso.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opcao-recursos/:id} : Partial updates given fields of an existing opcaoRecurso, field will ignore if it is null
     *
     * @param id the id of the opcaoRecurso to save.
     * @param opcaoRecurso the opcaoRecurso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRecurso,
     * or with status {@code 400 (Bad Request)} if the opcaoRecurso is not valid,
     * or with status {@code 404 (Not Found)} if the opcaoRecurso is not found,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRecurso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opcao-recursos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OpcaoRecurso> partialUpdateOpcaoRecurso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpcaoRecurso opcaoRecurso
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpcaoRecurso partially : {}, {}", id, opcaoRecurso);
        if (opcaoRecurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRecurso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRecursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpcaoRecurso> result = opcaoRecursoService.partialUpdate(opcaoRecurso);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRecurso.getId().toString())
        );
    }

    /**
     * {@code GET  /opcao-recursos} : get all the opcaoRecursos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opcaoRecursos in body.
     */
    @GetMapping("/opcao-recursos")
    public ResponseEntity<List<OpcaoRecurso>> getAllOpcaoRecursos(OpcaoRecursoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OpcaoRecursos by criteria: {}", criteria);
        Page<OpcaoRecurso> page = opcaoRecursoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opcao-recursos/count} : count all the opcaoRecursos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/opcao-recursos/count")
    public ResponseEntity<Long> countOpcaoRecursos(OpcaoRecursoCriteria criteria) {
        log.debug("REST request to count OpcaoRecursos by criteria: {}", criteria);
        return ResponseEntity.ok().body(opcaoRecursoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opcao-recursos/:id} : get the "id" opcaoRecurso.
     *
     * @param id the id of the opcaoRecurso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opcaoRecurso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opcao-recursos/{id}")
    public ResponseEntity<OpcaoRecurso> getOpcaoRecurso(@PathVariable Long id) {
        log.debug("REST request to get OpcaoRecurso : {}", id);
        Optional<OpcaoRecurso> opcaoRecurso = opcaoRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opcaoRecurso);
    }

    /**
     * {@code DELETE  /opcao-recursos/:id} : delete the "id" opcaoRecurso.
     *
     * @param id the id of the opcaoRecurso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opcao-recursos/{id}")
    public ResponseEntity<Void> deleteOpcaoRecurso(@PathVariable Long id) {
        log.debug("REST request to delete OpcaoRecurso : {}", id);
        opcaoRecursoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
