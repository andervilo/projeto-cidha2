package br.com.cidha.web.rest;

import br.com.cidha.domain.TipoData;
import br.com.cidha.repository.TipoDataRepository;
import br.com.cidha.service.TipoDataQueryService;
import br.com.cidha.service.TipoDataService;
import br.com.cidha.service.criteria.TipoDataCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.TipoData}.
 */
@RestController
@RequestMapping("/api")
public class TipoDataResource {

    private final Logger log = LoggerFactory.getLogger(TipoDataResource.class);

    private static final String ENTITY_NAME = "tipoData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDataService tipoDataService;

    private final TipoDataRepository tipoDataRepository;

    private final TipoDataQueryService tipoDataQueryService;

    public TipoDataResource(
        TipoDataService tipoDataService,
        TipoDataRepository tipoDataRepository,
        TipoDataQueryService tipoDataQueryService
    ) {
        this.tipoDataService = tipoDataService;
        this.tipoDataRepository = tipoDataRepository;
        this.tipoDataQueryService = tipoDataQueryService;
    }

    /**
     * {@code POST  /tipo-data} : Create a new tipoData.
     *
     * @param tipoData the tipoData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoData, or with status {@code 400 (Bad Request)} if the tipoData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-data")
    public ResponseEntity<TipoData> createTipoData(@RequestBody TipoData tipoData) throws URISyntaxException {
        log.debug("REST request to save TipoData : {}", tipoData);
        if (tipoData.getId() != null) {
            throw new BadRequestAlertException("A new tipoData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoData result = tipoDataService.save(tipoData);
        return ResponseEntity
            .created(new URI("/api/tipo-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-data/:id} : Updates an existing tipoData.
     *
     * @param id the id of the tipoData to save.
     * @param tipoData the tipoData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoData,
     * or with status {@code 400 (Bad Request)} if the tipoData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-data/{id}")
    public ResponseEntity<TipoData> updateTipoData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoData tipoData
    ) throws URISyntaxException {
        log.debug("REST request to update TipoData : {}, {}", id, tipoData);
        if (tipoData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoData result = tipoDataService.save(tipoData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-data/:id} : Partial updates given fields of an existing tipoData, field will ignore if it is null
     *
     * @param id the id of the tipoData to save.
     * @param tipoData the tipoData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoData,
     * or with status {@code 400 (Bad Request)} if the tipoData is not valid,
     * or with status {@code 404 (Not Found)} if the tipoData is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoData> partialUpdateTipoData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoData tipoData
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoData partially : {}, {}", id, tipoData);
        if (tipoData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoData> result = tipoDataService.partialUpdate(tipoData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoData.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-data} : get all the tipoData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoData in body.
     */
    @GetMapping("/tipo-data")
    public ResponseEntity<List<TipoData>> getAllTipoData(TipoDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoData by criteria: {}", criteria);
        Page<TipoData> page = tipoDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-data/count} : count all the tipoData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-data/count")
    public ResponseEntity<Long> countTipoData(TipoDataCriteria criteria) {
        log.debug("REST request to count TipoData by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-data/:id} : get the "id" tipoData.
     *
     * @param id the id of the tipoData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-data/{id}")
    public ResponseEntity<TipoData> getTipoData(@PathVariable Long id) {
        log.debug("REST request to get TipoData : {}", id);
        Optional<TipoData> tipoData = tipoDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoData);
    }

    /**
     * {@code DELETE  /tipo-data/:id} : delete the "id" tipoData.
     *
     * @param id the id of the tipoData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-data/{id}")
    public ResponseEntity<Void> deleteTipoData(@PathVariable Long id) {
        log.debug("REST request to delete TipoData : {}", id);
        tipoDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
