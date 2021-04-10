package br.com.cidha.web.rest;

import br.com.cidha.domain.EtniaIndigena;
import br.com.cidha.repository.EtniaIndigenaRepository;
import br.com.cidha.service.EtniaIndigenaQueryService;
import br.com.cidha.service.EtniaIndigenaService;
import br.com.cidha.service.criteria.EtniaIndigenaCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.EtniaIndigena}.
 */
@RestController
@RequestMapping("/api")
public class EtniaIndigenaResource {

    private final Logger log = LoggerFactory.getLogger(EtniaIndigenaResource.class);

    private static final String ENTITY_NAME = "etniaIndigena";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtniaIndigenaService etniaIndigenaService;

    private final EtniaIndigenaRepository etniaIndigenaRepository;

    private final EtniaIndigenaQueryService etniaIndigenaQueryService;

    public EtniaIndigenaResource(
        EtniaIndigenaService etniaIndigenaService,
        EtniaIndigenaRepository etniaIndigenaRepository,
        EtniaIndigenaQueryService etniaIndigenaQueryService
    ) {
        this.etniaIndigenaService = etniaIndigenaService;
        this.etniaIndigenaRepository = etniaIndigenaRepository;
        this.etniaIndigenaQueryService = etniaIndigenaQueryService;
    }

    /**
     * {@code POST  /etnia-indigenas} : Create a new etniaIndigena.
     *
     * @param etniaIndigena the etniaIndigena to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etniaIndigena, or with status {@code 400 (Bad Request)} if the etniaIndigena has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etnia-indigenas")
    public ResponseEntity<EtniaIndigena> createEtniaIndigena(@RequestBody EtniaIndigena etniaIndigena) throws URISyntaxException {
        log.debug("REST request to save EtniaIndigena : {}", etniaIndigena);
        if (etniaIndigena.getId() != null) {
            throw new BadRequestAlertException("A new etniaIndigena cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtniaIndigena result = etniaIndigenaService.save(etniaIndigena);
        return ResponseEntity
            .created(new URI("/api/etnia-indigenas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etnia-indigenas/:id} : Updates an existing etniaIndigena.
     *
     * @param id the id of the etniaIndigena to save.
     * @param etniaIndigena the etniaIndigena to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etniaIndigena,
     * or with status {@code 400 (Bad Request)} if the etniaIndigena is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etniaIndigena couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etnia-indigenas/{id}")
    public ResponseEntity<EtniaIndigena> updateEtniaIndigena(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtniaIndigena etniaIndigena
    ) throws URISyntaxException {
        log.debug("REST request to update EtniaIndigena : {}, {}", id, etniaIndigena);
        if (etniaIndigena.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etniaIndigena.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etniaIndigenaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtniaIndigena result = etniaIndigenaService.save(etniaIndigena);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etniaIndigena.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etnia-indigenas/:id} : Partial updates given fields of an existing etniaIndigena, field will ignore if it is null
     *
     * @param id the id of the etniaIndigena to save.
     * @param etniaIndigena the etniaIndigena to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etniaIndigena,
     * or with status {@code 400 (Bad Request)} if the etniaIndigena is not valid,
     * or with status {@code 404 (Not Found)} if the etniaIndigena is not found,
     * or with status {@code 500 (Internal Server Error)} if the etniaIndigena couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etnia-indigenas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EtniaIndigena> partialUpdateEtniaIndigena(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtniaIndigena etniaIndigena
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtniaIndigena partially : {}, {}", id, etniaIndigena);
        if (etniaIndigena.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etniaIndigena.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etniaIndigenaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtniaIndigena> result = etniaIndigenaService.partialUpdate(etniaIndigena);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etniaIndigena.getId().toString())
        );
    }

    /**
     * {@code GET  /etnia-indigenas} : get all the etniaIndigenas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etniaIndigenas in body.
     */
    @GetMapping("/etnia-indigenas")
    public ResponseEntity<List<EtniaIndigena>> getAllEtniaIndigenas(EtniaIndigenaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EtniaIndigenas by criteria: {}", criteria);
        Page<EtniaIndigena> page = etniaIndigenaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etnia-indigenas/count} : count all the etniaIndigenas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/etnia-indigenas/count")
    public ResponseEntity<Long> countEtniaIndigenas(EtniaIndigenaCriteria criteria) {
        log.debug("REST request to count EtniaIndigenas by criteria: {}", criteria);
        return ResponseEntity.ok().body(etniaIndigenaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /etnia-indigenas/:id} : get the "id" etniaIndigena.
     *
     * @param id the id of the etniaIndigena to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etniaIndigena, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etnia-indigenas/{id}")
    public ResponseEntity<EtniaIndigena> getEtniaIndigena(@PathVariable Long id) {
        log.debug("REST request to get EtniaIndigena : {}", id);
        Optional<EtniaIndigena> etniaIndigena = etniaIndigenaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etniaIndigena);
    }

    /**
     * {@code DELETE  /etnia-indigenas/:id} : delete the "id" etniaIndigena.
     *
     * @param id the id of the etniaIndigena to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etnia-indigenas/{id}")
    public ResponseEntity<Void> deleteEtniaIndigena(@PathVariable Long id) {
        log.debug("REST request to delete EtniaIndigena : {}", id);
        etniaIndigenaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
