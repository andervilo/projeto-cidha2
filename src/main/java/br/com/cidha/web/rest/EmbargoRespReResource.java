package br.com.cidha.web.rest;

import br.com.cidha.domain.EmbargoRespRe;
import br.com.cidha.repository.EmbargoRespReRepository;
import br.com.cidha.service.EmbargoRespReQueryService;
import br.com.cidha.service.EmbargoRespReService;
import br.com.cidha.service.criteria.EmbargoRespReCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.EmbargoRespRe}.
 */
@RestController
@RequestMapping("/api")
public class EmbargoRespReResource {

    private final Logger log = LoggerFactory.getLogger(EmbargoRespReResource.class);

    private static final String ENTITY_NAME = "embargoRespRe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbargoRespReService embargoRespReService;

    private final EmbargoRespReRepository embargoRespReRepository;

    private final EmbargoRespReQueryService embargoRespReQueryService;

    public EmbargoRespReResource(
        EmbargoRespReService embargoRespReService,
        EmbargoRespReRepository embargoRespReRepository,
        EmbargoRespReQueryService embargoRespReQueryService
    ) {
        this.embargoRespReService = embargoRespReService;
        this.embargoRespReRepository = embargoRespReRepository;
        this.embargoRespReQueryService = embargoRespReQueryService;
    }

    /**
     * {@code POST  /embargo-resp-res} : Create a new embargoRespRe.
     *
     * @param embargoRespRe the embargoRespRe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embargoRespRe, or with status {@code 400 (Bad Request)} if the embargoRespRe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embargo-resp-res")
    public ResponseEntity<EmbargoRespRe> createEmbargoRespRe(@RequestBody EmbargoRespRe embargoRespRe) throws URISyntaxException {
        log.debug("REST request to save EmbargoRespRe : {}", embargoRespRe);
        if (embargoRespRe.getId() != null) {
            throw new BadRequestAlertException("A new embargoRespRe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbargoRespRe result = embargoRespReService.save(embargoRespRe);
        return ResponseEntity
            .created(new URI("/api/embargo-resp-res/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embargo-resp-res/:id} : Updates an existing embargoRespRe.
     *
     * @param id the id of the embargoRespRe to save.
     * @param embargoRespRe the embargoRespRe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoRespRe,
     * or with status {@code 400 (Bad Request)} if the embargoRespRe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embargoRespRe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embargo-resp-res/{id}")
    public ResponseEntity<EmbargoRespRe> updateEmbargoRespRe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoRespRe embargoRespRe
    ) throws URISyntaxException {
        log.debug("REST request to update EmbargoRespRe : {}, {}", id, embargoRespRe);
        if (embargoRespRe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoRespRe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoRespReRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmbargoRespRe result = embargoRespReService.save(embargoRespRe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoRespRe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /embargo-resp-res/:id} : Partial updates given fields of an existing embargoRespRe, field will ignore if it is null
     *
     * @param id the id of the embargoRespRe to save.
     * @param embargoRespRe the embargoRespRe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoRespRe,
     * or with status {@code 400 (Bad Request)} if the embargoRespRe is not valid,
     * or with status {@code 404 (Not Found)} if the embargoRespRe is not found,
     * or with status {@code 500 (Internal Server Error)} if the embargoRespRe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/embargo-resp-res/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmbargoRespRe> partialUpdateEmbargoRespRe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoRespRe embargoRespRe
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmbargoRespRe partially : {}, {}", id, embargoRespRe);
        if (embargoRespRe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoRespRe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoRespReRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmbargoRespRe> result = embargoRespReService.partialUpdate(embargoRespRe);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoRespRe.getId().toString())
        );
    }

    /**
     * {@code GET  /embargo-resp-res} : get all the embargoRespRes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embargoRespRes in body.
     */
    @GetMapping("/embargo-resp-res")
    public ResponseEntity<List<EmbargoRespRe>> getAllEmbargoRespRes(EmbargoRespReCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmbargoRespRes by criteria: {}", criteria);
        Page<EmbargoRespRe> page = embargoRespReQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embargo-resp-res/count} : count all the embargoRespRes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/embargo-resp-res/count")
    public ResponseEntity<Long> countEmbargoRespRes(EmbargoRespReCriteria criteria) {
        log.debug("REST request to count EmbargoRespRes by criteria: {}", criteria);
        return ResponseEntity.ok().body(embargoRespReQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /embargo-resp-res/:id} : get the "id" embargoRespRe.
     *
     * @param id the id of the embargoRespRe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embargoRespRe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embargo-resp-res/{id}")
    public ResponseEntity<EmbargoRespRe> getEmbargoRespRe(@PathVariable Long id) {
        log.debug("REST request to get EmbargoRespRe : {}", id);
        Optional<EmbargoRespRe> embargoRespRe = embargoRespReService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embargoRespRe);
    }

    /**
     * {@code DELETE  /embargo-resp-res/:id} : delete the "id" embargoRespRe.
     *
     * @param id the id of the embargoRespRe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embargo-resp-res/{id}")
    public ResponseEntity<Void> deleteEmbargoRespRe(@PathVariable Long id) {
        log.debug("REST request to delete EmbargoRespRe : {}", id);
        embargoRespReService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
