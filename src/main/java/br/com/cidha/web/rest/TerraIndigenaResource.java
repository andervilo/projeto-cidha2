package br.com.cidha.web.rest;

import br.com.cidha.domain.TerraIndigena;
import br.com.cidha.service.TerraIndigenaService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.TerraIndigenaCriteria;
import br.com.cidha.service.TerraIndigenaQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.TerraIndigena}.
 */
@RestController
@RequestMapping("/api")
public class TerraIndigenaResource {

    private final Logger log = LoggerFactory.getLogger(TerraIndigenaResource.class);

    private static final String ENTITY_NAME = "terraIndigena";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerraIndigenaService terraIndigenaService;

    private final TerraIndigenaQueryService terraIndigenaQueryService;

    public TerraIndigenaResource(TerraIndigenaService terraIndigenaService, TerraIndigenaQueryService terraIndigenaQueryService) {
        this.terraIndigenaService = terraIndigenaService;
        this.terraIndigenaQueryService = terraIndigenaQueryService;
    }

    /**
     * {@code POST  /terra-indigenas} : Create a new terraIndigena.
     *
     * @param terraIndigena the terraIndigena to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terraIndigena, or with status {@code 400 (Bad Request)} if the terraIndigena has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terra-indigenas")
    public ResponseEntity<TerraIndigena> createTerraIndigena(@RequestBody TerraIndigena terraIndigena) throws URISyntaxException {
        log.debug("REST request to save TerraIndigena : {}", terraIndigena);
        if (terraIndigena.getId() != null) {
            throw new BadRequestAlertException("A new terraIndigena cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerraIndigena result = terraIndigenaService.save(terraIndigena);
        return ResponseEntity.created(new URI("/api/terra-indigenas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terra-indigenas} : Updates an existing terraIndigena.
     *
     * @param terraIndigena the terraIndigena to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terraIndigena,
     * or with status {@code 400 (Bad Request)} if the terraIndigena is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terraIndigena couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terra-indigenas")
    public ResponseEntity<TerraIndigena> updateTerraIndigena(@RequestBody TerraIndigena terraIndigena) throws URISyntaxException {
        log.debug("REST request to update TerraIndigena : {}", terraIndigena);
        if (terraIndigena.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TerraIndigena result = terraIndigenaService.save(terraIndigena);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terraIndigena.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terra-indigenas} : get all the terraIndigenas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terraIndigenas in body.
     */
    @GetMapping("/terra-indigenas")
    public ResponseEntity<List<TerraIndigena>> getAllTerraIndigenas(TerraIndigenaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TerraIndigenas by criteria: {}", criteria);
        Page<TerraIndigena> page = terraIndigenaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terra-indigenas/count} : count all the terraIndigenas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terra-indigenas/count")
    public ResponseEntity<Long> countTerraIndigenas(TerraIndigenaCriteria criteria) {
        log.debug("REST request to count TerraIndigenas by criteria: {}", criteria);
        return ResponseEntity.ok().body(terraIndigenaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terra-indigenas/:id} : get the "id" terraIndigena.
     *
     * @param id the id of the terraIndigena to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terraIndigena, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terra-indigenas/{id}")
    public ResponseEntity<TerraIndigena> getTerraIndigena(@PathVariable Long id) {
        log.debug("REST request to get TerraIndigena : {}", id);
        Optional<TerraIndigena> terraIndigena = terraIndigenaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terraIndigena);
    }

    /**
     * {@code DELETE  /terra-indigenas/:id} : delete the "id" terraIndigena.
     *
     * @param id the id of the terraIndigena to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terra-indigenas/{id}")
    public ResponseEntity<Void> deleteTerraIndigena(@PathVariable Long id) {
        log.debug("REST request to delete TerraIndigena : {}", id);
        terraIndigenaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
