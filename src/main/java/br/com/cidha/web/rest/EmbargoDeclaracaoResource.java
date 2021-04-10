package br.com.cidha.web.rest;

import br.com.cidha.domain.EmbargoDeclaracao;
import br.com.cidha.repository.EmbargoDeclaracaoRepository;
import br.com.cidha.service.EmbargoDeclaracaoQueryService;
import br.com.cidha.service.EmbargoDeclaracaoService;
import br.com.cidha.service.criteria.EmbargoDeclaracaoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.EmbargoDeclaracao}.
 */
@RestController
@RequestMapping("/api")
public class EmbargoDeclaracaoResource {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoResource.class);

    private static final String ENTITY_NAME = "embargoDeclaracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbargoDeclaracaoService embargoDeclaracaoService;

    private final EmbargoDeclaracaoRepository embargoDeclaracaoRepository;

    private final EmbargoDeclaracaoQueryService embargoDeclaracaoQueryService;

    public EmbargoDeclaracaoResource(
        EmbargoDeclaracaoService embargoDeclaracaoService,
        EmbargoDeclaracaoRepository embargoDeclaracaoRepository,
        EmbargoDeclaracaoQueryService embargoDeclaracaoQueryService
    ) {
        this.embargoDeclaracaoService = embargoDeclaracaoService;
        this.embargoDeclaracaoRepository = embargoDeclaracaoRepository;
        this.embargoDeclaracaoQueryService = embargoDeclaracaoQueryService;
    }

    /**
     * {@code POST  /embargo-declaracaos} : Create a new embargoDeclaracao.
     *
     * @param embargoDeclaracao the embargoDeclaracao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embargoDeclaracao, or with status {@code 400 (Bad Request)} if the embargoDeclaracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embargo-declaracaos")
    public ResponseEntity<EmbargoDeclaracao> createEmbargoDeclaracao(@RequestBody EmbargoDeclaracao embargoDeclaracao)
        throws URISyntaxException {
        log.debug("REST request to save EmbargoDeclaracao : {}", embargoDeclaracao);
        if (embargoDeclaracao.getId() != null) {
            throw new BadRequestAlertException("A new embargoDeclaracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbargoDeclaracao result = embargoDeclaracaoService.save(embargoDeclaracao);
        return ResponseEntity
            .created(new URI("/api/embargo-declaracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embargo-declaracaos/:id} : Updates an existing embargoDeclaracao.
     *
     * @param id the id of the embargoDeclaracao to save.
     * @param embargoDeclaracao the embargoDeclaracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoDeclaracao,
     * or with status {@code 400 (Bad Request)} if the embargoDeclaracao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embargoDeclaracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embargo-declaracaos/{id}")
    public ResponseEntity<EmbargoDeclaracao> updateEmbargoDeclaracao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoDeclaracao embargoDeclaracao
    ) throws URISyntaxException {
        log.debug("REST request to update EmbargoDeclaracao : {}, {}", id, embargoDeclaracao);
        if (embargoDeclaracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoDeclaracao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoDeclaracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmbargoDeclaracao result = embargoDeclaracaoService.save(embargoDeclaracao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoDeclaracao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /embargo-declaracaos/:id} : Partial updates given fields of an existing embargoDeclaracao, field will ignore if it is null
     *
     * @param id the id of the embargoDeclaracao to save.
     * @param embargoDeclaracao the embargoDeclaracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoDeclaracao,
     * or with status {@code 400 (Bad Request)} if the embargoDeclaracao is not valid,
     * or with status {@code 404 (Not Found)} if the embargoDeclaracao is not found,
     * or with status {@code 500 (Internal Server Error)} if the embargoDeclaracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/embargo-declaracaos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmbargoDeclaracao> partialUpdateEmbargoDeclaracao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmbargoDeclaracao embargoDeclaracao
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmbargoDeclaracao partially : {}, {}", id, embargoDeclaracao);
        if (embargoDeclaracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, embargoDeclaracao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!embargoDeclaracaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmbargoDeclaracao> result = embargoDeclaracaoService.partialUpdate(embargoDeclaracao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoDeclaracao.getId().toString())
        );
    }

    /**
     * {@code GET  /embargo-declaracaos} : get all the embargoDeclaracaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embargoDeclaracaos in body.
     */
    @GetMapping("/embargo-declaracaos")
    public ResponseEntity<List<EmbargoDeclaracao>> getAllEmbargoDeclaracaos(EmbargoDeclaracaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmbargoDeclaracaos by criteria: {}", criteria);
        Page<EmbargoDeclaracao> page = embargoDeclaracaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embargo-declaracaos/count} : count all the embargoDeclaracaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/embargo-declaracaos/count")
    public ResponseEntity<Long> countEmbargoDeclaracaos(EmbargoDeclaracaoCriteria criteria) {
        log.debug("REST request to count EmbargoDeclaracaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(embargoDeclaracaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /embargo-declaracaos/:id} : get the "id" embargoDeclaracao.
     *
     * @param id the id of the embargoDeclaracao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embargoDeclaracao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embargo-declaracaos/{id}")
    public ResponseEntity<EmbargoDeclaracao> getEmbargoDeclaracao(@PathVariable Long id) {
        log.debug("REST request to get EmbargoDeclaracao : {}", id);
        Optional<EmbargoDeclaracao> embargoDeclaracao = embargoDeclaracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embargoDeclaracao);
    }

    /**
     * {@code DELETE  /embargo-declaracaos/:id} : delete the "id" embargoDeclaracao.
     *
     * @param id the id of the embargoDeclaracao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embargo-declaracaos/{id}")
    public ResponseEntity<Void> deleteEmbargoDeclaracao(@PathVariable Long id) {
        log.debug("REST request to delete EmbargoDeclaracao : {}", id);
        embargoDeclaracaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
