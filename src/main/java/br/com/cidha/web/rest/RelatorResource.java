package br.com.cidha.web.rest;

import br.com.cidha.domain.Relator;
import br.com.cidha.service.RelatorService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.RelatorCriteria;
import br.com.cidha.service.RelatorQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.Relator}.
 */
@RestController
@RequestMapping("/api")
public class RelatorResource {

    private final Logger log = LoggerFactory.getLogger(RelatorResource.class);

    private static final String ENTITY_NAME = "relator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatorService relatorService;

    private final RelatorQueryService relatorQueryService;

    public RelatorResource(RelatorService relatorService, RelatorQueryService relatorQueryService) {
        this.relatorService = relatorService;
        this.relatorQueryService = relatorQueryService;
    }

    /**
     * {@code POST  /relators} : Create a new relator.
     *
     * @param relator the relator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relator, or with status {@code 400 (Bad Request)} if the relator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relators")
    public ResponseEntity<Relator> createRelator(@RequestBody Relator relator) throws URISyntaxException {
        log.debug("REST request to save Relator : {}", relator);
        if (relator.getId() != null) {
            throw new BadRequestAlertException("A new relator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relator result = relatorService.save(relator);
        return ResponseEntity.created(new URI("/api/relators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relators} : Updates an existing relator.
     *
     * @param relator the relator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relator,
     * or with status {@code 400 (Bad Request)} if the relator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relators")
    public ResponseEntity<Relator> updateRelator(@RequestBody Relator relator) throws URISyntaxException {
        log.debug("REST request to update Relator : {}", relator);
        if (relator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Relator result = relatorService.save(relator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relator.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relators} : get all the relators.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relators in body.
     */
    @GetMapping("/relators")
    public ResponseEntity<List<Relator>> getAllRelators(RelatorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Relators by criteria: {}", criteria);
        Page<Relator> page = relatorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relators/count} : count all the relators.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/relators/count")
    public ResponseEntity<Long> countRelators(RelatorCriteria criteria) {
        log.debug("REST request to count Relators by criteria: {}", criteria);
        return ResponseEntity.ok().body(relatorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /relators/:id} : get the "id" relator.
     *
     * @param id the id of the relator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relators/{id}")
    public ResponseEntity<Relator> getRelator(@PathVariable Long id) {
        log.debug("REST request to get Relator : {}", id);
        Optional<Relator> relator = relatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relator);
    }

    /**
     * {@code DELETE  /relators/:id} : delete the "id" relator.
     *
     * @param id the id of the relator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relators/{id}")
    public ResponseEntity<Void> deleteRelator(@PathVariable Long id) {
        log.debug("REST request to delete Relator : {}", id);
        relatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
