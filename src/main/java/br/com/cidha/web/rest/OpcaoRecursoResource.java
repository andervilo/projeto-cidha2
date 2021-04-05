package br.com.cidha.web.rest;

import br.com.cidha.domain.OpcaoRecurso;
import br.com.cidha.service.OpcaoRecursoService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.OpcaoRecursoCriteria;
import br.com.cidha.service.OpcaoRecursoQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

    private final OpcaoRecursoQueryService opcaoRecursoQueryService;

    public OpcaoRecursoResource(OpcaoRecursoService opcaoRecursoService, OpcaoRecursoQueryService opcaoRecursoQueryService) {
        this.opcaoRecursoService = opcaoRecursoService;
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
        return ResponseEntity.created(new URI("/api/opcao-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opcao-recursos} : Updates an existing opcaoRecurso.
     *
     * @param opcaoRecurso the opcaoRecurso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRecurso,
     * or with status {@code 400 (Bad Request)} if the opcaoRecurso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRecurso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opcao-recursos")
    public ResponseEntity<OpcaoRecurso> updateOpcaoRecurso(@RequestBody OpcaoRecurso opcaoRecurso) throws URISyntaxException {
        log.debug("REST request to update OpcaoRecurso : {}", opcaoRecurso);
        if (opcaoRecurso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpcaoRecurso result = opcaoRecursoService.save(opcaoRecurso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRecurso.getId().toString()))
            .body(result);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
