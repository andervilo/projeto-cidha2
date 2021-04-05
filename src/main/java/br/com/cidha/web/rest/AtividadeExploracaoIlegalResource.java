package br.com.cidha.web.rest;

import br.com.cidha.domain.AtividadeExploracaoIlegal;
import br.com.cidha.service.AtividadeExploracaoIlegalService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.AtividadeExploracaoIlegalCriteria;
import br.com.cidha.service.AtividadeExploracaoIlegalQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.AtividadeExploracaoIlegal}.
 */
@RestController
@RequestMapping("/api")
public class AtividadeExploracaoIlegalResource {

    private final Logger log = LoggerFactory.getLogger(AtividadeExploracaoIlegalResource.class);

    private static final String ENTITY_NAME = "atividadeExploracaoIlegal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtividadeExploracaoIlegalService atividadeExploracaoIlegalService;

    private final AtividadeExploracaoIlegalQueryService atividadeExploracaoIlegalQueryService;

    public AtividadeExploracaoIlegalResource(AtividadeExploracaoIlegalService atividadeExploracaoIlegalService, AtividadeExploracaoIlegalQueryService atividadeExploracaoIlegalQueryService) {
        this.atividadeExploracaoIlegalService = atividadeExploracaoIlegalService;
        this.atividadeExploracaoIlegalQueryService = atividadeExploracaoIlegalQueryService;
    }

    /**
     * {@code POST  /atividade-exploracao-ilegals} : Create a new atividadeExploracaoIlegal.
     *
     * @param atividadeExploracaoIlegal the atividadeExploracaoIlegal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atividadeExploracaoIlegal, or with status {@code 400 (Bad Request)} if the atividadeExploracaoIlegal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atividade-exploracao-ilegals")
    public ResponseEntity<AtividadeExploracaoIlegal> createAtividadeExploracaoIlegal(@RequestBody AtividadeExploracaoIlegal atividadeExploracaoIlegal) throws URISyntaxException {
        log.debug("REST request to save AtividadeExploracaoIlegal : {}", atividadeExploracaoIlegal);
        if (atividadeExploracaoIlegal.getId() != null) {
            throw new BadRequestAlertException("A new atividadeExploracaoIlegal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtividadeExploracaoIlegal result = atividadeExploracaoIlegalService.save(atividadeExploracaoIlegal);
        return ResponseEntity.created(new URI("/api/atividade-exploracao-ilegals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atividade-exploracao-ilegals} : Updates an existing atividadeExploracaoIlegal.
     *
     * @param atividadeExploracaoIlegal the atividadeExploracaoIlegal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeExploracaoIlegal,
     * or with status {@code 400 (Bad Request)} if the atividadeExploracaoIlegal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atividadeExploracaoIlegal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atividade-exploracao-ilegals")
    public ResponseEntity<AtividadeExploracaoIlegal> updateAtividadeExploracaoIlegal(@RequestBody AtividadeExploracaoIlegal atividadeExploracaoIlegal) throws URISyntaxException {
        log.debug("REST request to update AtividadeExploracaoIlegal : {}", atividadeExploracaoIlegal);
        if (atividadeExploracaoIlegal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AtividadeExploracaoIlegal result = atividadeExploracaoIlegalService.save(atividadeExploracaoIlegal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeExploracaoIlegal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /atividade-exploracao-ilegals} : get all the atividadeExploracaoIlegals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atividadeExploracaoIlegals in body.
     */
    @GetMapping("/atividade-exploracao-ilegals")
    public ResponseEntity<List<AtividadeExploracaoIlegal>> getAllAtividadeExploracaoIlegals(AtividadeExploracaoIlegalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AtividadeExploracaoIlegals by criteria: {}", criteria);
        Page<AtividadeExploracaoIlegal> page = atividadeExploracaoIlegalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /atividade-exploracao-ilegals/count} : count all the atividadeExploracaoIlegals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/atividade-exploracao-ilegals/count")
    public ResponseEntity<Long> countAtividadeExploracaoIlegals(AtividadeExploracaoIlegalCriteria criteria) {
        log.debug("REST request to count AtividadeExploracaoIlegals by criteria: {}", criteria);
        return ResponseEntity.ok().body(atividadeExploracaoIlegalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /atividade-exploracao-ilegals/:id} : get the "id" atividadeExploracaoIlegal.
     *
     * @param id the id of the atividadeExploracaoIlegal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atividadeExploracaoIlegal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atividade-exploracao-ilegals/{id}")
    public ResponseEntity<AtividadeExploracaoIlegal> getAtividadeExploracaoIlegal(@PathVariable Long id) {
        log.debug("REST request to get AtividadeExploracaoIlegal : {}", id);
        Optional<AtividadeExploracaoIlegal> atividadeExploracaoIlegal = atividadeExploracaoIlegalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atividadeExploracaoIlegal);
    }

    /**
     * {@code DELETE  /atividade-exploracao-ilegals/:id} : delete the "id" atividadeExploracaoIlegal.
     *
     * @param id the id of the atividadeExploracaoIlegal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atividade-exploracao-ilegals/{id}")
    public ResponseEntity<Void> deleteAtividadeExploracaoIlegal(@PathVariable Long id) {
        log.debug("REST request to delete AtividadeExploracaoIlegal : {}", id);
        atividadeExploracaoIlegalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
