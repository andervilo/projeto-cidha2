package br.com.cidha.web.rest;

import br.com.cidha.domain.EmbargoDeclaracaoAgravo;
import br.com.cidha.service.EmbargoDeclaracaoAgravoService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.EmbargoDeclaracaoAgravoCriteria;
import br.com.cidha.service.EmbargoDeclaracaoAgravoQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.EmbargoDeclaracaoAgravo}.
 */
@RestController
@RequestMapping("/api")
public class EmbargoDeclaracaoAgravoResource {

    private final Logger log = LoggerFactory.getLogger(EmbargoDeclaracaoAgravoResource.class);

    private static final String ENTITY_NAME = "embargoDeclaracaoAgravo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmbargoDeclaracaoAgravoService embargoDeclaracaoAgravoService;

    private final EmbargoDeclaracaoAgravoQueryService embargoDeclaracaoAgravoQueryService;

    public EmbargoDeclaracaoAgravoResource(EmbargoDeclaracaoAgravoService embargoDeclaracaoAgravoService, EmbargoDeclaracaoAgravoQueryService embargoDeclaracaoAgravoQueryService) {
        this.embargoDeclaracaoAgravoService = embargoDeclaracaoAgravoService;
        this.embargoDeclaracaoAgravoQueryService = embargoDeclaracaoAgravoQueryService;
    }

    /**
     * {@code POST  /embargo-declaracao-agravos} : Create a new embargoDeclaracaoAgravo.
     *
     * @param embargoDeclaracaoAgravo the embargoDeclaracaoAgravo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new embargoDeclaracaoAgravo, or with status {@code 400 (Bad Request)} if the embargoDeclaracaoAgravo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/embargo-declaracao-agravos")
    public ResponseEntity<EmbargoDeclaracaoAgravo> createEmbargoDeclaracaoAgravo(@RequestBody EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) throws URISyntaxException {
        log.debug("REST request to save EmbargoDeclaracaoAgravo : {}", embargoDeclaracaoAgravo);
        if (embargoDeclaracaoAgravo.getId() != null) {
            throw new BadRequestAlertException("A new embargoDeclaracaoAgravo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmbargoDeclaracaoAgravo result = embargoDeclaracaoAgravoService.save(embargoDeclaracaoAgravo);
        return ResponseEntity.created(new URI("/api/embargo-declaracao-agravos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /embargo-declaracao-agravos} : Updates an existing embargoDeclaracaoAgravo.
     *
     * @param embargoDeclaracaoAgravo the embargoDeclaracaoAgravo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated embargoDeclaracaoAgravo,
     * or with status {@code 400 (Bad Request)} if the embargoDeclaracaoAgravo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the embargoDeclaracaoAgravo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/embargo-declaracao-agravos")
    public ResponseEntity<EmbargoDeclaracaoAgravo> updateEmbargoDeclaracaoAgravo(@RequestBody EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo) throws URISyntaxException {
        log.debug("REST request to update EmbargoDeclaracaoAgravo : {}", embargoDeclaracaoAgravo);
        if (embargoDeclaracaoAgravo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmbargoDeclaracaoAgravo result = embargoDeclaracaoAgravoService.save(embargoDeclaracaoAgravo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, embargoDeclaracaoAgravo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /embargo-declaracao-agravos} : get all the embargoDeclaracaoAgravos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of embargoDeclaracaoAgravos in body.
     */
    @GetMapping("/embargo-declaracao-agravos")
    public ResponseEntity<List<EmbargoDeclaracaoAgravo>> getAllEmbargoDeclaracaoAgravos(EmbargoDeclaracaoAgravoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmbargoDeclaracaoAgravos by criteria: {}", criteria);
        Page<EmbargoDeclaracaoAgravo> page = embargoDeclaracaoAgravoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /embargo-declaracao-agravos/count} : count all the embargoDeclaracaoAgravos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/embargo-declaracao-agravos/count")
    public ResponseEntity<Long> countEmbargoDeclaracaoAgravos(EmbargoDeclaracaoAgravoCriteria criteria) {
        log.debug("REST request to count EmbargoDeclaracaoAgravos by criteria: {}", criteria);
        return ResponseEntity.ok().body(embargoDeclaracaoAgravoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /embargo-declaracao-agravos/:id} : get the "id" embargoDeclaracaoAgravo.
     *
     * @param id the id of the embargoDeclaracaoAgravo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the embargoDeclaracaoAgravo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/embargo-declaracao-agravos/{id}")
    public ResponseEntity<EmbargoDeclaracaoAgravo> getEmbargoDeclaracaoAgravo(@PathVariable Long id) {
        log.debug("REST request to get EmbargoDeclaracaoAgravo : {}", id);
        Optional<EmbargoDeclaracaoAgravo> embargoDeclaracaoAgravo = embargoDeclaracaoAgravoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(embargoDeclaracaoAgravo);
    }

    /**
     * {@code DELETE  /embargo-declaracao-agravos/:id} : delete the "id" embargoDeclaracaoAgravo.
     *
     * @param id the id of the embargoDeclaracaoAgravo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/embargo-declaracao-agravos/{id}")
    public ResponseEntity<Void> deleteEmbargoDeclaracaoAgravo(@PathVariable Long id) {
        log.debug("REST request to delete EmbargoDeclaracaoAgravo : {}", id);
        embargoDeclaracaoAgravoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
