package br.com.cidha.web.rest;

import br.com.cidha.domain.EnvolvidosConflitoLitigio;
import br.com.cidha.repository.EnvolvidosConflitoLitigioRepository;
import br.com.cidha.service.EnvolvidosConflitoLitigioQueryService;
import br.com.cidha.service.EnvolvidosConflitoLitigioService;
import br.com.cidha.service.criteria.EnvolvidosConflitoLitigioCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.EnvolvidosConflitoLitigio}.
 */
@RestController
@RequestMapping("/api")
public class EnvolvidosConflitoLitigioResource {

    private final Logger log = LoggerFactory.getLogger(EnvolvidosConflitoLitigioResource.class);

    private static final String ENTITY_NAME = "envolvidosConflitoLitigio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvolvidosConflitoLitigioService envolvidosConflitoLitigioService;

    private final EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository;

    private final EnvolvidosConflitoLitigioQueryService envolvidosConflitoLitigioQueryService;

    public EnvolvidosConflitoLitigioResource(
        EnvolvidosConflitoLitigioService envolvidosConflitoLitigioService,
        EnvolvidosConflitoLitigioRepository envolvidosConflitoLitigioRepository,
        EnvolvidosConflitoLitigioQueryService envolvidosConflitoLitigioQueryService
    ) {
        this.envolvidosConflitoLitigioService = envolvidosConflitoLitigioService;
        this.envolvidosConflitoLitigioRepository = envolvidosConflitoLitigioRepository;
        this.envolvidosConflitoLitigioQueryService = envolvidosConflitoLitigioQueryService;
    }

    /**
     * {@code POST  /envolvidos-conflito-litigios} : Create a new envolvidosConflitoLitigio.
     *
     * @param envolvidosConflitoLitigio the envolvidosConflitoLitigio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new envolvidosConflitoLitigio, or with status {@code 400 (Bad Request)} if the envolvidosConflitoLitigio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/envolvidos-conflito-litigios")
    public ResponseEntity<EnvolvidosConflitoLitigio> createEnvolvidosConflitoLitigio(
        @RequestBody EnvolvidosConflitoLitigio envolvidosConflitoLitigio
    ) throws URISyntaxException {
        log.debug("REST request to save EnvolvidosConflitoLitigio : {}", envolvidosConflitoLitigio);
        if (envolvidosConflitoLitigio.getId() != null) {
            throw new BadRequestAlertException("A new envolvidosConflitoLitigio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvolvidosConflitoLitigio result = envolvidosConflitoLitigioService.save(envolvidosConflitoLitigio);
        return ResponseEntity
            .created(new URI("/api/envolvidos-conflito-litigios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /envolvidos-conflito-litigios/:id} : Updates an existing envolvidosConflitoLitigio.
     *
     * @param id the id of the envolvidosConflitoLitigio to save.
     * @param envolvidosConflitoLitigio the envolvidosConflitoLitigio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envolvidosConflitoLitigio,
     * or with status {@code 400 (Bad Request)} if the envolvidosConflitoLitigio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the envolvidosConflitoLitigio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/envolvidos-conflito-litigios/{id}")
    public ResponseEntity<EnvolvidosConflitoLitigio> updateEnvolvidosConflitoLitigio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnvolvidosConflitoLitigio envolvidosConflitoLitigio
    ) throws URISyntaxException {
        log.debug("REST request to update EnvolvidosConflitoLitigio : {}, {}", id, envolvidosConflitoLitigio);
        if (envolvidosConflitoLitigio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, envolvidosConflitoLitigio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!envolvidosConflitoLitigioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnvolvidosConflitoLitigio result = envolvidosConflitoLitigioService.save(envolvidosConflitoLitigio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, envolvidosConflitoLitigio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /envolvidos-conflito-litigios/:id} : Partial updates given fields of an existing envolvidosConflitoLitigio, field will ignore if it is null
     *
     * @param id the id of the envolvidosConflitoLitigio to save.
     * @param envolvidosConflitoLitigio the envolvidosConflitoLitigio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envolvidosConflitoLitigio,
     * or with status {@code 400 (Bad Request)} if the envolvidosConflitoLitigio is not valid,
     * or with status {@code 404 (Not Found)} if the envolvidosConflitoLitigio is not found,
     * or with status {@code 500 (Internal Server Error)} if the envolvidosConflitoLitigio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/envolvidos-conflito-litigios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EnvolvidosConflitoLitigio> partialUpdateEnvolvidosConflitoLitigio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnvolvidosConflitoLitigio envolvidosConflitoLitigio
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnvolvidosConflitoLitigio partially : {}, {}", id, envolvidosConflitoLitigio);
        if (envolvidosConflitoLitigio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, envolvidosConflitoLitigio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!envolvidosConflitoLitigioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnvolvidosConflitoLitigio> result = envolvidosConflitoLitigioService.partialUpdate(envolvidosConflitoLitigio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, envolvidosConflitoLitigio.getId().toString())
        );
    }

    /**
     * {@code GET  /envolvidos-conflito-litigios} : get all the envolvidosConflitoLitigios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of envolvidosConflitoLitigios in body.
     */
    @GetMapping("/envolvidos-conflito-litigios")
    public ResponseEntity<List<EnvolvidosConflitoLitigio>> getAllEnvolvidosConflitoLitigios(
        EnvolvidosConflitoLitigioCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EnvolvidosConflitoLitigios by criteria: {}", criteria);
        Page<EnvolvidosConflitoLitigio> page = envolvidosConflitoLitigioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /envolvidos-conflito-litigios/count} : count all the envolvidosConflitoLitigios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/envolvidos-conflito-litigios/count")
    public ResponseEntity<Long> countEnvolvidosConflitoLitigios(EnvolvidosConflitoLitigioCriteria criteria) {
        log.debug("REST request to count EnvolvidosConflitoLitigios by criteria: {}", criteria);
        return ResponseEntity.ok().body(envolvidosConflitoLitigioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /envolvidos-conflito-litigios/:id} : get the "id" envolvidosConflitoLitigio.
     *
     * @param id the id of the envolvidosConflitoLitigio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the envolvidosConflitoLitigio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/envolvidos-conflito-litigios/{id}")
    public ResponseEntity<EnvolvidosConflitoLitigio> getEnvolvidosConflitoLitigio(@PathVariable Long id) {
        log.debug("REST request to get EnvolvidosConflitoLitigio : {}", id);
        Optional<EnvolvidosConflitoLitigio> envolvidosConflitoLitigio = envolvidosConflitoLitigioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envolvidosConflitoLitigio);
    }

    /**
     * {@code DELETE  /envolvidos-conflito-litigios/:id} : delete the "id" envolvidosConflitoLitigio.
     *
     * @param id the id of the envolvidosConflitoLitigio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/envolvidos-conflito-litigios/{id}")
    public ResponseEntity<Void> deleteEnvolvidosConflitoLitigio(@PathVariable Long id) {
        log.debug("REST request to delete EnvolvidosConflitoLitigio : {}", id);
        envolvidosConflitoLitigioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
