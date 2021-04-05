package br.com.cidha.web.rest;

import br.com.cidha.domain.Comarca;
import br.com.cidha.service.ComarcaService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.ComarcaCriteria;
import br.com.cidha.service.ComarcaQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.Comarca}.
 */
@RestController
@RequestMapping("/api")
public class ComarcaResource {

    private final Logger log = LoggerFactory.getLogger(ComarcaResource.class);

    private static final String ENTITY_NAME = "comarca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComarcaService comarcaService;

    private final ComarcaQueryService comarcaQueryService;

    public ComarcaResource(ComarcaService comarcaService, ComarcaQueryService comarcaQueryService) {
        this.comarcaService = comarcaService;
        this.comarcaQueryService = comarcaQueryService;
    }

    /**
     * {@code POST  /comarcas} : Create a new comarca.
     *
     * @param comarca the comarca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comarca, or with status {@code 400 (Bad Request)} if the comarca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comarcas")
    public ResponseEntity<Comarca> createComarca(@RequestBody Comarca comarca) throws URISyntaxException {
        log.debug("REST request to save Comarca : {}", comarca);
        if (comarca.getId() != null) {
            throw new BadRequestAlertException("A new comarca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comarca result = comarcaService.save(comarca);
        return ResponseEntity.created(new URI("/api/comarcas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comarcas} : Updates an existing comarca.
     *
     * @param comarca the comarca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comarca,
     * or with status {@code 400 (Bad Request)} if the comarca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comarca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comarcas")
    public ResponseEntity<Comarca> updateComarca(@RequestBody Comarca comarca) throws URISyntaxException {
        log.debug("REST request to update Comarca : {}", comarca);
        if (comarca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comarca result = comarcaService.save(comarca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comarca.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comarcas} : get all the comarcas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comarcas in body.
     */
    @GetMapping("/comarcas")
    public ResponseEntity<List<Comarca>> getAllComarcas(ComarcaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comarcas by criteria: {}", criteria);
        Page<Comarca> page = comarcaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comarcas/count} : count all the comarcas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comarcas/count")
    public ResponseEntity<Long> countComarcas(ComarcaCriteria criteria) {
        log.debug("REST request to count Comarcas by criteria: {}", criteria);
        return ResponseEntity.ok().body(comarcaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comarcas/:id} : get the "id" comarca.
     *
     * @param id the id of the comarca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comarca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comarcas/{id}")
    public ResponseEntity<Comarca> getComarca(@PathVariable Long id) {
        log.debug("REST request to get Comarca : {}", id);
        Optional<Comarca> comarca = comarcaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comarca);
    }

    /**
     * {@code DELETE  /comarcas/:id} : delete the "id" comarca.
     *
     * @param id the id of the comarca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comarcas/{id}")
    public ResponseEntity<Void> deleteComarca(@PathVariable Long id) {
        log.debug("REST request to delete Comarca : {}", id);
        comarcaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
