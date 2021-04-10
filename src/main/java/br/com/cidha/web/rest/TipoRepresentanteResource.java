package br.com.cidha.web.rest;

import br.com.cidha.domain.TipoRepresentante;
import br.com.cidha.repository.TipoRepresentanteRepository;
import br.com.cidha.service.TipoRepresentanteQueryService;
import br.com.cidha.service.TipoRepresentanteService;
import br.com.cidha.service.criteria.TipoRepresentanteCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.TipoRepresentante}.
 */
@RestController
@RequestMapping("/api")
public class TipoRepresentanteResource {

    private final Logger log = LoggerFactory.getLogger(TipoRepresentanteResource.class);

    private static final String ENTITY_NAME = "tipoRepresentante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoRepresentanteService tipoRepresentanteService;

    private final TipoRepresentanteRepository tipoRepresentanteRepository;

    private final TipoRepresentanteQueryService tipoRepresentanteQueryService;

    public TipoRepresentanteResource(
        TipoRepresentanteService tipoRepresentanteService,
        TipoRepresentanteRepository tipoRepresentanteRepository,
        TipoRepresentanteQueryService tipoRepresentanteQueryService
    ) {
        this.tipoRepresentanteService = tipoRepresentanteService;
        this.tipoRepresentanteRepository = tipoRepresentanteRepository;
        this.tipoRepresentanteQueryService = tipoRepresentanteQueryService;
    }

    /**
     * {@code POST  /tipo-representantes} : Create a new tipoRepresentante.
     *
     * @param tipoRepresentante the tipoRepresentante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoRepresentante, or with status {@code 400 (Bad Request)} if the tipoRepresentante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-representantes")
    public ResponseEntity<TipoRepresentante> createTipoRepresentante(@RequestBody TipoRepresentante tipoRepresentante)
        throws URISyntaxException {
        log.debug("REST request to save TipoRepresentante : {}", tipoRepresentante);
        if (tipoRepresentante.getId() != null) {
            throw new BadRequestAlertException("A new tipoRepresentante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRepresentante result = tipoRepresentanteService.save(tipoRepresentante);
        return ResponseEntity
            .created(new URI("/api/tipo-representantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-representantes/:id} : Updates an existing tipoRepresentante.
     *
     * @param id the id of the tipoRepresentante to save.
     * @param tipoRepresentante the tipoRepresentante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRepresentante,
     * or with status {@code 400 (Bad Request)} if the tipoRepresentante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoRepresentante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-representantes/{id}")
    public ResponseEntity<TipoRepresentante> updateTipoRepresentante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRepresentante tipoRepresentante
    ) throws URISyntaxException {
        log.debug("REST request to update TipoRepresentante : {}, {}", id, tipoRepresentante);
        if (tipoRepresentante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRepresentante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRepresentanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoRepresentante result = tipoRepresentanteService.save(tipoRepresentante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoRepresentante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-representantes/:id} : Partial updates given fields of an existing tipoRepresentante, field will ignore if it is null
     *
     * @param id the id of the tipoRepresentante to save.
     * @param tipoRepresentante the tipoRepresentante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoRepresentante,
     * or with status {@code 400 (Bad Request)} if the tipoRepresentante is not valid,
     * or with status {@code 404 (Not Found)} if the tipoRepresentante is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoRepresentante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-representantes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoRepresentante> partialUpdateTipoRepresentante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoRepresentante tipoRepresentante
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoRepresentante partially : {}, {}", id, tipoRepresentante);
        if (tipoRepresentante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoRepresentante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoRepresentanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoRepresentante> result = tipoRepresentanteService.partialUpdate(tipoRepresentante);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoRepresentante.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-representantes} : get all the tipoRepresentantes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRepresentantes in body.
     */
    @GetMapping("/tipo-representantes")
    public ResponseEntity<List<TipoRepresentante>> getAllTipoRepresentantes(TipoRepresentanteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoRepresentantes by criteria: {}", criteria);
        Page<TipoRepresentante> page = tipoRepresentanteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-representantes/count} : count all the tipoRepresentantes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-representantes/count")
    public ResponseEntity<Long> countTipoRepresentantes(TipoRepresentanteCriteria criteria) {
        log.debug("REST request to count TipoRepresentantes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoRepresentanteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-representantes/:id} : get the "id" tipoRepresentante.
     *
     * @param id the id of the tipoRepresentante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoRepresentante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-representantes/{id}")
    public ResponseEntity<TipoRepresentante> getTipoRepresentante(@PathVariable Long id) {
        log.debug("REST request to get TipoRepresentante : {}", id);
        Optional<TipoRepresentante> tipoRepresentante = tipoRepresentanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRepresentante);
    }

    /**
     * {@code DELETE  /tipo-representantes/:id} : delete the "id" tipoRepresentante.
     *
     * @param id the id of the tipoRepresentante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-representantes/{id}")
    public ResponseEntity<Void> deleteTipoRepresentante(@PathVariable Long id) {
        log.debug("REST request to delete TipoRepresentante : {}", id);
        tipoRepresentanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
