package br.com.cidha.web.rest;

import br.com.cidha.domain.FundamentacaoDoutrinaria;
import br.com.cidha.repository.FundamentacaoDoutrinariaRepository;
import br.com.cidha.service.FundamentacaoDoutrinariaQueryService;
import br.com.cidha.service.FundamentacaoDoutrinariaService;
import br.com.cidha.service.criteria.FundamentacaoDoutrinariaCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.FundamentacaoDoutrinaria}.
 */
@RestController
@RequestMapping("/api")
public class FundamentacaoDoutrinariaResource {

    private final Logger log = LoggerFactory.getLogger(FundamentacaoDoutrinariaResource.class);

    private static final String ENTITY_NAME = "fundamentacaoDoutrinaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FundamentacaoDoutrinariaService fundamentacaoDoutrinariaService;

    private final FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository;

    private final FundamentacaoDoutrinariaQueryService fundamentacaoDoutrinariaQueryService;

    public FundamentacaoDoutrinariaResource(
        FundamentacaoDoutrinariaService fundamentacaoDoutrinariaService,
        FundamentacaoDoutrinariaRepository fundamentacaoDoutrinariaRepository,
        FundamentacaoDoutrinariaQueryService fundamentacaoDoutrinariaQueryService
    ) {
        this.fundamentacaoDoutrinariaService = fundamentacaoDoutrinariaService;
        this.fundamentacaoDoutrinariaRepository = fundamentacaoDoutrinariaRepository;
        this.fundamentacaoDoutrinariaQueryService = fundamentacaoDoutrinariaQueryService;
    }

    /**
     * {@code POST  /fundamentacao-doutrinarias} : Create a new fundamentacaoDoutrinaria.
     *
     * @param fundamentacaoDoutrinaria the fundamentacaoDoutrinaria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fundamentacaoDoutrinaria, or with status {@code 400 (Bad Request)} if the fundamentacaoDoutrinaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fundamentacao-doutrinarias")
    public ResponseEntity<FundamentacaoDoutrinaria> createFundamentacaoDoutrinaria(
        @RequestBody FundamentacaoDoutrinaria fundamentacaoDoutrinaria
    ) throws URISyntaxException {
        log.debug("REST request to save FundamentacaoDoutrinaria : {}", fundamentacaoDoutrinaria);
        if (fundamentacaoDoutrinaria.getId() != null) {
            throw new BadRequestAlertException("A new fundamentacaoDoutrinaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FundamentacaoDoutrinaria result = fundamentacaoDoutrinariaService.save(fundamentacaoDoutrinaria);
        return ResponseEntity
            .created(new URI("/api/fundamentacao-doutrinarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fundamentacao-doutrinarias/:id} : Updates an existing fundamentacaoDoutrinaria.
     *
     * @param id the id of the fundamentacaoDoutrinaria to save.
     * @param fundamentacaoDoutrinaria the fundamentacaoDoutrinaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundamentacaoDoutrinaria,
     * or with status {@code 400 (Bad Request)} if the fundamentacaoDoutrinaria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fundamentacaoDoutrinaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fundamentacao-doutrinarias/{id}")
    public ResponseEntity<FundamentacaoDoutrinaria> updateFundamentacaoDoutrinaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundamentacaoDoutrinaria fundamentacaoDoutrinaria
    ) throws URISyntaxException {
        log.debug("REST request to update FundamentacaoDoutrinaria : {}, {}", id, fundamentacaoDoutrinaria);
        if (fundamentacaoDoutrinaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundamentacaoDoutrinaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundamentacaoDoutrinariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FundamentacaoDoutrinaria result = fundamentacaoDoutrinariaService.save(fundamentacaoDoutrinaria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundamentacaoDoutrinaria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fundamentacao-doutrinarias/:id} : Partial updates given fields of an existing fundamentacaoDoutrinaria, field will ignore if it is null
     *
     * @param id the id of the fundamentacaoDoutrinaria to save.
     * @param fundamentacaoDoutrinaria the fundamentacaoDoutrinaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundamentacaoDoutrinaria,
     * or with status {@code 400 (Bad Request)} if the fundamentacaoDoutrinaria is not valid,
     * or with status {@code 404 (Not Found)} if the fundamentacaoDoutrinaria is not found,
     * or with status {@code 500 (Internal Server Error)} if the fundamentacaoDoutrinaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fundamentacao-doutrinarias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FundamentacaoDoutrinaria> partialUpdateFundamentacaoDoutrinaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundamentacaoDoutrinaria fundamentacaoDoutrinaria
    ) throws URISyntaxException {
        log.debug("REST request to partial update FundamentacaoDoutrinaria partially : {}, {}", id, fundamentacaoDoutrinaria);
        if (fundamentacaoDoutrinaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundamentacaoDoutrinaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundamentacaoDoutrinariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FundamentacaoDoutrinaria> result = fundamentacaoDoutrinariaService.partialUpdate(fundamentacaoDoutrinaria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fundamentacaoDoutrinaria.getId().toString())
        );
    }

    /**
     * {@code GET  /fundamentacao-doutrinarias} : get all the fundamentacaoDoutrinarias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fundamentacaoDoutrinarias in body.
     */
    @GetMapping("/fundamentacao-doutrinarias")
    public ResponseEntity<List<FundamentacaoDoutrinaria>> getAllFundamentacaoDoutrinarias(
        FundamentacaoDoutrinariaCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get FundamentacaoDoutrinarias by criteria: {}", criteria);
        Page<FundamentacaoDoutrinaria> page = fundamentacaoDoutrinariaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fundamentacao-doutrinarias/count} : count all the fundamentacaoDoutrinarias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fundamentacao-doutrinarias/count")
    public ResponseEntity<Long> countFundamentacaoDoutrinarias(FundamentacaoDoutrinariaCriteria criteria) {
        log.debug("REST request to count FundamentacaoDoutrinarias by criteria: {}", criteria);
        return ResponseEntity.ok().body(fundamentacaoDoutrinariaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fundamentacao-doutrinarias/:id} : get the "id" fundamentacaoDoutrinaria.
     *
     * @param id the id of the fundamentacaoDoutrinaria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fundamentacaoDoutrinaria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fundamentacao-doutrinarias/{id}")
    public ResponseEntity<FundamentacaoDoutrinaria> getFundamentacaoDoutrinaria(@PathVariable Long id) {
        log.debug("REST request to get FundamentacaoDoutrinaria : {}", id);
        Optional<FundamentacaoDoutrinaria> fundamentacaoDoutrinaria = fundamentacaoDoutrinariaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fundamentacaoDoutrinaria);
    }

    /**
     * {@code DELETE  /fundamentacao-doutrinarias/:id} : delete the "id" fundamentacaoDoutrinaria.
     *
     * @param id the id of the fundamentacaoDoutrinaria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fundamentacao-doutrinarias/{id}")
    public ResponseEntity<Void> deleteFundamentacaoDoutrinaria(@PathVariable Long id) {
        log.debug("REST request to delete FundamentacaoDoutrinaria : {}", id);
        fundamentacaoDoutrinariaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
