package br.com.cidha.web.rest;

import br.com.cidha.domain.ProblemaJuridico;
import br.com.cidha.service.ProblemaJuridicoService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.ProblemaJuridicoCriteria;
import br.com.cidha.service.ProblemaJuridicoQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.ProblemaJuridico}.
 */
@RestController
@RequestMapping("/api")
public class ProblemaJuridicoResource {

    private final Logger log = LoggerFactory.getLogger(ProblemaJuridicoResource.class);

    private static final String ENTITY_NAME = "problemaJuridico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProblemaJuridicoService problemaJuridicoService;

    private final ProblemaJuridicoQueryService problemaJuridicoQueryService;

    public ProblemaJuridicoResource(ProblemaJuridicoService problemaJuridicoService, ProblemaJuridicoQueryService problemaJuridicoQueryService) {
        this.problemaJuridicoService = problemaJuridicoService;
        this.problemaJuridicoQueryService = problemaJuridicoQueryService;
    }

    /**
     * {@code POST  /problema-juridicos} : Create a new problemaJuridico.
     *
     * @param problemaJuridico the problemaJuridico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new problemaJuridico, or with status {@code 400 (Bad Request)} if the problemaJuridico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/problema-juridicos")
    public ResponseEntity<ProblemaJuridico> createProblemaJuridico(@RequestBody ProblemaJuridico problemaJuridico) throws URISyntaxException {
        log.debug("REST request to save ProblemaJuridico : {}", problemaJuridico);
        if (problemaJuridico.getId() != null) {
            throw new BadRequestAlertException("A new problemaJuridico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProblemaJuridico result = problemaJuridicoService.save(problemaJuridico);
        return ResponseEntity.created(new URI("/api/problema-juridicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /problema-juridicos} : Updates an existing problemaJuridico.
     *
     * @param problemaJuridico the problemaJuridico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated problemaJuridico,
     * or with status {@code 400 (Bad Request)} if the problemaJuridico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the problemaJuridico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/problema-juridicos")
    public ResponseEntity<ProblemaJuridico> updateProblemaJuridico(@RequestBody ProblemaJuridico problemaJuridico) throws URISyntaxException {
        log.debug("REST request to update ProblemaJuridico : {}", problemaJuridico);
        if (problemaJuridico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProblemaJuridico result = problemaJuridicoService.save(problemaJuridico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, problemaJuridico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /problema-juridicos} : get all the problemaJuridicos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of problemaJuridicos in body.
     */
    @GetMapping("/problema-juridicos")
    public ResponseEntity<List<ProblemaJuridico>> getAllProblemaJuridicos(ProblemaJuridicoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProblemaJuridicos by criteria: {}", criteria);
        Page<ProblemaJuridico> page = problemaJuridicoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /problema-juridicos/count} : count all the problemaJuridicos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/problema-juridicos/count")
    public ResponseEntity<Long> countProblemaJuridicos(ProblemaJuridicoCriteria criteria) {
        log.debug("REST request to count ProblemaJuridicos by criteria: {}", criteria);
        return ResponseEntity.ok().body(problemaJuridicoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /problema-juridicos/:id} : get the "id" problemaJuridico.
     *
     * @param id the id of the problemaJuridico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the problemaJuridico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/problema-juridicos/{id}")
    public ResponseEntity<ProblemaJuridico> getProblemaJuridico(@PathVariable Long id) {
        log.debug("REST request to get ProblemaJuridico : {}", id);
        Optional<ProblemaJuridico> problemaJuridico = problemaJuridicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(problemaJuridico);
    }

    /**
     * {@code DELETE  /problema-juridicos/:id} : delete the "id" problemaJuridico.
     *
     * @param id the id of the problemaJuridico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/problema-juridicos/{id}")
    public ResponseEntity<Void> deleteProblemaJuridico(@PathVariable Long id) {
        log.debug("REST request to delete ProblemaJuridico : {}", id);
        problemaJuridicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
