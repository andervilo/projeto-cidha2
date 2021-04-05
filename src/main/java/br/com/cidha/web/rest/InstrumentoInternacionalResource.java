package br.com.cidha.web.rest;

import br.com.cidha.domain.InstrumentoInternacional;
import br.com.cidha.service.InstrumentoInternacionalService;
import br.com.cidha.web.rest.errors.BadRequestAlertException;
import br.com.cidha.service.dto.InstrumentoInternacionalCriteria;
import br.com.cidha.service.InstrumentoInternacionalQueryService;

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
 * REST controller for managing {@link br.com.cidha.domain.InstrumentoInternacional}.
 */
@RestController
@RequestMapping("/api")
public class InstrumentoInternacionalResource {

    private final Logger log = LoggerFactory.getLogger(InstrumentoInternacionalResource.class);

    private static final String ENTITY_NAME = "instrumentoInternacional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstrumentoInternacionalService instrumentoInternacionalService;

    private final InstrumentoInternacionalQueryService instrumentoInternacionalQueryService;

    public InstrumentoInternacionalResource(InstrumentoInternacionalService instrumentoInternacionalService, InstrumentoInternacionalQueryService instrumentoInternacionalQueryService) {
        this.instrumentoInternacionalService = instrumentoInternacionalService;
        this.instrumentoInternacionalQueryService = instrumentoInternacionalQueryService;
    }

    /**
     * {@code POST  /instrumento-internacionals} : Create a new instrumentoInternacional.
     *
     * @param instrumentoInternacional the instrumentoInternacional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instrumentoInternacional, or with status {@code 400 (Bad Request)} if the instrumentoInternacional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instrumento-internacionals")
    public ResponseEntity<InstrumentoInternacional> createInstrumentoInternacional(@RequestBody InstrumentoInternacional instrumentoInternacional) throws URISyntaxException {
        log.debug("REST request to save InstrumentoInternacional : {}", instrumentoInternacional);
        if (instrumentoInternacional.getId() != null) {
            throw new BadRequestAlertException("A new instrumentoInternacional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstrumentoInternacional result = instrumentoInternacionalService.save(instrumentoInternacional);
        return ResponseEntity.created(new URI("/api/instrumento-internacionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instrumento-internacionals} : Updates an existing instrumentoInternacional.
     *
     * @param instrumentoInternacional the instrumentoInternacional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instrumentoInternacional,
     * or with status {@code 400 (Bad Request)} if the instrumentoInternacional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instrumentoInternacional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instrumento-internacionals")
    public ResponseEntity<InstrumentoInternacional> updateInstrumentoInternacional(@RequestBody InstrumentoInternacional instrumentoInternacional) throws URISyntaxException {
        log.debug("REST request to update InstrumentoInternacional : {}", instrumentoInternacional);
        if (instrumentoInternacional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstrumentoInternacional result = instrumentoInternacionalService.save(instrumentoInternacional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instrumentoInternacional.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instrumento-internacionals} : get all the instrumentoInternacionals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instrumentoInternacionals in body.
     */
    @GetMapping("/instrumento-internacionals")
    public ResponseEntity<List<InstrumentoInternacional>> getAllInstrumentoInternacionals(InstrumentoInternacionalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InstrumentoInternacionals by criteria: {}", criteria);
        Page<InstrumentoInternacional> page = instrumentoInternacionalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instrumento-internacionals/count} : count all the instrumentoInternacionals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/instrumento-internacionals/count")
    public ResponseEntity<Long> countInstrumentoInternacionals(InstrumentoInternacionalCriteria criteria) {
        log.debug("REST request to count InstrumentoInternacionals by criteria: {}", criteria);
        return ResponseEntity.ok().body(instrumentoInternacionalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /instrumento-internacionals/:id} : get the "id" instrumentoInternacional.
     *
     * @param id the id of the instrumentoInternacional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instrumentoInternacional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instrumento-internacionals/{id}")
    public ResponseEntity<InstrumentoInternacional> getInstrumentoInternacional(@PathVariable Long id) {
        log.debug("REST request to get InstrumentoInternacional : {}", id);
        Optional<InstrumentoInternacional> instrumentoInternacional = instrumentoInternacionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instrumentoInternacional);
    }

    /**
     * {@code DELETE  /instrumento-internacionals/:id} : delete the "id" instrumentoInternacional.
     *
     * @param id the id of the instrumentoInternacional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instrumento-internacionals/{id}")
    public ResponseEntity<Void> deleteInstrumentoInternacional(@PathVariable Long id) {
        log.debug("REST request to delete InstrumentoInternacional : {}", id);
        instrumentoInternacionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
