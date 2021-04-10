package br.com.cidha.web.rest;

import br.com.cidha.domain.ParteInteresssada;
import br.com.cidha.repository.ParteInteresssadaRepository;
import br.com.cidha.service.ParteInteresssadaQueryService;
import br.com.cidha.service.ParteInteresssadaService;
import br.com.cidha.service.criteria.ParteInteresssadaCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.ParteInteresssada}.
 */
@RestController
@RequestMapping("/api")
public class ParteInteresssadaResource {

    private final Logger log = LoggerFactory.getLogger(ParteInteresssadaResource.class);

    private static final String ENTITY_NAME = "parteInteresssada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParteInteresssadaService parteInteresssadaService;

    private final ParteInteresssadaRepository parteInteresssadaRepository;

    private final ParteInteresssadaQueryService parteInteresssadaQueryService;

    public ParteInteresssadaResource(
        ParteInteresssadaService parteInteresssadaService,
        ParteInteresssadaRepository parteInteresssadaRepository,
        ParteInteresssadaQueryService parteInteresssadaQueryService
    ) {
        this.parteInteresssadaService = parteInteresssadaService;
        this.parteInteresssadaRepository = parteInteresssadaRepository;
        this.parteInteresssadaQueryService = parteInteresssadaQueryService;
    }

    /**
     * {@code POST  /parte-interesssadas} : Create a new parteInteresssada.
     *
     * @param parteInteresssada the parteInteresssada to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parteInteresssada, or with status {@code 400 (Bad Request)} if the parteInteresssada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parte-interesssadas")
    public ResponseEntity<ParteInteresssada> createParteInteresssada(@RequestBody ParteInteresssada parteInteresssada)
        throws URISyntaxException {
        log.debug("REST request to save ParteInteresssada : {}", parteInteresssada);
        if (parteInteresssada.getId() != null) {
            throw new BadRequestAlertException("A new parteInteresssada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParteInteresssada result = parteInteresssadaService.save(parteInteresssada);
        return ResponseEntity
            .created(new URI("/api/parte-interesssadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parte-interesssadas/:id} : Updates an existing parteInteresssada.
     *
     * @param id the id of the parteInteresssada to save.
     * @param parteInteresssada the parteInteresssada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parteInteresssada,
     * or with status {@code 400 (Bad Request)} if the parteInteresssada is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parteInteresssada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parte-interesssadas/{id}")
    public ResponseEntity<ParteInteresssada> updateParteInteresssada(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParteInteresssada parteInteresssada
    ) throws URISyntaxException {
        log.debug("REST request to update ParteInteresssada : {}, {}", id, parteInteresssada);
        if (parteInteresssada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parteInteresssada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parteInteresssadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParteInteresssada result = parteInteresssadaService.save(parteInteresssada);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parteInteresssada.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parte-interesssadas/:id} : Partial updates given fields of an existing parteInteresssada, field will ignore if it is null
     *
     * @param id the id of the parteInteresssada to save.
     * @param parteInteresssada the parteInteresssada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parteInteresssada,
     * or with status {@code 400 (Bad Request)} if the parteInteresssada is not valid,
     * or with status {@code 404 (Not Found)} if the parteInteresssada is not found,
     * or with status {@code 500 (Internal Server Error)} if the parteInteresssada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parte-interesssadas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ParteInteresssada> partialUpdateParteInteresssada(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParteInteresssada parteInteresssada
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParteInteresssada partially : {}, {}", id, parteInteresssada);
        if (parteInteresssada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parteInteresssada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parteInteresssadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParteInteresssada> result = parteInteresssadaService.partialUpdate(parteInteresssada);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parteInteresssada.getId().toString())
        );
    }

    /**
     * {@code GET  /parte-interesssadas} : get all the parteInteresssadas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parteInteresssadas in body.
     */
    @GetMapping("/parte-interesssadas")
    public ResponseEntity<List<ParteInteresssada>> getAllParteInteresssadas(ParteInteresssadaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParteInteresssadas by criteria: {}", criteria);
        Page<ParteInteresssada> page = parteInteresssadaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parte-interesssadas/count} : count all the parteInteresssadas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parte-interesssadas/count")
    public ResponseEntity<Long> countParteInteresssadas(ParteInteresssadaCriteria criteria) {
        log.debug("REST request to count ParteInteresssadas by criteria: {}", criteria);
        return ResponseEntity.ok().body(parteInteresssadaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parte-interesssadas/:id} : get the "id" parteInteresssada.
     *
     * @param id the id of the parteInteresssada to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parteInteresssada, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parte-interesssadas/{id}")
    public ResponseEntity<ParteInteresssada> getParteInteresssada(@PathVariable Long id) {
        log.debug("REST request to get ParteInteresssada : {}", id);
        Optional<ParteInteresssada> parteInteresssada = parteInteresssadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parteInteresssada);
    }

    /**
     * {@code DELETE  /parte-interesssadas/:id} : delete the "id" parteInteresssada.
     *
     * @param id the id of the parteInteresssada to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parte-interesssadas/{id}")
    public ResponseEntity<Void> deleteParteInteresssada(@PathVariable Long id) {
        log.debug("REST request to delete ParteInteresssada : {}", id);
        parteInteresssadaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
