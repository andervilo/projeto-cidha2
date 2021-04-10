package br.com.cidha.web.rest;

import br.com.cidha.domain.Territorio;
import br.com.cidha.service.TerritorioService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.TerritorioCriteria;
import br.com.cidha.service.TerritorioQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.Territorio}.
 */
@RestController
@RequestMapping("/api")
public class TerritorioResource {

    private final Logger log = LoggerFactory.getLogger(TerritorioResource.class);

    private static final String ENTITY_NAME = "territorio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerritorioService territorioService;

    private final TerritorioQueryService territorioQueryService;

    public TerritorioResource(TerritorioService territorioService, TerritorioQueryService territorioQueryService) {
        this.territorioService = territorioService;
        this.territorioQueryService = territorioQueryService;
    }

    /**
     * {@code POST  /territorios} : Create a new territorio.
     *
     * @param territorio the territorio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new territorio, or with status {@code 400 (Bad Request)} if the territorio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/territorios")
    public ResponseEntity<Territorio> createTerritorio(@RequestBody Territorio territorio) throws URISyntaxException {
        log.debug("REST request to save Territorio : {}", territorio);
        if (territorio.getId() != null) {
            throw new BadRequestAlertException("A new territorio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Territorio result = territorioService.save(territorio);
        return ResponseEntity.created(new URI("/api/territorios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /territorios} : Updates an existing territorio.
     *
     * @param territorio the territorio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated territorio,
     * or with status {@code 400 (Bad Request)} if the territorio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the territorio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/territorios")
    public ResponseEntity<Territorio> updateTerritorio(@RequestBody Territorio territorio) throws URISyntaxException {
        log.debug("REST request to update Territorio : {}", territorio);
        if (territorio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Territorio result = territorioService.save(territorio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, territorio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /territorios} : get all the territorios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of territorios in body.
     */
    @GetMapping("/territorios")
    public ResponseEntity<List<Territorio>> getAllTerritorios(TerritorioCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Territorios by criteria: {}", criteria);
        Page<Territorio> page = territorioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /territorios/count} : count all the territorios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/territorios/count")
    public ResponseEntity<Long> countTerritorios(TerritorioCriteria criteria) {
        log.debug("REST request to count Territorios by criteria: {}", criteria);
        return ResponseEntity.ok().body(territorioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /territorios/:id} : get the "id" territorio.
     *
     * @param id the id of the territorio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the territorio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/territorios/{id}")
    public ResponseEntity<Territorio> getTerritorio(@PathVariable Long id) {
        log.debug("REST request to get Territorio : {}", id);
        Optional<Territorio> territorio = territorioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(territorio);
    }

    /**
     * {@code DELETE  /territorios/:id} : delete the "id" territorio.
     *
     * @param id the id of the territorio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/territorios/{id}")
    public ResponseEntity<Void> deleteTerritorio(@PathVariable Long id) {
        log.debug("REST request to delete Territorio : {}", id);
        territorioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
