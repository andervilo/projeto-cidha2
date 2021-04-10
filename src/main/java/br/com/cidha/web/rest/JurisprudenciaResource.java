package br.com.cidha.web.rest;

import br.com.cidha.domain.Jurisprudencia;
import br.com.cidha.service.JurisprudenciaService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.JurisprudenciaCriteria;
import br.com.cidha.service.JurisprudenciaQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.Jurisprudencia}.
 */
@RestController
@RequestMapping("/api")
public class JurisprudenciaResource {

    private final Logger log = LoggerFactory.getLogger(JurisprudenciaResource.class);

    private static final String ENTITY_NAME = "jurisprudencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JurisprudenciaService jurisprudenciaService;

    private final JurisprudenciaQueryService jurisprudenciaQueryService;

    public JurisprudenciaResource(JurisprudenciaService jurisprudenciaService, JurisprudenciaQueryService jurisprudenciaQueryService) {
        this.jurisprudenciaService = jurisprudenciaService;
        this.jurisprudenciaQueryService = jurisprudenciaQueryService;
    }

    /**
     * {@code POST  /jurisprudencias} : Create a new jurisprudencia.
     *
     * @param jurisprudencia the jurisprudencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jurisprudencia, or with status {@code 400 (Bad Request)} if the jurisprudencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jurisprudencias")
    public ResponseEntity<Jurisprudencia> createJurisprudencia(@RequestBody Jurisprudencia jurisprudencia) throws URISyntaxException {
        log.debug("REST request to save Jurisprudencia : {}", jurisprudencia);
        if (jurisprudencia.getId() != null) {
            throw new BadRequestAlertException("A new jurisprudencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jurisprudencia result = jurisprudenciaService.save(jurisprudencia);
        return ResponseEntity.created(new URI("/api/jurisprudencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jurisprudencias} : Updates an existing jurisprudencia.
     *
     * @param jurisprudencia the jurisprudencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jurisprudencia,
     * or with status {@code 400 (Bad Request)} if the jurisprudencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jurisprudencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jurisprudencias")
    public ResponseEntity<Jurisprudencia> updateJurisprudencia(@RequestBody Jurisprudencia jurisprudencia) throws URISyntaxException {
        log.debug("REST request to update Jurisprudencia : {}", jurisprudencia);
        if (jurisprudencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jurisprudencia result = jurisprudenciaService.save(jurisprudencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jurisprudencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jurisprudencias} : get all the jurisprudencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jurisprudencias in body.
     */
    @GetMapping("/jurisprudencias")
    public ResponseEntity<List<Jurisprudencia>> getAllJurisprudencias(JurisprudenciaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Jurisprudencias by criteria: {}", criteria);
        Page<Jurisprudencia> page = jurisprudenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jurisprudencias/count} : count all the jurisprudencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/jurisprudencias/count")
    public ResponseEntity<Long> countJurisprudencias(JurisprudenciaCriteria criteria) {
        log.debug("REST request to count Jurisprudencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(jurisprudenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /jurisprudencias/:id} : get the "id" jurisprudencia.
     *
     * @param id the id of the jurisprudencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jurisprudencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jurisprudencias/{id}")
    public ResponseEntity<Jurisprudencia> getJurisprudencia(@PathVariable Long id) {
        log.debug("REST request to get Jurisprudencia : {}", id);
        Optional<Jurisprudencia> jurisprudencia = jurisprudenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jurisprudencia);
    }

    /**
     * {@code DELETE  /jurisprudencias/:id} : delete the "id" jurisprudencia.
     *
     * @param id the id of the jurisprudencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jurisprudencias/{id}")
    public ResponseEntity<Void> deleteJurisprudencia(@PathVariable Long id) {
        log.debug("REST request to delete Jurisprudencia : {}", id);
        jurisprudenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
