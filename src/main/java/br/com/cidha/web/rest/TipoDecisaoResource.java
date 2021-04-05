package br.com.cidha.web.rest;

import br.com.cidha.domain.TipoDecisao;
import br.com.cidha.service.TipoDecisaoService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.TipoDecisaoCriteria;
import br.com.cidha.service.TipoDecisaoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.cidha.domain.TipoDecisao}.
 */
@RestController
@RequestMapping("/api")
public class TipoDecisaoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDecisaoResource.class);

    private static final String ENTITY_NAME = "tipoDecisao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDecisaoService tipoDecisaoService;

    private final TipoDecisaoQueryService tipoDecisaoQueryService;

    public TipoDecisaoResource(TipoDecisaoService tipoDecisaoService, TipoDecisaoQueryService tipoDecisaoQueryService) {
        this.tipoDecisaoService = tipoDecisaoService;
        this.tipoDecisaoQueryService = tipoDecisaoQueryService;
    }

    /**
     * {@code POST  /tipo-decisaos} : Create a new tipoDecisao.
     *
     * @param tipoDecisao the tipoDecisao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDecisao, or with status {@code 400 (Bad Request)} if the tipoDecisao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-decisaos")
    public ResponseEntity<TipoDecisao> createTipoDecisao(@RequestBody TipoDecisao tipoDecisao) throws URISyntaxException {
        log.debug("REST request to save TipoDecisao : {}", tipoDecisao);
        if (tipoDecisao.getId() != null) {
            throw new BadRequestAlertException("A new tipoDecisao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDecisao result = tipoDecisaoService.save(tipoDecisao);
        return ResponseEntity.created(new URI("/api/tipo-decisaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-decisaos} : Updates an existing tipoDecisao.
     *
     * @param tipoDecisao the tipoDecisao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDecisao,
     * or with status {@code 400 (Bad Request)} if the tipoDecisao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDecisao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-decisaos")
    public ResponseEntity<TipoDecisao> updateTipoDecisao(@RequestBody TipoDecisao tipoDecisao) throws URISyntaxException {
        log.debug("REST request to update TipoDecisao : {}", tipoDecisao);
        if (tipoDecisao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDecisao result = tipoDecisaoService.save(tipoDecisao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDecisao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-decisaos} : get all the tipoDecisaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDecisaos in body.
     */
    @GetMapping("/tipo-decisaos")
    public ResponseEntity<List<TipoDecisao>> getAllTipoDecisaos(TipoDecisaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoDecisaos by criteria: {}", criteria);
        Page<TipoDecisao> page = tipoDecisaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-decisaos/count} : count all the tipoDecisaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-decisaos/count")
    public ResponseEntity<Long> countTipoDecisaos(TipoDecisaoCriteria criteria) {
        log.debug("REST request to count TipoDecisaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoDecisaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-decisaos/:id} : get the "id" tipoDecisao.
     *
     * @param id the id of the tipoDecisao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDecisao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-decisaos/{id}")
    public ResponseEntity<TipoDecisao> getTipoDecisao(@PathVariable Long id) {
        log.debug("REST request to get TipoDecisao : {}", id);
        Optional<TipoDecisao> tipoDecisao = tipoDecisaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDecisao);
    }

    /**
     * {@code DELETE  /tipo-decisaos/:id} : delete the "id" tipoDecisao.
     *
     * @param id the id of the tipoDecisao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-decisaos/{id}")
    public ResponseEntity<Void> deleteTipoDecisao(@PathVariable Long id) {
        log.debug("REST request to delete TipoDecisao : {}", id);
        tipoDecisaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
