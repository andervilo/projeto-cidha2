package br.com.cidha.web.rest;

import br.com.cidha.domain.ConcessaoLiminar;
import br.com.cidha.repository.ConcessaoLiminarRepository;
import br.com.cidha.service.ConcessaoLiminarQueryService;
import br.com.cidha.service.ConcessaoLiminarService;
import br.com.cidha.service.criteria.ConcessaoLiminarCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.ConcessaoLiminar}.
 */
@RestController
@RequestMapping("/api")
public class ConcessaoLiminarResource {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarResource.class);

    private static final String ENTITY_NAME = "concessaoLiminar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcessaoLiminarService concessaoLiminarService;

    private final ConcessaoLiminarRepository concessaoLiminarRepository;

    private final ConcessaoLiminarQueryService concessaoLiminarQueryService;

    public ConcessaoLiminarResource(
        ConcessaoLiminarService concessaoLiminarService,
        ConcessaoLiminarRepository concessaoLiminarRepository,
        ConcessaoLiminarQueryService concessaoLiminarQueryService
    ) {
        this.concessaoLiminarService = concessaoLiminarService;
        this.concessaoLiminarRepository = concessaoLiminarRepository;
        this.concessaoLiminarQueryService = concessaoLiminarQueryService;
    }

    /**
     * {@code POST  /concessao-liminars} : Create a new concessaoLiminar.
     *
     * @param concessaoLiminar the concessaoLiminar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concessaoLiminar, or with status {@code 400 (Bad Request)} if the concessaoLiminar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concessao-liminars")
    public ResponseEntity<ConcessaoLiminar> createConcessaoLiminar(@RequestBody ConcessaoLiminar concessaoLiminar)
        throws URISyntaxException {
        log.debug("REST request to save ConcessaoLiminar : {}", concessaoLiminar);
        if (concessaoLiminar.getId() != null) {
            throw new BadRequestAlertException("A new concessaoLiminar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcessaoLiminar result = concessaoLiminarService.save(concessaoLiminar);
        return ResponseEntity
            .created(new URI("/api/concessao-liminars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concessao-liminars/:id} : Updates an existing concessaoLiminar.
     *
     * @param id the id of the concessaoLiminar to save.
     * @param concessaoLiminar the concessaoLiminar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessaoLiminar,
     * or with status {@code 400 (Bad Request)} if the concessaoLiminar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concessaoLiminar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concessao-liminars/{id}")
    public ResponseEntity<ConcessaoLiminar> updateConcessaoLiminar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcessaoLiminar concessaoLiminar
    ) throws URISyntaxException {
        log.debug("REST request to update ConcessaoLiminar : {}, {}", id, concessaoLiminar);
        if (concessaoLiminar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessaoLiminar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoLiminarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConcessaoLiminar result = concessaoLiminarService.save(concessaoLiminar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessaoLiminar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concessao-liminars/:id} : Partial updates given fields of an existing concessaoLiminar, field will ignore if it is null
     *
     * @param id the id of the concessaoLiminar to save.
     * @param concessaoLiminar the concessaoLiminar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessaoLiminar,
     * or with status {@code 400 (Bad Request)} if the concessaoLiminar is not valid,
     * or with status {@code 404 (Not Found)} if the concessaoLiminar is not found,
     * or with status {@code 500 (Internal Server Error)} if the concessaoLiminar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concessao-liminars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ConcessaoLiminar> partialUpdateConcessaoLiminar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcessaoLiminar concessaoLiminar
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConcessaoLiminar partially : {}, {}", id, concessaoLiminar);
        if (concessaoLiminar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessaoLiminar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoLiminarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConcessaoLiminar> result = concessaoLiminarService.partialUpdate(concessaoLiminar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessaoLiminar.getId().toString())
        );
    }

    /**
     * {@code GET  /concessao-liminars} : get all the concessaoLiminars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concessaoLiminars in body.
     */
    @GetMapping("/concessao-liminars")
    public ResponseEntity<List<ConcessaoLiminar>> getAllConcessaoLiminars(ConcessaoLiminarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ConcessaoLiminars by criteria: {}", criteria);
        Page<ConcessaoLiminar> page = concessaoLiminarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concessao-liminars/count} : count all the concessaoLiminars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/concessao-liminars/count")
    public ResponseEntity<Long> countConcessaoLiminars(ConcessaoLiminarCriteria criteria) {
        log.debug("REST request to count ConcessaoLiminars by criteria: {}", criteria);
        return ResponseEntity.ok().body(concessaoLiminarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /concessao-liminars/:id} : get the "id" concessaoLiminar.
     *
     * @param id the id of the concessaoLiminar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concessaoLiminar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concessao-liminars/{id}")
    public ResponseEntity<ConcessaoLiminar> getConcessaoLiminar(@PathVariable Long id) {
        log.debug("REST request to get ConcessaoLiminar : {}", id);
        Optional<ConcessaoLiminar> concessaoLiminar = concessaoLiminarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concessaoLiminar);
    }

    /**
     * {@code DELETE  /concessao-liminars/:id} : delete the "id" concessaoLiminar.
     *
     * @param id the id of the concessaoLiminar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concessao-liminars/{id}")
    public ResponseEntity<Void> deleteConcessaoLiminar(@PathVariable Long id) {
        log.debug("REST request to delete ConcessaoLiminar : {}", id);
        concessaoLiminarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
