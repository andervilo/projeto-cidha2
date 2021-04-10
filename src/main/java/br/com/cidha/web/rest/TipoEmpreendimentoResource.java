package br.com.cidha.web.rest;

import br.com.cidha.domain.TipoEmpreendimento;
import br.com.cidha.service.TipoEmpreendimentoService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.TipoEmpreendimentoCriteria;
import br.com.cidha.service.TipoEmpreendimentoQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.TipoEmpreendimento}.
 */
@RestController
@RequestMapping("/api")
public class TipoEmpreendimentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoEmpreendimentoResource.class);

    private static final String ENTITY_NAME = "tipoEmpreendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEmpreendimentoService tipoEmpreendimentoService;

    private final TipoEmpreendimentoQueryService tipoEmpreendimentoQueryService;

    public TipoEmpreendimentoResource(TipoEmpreendimentoService tipoEmpreendimentoService, TipoEmpreendimentoQueryService tipoEmpreendimentoQueryService) {
        this.tipoEmpreendimentoService = tipoEmpreendimentoService;
        this.tipoEmpreendimentoQueryService = tipoEmpreendimentoQueryService;
    }

    /**
     * {@code POST  /tipo-empreendimentos} : Create a new tipoEmpreendimento.
     *
     * @param tipoEmpreendimento the tipoEmpreendimento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEmpreendimento, or with status {@code 400 (Bad Request)} if the tipoEmpreendimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-empreendimentos")
    public ResponseEntity<TipoEmpreendimento> createTipoEmpreendimento(@RequestBody TipoEmpreendimento tipoEmpreendimento) throws URISyntaxException {
        log.debug("REST request to save TipoEmpreendimento : {}", tipoEmpreendimento);
        if (tipoEmpreendimento.getId() != null) {
            throw new BadRequestAlertException("A new tipoEmpreendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEmpreendimento result = tipoEmpreendimentoService.save(tipoEmpreendimento);
        return ResponseEntity.created(new URI("/api/tipo-empreendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-empreendimentos} : Updates an existing tipoEmpreendimento.
     *
     * @param tipoEmpreendimento the tipoEmpreendimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEmpreendimento,
     * or with status {@code 400 (Bad Request)} if the tipoEmpreendimento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEmpreendimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-empreendimentos")
    public ResponseEntity<TipoEmpreendimento> updateTipoEmpreendimento(@RequestBody TipoEmpreendimento tipoEmpreendimento) throws URISyntaxException {
        log.debug("REST request to update TipoEmpreendimento : {}", tipoEmpreendimento);
        if (tipoEmpreendimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEmpreendimento result = tipoEmpreendimentoService.save(tipoEmpreendimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoEmpreendimento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-empreendimentos} : get all the tipoEmpreendimentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEmpreendimentos in body.
     */
    @GetMapping("/tipo-empreendimentos")
    public ResponseEntity<List<TipoEmpreendimento>> getAllTipoEmpreendimentos(TipoEmpreendimentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoEmpreendimentos by criteria: {}", criteria);
        Page<TipoEmpreendimento> page = tipoEmpreendimentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-empreendimentos/count} : count all the tipoEmpreendimentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-empreendimentos/count")
    public ResponseEntity<Long> countTipoEmpreendimentos(TipoEmpreendimentoCriteria criteria) {
        log.debug("REST request to count TipoEmpreendimentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoEmpreendimentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-empreendimentos/:id} : get the "id" tipoEmpreendimento.
     *
     * @param id the id of the tipoEmpreendimento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEmpreendimento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-empreendimentos/{id}")
    public ResponseEntity<TipoEmpreendimento> getTipoEmpreendimento(@PathVariable Long id) {
        log.debug("REST request to get TipoEmpreendimento : {}", id);
        Optional<TipoEmpreendimento> tipoEmpreendimento = tipoEmpreendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoEmpreendimento);
    }

    /**
     * {@code DELETE  /tipo-empreendimentos/:id} : delete the "id" tipoEmpreendimento.
     *
     * @param id the id of the tipoEmpreendimento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-empreendimentos/{id}")
    public ResponseEntity<Void> deleteTipoEmpreendimento(@PathVariable Long id) {
        log.debug("REST request to delete TipoEmpreendimento : {}", id);
        tipoEmpreendimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
