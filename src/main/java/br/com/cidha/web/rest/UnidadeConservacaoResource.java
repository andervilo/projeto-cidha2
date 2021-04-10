package br.com.cidha.web.rest;

import br.com.cidha.domain.UnidadeConservacao;
import br.com.cidha.repository.UnidadeConservacaoRepository;
import br.com.cidha.service.UnidadeConservacaoQueryService;
import br.com.cidha.service.UnidadeConservacaoService;
import br.com.cidha.service.criteria.UnidadeConservacaoCriteria;
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
 * REST controller for managing {@link br.com.cidha.domain.UnidadeConservacao}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeConservacaoResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeConservacaoResource.class);

    private static final String ENTITY_NAME = "unidadeConservacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeConservacaoService unidadeConservacaoService;

    private final UnidadeConservacaoRepository unidadeConservacaoRepository;

    private final UnidadeConservacaoQueryService unidadeConservacaoQueryService;

    public UnidadeConservacaoResource(
        UnidadeConservacaoService unidadeConservacaoService,
        UnidadeConservacaoRepository unidadeConservacaoRepository,
        UnidadeConservacaoQueryService unidadeConservacaoQueryService
    ) {
        this.unidadeConservacaoService = unidadeConservacaoService;
        this.unidadeConservacaoRepository = unidadeConservacaoRepository;
        this.unidadeConservacaoQueryService = unidadeConservacaoQueryService;
    }

    /**
     * {@code POST  /unidade-conservacaos} : Create a new unidadeConservacao.
     *
     * @param unidadeConservacao the unidadeConservacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeConservacao, or with status {@code 400 (Bad Request)} if the unidadeConservacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-conservacaos")
    public ResponseEntity<UnidadeConservacao> createUnidadeConservacao(@RequestBody UnidadeConservacao unidadeConservacao)
        throws URISyntaxException {
        log.debug("REST request to save UnidadeConservacao : {}", unidadeConservacao);
        if (unidadeConservacao.getId() != null) {
            throw new BadRequestAlertException("A new unidadeConservacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeConservacao result = unidadeConservacaoService.save(unidadeConservacao);
        return ResponseEntity
            .created(new URI("/api/unidade-conservacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-conservacaos/:id} : Updates an existing unidadeConservacao.
     *
     * @param id the id of the unidadeConservacao to save.
     * @param unidadeConservacao the unidadeConservacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeConservacao,
     * or with status {@code 400 (Bad Request)} if the unidadeConservacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeConservacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-conservacaos/{id}")
    public ResponseEntity<UnidadeConservacao> updateUnidadeConservacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadeConservacao unidadeConservacao
    ) throws URISyntaxException {
        log.debug("REST request to update UnidadeConservacao : {}, {}", id, unidadeConservacao);
        if (unidadeConservacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadeConservacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadeConservacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnidadeConservacao result = unidadeConservacaoService.save(unidadeConservacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadeConservacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /unidade-conservacaos/:id} : Partial updates given fields of an existing unidadeConservacao, field will ignore if it is null
     *
     * @param id the id of the unidadeConservacao to save.
     * @param unidadeConservacao the unidadeConservacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeConservacao,
     * or with status {@code 400 (Bad Request)} if the unidadeConservacao is not valid,
     * or with status {@code 404 (Not Found)} if the unidadeConservacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidadeConservacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unidade-conservacaos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UnidadeConservacao> partialUpdateUnidadeConservacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadeConservacao unidadeConservacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnidadeConservacao partially : {}, {}", id, unidadeConservacao);
        if (unidadeConservacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadeConservacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadeConservacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnidadeConservacao> result = unidadeConservacaoService.partialUpdate(unidadeConservacao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadeConservacao.getId().toString())
        );
    }

    /**
     * {@code GET  /unidade-conservacaos} : get all the unidadeConservacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeConservacaos in body.
     */
    @GetMapping("/unidade-conservacaos")
    public ResponseEntity<List<UnidadeConservacao>> getAllUnidadeConservacaos(UnidadeConservacaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UnidadeConservacaos by criteria: {}", criteria);
        Page<UnidadeConservacao> page = unidadeConservacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-conservacaos/count} : count all the unidadeConservacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/unidade-conservacaos/count")
    public ResponseEntity<Long> countUnidadeConservacaos(UnidadeConservacaoCriteria criteria) {
        log.debug("REST request to count UnidadeConservacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(unidadeConservacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /unidade-conservacaos/:id} : get the "id" unidadeConservacao.
     *
     * @param id the id of the unidadeConservacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeConservacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-conservacaos/{id}")
    public ResponseEntity<UnidadeConservacao> getUnidadeConservacao(@PathVariable Long id) {
        log.debug("REST request to get UnidadeConservacao : {}", id);
        Optional<UnidadeConservacao> unidadeConservacao = unidadeConservacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeConservacao);
    }

    /**
     * {@code DELETE  /unidade-conservacaos/:id} : delete the "id" unidadeConservacao.
     *
     * @param id the id of the unidadeConservacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-conservacaos/{id}")
    public ResponseEntity<Void> deleteUnidadeConservacao(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeConservacao : {}", id);
        unidadeConservacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
