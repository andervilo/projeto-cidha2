package br.com.cidha.web.rest;

import br.com.cidha.domain.ConcessaoLiminarCassada;
import br.com.cidha.repository.ConcessaoLiminarCassadaRepository;
import br.com.cidha.service.ConcessaoLiminarCassadaQueryService;
import br.com.cidha.service.ConcessaoLiminarCassadaService;
import br.com.cidha.service.criteria.ConcessaoLiminarCassadaCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.ConcessaoLiminarCassada}.
 */
@RestController
@RequestMapping("/api")
public class ConcessaoLiminarCassadaResource {

    private final Logger log = LoggerFactory.getLogger(ConcessaoLiminarCassadaResource.class);

    private static final String ENTITY_NAME = "concessaoLiminarCassada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcessaoLiminarCassadaService concessaoLiminarCassadaService;

    private final ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository;

    private final ConcessaoLiminarCassadaQueryService concessaoLiminarCassadaQueryService;

    public ConcessaoLiminarCassadaResource(
        ConcessaoLiminarCassadaService concessaoLiminarCassadaService,
        ConcessaoLiminarCassadaRepository concessaoLiminarCassadaRepository,
        ConcessaoLiminarCassadaQueryService concessaoLiminarCassadaQueryService
    ) {
        this.concessaoLiminarCassadaService = concessaoLiminarCassadaService;
        this.concessaoLiminarCassadaRepository = concessaoLiminarCassadaRepository;
        this.concessaoLiminarCassadaQueryService = concessaoLiminarCassadaQueryService;
    }

    /**
     * {@code POST  /concessao-liminar-cassadas} : Create a new concessaoLiminarCassada.
     *
     * @param concessaoLiminarCassada the concessaoLiminarCassada to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concessaoLiminarCassada, or with status {@code 400 (Bad Request)} if the concessaoLiminarCassada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concessao-liminar-cassadas")
    public ResponseEntity<ConcessaoLiminarCassada> createConcessaoLiminarCassada(
        @RequestBody ConcessaoLiminarCassada concessaoLiminarCassada
    ) throws URISyntaxException {
        log.debug("REST request to save ConcessaoLiminarCassada : {}", concessaoLiminarCassada);
        if (concessaoLiminarCassada.getId() != null) {
            throw new BadRequestAlertException("A new concessaoLiminarCassada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConcessaoLiminarCassada result = concessaoLiminarCassadaService.save(concessaoLiminarCassada);
        return ResponseEntity
            .created(new URI("/api/concessao-liminar-cassadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concessao-liminar-cassadas/:id} : Updates an existing concessaoLiminarCassada.
     *
     * @param id the id of the concessaoLiminarCassada to save.
     * @param concessaoLiminarCassada the concessaoLiminarCassada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessaoLiminarCassada,
     * or with status {@code 400 (Bad Request)} if the concessaoLiminarCassada is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concessaoLiminarCassada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concessao-liminar-cassadas/{id}")
    public ResponseEntity<ConcessaoLiminarCassada> updateConcessaoLiminarCassada(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcessaoLiminarCassada concessaoLiminarCassada
    ) throws URISyntaxException {
        log.debug("REST request to update ConcessaoLiminarCassada : {}, {}", id, concessaoLiminarCassada);
        if (concessaoLiminarCassada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessaoLiminarCassada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoLiminarCassadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConcessaoLiminarCassada result = concessaoLiminarCassadaService.save(concessaoLiminarCassada);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessaoLiminarCassada.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concessao-liminar-cassadas/:id} : Partial updates given fields of an existing concessaoLiminarCassada, field will ignore if it is null
     *
     * @param id the id of the concessaoLiminarCassada to save.
     * @param concessaoLiminarCassada the concessaoLiminarCassada to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concessaoLiminarCassada,
     * or with status {@code 400 (Bad Request)} if the concessaoLiminarCassada is not valid,
     * or with status {@code 404 (Not Found)} if the concessaoLiminarCassada is not found,
     * or with status {@code 500 (Internal Server Error)} if the concessaoLiminarCassada couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concessao-liminar-cassadas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ConcessaoLiminarCassada> partialUpdateConcessaoLiminarCassada(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConcessaoLiminarCassada concessaoLiminarCassada
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConcessaoLiminarCassada partially : {}, {}", id, concessaoLiminarCassada);
        if (concessaoLiminarCassada.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concessaoLiminarCassada.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concessaoLiminarCassadaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConcessaoLiminarCassada> result = concessaoLiminarCassadaService.partialUpdate(concessaoLiminarCassada);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concessaoLiminarCassada.getId().toString())
        );
    }

    /**
     * {@code GET  /concessao-liminar-cassadas} : get all the concessaoLiminarCassadas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concessaoLiminarCassadas in body.
     */
    @GetMapping("/concessao-liminar-cassadas")
    public ResponseEntity<List<ConcessaoLiminarCassada>> getAllConcessaoLiminarCassadas(
        ConcessaoLiminarCassadaCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get ConcessaoLiminarCassadas by criteria: {}", criteria);
        Page<ConcessaoLiminarCassada> page = concessaoLiminarCassadaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concessao-liminar-cassadas/count} : count all the concessaoLiminarCassadas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/concessao-liminar-cassadas/count")
    public ResponseEntity<Long> countConcessaoLiminarCassadas(ConcessaoLiminarCassadaCriteria criteria) {
        log.debug("REST request to count ConcessaoLiminarCassadas by criteria: {}", criteria);
        return ResponseEntity.ok().body(concessaoLiminarCassadaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /concessao-liminar-cassadas/:id} : get the "id" concessaoLiminarCassada.
     *
     * @param id the id of the concessaoLiminarCassada to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concessaoLiminarCassada, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concessao-liminar-cassadas/{id}")
    public ResponseEntity<ConcessaoLiminarCassada> getConcessaoLiminarCassada(@PathVariable Long id) {
        log.debug("REST request to get ConcessaoLiminarCassada : {}", id);
        Optional<ConcessaoLiminarCassada> concessaoLiminarCassada = concessaoLiminarCassadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concessaoLiminarCassada);
    }

    /**
     * {@code DELETE  /concessao-liminar-cassadas/:id} : delete the "id" concessaoLiminarCassada.
     *
     * @param id the id of the concessaoLiminarCassada to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concessao-liminar-cassadas/{id}")
    public ResponseEntity<Void> deleteConcessaoLiminarCassada(@PathVariable Long id) {
        log.debug("REST request to delete ConcessaoLiminarCassada : {}", id);
        concessaoLiminarCassadaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
